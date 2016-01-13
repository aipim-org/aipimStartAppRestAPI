package org.aipim.web.service;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.aipim.web.service.dao.CartProductManager;
import org.aipim.web.service.domain.CartProduct;
import org.apache.log4j.Logger;
import org.json.JSONObject;

@Path("/cart/product")
public class CartProductService {
    private final static Logger logger = Logger.getLogger(CartProductService.class);
    private CartProductManager cartProductManager = new CartProductManager("0", true);
    
    @POST
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public ArrayList<CartProduct> getCartProductsByCart(String json) {
		ArrayList<CartProduct> cartProducts = new ArrayList<CartProduct>();
		String message = "";
		try {
			JSONObject jsonRequest = new JSONObject(json);
			int cartUid = (jsonRequest.has("cartUid") && (!jsonRequest.isNull("cartUid"))) ?
					(jsonRequest.get("cartUid") instanceof Integer) ?
							jsonRequest.getInt("cartUid") : 0  : 0;
			cartProducts = cartProductManager.getCartProductsByCartUid(cartUid);
		} catch (Exception e) {
			e.printStackTrace();
			message = e.toString();
			logger.error(message);
		}
		return cartProducts;
	}
    
	@POST
	@Path("/insert")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public CartProduct insertCartProduct(String json) {
		CartProduct cartProduct = new CartProduct();
		String message = "";
		try {
			JSONObject jsonRequest = new JSONObject(json);
			int cartUid = (jsonRequest.has("cartUid") && (!jsonRequest.isNull("cartUid"))) ?
					(jsonRequest.get("cartUid") instanceof Integer) ?
							jsonRequest.getInt("cartUid") : 0  : 0;
			int productUid = (jsonRequest.has("productUid") && (!jsonRequest.isNull("productUid"))) ?
					(jsonRequest.get("productUid") instanceof Integer) ?
							jsonRequest.getInt("productUid") : 0  : 0;
			int quantity = (jsonRequest.has("quantity") && (!jsonRequest.isNull("quantity"))) ?
					(jsonRequest.get("quantity") instanceof Integer) ?
							jsonRequest.getInt("quantity") : 0  : 0;
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
			
			cartProduct.setCartUid(cartUid);
			cartProduct.setProductUid(productUid);
			cartProduct.setQuantity(quantity);
			cartProduct.setPrice(price);
			cartProduct.setPriceSave(priceSave);
			cartProduct.setShipPrice(shipPrice);
			cartProduct.setShipCode(shipCode);
            
			if (!cartProductManager.insertCartProduct(cartProduct)) {
				cartProduct = new CartProduct();
				message = "Some problems occurried with data manager. Try again an a few minutes.";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = e.toString();
			logger.error(message);
		}
		//cartProduct.setMessage(message);
		return cartProduct;
	}
	
	@POST
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public CartProduct deleteCartProduct(String json) {
		CartProduct cartProduct = new CartProduct();
		String message = "";
		logger.error(json);
		try {
			JSONObject jsonRequest = new JSONObject(json);
			int cartUid = (jsonRequest.has("cartUid") && (!jsonRequest.isNull("cartUid"))) ?
					(jsonRequest.get("cartUid") instanceof Integer) ?
							jsonRequest.getInt("cartUid") : 0  : 0;
			int productUid = (jsonRequest.has("productUid") && (!jsonRequest.isNull("productUid"))) ?
					(jsonRequest.get("productUid") instanceof Integer) ?
							jsonRequest.getInt("productUid") : 0  : 0;
			
			cartProduct.setCartUid(cartUid);
			cartProduct.setProductUid(productUid);
			
            cartProductManager.deleteCartProduct(cartProduct);
            
		} catch (Exception e) {
			e.printStackTrace();
			message = e.toString();
			logger.error(message);
		}
		return cartProduct;
	}
	
}
