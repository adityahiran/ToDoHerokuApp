package com.mashape.interview.ToDoHerokuApp.daos;

import java.util.Set;

import com.mashape.interview.ToDoHerokuApp.domains.Item;

public class ToDoListDaoMongoImplementation implements ToDoListDao {

	@Override
	public Set<Item> getAllItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item getItemById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item addItem(String title, String body, boolean done) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item updateItemById(long id, Item item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item deleteItemByKey(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item searchItemById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item getItemByTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Item> getItemsByStatus(boolean status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item deleteItemByTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Item> deleteAllItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item updateItem(String oldTitle, String newTitle, String newBody) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String markItemAsDone(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item getLastItem() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
