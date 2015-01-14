package com.mashape.interview.ToDoHerokuApp.daos;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import com.mashape.interview.ToDoHerokuApp.domains.Item;
import com.mashape.interview.ToDoHerokuApp.inmemorydatabase.ToDoList;

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
		lastIndex++;
		
		// TODO Replace is with a factory.getInstance()
		Item item = new Item(title, body, done);
		item.setId(lastIndex);
		databaseInstance.put(lastIndex, item);
		
		return databaseInstance.get(lastIndex);
	}
	
	public Item updateItemById(long id, Item item) {
		if(databaseInstance.containsKey(id))
			databaseInstance.put(id, item);	
		else
			System.out.println("Error");
		
		return databaseInstance.get(lastIndex);
	}
	
	public Item deleteItemByKey(long id) {
		return databaseInstance.remove(id);
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
		Set<Long> keySet = databaseInstance.keySet();
		Iterator<Long> iterator = keySet.iterator();
		while(iterator.hasNext()) {
			Long nextId = iterator.next();
			Item item = databaseInstance.get(nextId);
			if(item.getTitle().equalsIgnoreCase(title))
				return databaseInstance.remove(nextId); 
		}
		return null;
	}

	@Override
	public Set<Item> deleteAllItems() {
		Set<Item> set = new HashSet<Item>();
		Set<Long> keySet = databaseInstance.keySet();
		Iterator<Long> iterator = keySet.iterator();
		while(iterator.hasNext()) {
			Long nextId = iterator.next();
			set.add(databaseInstance.remove(nextId)); 
		}
		return set;
	}

	@Override
	public void updateItem(String oldTitle, String newTitle, String newBody) {
		Set<Long> keySet = databaseInstance.keySet();
		Iterator<Long> iterator = keySet.iterator();
		while(iterator.hasNext()) {
			Long nextId = iterator.next();
			Item item = databaseInstance.get(nextId);
			String titleInDb = item.getTitle();
			String bodyInDb = item.getBody();
			if(!titleInDb.equalsIgnoreCase(newTitle)) item.setTitle(newTitle);
			if(newBody != null && !bodyInDb.equalsIgnoreCase(newBody)) item.setBody(newBody);
		}
	}

	@Override
	public void markItemAsDone(String title) {
		Set<Long> keySet = databaseInstance.keySet();
		Iterator<Long> iterator = keySet.iterator();
		while(iterator.hasNext()) {
			Long nextId = iterator.next();
			Item item = databaseInstance.get(nextId);
			String titleInDb = item.getTitle();
			if(titleInDb.equalsIgnoreCase(title)) {
				item.setDone(true);
			}
		}
	}
	
	
}
