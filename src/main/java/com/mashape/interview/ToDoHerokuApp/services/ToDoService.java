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

	public Item saveItem(String title, String body, boolean done) {
		return dao.addItem(title, body, done);
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

	public Set<Item> deletAllItems() {
		return dao.deleteAllItems();
	}

	public void updateItem(String oldTitle, String newTitle, String newBody) {
		dao.updateItem(oldTitle, newTitle, newBody);
	}

	public Item markItemAsDone(String title) {
		return dao.markItemAsDone(title);
	}
}
