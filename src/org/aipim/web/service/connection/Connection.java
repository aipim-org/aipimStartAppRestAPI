package org.aipim.web.service.connection;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.aipim.web.service.Config;
import org.apache.log4j.Logger;

public class Connection {
    private final static Logger logger = Logger.getLogger(Connection.class);
    private static java.sql.Connection connection = null;
    
    private final static String TAG_KEY_DRIVER = "driver";
    private final static String TAG_KEY_URL = "url";
    private final static String TAG_KEY_USER = "user";
    private final static String TAG_KEY_PASSWORD = "password";
    
    private final static int maxLimitTries = 3;
    
    public static java.sql.Connection getConnection() {
    	return getConnection(null, true);
    }
    
    public static java.sql.Connection getConnection(String token) {
    	return getConnection(token, true);
    }
    
    public static java.sql.Connection getConnection(String token, boolean staging) {
    	return getConnection(token, staging, maxLimitTries);
    }
    
    private static java.sql.Connection getConnection(String token, boolean staging, int limitTries) {
    	if (limitTries > maxLimitTries) limitTries = maxLimitTries;
    	
    	if (connection == null) {
        	try {
                String driver = Config.getProperty(TAG_KEY_DRIVER, token, staging);
                String url = Config.getProperty(TAG_KEY_URL, token, staging);
                String user = Config.getProperty(TAG_KEY_USER, token, staging);
                String password = Config.getProperty(TAG_KEY_PASSWORD, token, staging);

                Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("connection["+ url +";"+ user +"]");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                logger.error(e.toString());
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error(e.toString());
            }
        }
        try {
        	if (limitTries > 0 && (connection == null || connection.isClosed())) {
        		connection = null;
        		connection = getConnection(token, staging, limitTries -1);
            }
        }
        catch (Exception e) {
        	e.printStackTrace();
        	if (limitTries > 0) {
        		connection = getConnection(token, staging, limitTries -1);
            }
        }
        return connection;
    }
}