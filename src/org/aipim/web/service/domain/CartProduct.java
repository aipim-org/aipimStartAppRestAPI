package org.aipim.web.service.domain;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;

@XmlRootElement
public class CartProduct {
	private final static Logger logger = Logger.getLogger(CartProduct.class);

    private int cartUid;
    private int productUid;
    private int quantity;
    private double price; /* preço efetivado na compra (o produto tem seu preço dinâmico) */
    private double priceSave; /* preço praticado na compra */
    private double shipPrice; /* frete do produto */
    private String shipCode;
    
    public CartProduct() {}
    
    /**
	 * @return the cartUid
	 */
	public int getCartUid() {
		return cartUid;
	}

	/**
	 * @param cartUid the cartUid to set
	 */
	public void setCartUid(int cartUid) {
		this.cartUid = cartUid;
	}

	/**
	 * @return the productUid
	 */
	public int getProductUid() {
		return productUid;
	}

	/**
	 * @param productUid the productUid to set
	 */
	public void setProductUid(int productUid) {
		this.productUid = productUid;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the priceSave
	 */
	public double getPriceSave() {
		return priceSave;
	}

	/**
	 * @param priceSave the priceSave to set
	 */
	public void setPriceSave(double priceSave) {
		this.priceSave = priceSave;
	}

	/**
	 * @return the shipPrice
	 */
	public double getShipPrice() {
		return shipPrice;
	}

	/**
	 * @param shipPrice the shipPrice to set
	 */
	public void setShipPrice(double shipPrice) {
		this.shipPrice = shipPrice;
	}

	/**
	 * @return the shipCode
	 */
	public String getShipCode() {
		return shipCode;
	}

	/**
	 * @param shipCode the shipCode to set
	 */
	public void setShipCode(String shipCode) {
		this.shipCode = shipCode;
	}
	
	@Override
	public String toString() {
		String body = "CartProduct [";
		body += "cartUid=" + cartUid + ", ";
		body += "productUid=" + productUid + ", ";
		body += "quantity=" + quantity + ", ";
		body += "price=" + price + ", ";
		body += "priceSave=" + priceSave + ", ";
		body += "shipPrice=" + shipPrice + ", ";
		body += "shipCode=\"" + shipCode + "\"]";
		
		logger.debug(body);
		return body;
	}
}
