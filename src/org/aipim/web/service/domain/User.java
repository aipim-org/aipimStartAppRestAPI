package org.aipim.web.service.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;

@XmlRootElement
public class User extends BaseModel {
	private final static Logger logger = Logger.getLogger(User.class);

    private int uid;
    private String url;
    private String uniqueId;
    private String name;
    private String label;
    private String email;
    private String alternativeEmail;
    private String authbind;
    private String password;
    private String encryptedPassword;
    private String salt;
    private int role;
    private String address;
    private int stars;
    private double lastLat;
    private double lastLon;
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
	 * @return the uniqueId
	 */
	public String getUniqueId() {
		return uniqueId;
	}

	/**
	 * @param uniqueId the uniqueId to set
	 */
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the alternativeEmail
	 */
	public String getAlternativeEmail() {
		return alternativeEmail;
	}

	/**
	 * @param alternativeEmail the alternativeEmail to set
	 */
	public void setAlternativeEmail(String alternativeEmail) {
		this.alternativeEmail = alternativeEmail;
	}

	/**
	 * @return the authbind
	 */
	public String getAuthbind() {
		return authbind;
	}

	/**
	 * @param authbind the authbind to set
	 */
	public void setAuthbind(String authbind) {
		this.authbind = authbind;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the encryptedPassword
	 */
	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	/**
	 * @param encryptedPassword the encryptedPassword to set
	 */
	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	/**
	 * @return the salt
	 */
	public String getSalt() {
		return salt;
	}

	/**
	 * @param salt the salt to set
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}

	/**
	 * @return the role
	 */
	public int getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(int role) {
		this.role = role;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the stars
	 */
	public int getStars() {
		return stars;
	}

	/**
	 * @param stars the stars to set
	 */
	public void setStars(int stars) {
		this.stars = stars;
	}

	/**
	 * @return the lastLat
	 */
	public double getLastLat() {
		return lastLat;
	}

	/**
	 * @param lastLat the lastLat to set
	 */
	public void setLastLat(double lastLat) {
		this.lastLat = lastLat;
	}

	/**
	 * @return the lastLon
	 */
	public double getLastLon() {
		return lastLon;
	}

	/**
	 * @param lastLon the lastLon to set
	 */
	public void setLastLon(double lastLon) {
		this.lastLon = lastLon;
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

    public User() {}
	
    public String toJson() { return super.toString(); }
    
	@Override
	public String toString() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String _createdAt = createdAt != null ? dateFormat.format(createdAt) : null;
		String _updatedAt = updatedAt != null ? dateFormat.format(updatedAt) : null;
		
		String body = "User [";
		body += "uid=" + uid + ", ";
		body += "url=\"" + url + "\", ";
		body += "uniqueId=\"" + uniqueId + "\", ";
		body += "name=\"" + name + "\", ";
		body += "label=\"" + label + "\", ";
		body += "email=\"" + email + "\", ";
		body += "alternativeEmail=\"" + alternativeEmail + "\", ";
		body += "authbind=\"" + authbind + "\", ";
		body += "password=\"" + password + "\", ";
		body += "encryptedPassword=\"" + encryptedPassword + "\", ";
		body += "salt=\"" + salt + "\", ";
		body += "role=" + role + ", ";
		body += "address=\"" + address + "\", ";
		body += "stars=" + stars + ", ";
		body += "lastLat=" + lastLat + ", ";
		body += "lastLon=" + lastLon + ", ";
		body += "status=\"" + status + "\", ";
		body += "ownerUid=" + ownerUid + ", ";
		body += "createdAt=\"" + _createdAt + "\", ";
		body += "updatedAt=\"" + _updatedAt + "\"]";
		
		logger.debug(body);
		return body;
	}
}
