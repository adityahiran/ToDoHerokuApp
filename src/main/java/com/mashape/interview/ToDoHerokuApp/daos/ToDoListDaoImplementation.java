package com.mashape.interview.ToDoHerokuApp.daos;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import com.mashape.interview.ToDoHerokuApp.databases.ToDoList;
import com.mashape.interview.ToDoHerokuApp.domains.Item;

/* DATA ACCESS OBJECT DESIGN PATTERN:
 * Helps in maintaining the database loosly coupled with the rest of the application. */

public class ToDoListDaoImplementation implements ToDoListDao {

	private static ToDoListDaoImplementation instance = null;
	private static ToDoList toDoList = ToDoList.getInstance();
	private static Hashtable<Long, Item> databaseInstance = toDoList.getDatabaseInstance();
	private static long lastIndex = toDoList.getLastIndex();	// Same as the index of the lastInserted record in the database. This also matches with the ID of the todo-list-item's id.
	
	// Making sure that you cannot make an instance of this class
	protected ToDoListDaoImplementation() {}
	
	public static ToDoListDaoImplementation getInstance() {
		if(instance==null) instance = new ToDoListDaoImplementation();
		return instance;
	}
	
	@Override
	public Set<Item> getAllItems() {
		// Will return this object
		Set<Item> items = new HashSet<Item>();
		
		// Iterate through the database records and add each record (instance of Item) to a java.util.Set and return it.
		Set<Long> keySet = databaseInstance.keySet();
		Iterator<Long> iter = keySet.iterator();
		while(iter.hasNext()) {
			Long next = iter.next();
			Item item = databaseInstance.get(next);
			items.add(item);
		}
		
		return items;
	}
	
	@Override
	public Item getItemById(long id) {
		return databaseInstance.get(id);
	}
	
	@Override
	public Item addItem(String title, String body, boolean done) {
		return ToDoList.addRecord(title, body, done);
	}
	
	@Override
	public Item updateItemById(long id, Item item) {
		return ToDoList.updateItemById(id, item);
	}
	
	@Override
	public Item deleteItemByKey(long id) {
		return ToDoList.deleteItemById(id);
	}
	
	@Override
	public Item searchItemById(long id) {
		return databaseInstance.get(id);
	}

	@Override
	public Item getItemByTitle(String title) {
		// Iterate through the database records and search for the record having the title same as that of the parameter passed. Return this record.
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

	@Override
	public Set<Item> getItemsByStatus(boolean status) {
		
		// Intended to return either those items that have been completed or those that are yet to be completed.
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

	@Override
	public Item deleteItemByTitle(String title) {
		// As the title is unique for each todo-list-item, we can delete an item by providing its title
		return ToDoList.deleteItemByTitle(title);
	}

	@Override
	public Set<Item> deleteAllItems() {
		return ToDoList.deleteAllItems();
	}

	@Override
	public Item updateItem(String oldTitle, String newTitle, String newBody) {
		// Identify an item by its title, update either its title or its body or both.
		return ToDoList.updateItem(oldTitle, newTitle, newBody);
	}

	@Override
	public Item markItemAsDone(String title) {
		// Update an item's status from undone to done
		return ToDoList.markItemAsDone(title);
	}

	@Override
	public Item getLastItem() {
		// Get the last inserted record in the database 
		return ToDoList.getLastItem();
	}
	
	
}
