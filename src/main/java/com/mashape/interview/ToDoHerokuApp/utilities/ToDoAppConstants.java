package com.mashape.interview.ToDoHerokuApp.utilities;

public class ToDoAppConstants {

	private static final String DB_NAME = "todo-db";
	private static final String COLLECTION_NAME = "items";

	public static String getCollectionName() {
		return COLLECTION_NAME;
	}

	public static String getDbName() {
		return DB_NAME;
	}
	
}
