package com.mashape.interview.ToDoHerokuApp.services;

import java.util.Iterator;
import java.util.Set;

import com.mashape.interview.ToDoHerokuApp.domains.Item;
import com.mashape.interview.ToDoHerokuApp.inmemorydatabase.ToDoList;

public class ToDoService {

	private ToDoList toDoList = new ToDoList();
	
	public Set<Item> getAllItems() {
		Set<Item> allItems = toDoList.getAllItems();
		//Iterator<Item> iterator = allItems.iterator();
		//if(iterator.hasNext()) return iterator.next();
		return allItems;
	}

	public Item getItemById(long id) {
		Item itemById = toDoList.getItemById(id);
		return itemById;
	}

	public Item saveItem(String title) {
		return toDoList.addItem(title);
	}

	public Item getItemByTitle(String title) {
		return toDoList.getItemByTitle(title);
	}

	public Set<Item> getItemsByStatus(boolean status) {
		return toDoList.getItemsByStatus(status);
	}

	public Item deleteItemById(long id) {
		return toDoList.deleteItemByKey(id);
	}
}
