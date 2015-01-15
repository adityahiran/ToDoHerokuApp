package com.mashape.interview.ToDoHerokuApp.inmemorydatabase;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import com.mashape.interview.ToDoHerokuApp.domains.Item;

public class ToDoList {

	private static ToDoList instance = null;
	private static Hashtable<Long, Item> inMemoryDatabase = new Hashtable<Long, Item>();
	private static long lastIndex=0;
	
	protected ToDoList() {
		// Doesn't allow instantiation of this class 
	}
	
	public static Hashtable<Long, Item> getDatabaseInstance() {
		return inMemoryDatabase;
	}
	
	private static void initData() {
		lastIndex=0L;
		getInstance().getDatabaseInstance().put(lastIndex, new Item("title1", "body1", true));
		lastIndex+=1L;
		getInstance().getDatabaseInstance().put(lastIndex, new Item("title2", "body2", false));
	}
	
	/*public Set<Item> getAllItems() {
		Set<Item> items = new HashSet<Item>();
		Set<Long> keySet = inMemoryDatabase.keySet();
		Iterator<Long> iter = keySet.iterator();
		while(iter.hasNext()) {
			Long next = iter.next();
			Item item = inMemoryDatabase.get(next);
			items.add(item);
		}
		return items;
	}
	
	public Item getItemById(long id) {
		return inMemoryDatabase.get(id);
	}
	
	public Item addItem(String title) {
		lastIndex++;
		inMemoryDatabase.put(lastIndex, new Item(title, "", false));
		return inMemoryDatabase.get(lastIndex);
	}
	
	public Item updateItemById(long id, Item item) {
		if(inMemoryDatabase.containsKey(id))
			inMemoryDatabase.put(id, item);	
		else
			System.out.println("Error");
		
		return inMemoryDatabase.get(lastIndex);
	}
	
	public Item deleteItemByKey(long id) {
		return inMemoryDatabase.remove(id);
	}
	
	public Item searchItemById(long id) {
		return inMemoryDatabase.get(id);
	}

	public Item getItemByTitle(String title) {
		Set<Long> keySet = inMemoryDatabase.keySet();
		Iterator<Long> iterator = keySet.iterator();
		while(iterator.hasNext()) {
			Long nextId = iterator.next();
			Item item = inMemoryDatabase.get(nextId);
			if(item.getTitle().equalsIgnoreCase(title))
				return item; 
		}
		return null;
	}

	public Set<Item> getItemsByStatus(boolean status) {
		Set<Item> items = new HashSet<Item>();
		Set<Long> keySet = inMemoryDatabase.keySet();
		Iterator<Long> iterator = keySet.iterator();
		while(iterator.hasNext()) {
			Long nextId = iterator.next();
			Item item = inMemoryDatabase.get(nextId);
			if(item.isDone()==status)
				items.add(item);
		}
		return items;
	}

	public Item deleteItemByTitle(String title) {
		Set<Long> keySet = inMemoryDatabase.keySet();
		Iterator<Long> iterator = keySet.iterator();
		while(iterator.hasNext()) {
			Long nextId = iterator.next();
			Item item = inMemoryDatabase.get(nextId);
			if(item.getTitle().equalsIgnoreCase(title))
				return inMemoryDatabase.remove(nextId); 
		}
		return null;
	}*/
	
	 public static ToDoList getInstance() {
	      if(instance == null) {
	         instance = new ToDoList();
	         initData();
	      }
	      return instance;
	   }

	public long getLastIndex() {
		return lastIndex;
	}
}
