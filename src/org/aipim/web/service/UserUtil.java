package org.aipim.web.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import org.aipim.web.service.domain.User;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;

public class UserUtil {
    private final static Logger logger = Logger.getLogger(UserUtil.class);

    public static HashMap<String, String> getNewSecurityPassword() {
    	return getNewSecurityPassword(null);
    }
    
    public static HashMap<String, String> getNewSecurityPassword(User user) {
		HashMap<String, String> newSecurity = new HashMap<String, String>();
    	try {
    		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed((Integer.toString(1001)).getBytes());
            String sgen = Integer.toString(random.nextInt());
            int endIndex = (sgen.length() <= 10 ? sgen.length() : 10);
            sgen = sgen.substring(0, endIndex);
            newSecurity.put("password", sgen);
            
    		HashMap<String, String> shash = encryptedPassword(sgen);
    		newSecurity.put("salt", shash.get("salt"));
    		newSecurity.put("encryptedPassword", shash.get("password"));
    		
    		if (user != null) {
    			user.setSalt(newSecurity.get("salt"));
    			user.setEncryptedPassword(newSecurity.get("encryptedPassword"));
    		}
    	} catch (Exception e) {
    		logger.error(e.toString());
    	}
    	return newSecurity;
    }
    
    public static boolean setNewPassword(final User user, final String newPassword) {
    	boolean checked = false;
    	try {
    		if (user != null) {
        		HashMap<String, String> shash = encryptedPassword(newPassword);
        		user.setPassword(newPassword);
        		user.setSalt(shash.get("salt"));
        		user.setEncryptedPassword(shash.get("password"));
        		checked = true;
        	}
    	} catch (Exception e) {
    		logger.error(e.toString());
    	}
    	
    	return checked;
    }
    
    public static boolean checkPassword(User user, String password) {
    	boolean checked = false;
    	try {
    		if (user != null) {
        		String ssalt = user.getSalt();
        		String spass = user.getEncryptedPassword();
        		checked = checkPassword(ssalt, spass, password);
        	}
    	} catch (Exception e) {
    		logger.error(e.toString());
    	}
    	
    	return checked;
    }
    
    /**
    * Checking password
    * @param ssalt, spass, password
    * returns boolean
    */
    public static boolean checkPassword(String ssalt, String spass, String password) {
        boolean checked = false;
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA1");
            digest.reset();
            digest.update(ssalt.getBytes("UTF-8"));
            byte[] encryptedPasswordBytes = digest.digest(password.getBytes("UTF-8"));
            
            //String encryptedPassword = Base64.getEncoder().encodeToString(encryptedPasswordBytes); java.util.Base64
            String encryptedPassword = Base64.encodeBase64String(encryptedPasswordBytes);
            int endIndex = (encryptedPassword.length() <= 80 ? encryptedPassword.length() : 80);
            encryptedPassword = encryptedPassword.substring(0, endIndex);
            checked = (spass.compareTo(encryptedPassword) == 0);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return checked;
    }
    
    /**
     * Encrypting password
     * @param password
     * returns salt and encrypted password
     */
     public static HashMap<String, String> encryptedPassword(String password) {
         HashMap<String, String> hash = new HashMap<String, String>();

         try {
             SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
             random.setSeed((Integer.toString(1001)).getBytes());
             String salt = Integer.toString(random.nextInt());
             int endIndex = (salt.length() <= 10 ? salt.length() : 10);
             salt = salt.substring(0, endIndex);
             hash.put("salt", salt);
  
             MessageDigest digest = null;
             digest = MessageDigest.getInstance("SHA1");
             digest.reset();
             digest.update(salt.getBytes("UTF-8"));
             byte[] encryptedPasswordBytes = digest.digest(password.getBytes("UTF-8"));
             //String encryptedPassword = Base64.getEncoder().encodeToString(encryptedPasswordBytes);
             String encryptedPassword = Base64.encodeBase64String(encryptedPasswordBytes);
             endIndex = (encryptedPassword.length() <= 80 ? encryptedPassword.length() : 80);
             encryptedPassword = encryptedPassword.substring(0, endIndex);
             hash.put("password", encryptedPassword);	
         } catch (NoSuchAlgorithmException e) {
             e.printStackTrace();
         } catch (UnsupportedEncodingException e) {
             e.printStackTrace();
         }
         return hash;
     }
     
     /**
      * Get encoded uniqueId String
      * @param uniqueId
      * returns encoded uniqueId
      */
      public static String getEncodedUniqueId(String uniqueId) {
    	  String result = null;
    	  try {
    		  byte[] uniqueIdBytes = uniqueId.getBytes("UTF-8");
              result = Base64.encodeBase64String(uniqueIdBytes);
          } catch (UnsupportedEncodingException e) {
              e.printStackTrace();
          }
          return result.replaceAll("=", "");
      }
      
      /**
       * Get Saved File Url
       * @param encodedImages
       * returns saved url
       */
       public static String getSavedFileUrl(String encodedImages, String uniqueIdDir, String _appToken, boolean _staging) {
     	  String savedUrl = null;
           JSONArray array = null;
           try { array = new JSONArray(encodedImages); } catch (JSONException e) { e.printStackTrace(); }
           if (array != null) {
         	  for (int i = 0; i < array.length(); i++) {
         		  String encodedImage = array.optString(i);
         		  byte[] jpegImage = Base64.decodeBase64(encodedImage);
         		  // begin - save file
         		  String path = getBaseAssetsPathFromConfigProperties(_appToken, _staging);
         		  savedUrl = getBaseAssetsUrlFromConfigProperties(_appToken, _staging);
         		  
         		  File catalinaBase = new File( System.getProperty( "catalina.base" ) ).getAbsoluteFile();
         		  String destinationPath = uniqueIdDir +"/i"+ System.currentTimeMillis() + ".jpg";
         		  savedUrl = savedUrl + "/" + destinationPath;
         		  
         		  destinationPath = "webapps/"+ path +"/"+ destinationPath;
         		  File destination = new File(catalinaBase, destinationPath);
         		  FileOutputStream fo;
                   try {
                	   destination.getParentFile().mkdirs();
                       destination.createNewFile();
                       fo = new FileOutputStream(destination);
                       fo.write(jpegImage);
                       fo.close();
                   } catch (FileNotFoundException e) {
                       e.printStackTrace();
                       savedUrl = null;
                   } catch (IOException e) {
                       e.printStackTrace();
                       savedUrl = null;
                   }
         		  // end - save file
         	  }
           }
           return savedUrl;
       }
      
      public static String getBaseAssetsUrlFromConfigProperties(String token, boolean staging) {
      	return Config.getProperty("assetsUrl", token, staging);
      }
      
      public static String getBaseAssetsPathFromConfigProperties(String token, boolean staging) {
    	  return Config.getProperty("assetsPath", token, staging);
    }
}
