package com.mashape.interview.ToDoHerokuApp.services;

import java.util.Iterator;
import java.util.Set;

import com.mashape.interview.ToDoHerokuApp.domains.Item;
import com.mashape.interview.ToDoHerokuApp.inmemorydatabase.ToDoList;

public class ToDoService {

	private ToDoList toDoList = new ToDoList();
	
	public Item getAllItems() {
		Set<Item> allItems = toDoList.getAllItems();
		Iterator<Item> iterator = allItems.iterator();
		if(iterator.hasNext()) return iterator.next();
		return null;
	}
}
