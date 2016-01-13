package org.aipim.web.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.aipim.web.service.dao.TaskManager;
import org.aipim.web.service.domain.Task;
import org.apache.log4j.Logger;
import org.json.JSONObject;

@Path("/sync")
public class SyncService {
    private final static Logger logger = Logger.getLogger(SyncService.class);

	public final static String SYNC_GET_TOKEN = "gettoken";
	public final static String SYNC_GET_USERS = "getusers";
	public final static String SYNC_SET_USERS = "setusers";
	public final static String SYNC_GET_PRODUCTS = "getproducts";
	public final static String SYNC_SET_PRODUCTS = "setproducts";
	public final static String SYNC_GET_CARTS = "getcarts";
	public final static String SYNC_SET_CARTS = "setcarts";
	public final static String SYNC_GET_TASKS = "gettasks";
	public final static String SYNC_SET_TASKS = "settasks";
	
	@POST
	@Path("/gettoken")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Task getToken(String json) {
		System.out.println("gettoken: request["+ json +"]");
		Task task = new Task();
		task.setMessage(SYNC_GET_TOKEN);
		task.setStaging(true);
		try {
			JSONObject jsonRequest = new JSONObject(json);
			task.initMap(jsonRequest);
			TaskManager.notify(task);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
		}
		System.out.println("response: "+ task.toString());
		return task;
	}
	
	@POST
	@Path("/getusers")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Task getUsers(String json) {
		Task task = new Task();
		try {
			System.out.println("getusers: request["+ json +"]");
			JSONObject jsonRequest = new JSONObject(json);
			task.initMap(jsonRequest);
			TaskManager.notify(task);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
		}
		System.out.println("response: "+ task.toString());
		return task;
	}
	
	@POST
	@Path("/setusers")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Task setUsers(String json) {
		Task task = new Task();
		try {
			System.out.println("setusers: request["+ json +"]");
			JSONObject jsonRequest = new JSONObject(json);
			task.initMap(jsonRequest);
			TaskManager.notify(task);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
		}
		System.out.println("response: "+ task.toString());
		return task;
	}
	
	@POST
	@Path("/getproducts")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Task getProducts(String json) {
		Task task = new Task();
		try {
			System.out.println("getproducts: request["+ json +"]");
			JSONObject jsonRequest = new JSONObject(json);
			task.initMap(jsonRequest);
			TaskManager.notify(task);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
		}
		System.out.println("response: "+ task.toString());
		return task;
	}
	
	@POST
	@Path("/setproducts")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Task setProducts(String json) {
		Task task = new Task();
		try {
			System.out.println("setproducts: request["+ json +"]");
			JSONObject jsonRequest = new JSONObject(json);
			task.initMap(jsonRequest);
			TaskManager.notify(task);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
		}
		System.out.println("response: "+ task.toString());
		return task;
	}
	
	@POST
	@Path("/getcarts")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Task getCarts(String json) {
		Task task = new Task();
		try {
			System.out.println("getcarts: request["+ json +"]");
			JSONObject jsonRequest = new JSONObject(json);
			task.initMap(jsonRequest);
			TaskManager.notify(task);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
		}
		System.out.println("response: "+ task.toString());
		return task;
	}
	
	@POST
	@Path("/setcarts")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Task setCarts(String json) {
		Task task = new Task();
		try {
			System.out.println("setcarts: request["+ json +"]");
			JSONObject jsonRequest = new JSONObject(json);
			task.initMap(jsonRequest);
			TaskManager.notify(task);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
		}
		System.out.println("response: "+ task.toString());
		return task;
	}
	
	@POST
	@Path("/gettasks")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Task getTasks(String json) {
		Task task = new Task();
		try {
			System.out.println("gettasks: request["+ json +"]");
			JSONObject jsonRequest = new JSONObject(json);
			task.initMap(jsonRequest);
			TaskManager.notify(task);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
		}
		System.out.println("response: "+ task.toString());
		return task;
	}
	
	@POST
	@Path("/settasks")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Task setTasks(String json) {
		Task task = new Task();
		try {
			System.out.println("settasks: request["+ json +"]");
			JSONObject jsonRequest = new JSONObject(json);
			task.initMap(jsonRequest);
			TaskManager.notify(task);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
		}
		System.out.println("response: "+ task.toString());
		return task;
	}
}
