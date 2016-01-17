package org.aipim.web.service.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.aipim.web.service.UserUtil;
import org.aipim.web.service.connection.*;
import org.aipim.web.service.domain.*;
import org.apache.log4j.Logger;

public class UserManager {
    private final static Logger logger = Logger.getLogger(UserManager.class);
    private java.sql.Connection connection;
    
    public UserManager() {
        this(null, true);
    }
    
    public UserManager(String _appToken, boolean _staging) {
        this.connection = Connection.getConnection(_appToken, _staging);
    }
    
    public void getToken(final User user) {
    	if (user == null) return;
    	String uniqueId = user.getUniqueId();
		String password = user.getPassword();
		String authbind = user.getAuthbind();
		
		if ((!uniqueId.isEmpty()) && (!password.isEmpty()) && checkCredentials(uniqueId, password)) {
			user.initMap(getUserByUniqueId(uniqueId).toString());
		} else if ((!uniqueId.isEmpty()) && (!authbind.isEmpty())) {
			if (checkAuthBindUniqueIdExists(uniqueId, authbind)) {
				user.initMap(getUserByUniqueId(uniqueId).toJson());
			} else if (checkUniqueIdExists(uniqueId)) {
				user.initMap(getUserByUniqueId(uniqueId).toJson());
				user.setAuthbind(authbind);
				updateUser(user);
			} else {
				UserUtil.setNewPassword(user, "fOsSoF" + authbind);
				if (insertUser(user)) {
					user.initMap(getUserByUniqueId(uniqueId).toJson());
				}
			}
		}
    }
    
    public Boolean checkCredentials(String uniqueId, String password) {
        boolean checked = false;
    	User user = getUserByUniqueId(uniqueId);
    	if (user != null) {
    		checked = UserUtil.checkPassword(user.getSalt(), user.getEncryptedPassword(), password);
    	}
        return checked;
    }
    
    public Boolean checkUniqueIdExists(String uniqueId) {
        User user = getUserByUniqueId(uniqueId);
        return (user != null
        		&& user.getUid() > 0
        		&& (user.getUniqueId() != null) && user.getUniqueId().equals(uniqueId));
    }
    
    public Boolean checkAuthBindUniqueIdExists(String uniqueId, String authbind) {
        User user = getUserByUniqueId(uniqueId);
        return (user != null
        		&& user.getUid() > 0
        		&& (user.getUniqueId() != null) && user.getUniqueId().equals(uniqueId)
        		&& (user.getAuthbind() != null) && user.getAuthbind().equals(authbind));
    }
    
    public boolean changeUserPassword(String uniqueId, String oldPassword, String newPassword) {
        boolean changed = false;
        User user = getUserByUniqueId(uniqueId);
        if (user != null && UserUtil.checkPassword(user, oldPassword)) {
        	UserUtil.setNewPassword(user, newPassword);
            try {
                PreparedStatement preparedStatement = connection
                        .prepareStatement("update users set encrypted_password=?, salt=?, updated_at=now()"
                                + " where unique_id=?");
                System.out.println("user password changed: [" + uniqueId + "]");
                
                preparedStatement.setString(1, user.getEncryptedPassword());
                preparedStatement.setString(2, user.getSalt());
                preparedStatement.setString(4, user.getUniqueId());
                preparedStatement.executeUpdate();
                changed = true;
                
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error(e.toString());
            }finally {
    			if (this.connection != null) {
    				try {
    					this.connection.close();
    				} catch (Exception e) {
    					/* handle close exception, quite usually ignore */ }
    			}
    		}
        }
        return changed;
    }
    
    public boolean insertUser(User user) {
        boolean inserted = false;
    	try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into users( "
                            + "unique_id, name, email, alternative_email, "
                            + "authbind, encrypted_password, salt, status, created_at) "
                            + "values (?,?,?,?,?,?,?,'ENABLE',now());");

            System.out.println("user inserted: [" +user.getUniqueId()+ "]");
            preparedStatement.setString(1, user.getUniqueId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getAlternativeEmail());
            preparedStatement.setString(5, user.getAuthbind() != null && (!user.getAuthbind().isEmpty()) ? user.getAuthbind() :  "NONE");
            preparedStatement.setString(6, user.getEncryptedPassword());
            preparedStatement.setString(7, user.getSalt());
            
            preparedStatement.executeUpdate();
            inserted = true;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.toString());
        }finally {
			if (this.connection != null) {
				try {
					this.connection.close();
				} catch (Exception e) {
					/* handle close exception, quite usually ignore */ }
			}
		}
    	
    	return inserted;
    }
    
    public void updateUser(User user) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update users set url=?, name=?, label=?, email=?,"
                            + " alternative_email=?, authbind=?, role=?, address=?,"
                            + " stars=?, last_lat=?, last_lon=?, status=?,"
                            + " owner_uid=?, updated_at=now()"
                            + " where unique_id=?;");
            System.out.println("user updated: [" +user.getUniqueId()+ "]");
            
            preparedStatement.setString(1, user.getUrl());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getLabel());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getAlternativeEmail());
            preparedStatement.setString(6, user.getAuthbind());
            preparedStatement.setInt(7, user.getRole());
            preparedStatement.setString(8, user.getAddress());
            preparedStatement.setInt(9, user.getStars());
            preparedStatement.setDouble(10, user.getLastLat());
            preparedStatement.setDouble(11, user.getLastLon());
            preparedStatement.setString(12, user.getStatus());
            preparedStatement.setInt(13, user.getOwnerUid());
            
            preparedStatement.setString(14, user.getUniqueId());
            preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.toString());
        }finally {
			if (this.connection != null) {
				try {
					this.connection.close();
				} catch (Exception e) {
					/* handle close exception, quite usually ignore */ }
			}
		}
    }
    
    public User getUserByUniqueId(String uniqueId) {
        final User user = new User();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select * from users where unique_id=?;");
            preparedStatement.setString(1, uniqueId);
            ResultSet rs = preparedStatement.executeQuery();
            
            if (rs.next()) {
                setUserResult(user, rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.toString());
        }finally {
			if (this.connection != null) {
				try {
					this.connection.close();
				} catch (Exception e) {
					/* handle close exception, quite usually ignore */ }
			}
		}
        
        return user;
    }
    
    public ArrayList<User> getUsers(int startUid) {
        ArrayList<User> users = new ArrayList<User>();
        try {
        	PreparedStatement preparedStatement = connection
                .prepareStatement("select * from users order by owner_uid, label, uid limit ?,1000;");
        	preparedStatement.setInt(1, startUid);
        	ResultSet rs = preparedStatement.executeQuery();
        	
            while (rs.next()) {
                final User user = new User();
                setUserResult(user, rs);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.toString());
        }finally {
			if (this.connection != null) {
				try {
					this.connection.close();
				} catch (Exception e) {
					/* handle close exception, quite usually ignore */ }
			}
		}

        return users;
    }
    
    public ArrayList<User> getUsersByOwnerId(int ownerUid) {
    	ArrayList<User> users = new ArrayList<User>();
        try {
            PreparedStatement preparedStatement = connection
            		.prepareStatement("select * from users where owner_uid = ? order by label, uid;");
        	preparedStatement.setInt(1, ownerUid);
            ResultSet rs = preparedStatement.executeQuery();
            
            while (rs.next()) {
                final User user = new User();
                setUserResult(user, rs);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.toString());
        }
        finally {
			if (this.connection != null) {
				try {
					this.connection.close();
				} catch (Exception e) {
					/* handle close exception, quite usually ignore */ }
			}
		}
        
        return users;
    }
    
    public ArrayList<User> getOwnerUsers(int startUid) {
    	ArrayList<User> users = new ArrayList<User>();
        try {
            PreparedStatement preparedStatement = connection
            		.prepareStatement("select * from users where owner_uid = uid order by label, uid limit ?,1000;");
        	preparedStatement.setInt(1, startUid);
            ResultSet rs = preparedStatement.executeQuery();
            
            while (rs.next()) {
                final User user = new User();
                setUserResult(user, rs);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.toString());
        }finally {
			if (this.connection != null) {
				try {
					this.connection.close();
				} catch (Exception e) {
					/* handle close exception, quite usually ignore */ }
			}
		}
        
        return users;
    }
    
    public boolean setUserResult(final User user, final ResultSet resultSet) {
    	boolean done = false;
    	try {
        	user.setUid(resultSet.getInt("uid"));
            user.setUrl(resultSet.getString("url"));
            user.setUniqueId(resultSet.getString("unique_id"));
            user.setName(resultSet.getString("name"));
            user.setLabel(resultSet.getString("label"));
            user.setEmail(resultSet.getString("email"));
            user.setAlternativeEmail(resultSet.getString("alternative_email"));
            user.setAuthbind(resultSet.getString("authbind"));
            user.setEncryptedPassword(resultSet.getString("encrypted_password"));
            user.setSalt(resultSet.getString("salt"));
            user.setRole(resultSet.getInt("role"));
            user.setAddress(resultSet.getString("address"));
            user.setStars(resultSet.getInt("stars"));
            user.setLastLat(resultSet.getDouble("last_lat"));
            user.setLastLon(resultSet.getDouble("last_lon"));
            user.setStatus(resultSet.getString("status"));
            user.setOwnerUid(resultSet.getInt("owner_uid"));
            user.setCreatedAt(resultSet.getDate("created_at"));
            user.setUpdatedAt(resultSet.getDate("updated_at"));
            done = true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
        } 
    	
        return done;
    }
}