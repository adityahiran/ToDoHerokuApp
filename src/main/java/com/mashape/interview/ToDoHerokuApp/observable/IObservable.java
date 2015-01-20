package com.mashape.interview.ToDoHerokuApp.observable;

import com.mashape.interview.ToDoHerokuApp.domains.Item;

public interface IObservable {
	
	public void addObserver (IObserver obs);
	public void notifyObservers(int invokingOperation, Item lastModifiedItem);
}
