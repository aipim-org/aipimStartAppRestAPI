package org.aipim.web.service;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.aipim.web.service.dao.CartManager;
import org.aipim.web.service.domain.Cart;
import org.apache.log4j.Logger;
import org.json.JSONObject;

@Path("/carts")
public class CartService {
    private final static Logger logger = Logger.getLogger(CartService.class);
    private CartManager cartManager = new CartManager("0", true);
    
    @POST
	@Path("/user")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public ArrayList<Cart> getCarts(String json) {
		ArrayList<Cart> carts = new ArrayList<Cart>();
		String message = "";
		try {
			JSONObject jsonRequest = new JSONObject(json);
			int ownerUid = (jsonRequest.has("ownerUid") && (!jsonRequest.isNull("ownerUid"))) ?
					(jsonRequest.get("ownerUid") instanceof Integer) ?
							jsonRequest.getInt("ownerUid") : 0  : 0;
			carts = cartManager.getCartsByOwnerUid(ownerUid);
		} catch (Exception e) {
			e.printStackTrace();
			message = e.toString();
			logger.error(message);
		}
		return carts;
	}
    
	@POST
	@Path("/insert")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Cart insertCart(String json) {
		Cart cart = new Cart();
		String message = "";
		try {
			JSONObject jsonRequest = new JSONObject(json);
			String label = (jsonRequest.has("label") && (!jsonRequest.isNull("label"))) ?
					(jsonRequest.get("label") instanceof String) ?
							jsonRequest.getString("label") : ""  : "";
			int likes = (jsonRequest.has("likes") && (!jsonRequest.isNull("likes"))) ?
					(jsonRequest.get("likes") instanceof Integer) ?
							jsonRequest.getInt("likes") : 0  : 0;
			String type = (jsonRequest.has("type") && (!jsonRequest.isNull("type"))) ?
					(jsonRequest.get("type") instanceof String) ?
							jsonRequest.getString("type") : ""  : "";
			double price = (jsonRequest.has("price") && (!jsonRequest.isNull("price"))) ?
					(jsonRequest.get("price") instanceof Double) ?
							jsonRequest.getDouble("price") : 0.0  : 0.0;
			double priceSave = (jsonRequest.has("priceSave") && (!jsonRequest.isNull("priceSave"))) ?
					(jsonRequest.get("priceSave") instanceof Double) ?
							jsonRequest.getDouble("priceSave") : 0.0  : 0.0;
			double shipPrice = (jsonRequest.has("shipPrice") && (!jsonRequest.isNull("shipPrice"))) ?
					(jsonRequest.get("shipPrice") instanceof Double) ?
							jsonRequest.getDouble("shipPrice") : 0.0  : 0.0;
			String shipCode = (jsonRequest.has("shipCode") && (!jsonRequest.isNull("shipCode"))) ?
					(jsonRequest.get("shipCode") instanceof String) ?
							jsonRequest.getString("shipCode") : ""  : "";
			int ownerUid = (jsonRequest.has("ownerUid") && (!jsonRequest.isNull("ownerUid"))) ?
					(jsonRequest.get("ownerUid") instanceof Integer) ?
							jsonRequest.getInt("ownerUid") : 0  : 0;
			
			cart.setLabel(label);
			cart.setLikes(likes);
			cart.setType(type);
			cart.setPrice(price);
			cart.setPriceSave(priceSave);
			cart.setShipPrice(shipPrice);
			cart.setShipCode(shipCode);
			cart.setOwnerUid(ownerUid);
            
			if (cartManager.insertCart(cart)) {
				cart = cartManager.getNewInsertedCartByLabel(label);
			} else {
				message = "Some problems occurried with data manager. Try again an a few minutes.";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = e.toString();
			logger.error(message);
		}
		if (cart == null) cart = new Cart();
		//cart.setMessage(message);
		return cart;
	}
	
	@POST
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Cart updateCart(String json) {
		Cart cart = new Cart();
		String message = "";
		logger.error(json);
		try {
			JSONObject jsonRequest = new JSONObject(json);
			String label = (jsonRequest.has("label") && (!jsonRequest.isNull("label"))) ?
					(jsonRequest.get("label") instanceof String) ?
							jsonRequest.getString("label") : ""  : "";
			int likes = (jsonRequest.has("likes") && (!jsonRequest.isNull("likes"))) ?
					(jsonRequest.get("likes") instanceof Integer) ?
							jsonRequest.getInt("likes") : 0  : 0;
			String type = (jsonRequest.has("type") && (!jsonRequest.isNull("type"))) ?
					(jsonRequest.get("type") instanceof String) ?
							jsonRequest.getString("type") : ""  : "";
			double price = (jsonRequest.has("price") && (!jsonRequest.isNull("price"))) ?
					(jsonRequest.get("price") instanceof Double) ?
							jsonRequest.getDouble("price") : 0.0  : 0.0;
			double priceSave = (jsonRequest.has("priceSave") && (!jsonRequest.isNull("priceSave"))) ?
					(jsonRequest.get("priceSave") instanceof Double) ?
							jsonRequest.getDouble("priceSave") : 0.0  : 0.0;
			double shipPrice = (jsonRequest.has("shipPrice") && (!jsonRequest.isNull("shipPrice"))) ?
					(jsonRequest.get("shipPrice") instanceof Double) ?
							jsonRequest.getDouble("shipPrice") : 0.0  : 0.0;
			String shipCode = (jsonRequest.has("shipCode") && (!jsonRequest.isNull("shipCode"))) ?
					(jsonRequest.get("shipCode") instanceof String) ?
							jsonRequest.getString("shipCode") : ""  : "";
			String status = (jsonRequest.has("status") && (!jsonRequest.isNull("status"))) ?
					(jsonRequest.get("status") instanceof String) ?
							jsonRequest.getString("status") : ""  : "";
			int uid = (jsonRequest.has("uid") && (!jsonRequest.isNull("uid"))) ?
					(jsonRequest.get("uid") instanceof Integer) ?
							jsonRequest.getInt("uid") : 0  : 0;
			
			cart.setLabel(label);
			cart.setLikes(likes);
			cart.setType(type);
			cart.setPrice(price);
			cart.setPriceSave(priceSave);
			cart.setShipPrice(shipPrice);
			cart.setShipCode(shipCode);
			cart.setStatus(status);
			cart.setUid(uid);
			
            cartManager.updateCart(cart);
            
		} catch (Exception e) {
			e.printStackTrace();
			message = e.toString();
			logger.error(message);
		}
		return cart;
	}
	
}
