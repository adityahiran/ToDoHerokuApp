package com.mashape.interview.ToDoHerokuApp.services;

import com.mashape.interview.ToDoHerokuApp.domains.Item;
import com.mashape.interview.ToDoHerokuApp.inmemorydatabase.ToDoList;
import com.mashape.interview.ToDoHerokuApp.observable.IObserver;
import com.mashape.interview.ToDoHerokuApp.strategies.INotifyStrategy;
import com.mashape.interview.ToDoHerokuApp.strategies.TwilioStrategy;

public class NotifyService implements IObserver {

	private static NotifyService instance = null;
	private static INotifyStrategy notifyStrategy = null;
	
	public static NotifyService getInstance() {
		if (instance == null) {
			instance = new NotifyService();
			ToDoList.getInstance().addObserver(instance);
			
			// Skipping the context as there is only one notify strategy we are going to use in the scope of this project.
			notifyStrategy = TwilioStrategy.getInstance();
		}
		return instance;
	}

	@Override
	public void update(Item lastModifiedItem, int invokingOperation) {
		boolean success = false;
		if((invokingOperation == 4) && (lastModifiedItem != null)) {
			success = notifyStrategy.sendNotification(lastModifiedItem);
		} 
		//if(success) ToDoList.addRecord("title5", "Created after twilio responded with a success code", false);
	}
}
