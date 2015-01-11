package com.mashape.interview.ToDoHerokuApp.resources;

import java.util.Set;

import javax.validation.constraints.NotNull;
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

public class ToDoItems {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    
	private ToDoService toDoService = new ToDoService();
    
	// Get all the items in the to-do list
    @GET
    @Path("todolistitems")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Item> getAllItems() {
        return toDoService.getAllItems();
    }
    
    // Get the item with the specified id in the to-do list
    @GET
    @Path("todolistitems/item/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Item getItemById(@NotNull@PathParam("id") String id) {
    	long idParsed = Long.parseLong(id);
		return toDoService.getItemById(idParsed);
    }
    
    // Get the item with the specified title in the to-do list
    @GET
    @Path("todolistitems/item/{title}")
    @Produces(MediaType.APPLICATION_JSON)
    public Item getItemByTitle(@NotNull@PathParam("title") String title) {
		return toDoService.getItemByTitle(title);
    }
    
    // Get all the items in the to-do list with the specified status
    @GET
    @Path("todolistitems/item/{done}")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Item> getItemsByStatus(@NotNull@PathParam("done") String done) {
		boolean status = Boolean.parseBoolean(done);
    	return toDoService.getItemsByStatus(status);
    }
    
    /*@POST
    @Path("{title}")
    @Produces(MediaType.APPLICATION_JSON)
    public Item saveItem(Item item) {
    	return toDoService.saveItem(item);
    }*/
    
    
}

