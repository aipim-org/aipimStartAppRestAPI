package org.aipim.web.service.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;

@XmlRootElement
public class Cart {
	private final static Logger logger = Logger.getLogger(Cart.class);

    private int uid;
    private String label;
    private int likes;
    private String type;
    private double price;
    private double priceSave;
    private double shipPrice;
    private String shipCode;
    private String status;
    private int ownerUid;
    private Date createdAt;
    private Date updatedAt;
    
    /**
	 * @return the uid
	 */
	public int getUid() {
		return uid;
	}

	/**
	 * @param uid the uid to set
	 */
	public void setUid(int uid) {
		this.uid = uid;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	/**
	 * @return the likes
	 */
	public int getLikes() {
		return likes;
	}

	/**
	 * @param likes the likes to set
	 */
	public void setLikes(int likes) {
		this.likes = likes;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
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
	
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the ownerUid
	 */
	public int getOwnerUid() {
		return ownerUid;
	}

	/**
	 * @param ownerUid the ownerUid to set
	 */
	public void setOwnerUid(int ownerUid) {
		this.ownerUid = ownerUid;
	}

	/**
	 * @return the createdAt
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the updatedAt
	 */
	public Date getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * @param updatedAt the updatedAt to set
	 */
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

    public Cart() {}
	
	@Override
	public String toString() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String _createdAt = createdAt != null ? dateFormat.format(createdAt) : null;
		String _updatedAt = updatedAt != null ? dateFormat.format(updatedAt) : null;
		
		String body = "Cart [";
		body += "uid=" + uid + ", ";
		body += "label=\"" + label + "\", ";
		body += "likes=" + likes + ", ";
		body += "type=\"" + type + "\", ";
		body += "price=" + price + ", ";
		body += "priceSave=" + priceSave + ", ";
		body += "shipPrice=" + shipPrice + ", ";
		body += "shipCode=\"" + shipCode + "\", ";
		body += "status=\"" + status + "\", ";
		body += "ownerUid=" + ownerUid + ", ";
		body += "createdAt=\"" + _createdAt + "\", ";
		body += "updatedAt=\"" + _updatedAt + "\"]";
		
		logger.debug(body);
		return body;
	}
}
