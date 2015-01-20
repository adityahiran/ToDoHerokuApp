package com.mashape.interview.ToDoHerokuApp.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ToDoAppConstants {

	private static ToDoAppConstants instance = null;
	private static final String DB_NAME = "todo-db";
	private static final String COLLECTION_NAME = "items";
	private static final String JEST_URL= "https://site:feadc2f4d2e573c710cad584185a8965@bofur-us-east-1.searchly.com";
	private static String TWILIO_ACCOUNT_SID = "ACad0b05acb0ff8d54e96a5684cdeed25d"; // This could be made more secure on a production level code.
	private static String TWILIO_ACCOUNT_PASSWORD = "0263a083588152039b192a5ff9143ff1";
	private static final String TWILIO_FROM_NUMBER = "+19164321120";
	private static final String TWILIO_TO_NUMBER = "+19168137782";
	private static String TWILIO_SMS_URL ="https://api.twilio.com/2010-04-01/Accounts/"+ToDoAppConstants.getInstance().getTwilioAccountSid()+"/Messages.json";
	private static final int CRUD_CREATE = 1;
	private static final int CRUD_UPDATE = 2;
	private static final int CRUD_MARK_AS_DONE = 4;
	private static final int CRUD_DELETE = 3;
	
	public int getCrudCreate() {
		return CRUD_CREATE;
	}

	public int getCrudUpdate() {
		return CRUD_UPDATE;
	}

	public int getCrudMarkAsDone() {
		return CRUD_MARK_AS_DONE;
	}

	public int getCrudDelete() {
		return CRUD_DELETE;
	}

	public String getTwilioSmsUrl() {
		return TWILIO_SMS_URL;
	}

	public String getTwilioFromNumber() {
		return TWILIO_FROM_NUMBER;
	}

	public String getTwilioToNumber() {
		return TWILIO_TO_NUMBER;
	}

	public String getTwilioAccountPassword() {
		return TWILIO_ACCOUNT_PASSWORD;
	}

	public String getTwilioAccountSid() {
		return TWILIO_ACCOUNT_SID;
	}

	public static ToDoAppConstants getInstance() {
		if(instance == null) {
			instance = new ToDoAppConstants();
		}
		return instance;
	}
	
	public String getJesturl() {
		return JEST_URL;
	}

	public String getCollectionName() {
		return COLLECTION_NAME;
	}

	public String getDbName() {
		return DB_NAME;
	}
}
