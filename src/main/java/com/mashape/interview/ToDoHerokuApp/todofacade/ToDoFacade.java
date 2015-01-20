package com.mashape.interview.ToDoHerokuApp.todofacade;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.executable.ValidateOnExecution;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.client.utils.URIBuilder;

import com.mashape.interview.ToDoHerokuApp.domains.Item;

@Path("todo-list-items")
public class ToDoFacade {

	private static ToDoFacade instance = null;
	private ToDoWrapper toDoWrapper = ToDoWrapper.getInstance();

	public static ToDoFacade getInstance() {
		if (instance == null) {
			instance = new ToDoFacade();
		}
		return instance;
	}

	// GET: https://todo-app-mashape.herokuapp.com/todo-list-items - WORKING
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllItems() {
		Set<Item> allItems = toDoWrapper.getAllItems(); 
		return Response.ok(allItems).build();
	}

	// Get the item with the specified title in the to-do list - WORKING
	@GET
	@Path("{title}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getItemByTitle(@PathParam("title") String title) {
		Item item = toDoWrapper.getItemByTitle(title); 
		return Response.ok(item).build();
	}

	// Get all the completed todo list items - WORKING
	@GET
	@Path("done")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCompletedItems() {
		Set<Item> completedItems = toDoWrapper.getCompletedItems();
		return Response.ok(completedItems).build();
	}

	// Get all the todo list items that are yet to be completed - WORKING
	@GET
	@Path("undone")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getItemsYetToComplete() {
		Set<Item> itemsYetToComplete = toDoWrapper.getItemsYetToComplete();
		return Response.ok(itemsYetToComplete).build();
	}

	// Search the todo-list-items stored in the database by a search term that should match either partly or fully the contents in the item's title/body - WORKING 
	@GET
	@Path("search/{searchTerm}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchItem(@PathParam("searchTerm") String searchTerm) {
		Set<Item> items = toDoWrapper.searchItem(searchTerm);
		return Response.ok(items).build();
	}

	// Save a new todo-list-item - WORKING
	// Add the location of the created resource to the response header
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveItemByTitle(@NotNull@FormParam("title") String title,
			@FormParam("body") String body) {
		
		// Validation at the application level
		if(title==null || title.isEmpty()) {
			throw new WebApplicationException("Cannot create a todo-list-item without a valid title as a form parameter.", 400);
		}
		
		Item item = toDoWrapper.saveItemByTitle(title, body);
		URI uri = null;
		try {
			uri = new URI("https://todo-app-mashape.herokuapp.com/todo-list-items/"+item.getTitle());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		// Response has a status code of 201 with a location header and a body
		return Response.created(uri).entity(item).build();
	}

	// Delete a todo-list-item by providing the title as a path parameter - WORKING
	@DELETE
	@Path("/{title}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteItemByTitle(@NotNull@PathParam("title") String title) {
		
		// Application level Validation
		if(title==null || title.isEmpty()) {
			throw new WebApplicationException("Cannot delete a todo-list-item with a null title.", 400);
		}
		
		Item item = toDoWrapper.deleteItemByTitle(title);
		return Response.ok(item).build();
	}

	// HTTP REQUEST METHOD: PUT - WORKING
	@PUT
	@Path("{old-title}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateItem(@NotNull @PathParam("old-title") String oldTitle,
			@NotNull@FormParam("new-title") String newTitle,
			@FormParam("new-body") String newBody) {
		
		// Application level validation
		if(oldTitle==null || oldTitle.isEmpty()) {
			throw new WebApplicationException("While updating a todo-list-item, you should provide the title of the item you want to update. The title should be not null and not empty.", 400);
		}
		
		if(newTitle==null || newTitle.isEmpty()) {
			throw new WebApplicationException("While updating a todo-list-item, you should provide the new title of the item you want to update. The title should be not null and not empty.", 400);
		}
		
		Item item = toDoWrapper.updateItem(oldTitle, newTitle, newBody);
		return Response.ok(item).build();
	}

	// Mark a todo list item as done
	@PUT
	@Path("{title}/mark-as-done")
	@Produces(MediaType.APPLICATION_JSON)
	public Response markItemAsDone(@NotNull @PathParam("title") String title) {
		
		// Application level Validation
		if(title==null || title.isEmpty()) {
			throw new WebApplicationException("Cannot mark a todo-list-item with a null title as done.", 400);
		}
		
		Item item = toDoWrapper.markItemAsDone(title);
		return Response.ok(item).build();
	}
}
