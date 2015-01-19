package com.mashape.interview.ToDoHerokuApp.todofacade;

import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

	// Get all the completed todo list items - SUCCESS
	@GET
	@Path("done")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCompletedItems() {
		Set<Item> completedItems = toDoWrapper.getCompletedItems();
		return Response.ok(completedItems).build();
	}

	// Get all the todo list items that are yet to be completed - SUCCESS
	@GET
	@Path("undone")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getItemsYetToComplete() {
		Set<Item> itemsYetToComplete = toDoWrapper.getItemsYetToComplete();
		return Response.ok(itemsYetToComplete).build();
	}

	@GET
	@Path("search/{searchTerm}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchItem(@PathParam("searchTerm") String searchTerm) {
		List<Item> items = toDoWrapper.searchItem(searchTerm);
		return Response.ok(items).build();
	}

	@POST
	@Path("{title}/{body}")
	@Produces(MediaType.APPLICATION_JSON)
	public Item saveItemByTitle(@NotNull @PathParam("title") String title,
			@PathParam("body") String body) {
		return toDoWrapper.saveItemByTitle(title, body);
	}

	@DELETE
	@Path("/{title}")
	@Produces(MediaType.APPLICATION_JSON)
	public Item deleteItemByTitle(@NotNull @PathParam("title") String title) {
		return toDoWrapper.deleteItemByTitle(title);
	}

	// HTTP REQUEST METHOD: PUT
	@PUT
	@Path("{old-title}/{new-title}/{new-body}")
	@Produces(MediaType.APPLICATION_JSON)
	public void updateItem(@NotNull @PathParam("old-title") String oldTitle,
			@NotNull @PathParam("new-title") String newTitle,
			@PathParam("new-body") String newBody) {
		toDoWrapper.updateItem(oldTitle, newTitle, newBody);
	}

	// Mark a todo list item as done
	@PUT
	@Path("{title}/mark-as-done")
	@Produces(MediaType.APPLICATION_JSON)
	public Item markItemAsDone(@NotNull @PathParam("title") String title) {
		return toDoWrapper.markItemAsDone(title);
	}
}
