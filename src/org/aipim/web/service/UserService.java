package org.aipim.web.service;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.aipim.web.service.dao.UserManager;
import org.aipim.web.service.domain.DefaultModel;
import org.aipim.web.service.domain.User;
import org.apache.log4j.Logger;
import org.json.JSONObject;

@Path("/users")
public class UserService {
    private final static Logger logger = Logger.getLogger(UserService.class);
    private UserManager userManager = new UserManager("0", true);
	
    @POST
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public ArrayList<User> getUsers(String json) {
		ArrayList<User> users = new ArrayList<User>();
		String message = "";
		try {
			JSONObject jsonRequest = new JSONObject(json);
			int startUid = (jsonRequest.has("startUid") && (!jsonRequest.isNull("startUid"))) ?
					(jsonRequest.get("startUid") instanceof Integer) ?
							jsonRequest.getInt("startUid") : 0  : 0;
			users = userManager.getUsers(startUid);
		} catch (Exception e) {
			e.printStackTrace();
			message = e.toString();
			logger.error(message);
		}
		return users;
	}
    
    @POST
	@Path("/shopkeepers")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public ArrayList<User> getShopkeepers(String json) {
		ArrayList<User> users = new ArrayList<User>();
		String message = "";
		try {
			JSONObject jsonRequest = new JSONObject(json);
			int startUid = (jsonRequest.has("startUid") && (!jsonRequest.isNull("startUid"))) ?
					(jsonRequest.get("startUid") instanceof Integer) ?
							jsonRequest.getInt("startUid") : 0  : 0;
			users = userManager.getOwnerUsers(startUid);
		} catch (Exception e) {
			e.printStackTrace();
			message = e.toString();
			logger.error(message);
		}
		return users;
	}
    
	@POST
	@Path("/insert")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public User insertUser(String json) {
		User user = new User();
		String message = "";
		try {
			JSONObject jsonRequest = new JSONObject(json);
			String uniqueId = (jsonRequest.has("uniqueId") && (!jsonRequest.isNull("uniqueId"))) ?
					(jsonRequest.get("uniqueId") instanceof String) ?
							jsonRequest.getString("uniqueId") : ""  : "";
			String name = (jsonRequest.has("name") && (!jsonRequest.isNull("name"))) ?
					(jsonRequest.get("name") instanceof String) ?
							jsonRequest.getString("name") : ""  : "";
			String email = (jsonRequest.has("email") && (!jsonRequest.isNull("email"))) ?
					(jsonRequest.get("email") instanceof String) ?
							jsonRequest.getString("email") : ""  : "";
			String alternativeEmail = (jsonRequest.has("alternativeEmail") && (!jsonRequest.isNull("alternativeEmail"))) ?
					(jsonRequest.get("alternativeEmail") instanceof String) ?
							jsonRequest.getString("alternativeEmail") : ""  : "";
			String password = (jsonRequest.has("password") && (!jsonRequest.isNull("password"))) ?
					(jsonRequest.get("password") instanceof String) ?
							jsonRequest.getString("password") : ""  : "";
			String authbind = (jsonRequest.has("authbind") && (!jsonRequest.isNull("authbind"))) ?
					(jsonRequest.get("authbind") instanceof String) ?
							jsonRequest.getString("authbind") : "NONE"  : "NONE";
			
			String encodedImages = (jsonRequest.has("encodedImages") && (!jsonRequest.isNull("encodedImages"))) ?
					(jsonRequest.get("encodedImages") instanceof String) ?
							jsonRequest.getString("encodedImages") : null  : null;
			String encodedUniqueId = UserUtil.getEncodedUniqueId(uniqueId);
			if (encodedImages != null) {
				String url = UserUtil.getSavedFileUrl(encodedImages, encodedUniqueId, "1", true);
				user.setUrl(url);
				System.out.println("user url inserted: [" +user.getUrl()+ "]" + url);
			}
			
			user.setUniqueId(uniqueId);
            user.setName(name);
            user.setEmail(email);
            user.setAlternativeEmail(alternativeEmail);
            UserUtil.setNewPassword(user, password);
            user.setAuthbind(authbind);
            
            if (!userManager.checkUniqueIdExists(uniqueId)) {
				if (userManager.insertUser(user)) {
					user = userManager.getUserByUniqueId(uniqueId);
				} else {
					message = "Some problems occurried with data manager. Try again an a few minutes.";
				}
			} else {
				message = "Already registered.";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = e.toString();
			logger.error(message);
		}
		if (user == null) user = new User();
		//user.setMessage(message);
		return user;
	}
	
	@POST
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public User updateUser(String json) {
		User user = new User();
		String message = "";
		logger.debug(json);
		try {
			JSONObject jsonRequest = new JSONObject(json);
			String uniqueId = (jsonRequest.has("uniqueId") && (!jsonRequest.isNull("uniqueId"))) ?
					(jsonRequest.get("uniqueId") instanceof String) ?
							jsonRequest.getString("uniqueId") : ""  : "";
			String url = (jsonRequest.has("url") && (!jsonRequest.isNull("url"))) ?
					(jsonRequest.get("url") instanceof String) ?
							jsonRequest.getString("url") : ""  : "";
			String name = (jsonRequest.has("name") && (!jsonRequest.isNull("name"))) ?
					(jsonRequest.get("name") instanceof String) ?
							jsonRequest.getString("name") : ""  : "";
			String label = (jsonRequest.has("label") && (!jsonRequest.isNull("label"))) ?
					(jsonRequest.get("label") instanceof String) ?
							jsonRequest.getString("label") : ""  : "";
			String email = (jsonRequest.has("email") && (!jsonRequest.isNull("email"))) ?
					(jsonRequest.get("email") instanceof String) ?
							jsonRequest.getString("email") : ""  : "";
			String alternativeEmail = (jsonRequest.has("alternativeEmail") && (!jsonRequest.isNull("alternativeEmail"))) ?
					(jsonRequest.get("alternativeEmail") instanceof String) ?
							jsonRequest.getString("alternativeEmail") : ""  : "";
			String authbind = (jsonRequest.has("authbind") && (!jsonRequest.isNull("authbind"))) ?
					(jsonRequest.get("authbind") instanceof String) ?
							jsonRequest.getString("authbind") : "NONE"  : "NONE";
			int role = (jsonRequest.has("role") && (!jsonRequest.isNull("role"))) ?
					(jsonRequest.get("role") instanceof Integer) ?
							jsonRequest.getInt("role") : 0  : 0;
			String address = (jsonRequest.has("address") && (!jsonRequest.isNull("address"))) ?
					(jsonRequest.get("address") instanceof String) ?
							jsonRequest.getString("address") : ""  : "";
			int stars = (jsonRequest.has("stars") && (!jsonRequest.isNull("stars"))) ?
					(jsonRequest.get("stars") instanceof Integer) ?
							jsonRequest.getInt("stars") : 0  : 0;
			double lastLat = (jsonRequest.has("lastLat") && (!jsonRequest.isNull("lastLat"))) ?
					(jsonRequest.get("lastLat") instanceof Double) ?
							jsonRequest.getDouble("lastLat") : 0.0  : 0.0;
			double lastLon = (jsonRequest.has("lastLon") && (!jsonRequest.isNull("lastLon"))) ?
					(jsonRequest.get("lastLon") instanceof Double) ?
							jsonRequest.getDouble("lastLon") : 0.0  : 0.0;
			String status = (jsonRequest.has("status") && (!jsonRequest.isNull("status"))) ?
					(jsonRequest.get("status") instanceof String) ?
							jsonRequest.getString("status") : "DISABLE"  : "DISABLE";
			long ownerUid = (jsonRequest.has("ownerUid") && (!jsonRequest.isNull("ownerUid"))) ?
					(jsonRequest.get("ownerUid") instanceof Number) ?
							jsonRequest.getLong("ownerUid") : 0L  : 0L;
			String owner = (jsonRequest.has("owner") && (!jsonRequest.isNull("owner"))) ?
					(jsonRequest.get("owner") instanceof String) ?
							jsonRequest.getString("owner") : ""  : "";
			DefaultModel userOwner = new DefaultModel();
			userOwner.initMap(owner);
			if (ownerUid == 0) ownerUid = userOwner.getUid();
			
			String encodedImages = (jsonRequest.has("encodedImages") && (!jsonRequest.isNull("encodedImages"))) ?
					(jsonRequest.get("encodedImages") instanceof String) ?
							jsonRequest.getString("encodedImages") : null  : null;
			String encodedUniqueId = UserUtil.getEncodedUniqueId(uniqueId);
			if (encodedImages != null) {
				url = UserUtil.getSavedFileUrl(encodedImages, encodedUniqueId, "1", true);
				user.setUrl(url);
				System.out.println("user url: [" +user.getUrl()+ "]" + url);
			}
			user.setUniqueId(uniqueId);
            user.setUrl(url);
            user.setName(name);
            user.setLabel(label);
            user.setEmail(email);
            user.setAlternativeEmail(alternativeEmail);
            user.setAuthbind(authbind);
            user.setRole(role);
            user.setAddress(address);
            user.setStars(stars);
            user.setLastLat(lastLat);
            user.setLastLon(lastLon);
            user.setOwnerUid(Long.valueOf(ownerUid).intValue());
            user.setStatus(status);
            userManager.updateUser(user);
            
		} catch (Exception e) {
			e.printStackTrace();
			message = e.toString();
			logger.error(message);
		}
		return user;
	}
	
}
