package org.aipim.web.service.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.aipim.web.service.SyncService;
import org.aipim.web.service.UserUtil;
import org.aipim.web.service.connection.*;
import org.aipim.web.service.domain.*;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class TaskManager {
    private final static Logger logger = Logger.getLogger(TaskManager.class);
    private java.sql.Connection connection;
    
    public TaskManager() {
        this(null, true);
    }
    
    public TaskManager(String _appToken, boolean _staging) {
        this.connection = Connection.getConnection(_appToken, _staging);
    }
    
    public static TaskManager getInstance(Task task) {
    	if (task == null) {
    		task = new Task();
    		task.setToken("1");
    		task.setStaging(true);
    	}
    	return new TaskManager(task.getToken(), task.getStaging());
    }
    
    public static Task notify(final Task task) {
    	TaskManager taskManager = getInstance(task);
    	String message = task.getMessage();
    	switch (message) {
	    	case SyncService.SYNC_GET_TOKEN:
	    		taskManager.getToken(taskManager.newTask(task));
			break;
			case SyncService.SYNC_GET_USERS:
				taskManager.getUsers(taskManager.newTask(task));	
			break;
			case SyncService.SYNC_SET_USERS:
				taskManager.setUsers(taskManager.newTask(task));
			break;
			case SyncService.SYNC_GET_PRODUCTS:
				taskManager.getProducts(taskManager.newTask(task));
			break;
			case SyncService.SYNC_SET_PRODUCTS:
				taskManager.setProducts(taskManager.newTask(task));
			break;
			case SyncService.SYNC_GET_CARTS:
				taskManager.getCarts(taskManager.newTask(task));
			break;
			case SyncService.SYNC_SET_CARTS:
				taskManager.setCarts(taskManager.newTask(task));
			break;
			case SyncService.SYNC_GET_TASKS:
				taskManager.getTasks(taskManager.newTask(task));
			break;
			case SyncService.SYNC_SET_TASKS:
				taskManager.setTasks(taskManager.newTask(task));
			break;
		}
        return task;
    }
    
    public Task getToken(final Task task) {
    	String json = task.getRequest();
    	if (json != null && (!json.isEmpty())) {
    		JSONObject request = null;
    		try { request = new JSONObject(json); } catch (JSONException e) { e.printStackTrace(); }
    		if (request != null) {
    			//
    			System.out.println("#TEST#::"+ request.toString());
    			try {
    				String uniqueId = (request.has("uniqueId") && (!request.isNull("uniqueId"))) ?
        					(request.get("uniqueId") instanceof String) ?
        							request.getString("uniqueId") : ""  : "";
        			String password = (request.has("password") && (!request.isNull("password"))) ?
        					(request.get("password") instanceof String) ?
        							request.getString("password") : ""  : "";
        			String authbind = (request.has("authbind") && (!request.isNull("authbind"))) ?
        					(request.get("authbind") instanceof String) ?
        							request.getString("authbind") : ""  : "";
        			String name = (request.has("name") && (!request.isNull("name"))) ?
        					(request.get("name") instanceof String) ?
        							request.getString("name") : ""  : "";
        			String email = (request.has("email") && (!request.isNull("email"))) ?
        					(request.get("email") instanceof String) ?
        							request.getString("email") : ""  : "";
        			
        			UserManager userManager = new UserManager(task.getToken(), task.getStaging());
        			User user = new User();
        			if ((!uniqueId.isEmpty()) && (!password.isEmpty()) && userManager.checkCredentials(uniqueId, password)) {
        				user = userManager.getUserByUniqueId(uniqueId);
        			} else if ((!uniqueId.isEmpty()) && (!authbind.isEmpty())) {
        				if (userManager.checkAuthBindUniqueIdExists(uniqueId, authbind)) {
        					user = userManager.getUserByUniqueId(uniqueId);
        					user.setName(name);
        					user.setEmail(email);
        					userManager.updateUser(user);
        				} else if (userManager.checkUniqueIdExists(uniqueId)) {
        					user = userManager.getUserByUniqueId(uniqueId);
        					user.setAuthbind(authbind);
        					user.setName(name);
        					user.setEmail(email);
        					userManager.updateUser(user);
        				} else {
        					user.setUniqueId(uniqueId);
        					user.setName(name);
        					user.setEmail(email);
        					user.setAlternativeEmail(email);
        					UserUtil.setNewPassword(user, "fOsSoF" + authbind);
        					if (userManager.insertUser(user)) {
        						user = userManager.getUserByUniqueId(uniqueId);
        					}
        				}
        			}
        			task.setUrl(null);
        			task.setTitle(null);
        			task.setSubtitle(null);
        			task.setResponse(user.toString());
        			task.setRequest(null);
        			task.setStatus(Task.TASK_STATUS_DONE);
    			} catch (JSONException e) { e.printStackTrace(); }
    			//
    		}
    	}
    	return task;
    }
    
    public Task getUsers(final Task task) {
    	return task;
    }
    
    public Task setUsers(final Task task) {
    	return task;
    }
    
    public Task getProducts(final Task task) {
    	return task;
    }
    
    public Task setProducts(final Task task) {
    	return task;
    }
    
    public Task getCarts(final Task task) {
    	return task;
    }
    
    public Task setCarts(final Task task) {
    	return task;
    }
    
    public Task getTasks(final Task task) {
    	return task;
    }
    
    public Task setTasks(final Task task) {
    	return task;
    }
    
    public Task newTask(final Task task) {
    	task.setStatus(Task.TASK_STATUS_PENDING);
    	task.setArchived(Task.TASK_NOT_ARCHIVED);
    	task.setMessage(task.getMessage() +":"+ System.currentTimeMillis());
    	try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into tasks "
                    		+ "(token, message, status, request, start_time, archived) "
                            + "values "
                    		+ "(?,?,?,?,now(),0);");

            System.out.println("Task inserted: [" + task.getMessage() + "]");
            
            preparedStatement.setString(1, task.getToken());
            preparedStatement.setString(2, task.getMessage());
            preparedStatement.setString(3, task.getStatus());   
            preparedStatement.setString(4, task.getRequest());   
            preparedStatement.executeUpdate();
            
            getTaskByMessage(task, task.getMessage());
            
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
    	
        return task;
    }
    
    public Task getTaskByMessage(final Task task, String message) {
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * from tasks where message = ? and archived=0 order by uid desc limit 1;");
            preparedStatement.setString(1, message);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
            	setTaskResult(task, rs);
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
        return task;
    }
    
    public Task getTaskById(final Task task, int uid) {
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * from tasks where uid=?");
            preparedStatement.setInt(1, uid);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
            	setTaskResult(task, rs);
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
        return task;
    }
    
    public boolean setTaskResult(final Task task, final ResultSet resultSet) {
        boolean done = false;
    	try {
            task.setUid(resultSet.getLong("uid"));
            task.setToken(resultSet.getString("token"));
            task.setMessage(resultSet.getString("message"));
            task.setStatus(resultSet.getString("status"));
            task.setRequest(resultSet.getString("request"));
            task.setResponse(resultSet.getString("response"));
            task.setStartTime(resultSet.getDate("start_time"));
            task.setEndTime(resultSet.getDate("end_time"));
            task.setArchived(resultSet.getInt("archived"));
            done = true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
        }
        
        return done;
    }
    
}