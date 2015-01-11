package com.mashape.interview.ToDoHerokuApp.inmemorydatabase;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import com.mashape.interview.ToDoHerokuApp.domains.Item;

public class ToDoList {

	private static Hashtable<Long, Item> inMemoryDatabase = new Hashtable<Long, Item>();
	private static long lastIndex=0;
	
	public ToDoList() {
		initData();
	}
	
	private void initData() {
		inMemoryDatabase.put(++lastIndex, new Item("title1", "body1", true));
		inMemoryDatabase.put(++lastIndex, new Item("title2", "body2", false));
	}
	
	public Set<Item> getAllItems() {
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
	
	public Item addItem(Item item) {
		lastIndex++;
		inMemoryDatabase.put(lastIndex, item);
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
}
