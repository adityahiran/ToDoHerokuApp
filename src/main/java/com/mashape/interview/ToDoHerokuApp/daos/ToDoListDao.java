package com.mashape.interview.ToDoHerokuApp.daos;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.mashape.interview.ToDoHerokuApp.domains.Item;

public interface ToDoListDao {

	public Set<Item> getAllItems();
	
	public Item getItemById(long id);
	
	public Item addItem(String title);
	
	public Item updateItemById(long id, Item item);
	
	public Item deleteItemByKey(long id);
	
	public Item searchItemById(long id);

	public Item getItemByTitle(String title);

	public Set<Item> getItemsByStatus(boolean status);

	public Item deleteItemByTitle(String title);
}