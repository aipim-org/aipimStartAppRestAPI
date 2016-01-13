package org.aipim.web.service;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.aipim.web.service.dao.ProductManager;
import org.aipim.web.service.domain.DefaultModel;
import org.aipim.web.service.domain.Product;
import org.apache.log4j.Logger;
import org.json.JSONObject;

@Path("/products")
public class ProductService {
    private final static Logger logger = Logger.getLogger(ProductService.class);
	ProductManager productManager = new ProductManager("0", true);
	
	@GET
	@Path("/{ping}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public ArrayList<Product> getPingProducts(String ping) {
		ArrayList<Product> products = productManager.getProducts(0, 0);
		if (products.size() > 0) {
			products = new ArrayList<Product>(products.subList(0, products.size() > 100 ? 100 : products.size()));
		}
		logger.debug("Showing [0,0]");
		return products;
	}
	
	@POST
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public ArrayList<Product> getProducts(String json) {
		ArrayList<Product> products = new ArrayList<Product>();
		String message = "";
		try {
			JSONObject jsonRequest = new JSONObject(json);
			int startUid = (jsonRequest.has("startUid") && (!jsonRequest.isNull("startUid"))) ?
					(jsonRequest.get("startUid") instanceof Integer) ?
							jsonRequest.getInt("startUid") : 0  : 0;
			products = productManager.getProducts(0, startUid);
		} catch (Exception e) {
			e.printStackTrace();
			message = e.toString();
			logger.error(message);
		}
		return products;
	}
	
	@POST
	@Path("/shopkeeper")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public ArrayList<Product> getProductsByOwner(String json) {
		ArrayList<Product> products = new ArrayList<Product>();
		String message = "";
		try {
			JSONObject jsonRequest = new JSONObject(json);
			int ownerUid = (jsonRequest.has("ownerUid") && (!jsonRequest.isNull("ownerUid"))) ?
					(jsonRequest.get("ownerUid") instanceof Integer) ?
							jsonRequest.getInt("ownerUid") : -1  : -1;
			int startUid = (jsonRequest.has("startUid") && (!jsonRequest.isNull("startUid"))) ?
					(jsonRequest.get("startUid") instanceof Integer) ?
							jsonRequest.getInt("startUid") : 0  : 0;
			products = productManager.getProducts(ownerUid, startUid);
		} catch (Exception e) {
			e.printStackTrace();
			message = e.toString();
			logger.error(message);
		}
		return products;
	}
	
	@POST
	@Path("/insert")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Product insertProduct(String json) {
		Product product = new Product();
		try {
			JSONObject jsonRequest = new JSONObject(json);
			if (jsonRequest.has("url") && (!jsonRequest.isNull("url"))) product.setUrl(jsonRequest.getString("url"));
			if (jsonRequest.has("label") && (!jsonRequest.isNull("label"))) product.setLabel(jsonRequest.getString("label"));
			if (jsonRequest.has("lastPrice") && (!jsonRequest.isNull("lastPrice"))) product.setLastPrice(Double.valueOf(jsonRequest.getDouble("lastPrice")).floatValue());
			if (jsonRequest.has("price") && (!jsonRequest.isNull("price"))) product.setPrice(Double.valueOf(jsonRequest.getDouble("price")).floatValue());
			if (jsonRequest.has("likes") && (!jsonRequest.isNull("likes"))) product.setLikes(jsonRequest.getInt("likes"));
			if (jsonRequest.has("name") && (!jsonRequest.isNull("name"))) product.setName(jsonRequest.getString("name"));
			if (jsonRequest.has("brand") && (!jsonRequest.isNull("brand"))) product.setBrand(jsonRequest.getString("brand"));
			if (jsonRequest.has("typeDescription") && (!jsonRequest.isNull("typeDescription"))) product.setTypeDescription(jsonRequest.getString("typeDescription"));
			if (jsonRequest.has("subtypeDescription") && (!jsonRequest.isNull("subtypeDescription"))) product.setSubtypeDescription(jsonRequest.getString("subtypeDescription"));
			if (jsonRequest.has("packingType") && (!jsonRequest.isNull("packingType"))) product.setPackingType(jsonRequest.getString("packingType"));
			if (jsonRequest.has("packingCapacity") && (!jsonRequest.isNull("packingCapacity"))) product.setPackingCapacity(jsonRequest.getInt("packingCapacity"));
			if (jsonRequest.has("packingMeasure") && (!jsonRequest.isNull("packingMeasure"))) product.setPackingMeasure(jsonRequest.getString("packingMeasure"));
			if (jsonRequest.has("iqCode") && (!jsonRequest.isNull("iqCode"))) product.setIqCode(jsonRequest.getString("iqCode"));
			if (jsonRequest.has("aisleLabel") && (!jsonRequest.isNull("aisleLabel"))) product.setAisleLabel(jsonRequest.getString("aisleLabel"));
			if (jsonRequest.has("aisle") && (!jsonRequest.isNull("aisle"))) product.setAisle(jsonRequest.getInt("aisle"));
			if (jsonRequest.has("aisleOrder") && (!jsonRequest.isNull("aisleOrder"))) product.setAisleOrder(jsonRequest.getInt("aisleOrder"));
			if (jsonRequest.has("stock") && (!jsonRequest.isNull("stock"))) product.setStock(jsonRequest.getInt("stock"));
			if (jsonRequest.has("status") && (!jsonRequest.isNull("status"))) product.setStatus(jsonRequest.getString("status"));
			if (jsonRequest.has("ownerUid") && (!jsonRequest.isNull("ownerUid"))) product.setOwnerUid(jsonRequest.getInt("ownerUid"));
			
			if (productManager.insertProduct(product)) {
				product = productManager.getNewInsertedProductByLabel(product.getLabel());
			}
			if (product  == null) product = new Product();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
		}
		return product;
	}
	
	@POST
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Product updateProduct(String json) {
		Product product = new Product();
		try {
			JSONObject jsonRequest = new JSONObject(json);
			if (jsonRequest.has("uid") && (!jsonRequest.isNull("uid"))) product.setUid(jsonRequest.getInt("uid"));
			if (jsonRequest.has("label") && (!jsonRequest.isNull("label"))) product.setLabel(jsonRequest.getString("label"));
			if (jsonRequest.has("lastPrice") && (!jsonRequest.isNull("lastPrice"))) product.setLastPrice(Double.valueOf(jsonRequest.getDouble("lastPrice")).floatValue());
			if (jsonRequest.has("price") && (!jsonRequest.isNull("price"))) product.setPrice(Double.valueOf(jsonRequest.getDouble("price")).floatValue());
			if (jsonRequest.has("likes") && (!jsonRequest.isNull("likes"))) product.setLikes(jsonRequest.getInt("likes"));
			if (jsonRequest.has("name") && (!jsonRequest.isNull("name"))) product.setName(jsonRequest.getString("name"));
			if (jsonRequest.has("brand") && (!jsonRequest.isNull("brand"))) product.setBrand(jsonRequest.getString("brand"));
			if (jsonRequest.has("typeDescription") && (!jsonRequest.isNull("typeDescription"))) product.setTypeDescription(jsonRequest.getString("typeDescription"));
			if (jsonRequest.has("subtypeDescription") && (!jsonRequest.isNull("subtypeDescription"))) product.setSubtypeDescription(jsonRequest.getString("subtypeDescription"));
			if (jsonRequest.has("packingType") && (!jsonRequest.isNull("packingType"))) product.setPackingType(jsonRequest.getString("packingType"));
			if (jsonRequest.has("packingCapacity") && (!jsonRequest.isNull("packingCapacity"))) product.setPackingCapacity(jsonRequest.getInt("packingCapacity"));
			if (jsonRequest.has("packingMeasure") && (!jsonRequest.isNull("packingMeasure"))) product.setPackingMeasure(jsonRequest.getString("packingMeasure"));
			if (jsonRequest.has("iqCode") && (!jsonRequest.isNull("iqCode"))) product.setIqCode(jsonRequest.getString("iqCode"));
			if (jsonRequest.has("aisleLabel") && (!jsonRequest.isNull("aisleLabel"))) product.setAisleLabel(jsonRequest.getString("aisleLabel"));
			if (jsonRequest.has("aisle") && (!jsonRequest.isNull("aisle"))) product.setAisle(jsonRequest.getInt("aisle"));
			if (jsonRequest.has("aisleOrder") && (!jsonRequest.isNull("aisleOrder"))) product.setAisleOrder(jsonRequest.getInt("aisleOrder"));
			if (jsonRequest.has("stock") && (!jsonRequest.isNull("stock"))) product.setStock(jsonRequest.getInt("stock"));
			if (jsonRequest.has("status") && (!jsonRequest.isNull("status"))) product.setStatus(jsonRequest.getString("status"));
			
			String url = (jsonRequest.has("url") && (!jsonRequest.isNull("url"))) ?
					(jsonRequest.get("url") instanceof String) ?
							jsonRequest.getString("url") : ""  : "";
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
			String encodedUniqueId = UserUtil.getEncodedUniqueId("product" + product.getUid());
			if (encodedImages != null) {
				url = UserUtil.getSavedFileUrl(encodedImages, encodedUniqueId, "1", true);
				product.setUrl(url);
				System.out.println("user url: [" +product.getUrl()+ "]" + url);
			}
			product.setUrl(url);
			product.setOwnerUid(Long.valueOf(ownerUid).intValue());
			
			if (productManager.updateProduct(product)) {
				product = productManager.getProductById(product.getUid());
			}
			if (product  == null) product = new Product();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
		}
		return product;
	}
	
}
