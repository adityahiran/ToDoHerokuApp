package com.mashape.interview.ToDoHerokuApp.strategies;

import com.mashape.interview.ToDoHerokuApp.domains.Item;

public interface INotifyStrategy {

	public boolean sendNotification(Item item);
}
