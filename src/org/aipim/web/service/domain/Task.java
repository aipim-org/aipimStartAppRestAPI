package org.aipim.web.service.domain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;

@XmlRootElement
public class Task extends DefaultModel {
	private final static Logger logger = Logger.getLogger(Task.class);

	private static String TAG_ATTR_TOKEN = "token";
	private static String TAG_ATTR_STAGING = "staging";
	private static String TAG_ATTR_MESSAGE = "message";
	private static String TAG_ATTR_STATUS = "status";
	private static String TAG_ATTR_REQUEST = "request";
	private static String TAG_ATTR_RESPONSE = "response";
	private static String TAG_ATTR_START_TIME = "startTime";
	private static String TAG_ATTR_END_TIME = "endTime";
	private static String TAG_ATTR_ARCHIVED = "archived";

	public final static int TASK_NOT_ARCHIVED = 0;
	public final static int TASK_ARCHIVED = 1;
	public final static String TASK_STATUS_NOTIFIED = "NOTIFIED";
	public final static String TASK_STATUS_PENDING = "PENDING";
	public final static String TASK_STATUS_RUNNING = "RUNNING";
	public final static String TASK_STATUS_DONE = "DONE";

	/**
	 * @return the token
	 */
	public String getToken() {
		return super.getStringAttr(TAG_ATTR_TOKEN);
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		super.putStringAttr(TAG_ATTR_TOKEN, token);
	}
	
	/**
	 * @return the staging
	 */
	public boolean getStaging() {
		return super.getBooleanAttr(TAG_ATTR_STAGING);
	}

	/**
	 * @param staging
	 *            the staging to set
	 */
	public void setStaging(boolean staging) {
		super.putBooleanAttr(TAG_ATTR_STAGING, staging);
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return super.getStringAttr(TAG_ATTR_MESSAGE);
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		super.putStringAttr(TAG_ATTR_MESSAGE, message);
	}
	
	/**
	 * @return the status
	 */
	public String getStatus() {
		return super.getStringAttr(TAG_ATTR_STATUS);
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		super.putStringAttr(TAG_ATTR_STATUS, status);
	}

	/**
	 * @return the request
	 */
	public String getRequest() {
		return super.getStringAttr(TAG_ATTR_REQUEST);
	}

	/**
	 * @param request
	 *            the request to set
	 */
	public void setRequest(String request) {
		super.putStringAttr(TAG_ATTR_REQUEST, request);
	}

	/**
	 * @return the response
	 */
	public String getResponse() {
		return super.getStringAttr(TAG_ATTR_RESPONSE);
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setResponse(String response) {
		super.putStringAttr(TAG_ATTR_RESPONSE, response);
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return parseDate(super.getStringAttr(TAG_ATTR_START_TIME));
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(Date startTime) {
		super.putStringAttr(TAG_ATTR_START_TIME, dateFormat(startTime));
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return parseDate(super.getStringAttr(TAG_ATTR_END_TIME));
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(Date endTime) {
		super.putStringAttr(TAG_ATTR_END_TIME, dateFormat(endTime));
	}

	/**
	 * @return the archived
	 */
	public int getArchived() {
		return super.getIntAttr(TAG_ATTR_ARCHIVED);
	}

	/**
	 * @param archived
	 *            the archived to set
	 */
	public void setArchived(int archived) {
		super.putIntAttr(TAG_ATTR_ARCHIVED, archived);
	}

	public Task() {
		super();
		setStaging(true);
	}
	
	@Override
	public HashMap<String, Object> getAttrs() {
		return super.getAttrs();
	}

	public static String dateFormat(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return (date != null ? dateFormat.format(date) : null);
	}
	
	public static Date parseDate(String date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date result = null;
		if (date != null) { try { result = dateFormat.parse(date); } catch (ParseException e) { e.printStackTrace(); } }
		return result;
	}
	
	@Override
	public String toString() {
		int i = 0;
		String body = "Task [";
		for (String key : getAttrs().keySet()) {
			i++;
			Object object = getAttrs().get(key);
			body += key + "=";
			//begin
			if (object == null) {
				body += "null";
			} else if (object instanceof String) {
				body += "\"" + object.toString() + "\"";
			} else if (object instanceof Date) {
				body += "\"" + dateFormat((Date)object) + "\"";
			} else {
				body += object.toString();
			}
			//end
			if (i < getAttrs().size())
				body += ", ";
		}
		body += "]";
		logger.debug(body);
		return body;
	}
}
