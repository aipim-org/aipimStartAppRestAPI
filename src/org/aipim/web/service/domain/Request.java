package org.aipim.web.service.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@XmlRootElement
public class Request extends BaseModel {
	private final static Logger logger = Logger.getLogger(Request.class);

	public Request() { super(); }

	public Request(JSONObject json) { super(json); }
	
	public Request(String source) { super();initMap(source); }
	
	public Request(HashMap<String, Object> map) { super(map); }
	
	public JSONObject toJson() {
		JSONObject result = (new JSONObject(this.getAttrs())); 
		logger.debug(result.toString());
		return result;
	}
	
    private static String getUniformedValues(String key, Request request) {
    	String body = (!key.isEmpty()) ? (key + " [") : "[";
    	int count = 0;
    	if (request != null) {
    		JSONObject jsonRequest = request.toJson();
    		@SuppressWarnings("unchecked")
    		Iterator<String> mapKeys = jsonRequest.keys();
            while (mapKeys.hasNext()) {
                String mapKey = mapKeys.next();
                if (mapKey != null && (!mapKey.isEmpty())) {
                    Object object = null;
                    try { object = jsonRequest.get(mapKey); } catch (JSONException e) { e.printStackTrace(); }
                    if (object != null) {
                    	//System.out.println("test#"+count+"::"+ mapKey);
                    	if (count > 0) body += ", ";
                    	body += getUniformedValue(mapKey, object);
                    	count++;
                    }
                }
            }
    	}
    	body += "]";
    	logger.debug(body);
    	return body;
    }
    
    private static String getUniformedValues(String key, JSONArray objects) {
    	String body = key + " [";
    	if (objects != null) {
    		for (int i = 0; i < objects.length(); i++) {
    			Object object = null;
    			try { object = objects.get(i); } catch (JSONException e) { e.printStackTrace(); }
    			if (object != null) {
                	if (i > 0) body += ", ";
                	body += getUniformedValue(Integer.toString(i), object);
                }
    		}
    	}
    	body += "]";
    	logger.debug(body);
    	return body;
    }
    
    private static String getUniformedValue(String key, Object object) {
    	String body = "";
    	if (key != null && (!key.isEmpty()) && object != null) {
    		if (object instanceof String) {
    			body += key + "=\"" + (String) object + "\"";
    		} else if (object instanceof JSONObject) {
				Request child = new Request((JSONObject)object);
    			body += key + "=" + child.toString("");
			} else if (object instanceof JSONArray) {
				body += key + "=" + getUniformedValues("requests", (JSONArray) object);
			} else if (object instanceof Double) {
    			body += key + "=" + Double.valueOf((Double)object).toString();
			} else if (object instanceof Float) {
    			body += key + "=" + Float.valueOf((Float)object).toString();
			} else if (object instanceof Long) {
    			body += key + "=" + Long.valueOf((Long)object).toString();
			} else if (object instanceof Integer) {
    			body += key + "=" + Integer.valueOf((Integer)object).toString();
			} else if (object instanceof Date) {
    			body += key + "=" + dateFormat((Date)object);
			} else {
    			body += key + "=" + object.toString();
			}
    		//body += key + "=" + "";
    	}
    	return body;
    }
    
    private static String dateFormat(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return (date != null ? dateFormat.format(date) : null);
	}
    
    public String getStringAttr(String key) {
        return super.getStringAttr(key);
    }
    
    public void putStringAttr(String key, String value) {
        super.putStringAttr(key, value);
    }
    
    public boolean getBooleanAttr(String key) {
        return super.getBooleanAttr(key);
    }
    
    public void putBooleanAttr(String key, boolean value) {
        super.putBooleanAttr(key, value);
    }
    
    public int getIntAttr(String key) {
        return super.getIntAttr(key);
    }
    
    public void putIntAttr(String key, int value) {
        super.putIntAttr(key, value);
    }
    
    public long getLongAttr(String key) {
        return super.getLongAttr(key);
    }
    
    public void putLongAttr(String key, long value) {
        super.putLongAttr(key, value);
    }
    
    public double getDoubleAttr(String key) {
        return super.getDoubleAttr(key);
    }
    
    public void putDoubleAttr(String key, double value) {
        super.putDoubleAttr(key, value);
    }
    
    public BaseModel getModelAttr(String key) {
    	BaseModel model = new BaseModel();
    	String json = getStringAttr(key);
    	model.initMap(json);
    	return model;
    }
    
    public List<BaseModel> getModelListAttr(String key) {
    	List<BaseModel> list = new ArrayList<>();
    	String json = getStringAttr(key);
    	JSONArray array = null;
    	if (json == null) return list;
    	try { array = new JSONArray(json); } catch (JSONException e) { e.printStackTrace(); }
    	if (array != null) {
    		for (int i = 0; i < array.length(); i++) {
    			Object object = null;
    			try { object = array.get(i); } catch (JSONException e) { e.printStackTrace(); }
    			if (object != null && object instanceof String) {
    				BaseModel model = new BaseModel();
    				model.initMap((String) object);
    				list.add(model);
    			}
    		}
    	}
    	return list;
    }
    
	@Override
	public String toString() {
		return toString("Request");
	}
	
	public String toString(String name) {
		if (name == null) name = "";
		return getUniformedValues(name, this);
	}
}
