package org.aipim.web.service.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;

@XmlRootElement
public class Product {
	private final static Logger logger = Logger.getLogger(Product.class);
	
    private int uid;
    private String url;
    private String label;
    private double lastPrice;
    private double price;
    private int likes;
    private String name;
    private String brand;
    private String typeDescription;
    private String subtypeDescription;
    private String packingType;
    private int packingCapacity;
    private String packingMeasure;
    private String iqCode;
    private String aisleLabel;
    private int aisle;
    private int aisleOrder;
    private int stock;
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
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
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
	 * @return the lastPrice
	 */
	public double getLastPrice() {
		return lastPrice;
	}

	/**
	 * @param lastPrice the lastPrice to set
	 */
	public void setLastPrice(double lastPrice) {
		this.lastPrice = lastPrice;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * @param brand the brand to set
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * @return the typeDescription
	 */
	public String getTypeDescription() {
		return typeDescription;
	}

	/**
	 * @param typeDescription the typeDescription to set
	 */
	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}

	/**
	 * @return the subtypeDescription
	 */
	public String getSubtypeDescription() {
		return subtypeDescription;
	}

	/**
	 * @param subtypeDescription the subtypeDescription to set
	 */
	public void setSubtypeDescription(String subtypeDescription) {
		this.subtypeDescription = subtypeDescription;
	}

	/**
	 * @return the packingType
	 */
	public String getPackingType() {
		return packingType;
	}

	/**
	 * @param packingType the packingType to set
	 */
	public void setPackingType(String packingType) {
		this.packingType = packingType;
	}

	/**
	 * @return the packingCapacity
	 */
	public int getPackingCapacity() {
		return packingCapacity;
	}

	/**
	 * @param packingCapacity the packingCapacity to set
	 */
	public void setPackingCapacity(int packingCapacity) {
		this.packingCapacity = packingCapacity;
	}

	/**
	 * @return the packingMeasure
	 */
	public String getPackingMeasure() {
		return packingMeasure;
	}

	/**
	 * @param packingMeasure the packingMeasure to set
	 */
	public void setPackingMeasure(String packingMeasure) {
		this.packingMeasure = packingMeasure;
	}

	/**
	 * @return the iqCode
	 */
	public String getIqCode() {
		return iqCode;
	}

	/**
	 * @param iqCode the iqCode to set
	 */
	public void setIqCode(String iqCode) {
		this.iqCode = iqCode;
	}

	/**
	 * @return the aisleLabel
	 */
	public String getAisleLabel() {
		return aisleLabel;
	}

	/**
	 * @param aisleLabel the aisleLabel to set
	 */
	public void setAisleLabel(String aisleLabel) {
		this.aisleLabel = aisleLabel;
	}

	/**
	 * @return the aisle
	 */
	public int getAisle() {
		return aisle;
	}

	/**
	 * @param aisle the aisle to set
	 */
	public void setAisle(int aisle) {
		this.aisle = aisle;
	}

	/**
	 * @return the aisleOrder
	 */
	public int getAisleOrder() {
		return aisleOrder;
	}

	/**
	 * @param aisleOrder the aisleOrder to set
	 */
	public void setAisleOrder(int aisleOrder) {
		this.aisleOrder = aisleOrder;
	}
	
	/**
	 * @return the stock
	 */
	public int getStock() {
		return stock;
	}

	/**
	 * @param stock the stock to set
	 */
	public void setStock(int stock) {
		this.stock = stock;
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

    public Product() {}
	
	@Override
	public String toString() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String _createdAt = createdAt != null ? dateFormat.format(createdAt) : null;
		String _updatedAt = updatedAt != null ? dateFormat.format(updatedAt) : null;
		
		String body = "Product [";
		body += "uid=" + uid + ", ";
		body += "url=\"" + url + "\", ";
		body += "label=\"" + label + "\", ";
		body += "lastPrice=" + lastPrice + ", ";
		body += "price=" + price + ", ";
		body += "likes=" + likes + ", ";
		body += "name=\"" + name + "\", ";
		body += "brand=\"" + brand + "\", ";
		body += "typeDescription=\"" + typeDescription + "\", ";
		body += "subtypeDescription=\"" + subtypeDescription + "\", ";
		body += "packingType=\"" + packingType + "\", ";
		body += "packingCapacity=" + packingCapacity + ", ";
		body += "packingMeasure=\"" + packingMeasure + "\", ";
		body += "iqCode=\"" + iqCode + "\", ";
		body += "aisleLabel=\"" + aisleLabel + "\", ";
		body += "aisle=" + aisle + ", ";
		body += "aisleOrder=" + aisleOrder + ", ";
	    body += "stock=" + stock + ", ";
		body += "status=\"" + status + "\", ";
		body += "ownerUid=" + ownerUid + ", ";
		body += "createdAt=\"" + _createdAt + "\", ";
		body += "updatedAt=\"" + _updatedAt + "\"]";
		
		logger.debug(body);
		return body;
	}
}
