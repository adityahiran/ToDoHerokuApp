package com.mashape.interview.ToDoHerokuApp.todofacade;

import java.util.List;
import java.util.Set;

import com.mashape.interview.ToDoHerokuApp.domains.Item;
import com.mashape.interview.ToDoHerokuApp.services.SearchService;
import com.mashape.interview.ToDoHerokuApp.services.ToDoService;

class ToDoWrapper {

	private ToDoService toDoService = ToDoService.getInstance();
	private SearchService searchService = SearchService.getInstance();
	
	private static ToDoWrapper instance = null;
	
	public static ToDoWrapper getInstance() {
		if(instance == null) {
			instance = new ToDoWrapper();
		}
		return instance;
	}
	
	public Set<Item> getAllItems() {
        return toDoService.getAllItems();
    }
	
	public Item getItemByTitle(String title) {
		return toDoService.getItemByTitle(title);
    }
	
	public Set<Item> getCompletedItems() {
		boolean status = true;
    	return toDoService.getItemsByStatus(status);
    }
	
	public Set<Item> getItemsYetToComplete() {
		boolean status = false;
    	return toDoService.getItemsByStatus(status);
    }
    
    public Set<Item> searchItem(String searchTerm) {
    	return searchService.searchItems(searchTerm);
    }
    
    public Item saveItemByTitle(String title,  String body ) {
    	return toDoService.saveItem(title, body, false);	
    }
    
    public Item deleteItemByTitle(String title) {
    	return toDoService.deleteItemByTitle(title);
    }
    
    public Item updateItem(String oldTitle, String newTitle,  String newBody) {
    	return toDoService.updateItem(oldTitle, newTitle, newBody);
    }
    
    public Item markItemAsDone(String title) {
    	return toDoService.markItemAsDone(title);
    }
}
