package com.mashape.interview.ToDoHerokuApp.services;

import java.util.Iterator;
import java.util.Set;

import javax.inject.Inject;

import com.mashape.interview.ToDoHerokuApp.daos.ToDoListDao;
import com.mashape.interview.ToDoHerokuApp.daos.ToDoListDaoImplementation;
import com.mashape.interview.ToDoHerokuApp.domains.Item;
import com.mashape.interview.ToDoHerokuApp.inmemorydatabase.ToDoList;

public class ToDoService {

	private static ToDoListDao dao = ToDoListDaoImplementation.getInstance();
	
	private static ToDoService instance = null;
	
	protected ToDoService() {}
	
	public static ToDoService getInstance() {
		if(instance == null) {
			instance = new ToDoService();
		}
		return instance;
	}
	
	public Set<Item> getAllItems() {
		Set<Item> allItems = dao.getAllItems();
		return allItems;
	}

	public Item getItemById(long id) {
		Item itemById = dao.getItemById(id);
		return itemById;
	}

	public Item saveItem(String title) {
		return dao.addItem(title);
	}

	public Item getItemByTitle(String title) {
		return dao.getItemByTitle(title);
	}

	public Set<Item> getItemsByStatus(boolean status) {
		return dao.getItemsByStatus(status);
	}

	public Item deleteItemById(long id) {
		return dao.deleteItemByKey(id);
	}

	public Item deleteItemByTitle(String title) {
		return dao.deleteItemByTitle(title);
	}
}
