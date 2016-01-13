package org.aipim.web.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

public class Config extends Properties {
    /**
	 * 
	 */
	private static final long serialVersionUID = -289638730721530424L;
	private final static Logger logger = Logger.getLogger(Config.class);
    
    public Config() {}
    
    public static Config getInstance() {
    	return getInstance(null, true);
    }
    
    public static Config getInstance(String token, boolean staging) {
    	Config properties = newInstance();
    	if (properties != null) {
    		String driver = properties.getProperty("driver");

        	// # staging and production appToken
        	String stagingAppToken = properties.getProperty("stagingAppToken");
        	String appToken = properties.getProperty("appToken");

        	// # staging(H) environment settings (cross HOMO appToken)
        	String stagingUrlH = properties.getProperty("stagingUrlH");
        	String stagingUserH = properties.getProperty("stagingUserH");
        	String stagingPasswordH = properties.getProperty("stagingPasswordH");
        	// # production environment settings (cross HOMO appToken)
        	String stagingUrl = properties.getProperty("stagingUrl");
        	String stagingUser = properties.getProperty("stagingUser");
        	String stagingPassword = properties.getProperty("stagingPassword");
        	// # staging(H) environment settings (cross PROD appToken)
        	String urlH = properties.getProperty("urlH");
        	String userH = properties.getProperty("userH");
        	String passwordH = properties.getProperty("passwordH");
        	// # production environment settings (cross PROD appToken)
        	String url = properties.getProperty("url");
        	String user = properties.getProperty("user");
        	String password = properties.getProperty("password");

        	// # static (H) assets base url (cross HOMO appToken)
        	String stagingAssetsUrlH = properties.getProperty("stagingAssetsUrlH");
        	// # static assets base url (cross HOMO appToken)
        	String stagingAssetsUrl = properties.getProperty("stagingAssetsUrl");
        	// # static (H) assets base url (cross PROD appToken)
        	String assetsUrlH = properties.getProperty("assetsUrlH");
        	// # static assets base url (cross PROD appToken)
        	String assetsUrl = properties.getProperty("assetsUrl");

        	// # static (H) assets base path (cross HOMO appToken)
        	String stagingAssetsPathH = properties.getProperty("stagingAssetsPathH");
        	// # static assets base path (cross HOMO appToken)
        	String stagingAssetsPath = properties.getProperty("stagingAssetsPath");
        	// # static (H) assets base path (cross PROD appToken)
        	String assetsPathH = properties.getProperty("assetsPathH");
        	// # static assets base path (cross PROD appToken)
        	String assetsPath = properties.getProperty("assetsPath");
    	
        	boolean isStagingAppToken = true;
        	// if passed appToken is invalid or is empty
            if (token != null && (!token.isEmpty()) && token.equals(appToken)) {
            	isStagingAppToken = false;
            } else if (token != null && (!token.isEmpty()) && (!token.equals(appToken)) && (!token.equals(stagingAppToken))) {
            	staging = true; // force change to staging when invalid token was passed
            }
            
            properties.setProperty("appToken", isStagingAppToken ? stagingAppToken : appToken);
            properties.setProperty("driver", isStagingAppToken ? driver : driver);
            properties.setProperty("url", isStagingAppToken ? staging ? stagingUrlH : stagingUrl : staging ? urlH : url);
        	properties.setProperty("user", isStagingAppToken ? staging ? stagingUserH : stagingUser : staging ? userH : user);
        	properties.setProperty("password", isStagingAppToken ? staging ? stagingPasswordH : stagingPassword : staging ? passwordH : password);
        	properties.setProperty("assetsUrl", isStagingAppToken ? staging ? stagingAssetsUrlH : stagingAssetsUrl : staging ? assetsUrlH : assetsUrl);
        	properties.setProperty("assetsPath", isStagingAppToken ? staging ? stagingAssetsPathH : stagingAssetsPath : staging ? assetsPathH : assetsPath);
    	}
    	return properties;
    }
    
    public static Config newInstance() {
    	Config properties = new Config();
    	try {
        	InputStream inputStream = Config.class.getClassLoader().getResourceAsStream("/config.properties");
        	properties.load(inputStream);
    	} catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error(e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.toString());
        }
    	return properties;
    }
    
    public static String getProperty(String key, String token, boolean staging) {
    	return getInstance(token, staging).getProperty(key);
    }
}