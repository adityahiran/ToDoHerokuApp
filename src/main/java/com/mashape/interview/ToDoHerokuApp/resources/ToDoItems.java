package com.mashape.interview.ToDoHerokuApp.resources;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mashape.interview.ToDoHerokuApp.domains.Item;
import com.mashape.interview.ToDoHerokuApp.services.SearchService;
import com.mashape.interview.ToDoHerokuApp.services.ToDoService;

/**
 * Root resource (exposed at "todoitems" path)
 */
@Path("todolistitems")
public class ToDoItems {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
  
	private ToDoService toDoService = ToDoService.getInstance();
	private SearchService searchService = SearchService.getInstance();
    
	
	// HTTP REQUEST METHOD: GET
	
	// Get all the items in the to-do list -SUCCESS
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Item> getAllItems() {
        return toDoService.getAllItems();
    }
    
    // Get the item with the specified id in the to-do list
    /*@GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Item getItemById(@PathParam("id") String id) {
    	long idParsed = Long.parseLong(id);
		return toDoService.getItemById(idParsed);
    }*/
    
    // Get the item with the specified title in the to-do list - SUCCESS
    @GET
    @Path("{title}")
    @Produces(MediaType.APPLICATION_JSON)
    public Item getItemByTitle(@PathParam("title") String title) {
		return toDoService.getItemByTitle(title);
    }
    
    // Get all the items in the to-do list with the specified status
    /*@GET
    @Path("done/{done}")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Item> getItemsByStatus(@PathParam("done") String done) {
		boolean status = Boolean.parseBoolean(done);
    	return toDoService.getItemsByStatus(status);
    }*/
    
    // Get all the completed todo list items - SUCCESS
    @GET
    @Path("done")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Item> getCompletedItems() {
		boolean status = true;
    	return toDoService.getItemsByStatus(status);
    }
    
    // Get all the todo list items that are yet to be completed - SUCCESS
    @GET
    @Path("undone")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Item> getItemsYetToComplete() {
		boolean status = false;
    	return toDoService.getItemsByStatus(status);
    }
    
    @GET
    @Path("search/{searchTerm}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Item> searchItem(@PathParam("searchTerm") String searchTerm) {
    	return searchService.searchItems(searchTerm);
    }
    
    // HTTP REQUEST METHOD: POST
    
    /*@POST
    @Path("{title}/{body}/{done}")
    @Produces(MediaType.APPLICATION_JSON)
    public Item saveItemByTitle(@NotNull@PathParam("title")String title, @PathParam("body")String body, @PathParam("done") String done ) {
    	boolean doneFlag = Boolean.parseBoolean(done);
    	return toDoService.saveItem(title, body, doneFlag);	// TODO
    }*/
    
    // HTTP REQUEST METHOD: DELETE
    
    // TODO REMOVE THIS METHOD
    /*@DELETE
    @Path("id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Item deleteItemById(@NotNull@PathParam("id") String id) {
    	long idParsed = Long.parseLong(id);
		return toDoService.deleteItemById(idParsed);
    }
    
    @DELETE
    @Path("/{title}")
    @Produces(MediaType.APPLICATION_JSON)
    public Item deleteItemByTitle(@NotNull@PathParam("title") String title) {
    	return toDoService.deleteItemByTitle(title);
    }
    
    @DELETE
    @Path("done")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Item> deleteItemsYetToComplete() {
		boolean status = false;
    	return toDoService.getItemsByStatus(status);
    }
    
    @DELETE
    @Path("done")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Item> deleteItemsCompleted() {
		boolean status = true;
    	return toDoService.getItemsByStatus(status);
    }
    
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Item> deleteAllItems() {
        return toDoService.deletAllItems();
    }*/
    
    // HTTP REQUEST METHOD: PUT
   /* @PUT
    @Path("{old-title}/{new-title}/{new-body}")
    @Produces(MediaType.APPLICATION_JSON)
    public void updateItem(@NotNull@PathParam("old-title")String oldTitle, @NotNull@PathParam("new-title")String newTitle, @PathParam("new-body")String newBody) {
    	toDoService.updateItem(oldTitle, newTitle, newBody);
    }
    
    // Mark a todo list item as done
    @PUT
    @Path("{title}/mark-as-done")
    @Produces(MediaType.APPLICATION_JSON)
    public void markItemAsDone(@NotNull@PathParam("title")String title) {
    	toDoService.markItemAsDone(title);
    }*/
}

