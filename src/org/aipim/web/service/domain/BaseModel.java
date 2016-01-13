package org.aipim.web.service.domain;

import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class BaseModel {
	private final static Logger logger = Logger.getLogger(BaseModel.class);

    private HashMap<String, Object> attrs = new HashMap<>();

    protected static BaseModel valueOf(Object value) {
        if (value instanceof BaseModel) {
            return (BaseModel) value;
        }
        return null;
    }

    private static Boolean toBoolean(Object value) {
    	if (value == null) return null;
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value instanceof String) {
            String stringValue = (String) value;
            if ("true".equalsIgnoreCase(stringValue)) {
                return true;
            } else if ("false".equalsIgnoreCase(stringValue)) {
                return false;
            }
        }
        return null;
    }

    private static Double toDouble(Object value) {
    	if (value == null) return null;
        if (value instanceof Double) {
            return (Double) value;
        } else if (value instanceof Number) {
            return ((Number) value).doubleValue();
        } else if (value instanceof String) {
            try {
                return Double.valueOf((String) value);
            } catch (NumberFormatException ignored) {
            }
        }
        return null;
    }

    private static Integer toInteger(Object value) {
    	if (value == null) return null;
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof Number) {
            return ((Number) value).intValue();
        } else if (value instanceof String) {
            try {
                return (int) Double.parseDouble((String) value);
            } catch (NumberFormatException ignored) {
            }
        }
        return null;
    }

    private static Long toLong(Object value) {
    	if (value == null) return null;
        if (value instanceof Long) {
            return (Long) value;
        } else if (value instanceof Number) {
            return ((Number) value).longValue();
        } else if (value instanceof String) {
            try {
                return (long) Double.parseDouble((String) value);
            } catch (NumberFormatException ignored) {
            }
        }
        return null;
    }

    private static String toString(Object value) {
    	if (value == null) return null;
        if (value instanceof String) {
            return (String) value;
        } else {
            return String.valueOf(value);
        }
    }

	public BaseModel() {
        initMap(new HashMap<String, Object>());
    }

	public BaseModel(JSONObject attrs) {
        initMap(attrs);
	}

    public BaseModel(HashMap<String, Object> attrs) {
        initMap(attrs);
    }

    public void initMap(String json) {
        JSONObject map = null;
        if (json != null) {
            try { map = new JSONObject(json); } catch (JSONException e) { e.printStackTrace(); }
        }
        initMap(map);
    }

    @SuppressWarnings("unchecked")
	public void initMap(JSONObject map) {
        if (map != null) {
            Iterator<String> keys = map.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                if (key != null && (!key.isEmpty())) {
                    Object object = null;
                    try { object = map.get(key); } catch (JSONException e) { e.printStackTrace(); }
                    this.putAttr(key, object);
                }
            }
        }
    }

    /*public void initMap(List<String> keys, Object... objects) {
        HashMap<String, Object> map = new HashMap<>();
        int i = 0;
        for (String key : keys) {
            i++;
            Object object = i < objects.length ? objects[i] : null;
            map.put(key, object);
        }
        initMap(map);
    }*/

    public void initMap(HashMap<String, Object> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        this.getAttrs().putAll(map);
    }

    protected String getStringAttr(String key) {
        Object object = this.getAttr(key);
        return toString(object);
    }

    protected long getLongAttr(String key) {
        Object object = this.getAttr(key);
        Long result = toLong(object);
        return result != null ? result : 0L;
    }

    protected int getIntAttr(String key) {
        Object object = this.getAttr(key);
        Integer result = toInteger(object);
        return result != null ? result : 0;
    }

    protected double getDoubleAttr(String key) {
        Object object = this.getAttr(key);
        Double result = toDouble(object);
        return result != null ? result : 0.0;
    }

    protected boolean getBooleanAttr(String key) {
        Object object = this.getAttr(key);
        Boolean result = toBoolean(object);
        return result != null ? result : false;
    }

    protected void putStringAttr(String key, String value) {
        this.putAttr(key, value);
    }

    protected void putLongAttr(String key, long value) {
        this.putAttr(key, Long.valueOf(value));
    }

    protected void putIntAttr(String key, int value) {
        this.putAttr(key, Integer.valueOf(value));
    }

    protected void putDoubleAttr(String key, double value) {
        this.putAttr(key, Double.valueOf(value));
    }

    protected void putBooleanAttr(String key, boolean value) {
        this.putAttr(key, Boolean.valueOf(value));
    }

    private Object getAttr(String key) {
        return this.getAttrs().containsKey(key) ?
                this.getAttrs().get(key) : null;
    }

    private void putAttr(String key, Object object) {
        this.getAttrs().put(key, object);
    }

    protected HashMap<String, Object> getAttrs() {
        if (this.attrs == null) this.attrs = new HashMap<>();
        return this.attrs;
    }

	@Override
	public String toString() {
		String result = (new JSONObject(this.getAttrs())).toString(); 
		logger.debug(result);
		return result;
	}
}
