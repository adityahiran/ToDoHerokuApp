package com.mashape.interview.ToDoHerokuApp.utilities;

public class ToDoAppConstants {

	private static ToDoAppConstants instance = null;
	private static final String DB_NAME = "todo-db";
	private static final String COLLECTION_NAME = "items";
	private static final String JEST_URL= "https://site:feadc2f4d2e573c710cad584185a8965@bofur-us-east-1.searchly.com";
	private static final String TWILIO_ACCOUNT_SID = "AC712fe2e4e8f5620f46d435a6dae8ab3e";
	private static final String TWILIO_ACCOUNT_PASSWORD = "0263a083588152039b192a5ff9143ff1";
	private static final String TWILIO_FROM_NUMBER = "+19164321120";
	private static final String TWILIO_TO_NUMBER = "+19168137782";
	private static final String TWILIO_SMS_URL = "https://api.twilio.com/2010-04-01/Accounts/"+ToDoAppConstants.getInstance().getTwilioAccountSid()+"/Messages.json";
	
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
			instance.initData();
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
	
	public void initData() {
		// TODO: Create a properties file having all the values for the constants defined in this class.
	}
}
