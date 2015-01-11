package com.mashape.interview.ToDoHerokuApp.services;

import java.util.Set;

import com.mashape.interview.ToDoHerokuApp.domains.Item;
import com.mashape.interview.ToDoHerokuApp.inmemorydatabase.ToDoList;

public class ToDoService {

	private ToDoList toDoList = new ToDoList();
	
	public Set<Item> getAllItems() {
		return toDoList.getAllItems();
	}
}
