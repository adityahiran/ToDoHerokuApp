package com.mashape.interview.ToDoHerokuApp.utilities;

public class ToDoAppConstants {

	private static ToDoAppConstants instance = null;
	private static final String DB_NAME = "todo-db";
	private static final String COLLECTION_NAME = "items";
	private static final String jestUrl= "https://site:feadc2f4d2e573c710cad584185a8965@bofur-us-east-1.searchly.com";

	public static ToDoAppConstants getInstance() {
		if(instance == null) {
			instance = new ToDoAppConstants();
			instance.initData();
		}
		return instance;
	}
	
	public String getJesturl() {
		return jestUrl;
	}

	public String getCollectionName() {
		return COLLECTION_NAME;
	}

	public String getDbName() {
		return DB_NAME;
	}
	
	public void initData() {
		// TODO: Create a properties file having all the values for the constants defined in this class.
	}
}
