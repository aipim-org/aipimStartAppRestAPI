package org.aipim.web.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.aipim.web.service.dao.CartManager;
import org.aipim.web.service.dao.ProductManager;
import org.aipim.web.service.dao.UserManager;
import org.aipim.web.service.domain.BaseModel;
import org.aipim.web.service.domain.DefaultModel;
import org.aipim.web.service.domain.Request;
import org.aipim.web.service.domain.User;
import org.apache.log4j.Logger;

@Path("/v2")
public class ServiceProviders {
    private final static Logger logger = Logger.getLogger(ServiceProviders.class);

	public final static String TAG_SERVICE_PROVIDER_KEY = "SERVICE_PROVIDER_KEY";
	public final static String TAG_GET_TOKEN = "gettoken";
	public final static String TAG_GET_USERS = "getusers";
	public final static String TAG_SET_USERS = "setusers";
	public final static String TAG_GET_PRODUCTS = "getproducts";
	public final static String TAG_SET_PRODUCTS = "setproducts";
	public final static String TAG_GET_CARTS = "getcarts";
	public final static String TAG_SET_CARTS = "setcarts";
	
	@POST
	@Path("/gettoken")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Request getToken(String json) {
		System.out.println("request ["+ json +"]");
		logger.debug("request ["+ json +"]");
		Request request = new Request(json);
		request.putStringAttr(TAG_SERVICE_PROVIDER_KEY, TAG_GET_TOKEN);
		performRequest(request);
		System.out.println("response ["+ request.toString() +"]");
		logger.debug("response ["+ request.toString() +"]");
		return request;
	}
	
	@POST
	@Path("/getusers")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Request getUsers(String json) {
		System.out.println("request ["+ json +"]");
		Request request = new Request(json);
		request.putStringAttr(TAG_SERVICE_PROVIDER_KEY, TAG_GET_USERS);
		performRequest(request);
		System.out.println("response ["+ request.toString() +"]");
		return request;
	}
	
	@POST
	@Path("/setusers")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Request setUsers(String json) {
		System.out.println("request ["+ json +"]");
		Request request = new Request(json);
		request.putStringAttr(TAG_SERVICE_PROVIDER_KEY, TAG_SET_USERS);
		performRequest(request);
		System.out.println("response ["+ request.toString() +"]");
		return request;
	}
	
	@POST
	@Path("/getproducts")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Request getProducts(String json) {
		System.out.println("request ["+ json +"]");
		Request request = new Request(json);
		request.putStringAttr(TAG_SERVICE_PROVIDER_KEY, TAG_GET_PRODUCTS);
		performRequest(request);
		System.out.println("response ["+ request.toString() +"]");
		return request;
	}
	
	@POST
	@Path("/setproducts")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Request setProducts(String json) {
		System.out.println("request ["+ json +"]");
		Request request = new Request(json);
		request.putStringAttr(TAG_SERVICE_PROVIDER_KEY, TAG_SET_PRODUCTS);
		performRequest(request);
		System.out.println("response ["+ request.toString() +"]");
		return request;
	}
	
	@POST
	@Path("/getcarts")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Request getCarts(String json) {
		System.out.println("request ["+ json +"]");
		Request request = new Request(json);
		request.putStringAttr(TAG_SERVICE_PROVIDER_KEY, TAG_GET_CARTS);
		performRequest(request);
		System.out.println("response ["+ request.toString() +"]");
		return request;
	}
	
	@POST
	@Path("/setcarts")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Request setCarts(String json) {
		System.out.println("request ["+ json +"]");
		Request request = new Request(json);
		request.putStringAttr(TAG_SERVICE_PROVIDER_KEY, TAG_SET_CARTS);
		performRequest(request);
		System.out.println("response ["+ request.toString() +"]");
		return request;
	}
	
	private void performRequest(final Request request) {
		String providerKey = request.getStringAttr(TAG_SERVICE_PROVIDER_KEY);
		
		String token = request.getStringAttr("token");
		boolean staging = request.getBooleanAttr("staging");
		int maxRole = request.getIntAttr("maxRole");
		int minRole = request.getIntAttr("minRole");
		String status = request.getStringAttr("status");
		long ownerUid = request.getLongAttr("ownerUid");
		String typeDescription = request.getStringAttr("typeDescription");
		String subtypeDescription = request.getStringAttr("subtypeDescription");
		String aisle = request.getStringAttr("aisle");
		int limitStartIndex = request.getIntAttr("limitStartIndex");
		int signal = request.getIntAttr("signal"); //stock or updated_at > 72 hours.
		String encodedImages = request.getStringAttr("encodedImages");
		
		BaseModel user = request.getModelAttr("user");
		List<?> users = request.getModelListAttr("users");
		
		BaseModel product = request.getModelAttr("product");
		List<?> products = request.getModelListAttr("products");
		
		BaseModel cart = request.getModelAttr("cart");
		List<?> carts = request.getModelListAttr("carts");

		UserManager userManager = new UserManager(token, staging);
		ProductManager productManager = new ProductManager(token, staging);
		CartManager cartManager = new CartManager(token, staging);
		
		switch (providerKey) {
			case TAG_GET_TOKEN:
				DefaultModel def = new DefaultModel();
				def.initMap(user.toString());
				String uniqueId = def.getStringAttr("uniqueId");
				String password = def.getStringAttr("password");
				String authbind = def.getStringAttr("authbind");
				
				User appUser = new User();
				appUser.setUniqueId(uniqueId);
				appUser.setPassword(password);
				appUser.setAuthbind(authbind);
				userManager.getToken(appUser);
				break;
			case TAG_GET_USERS:
				
				break;
			case TAG_SET_USERS:
				
				break;
			case TAG_GET_PRODUCTS:
				
				break;
			case TAG_SET_PRODUCTS:
				
				break;
			case TAG_GET_CARTS:
				
				break;
			case TAG_SET_CARTS:
				
				break;
		}
	}
}
