package org.aipim.web.service.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class DefaultModel extends BaseModel {
	//private final static Logger logger = Logger.getLogger(DefaultModel.class);

	private static String TAG_ATTR_UID = "uid";
    private static String TAG_ATTR_URL = "url";
    private static String TAG_ATTR_TITLE = "title";
    private static String TAG_ATTR_SUBTITLE = "subtitle";
    private static String TAG_ATTR_OWNER = "owner";
    private static String TAG_ATTR_ENCODED_IMAGES = "encodedImages";

    private List<String> hashTags = new ArrayList<>();
    private List<String> encodedImages = new ArrayList<>();
    private DefaultModel owner = null;

    public static DefaultModel valueOf(Object value) {
        if (value instanceof DefaultModel) {
            return (DefaultModel) value;
        } else if (value instanceof BaseModel) {
            DefaultModel result = new DefaultModel(BaseModel.valueOf(value).getAttrs());
            return result;
        }
        return null;
    }

    public DefaultModel() {
        this(0, "", "untitle", "of empty description");
    }

    public DefaultModel(long uid, String url, String title, String subtitle) {
        super();
        this.setUid(uid);
        this.setUrl(url);
        this.setTitle(title);
        this.setSubtitle(subtitle);
    }

    public DefaultModel(JSONObject attrs) {
        super(attrs);
    }

    public DefaultModel(HashMap<String, Object> attrs) {
        super(attrs);
    }

    protected List<String> getRequiredTags() {
        List<String> requiredTags = new ArrayList<String>();
        requiredTags.add(getTitle());
        requiredTags.add(getSubtitle());
        return requiredTags;
    }

    public long getUid() {
        return super.getLongAttr(TAG_ATTR_UID);
    }

    public void setUid(long uid) {
        super.putLongAttr(TAG_ATTR_UID, uid);
    }

    public String getUrl() {
        return super.getStringAttr(TAG_ATTR_URL);
    }

    public void setUrl(String url) {
        super.putStringAttr(TAG_ATTR_URL, url);
    }

    public String getTitle() {
        return super.getStringAttr(TAG_ATTR_TITLE);
    }

    public void setTitle(String title) {
        super.putStringAttr(TAG_ATTR_TITLE, title);
    }

    public String getSubtitle() {
        return super.getStringAttr(TAG_ATTR_SUBTITLE);
    }

    public void setSubtitle(String subtitle) {
        super.putStringAttr(TAG_ATTR_SUBTITLE, subtitle);
    }

    public DefaultModel getOwner() {
        if (this.owner == null) {
            String owner = super.getStringAttr(TAG_ATTR_OWNER);
            if (owner != null) this.setOwner(owner);
        }
        return this.owner;
    }
    
    @Override
	public String getStringAttr(String key) {
        return super.getStringAttr(key);
    }

    public void setOwner(String jsonOwner) {
        try {this.owner = new DefaultModel(new JSONObject(jsonOwner)); } catch (JSONException e) { e.printStackTrace(); }
        this.setOwner(this.owner);
    }

    public void setOwner(DefaultModel owner) {
        this.owner = owner;
        super.putStringAttr(TAG_ATTR_OWNER, owner != null ? owner.toString() : null);
    }

    public void clearHashTags() {
        this.hashTags.clear();
    }

    public void addHashTag(String hashTag) {
        if (hashTag != null && (!hashTag.isEmpty())) this.hashTags.add(hashTag);
    }

    public void clearEncodedImages() { this.encodedImages.clear(); }

    public void addEncodedImage(String encodedImage) {
        if (encodedImage != null && (!encodedImage.isEmpty())) {
            this.encodedImages.add(encodedImage);
            JSONArray array = new JSONArray(this.encodedImages);
            this.putStringAttr(TAG_ATTR_ENCODED_IMAGES, array.toString());
        }
    }
    
    public List<String> getEncodedImages() {
        String encodedImages =  this.getStringAttr(TAG_ATTR_ENCODED_IMAGES);
        if (encodedImages != null && (!encodedImages.isEmpty())) {
            JSONArray array = null;
            try { array = new JSONArray(encodedImages); } catch (JSONException e) { e.printStackTrace(); }
            if (array != null) {
                for (int i = 0; i < array.length(); i++) {
                    String encodedImage = array.optString(i);
                    this.addEncodedImage(encodedImage);
                }
            }
        }
        return this.encodedImages;
    }

    public List<String> getHashTags() {
        List<String> tags = new ArrayList<String>();
        for (int i = 0; i < this.hashTags.size(); i++) {
            tags.add(this.hashTags.get(i));
        }
        List<String> requiredTags = getRequiredTags();
        for (int i = 0; i < requiredTags.size(); i++) {
            tags.add(requiredTags.get(i));
        }
        return tags;
    }

	@Override
	public String toString() {
		return super.toString();
	}
}
