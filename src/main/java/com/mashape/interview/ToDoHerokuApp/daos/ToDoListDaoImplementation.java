package com.mashape.interview.ToDoHerokuApp.daos;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import com.mashape.interview.ToDoHerokuApp.databases.ToDoList;
import com.mashape.interview.ToDoHerokuApp.domains.Item;

public class ToDoListDaoImplementation implements ToDoListDao {

	private static ToDoListDaoImplementation instance = null;
	private static ToDoList toDoList = ToDoList.getInstance();
	private static Hashtable<Long, Item> databaseInstance = toDoList.getDatabaseInstance();
	private static long lastIndex = toDoList.getLastIndex();
	
	protected ToDoListDaoImplementation() {}
	
	public static ToDoListDaoImplementation getInstance() {
		if(instance==null) instance = new ToDoListDaoImplementation();
		return instance;
	}
	
	public Set<Item> getAllItems() {
		Set<Item> items = new HashSet<Item>();
		
		Set<Long> keySet = databaseInstance.keySet();
		Iterator<Long> iter = keySet.iterator();
		while(iter.hasNext()) {
			Long next = iter.next();
			Item item = databaseInstance.get(next);
			items.add(item);
		}
		return items;
	}
	
	public Item getItemById(long id) {
		return databaseInstance.get(id);
	}
	
	public Item addItem(String title, String body, boolean done) {
		return ToDoList.addRecord(title, body, done);
	}
	
	public Item updateItemById(long id, Item item) {
		return ToDoList.updateItemById(id, item);
	}
	
	public Item deleteItemByKey(long id) {
		return ToDoList.deleteItemById(id);
	}
	
	public Item searchItemById(long id) {
		return databaseInstance.get(id);
	}

	public Item getItemByTitle(String title) {
		Set<Long> keySet = databaseInstance.keySet();
		Iterator<Long> iterator = keySet.iterator();
		while(iterator.hasNext()) {
			Long nextId = iterator.next();
			Item item = databaseInstance.get(nextId);
			if(item.getTitle().equalsIgnoreCase(title))
				return item; 
		}
		return null;
	}

	public Set<Item> getItemsByStatus(boolean status) {
		Set<Item> items = new HashSet<Item>();
		Set<Long> keySet = databaseInstance.keySet();
		Iterator<Long> iterator = keySet.iterator();
		while(iterator.hasNext()) {
			Long nextId = iterator.next();
			Item item = databaseInstance.get(nextId);
			if(item.isDone()==status)
				items.add(item);
		}
		return items;
	}

	public Item deleteItemByTitle(String title) {
		return ToDoList.deleteItemByTitle(title);
	}

	//@Override
	public Set<Item> deleteAllItems() {
		return ToDoList.deleteAllItems();
	}

	//@Override
	public Item updateItem(String oldTitle, String newTitle, String newBody) {
		return ToDoList.updateItem(oldTitle, newTitle, newBody);
	}

	//@Override
	public String markItemAsDone(String title) {
		return ToDoList.markItemAsDone(title);
	}

	//@Override
	public Item getLastItem() {
		return ToDoList.getLastItem();
	}
	
	
}
