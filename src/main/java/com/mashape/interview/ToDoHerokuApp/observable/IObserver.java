package com.mashape.interview.ToDoHerokuApp.observable;

import com.mashape.interview.ToDoHerokuApp.domains.Item;

public interface IObserver {

	public void update (Item lastModifiedItem, int invokingOperation);
}
