package org.aipim.web.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.aipim.web.service.dao.UserManager;
import org.aipim.web.service.domain.User;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

@Path("/")
public class ROOTService {
    private final static Logger logger = Logger.getLogger(ROOTService.class);
	
	@POST
	@Path("/gettoken")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public User getToken(String json) {
		System.out.println("gettoken: request["+ json +"]");
		String token = null;
		boolean staging = true;
		User user = new User();
		JSONObject request = null;
		JSONObject jsonUser = null;
		if (json != null && (!json.isEmpty()))
			try { request = new JSONObject(json); } catch (JSONException e) { e.printStackTrace(); logger.error(e.toString()); }
		if (request != null) {
			if (request.has("token") && (!request.isNull("token"))) {
				try { token = request.getString("token"); } catch (JSONException e) { e.printStackTrace(); }
    		}
			if (request.has("staging") && (!request.isNull("staging"))) {
				try { staging = request.getBoolean("staging"); } catch (JSONException e) { e.printStackTrace(); }
    		}
			if (request.has("user") && (!request.isNull("user"))) {
				try { jsonUser = request.getJSONObject("user"); } catch (JSONException e) { e.printStackTrace(); }
    		}
		}
		UserManager userManager = new UserManager(token, staging);
		
		if (jsonUser != null) {
			try {
				String uniqueId = (jsonUser.has("uniqueId") && (!jsonUser.isNull("uniqueId"))) ?
						(jsonUser.get("uniqueId") instanceof String) ?
								jsonUser.getString("uniqueId") : ""  : "";
				String password = (jsonUser.has("password") && (!jsonUser.isNull("password"))) ?
						(jsonUser.get("password") instanceof String) ?
								jsonUser.getString("password") : ""  : "";
				String authbind = (jsonUser.has("authbind") && (!jsonUser.isNull("authbind"))) ?
						(jsonUser.get("authbind") instanceof String) ?
								jsonUser.getString("authbind") : ""  : "";
				String name = (jsonUser.has("name") && (!jsonUser.isNull("name"))) ?
						(jsonUser.get("name") instanceof String) ?
								jsonUser.getString("name") : ""  : "";
				String email = (jsonUser.has("email") && (!jsonUser.isNull("email"))) ?
						(jsonUser.get("email") instanceof String) ?
								jsonUser.getString("email") : ""  : "";
				if ((!uniqueId.isEmpty()) && (!password.isEmpty()) && userManager.checkCredentials(uniqueId, password)) {
					user = userManager.getUserByUniqueId(uniqueId);
				} else if ((!uniqueId.isEmpty()) && (!authbind.isEmpty())) {
					if (userManager.checkAuthBindUniqueIdExists(uniqueId, authbind)) {
						user = userManager.getUserByUniqueId(uniqueId);
						user.setName(name);
						user.setEmail(email);
						userManager.updateUser(user);
					} else if (userManager.checkUniqueIdExists(uniqueId)) {
						user = userManager.getUserByUniqueId(uniqueId);
						user.setAuthbind(authbind);
						user.setName(name);
						user.setEmail(email);
						userManager.updateUser(user);
					} else {
						user.setUniqueId(uniqueId);
						user.setName(name);
						user.setEmail(email);
						user.setAlternativeEmail(email);
						UserUtil.setNewPassword(user, "fOsSoF" + authbind);
						if (userManager.insertUser(user)) {
							user = userManager.getUserByUniqueId(uniqueId);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.toString());
			}
		}
		System.out.println("response: "+ user.toString());
		return user;
	}
	
}
