package com.mashape.interview.ToDoHerokuApp.resources;

import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mashape.interview.ToDoHerokuApp.domains.Item;
import com.mashape.interview.ToDoHerokuApp.services.ToDoService;

/**
 * Root resource (exposed at "todoitems" path)
 */
@Path("todoitems")
public class ToDoItems {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    
	private ToDoService toDoService = new ToDoService();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Item> getAllItems() {
        return toDoService.getAllItems();
    }
    
    /*@GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Item getItemById(@PathParam("id") String id) {
    	long idParsed = Long.parseLong(id);
		return toDoService.getItemById(idParsed);
    }*/
    
    /*@POST
    @Path("{title}")
    @Produces(MediaType.APPLICATION_JSON)
    public Item saveItem(Item item) {
    	return toDoService.saveItem(item);
    }*/
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Item getItemQueriedById(@QueryParam("id") String id) {
    	long idParsed = Long.parseLong(id);
		return toDoService.getItemById(idParsed);
    }
}

