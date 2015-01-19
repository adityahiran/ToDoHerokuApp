package com.mashape.interview.ToDoHerokuApp.observable;

import com.mashape.interview.ToDoHerokuApp.domains.Item;

public interface IObservable {
	
	public void addObserver (IObserver obs);
	public String notifyObservers(int invokingOperation, Item lastModifiedItem);
}
