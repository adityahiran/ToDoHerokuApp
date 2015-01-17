package com.mashape.interview.ToDoHerokuApp.inmemorydatabase;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import org.elasticsearch.common.mvel2.optimizers.impl.refl.nodes.Notify;

import com.mashape.interview.ToDoHerokuApp.domains.Item;
import com.mashape.interview.ToDoHerokuApp.observable.IObservable;
import com.mashape.interview.ToDoHerokuApp.observable.IObserver;
import com.mashape.interview.ToDoHerokuApp.services.NotifyService;

public class ToDoList implements IObservable {

	// Singleton class having the database
	private static ToDoList instance = null;
	private static Hashtable<Long, Item> inMemoryDatabase = new Hashtable<Long, Item>();
	private static long lastIndex=0L;
	private static ArrayList<IObserver> observers = new ArrayList<IObserver>();
	private static boolean changed = false;
	private static NotifyService notifyService = NotifyService.getInstance();
	//operation = 1 for create; 2 for update; 3 for delete
	
	protected ToDoList() {
		// Doesn't allow instantiation of this class 
	}
	
	public static Hashtable<Long, Item> getDatabaseInstance() {
		return inMemoryDatabase;
	}
	
	public static Item addRecord(String title, String body, boolean done) {
		Item ret = null;

		lastIndex++;
		Item item = new Item(lastIndex, title, body, done);
		inMemoryDatabase.put(lastIndex, item);

		ret = inMemoryDatabase.get(lastIndex);
		setChanged(true, 1, ret);
		return ret;
	}
	
	public static Item updateItemById(long id, Item item) {
		Item ret = null;
		if(inMemoryDatabase.containsKey(id))
			ret = inMemoryDatabase.put(id, item);	
		setChanged(true, 2, ret);
		return ret;
	}
	
	public static Item deleteItemById(long id) {
		Item ret = inMemoryDatabase.remove(id);
		setChanged(true, 3, ret);
		return ret;
	}
	
	public static Item deleteItemByTitle(String title) {
		Item ret = null;
		Set<Long> keySet = inMemoryDatabase.keySet();
		Iterator<Long> iterator = keySet.iterator();
		while(iterator.hasNext()) {
			Long nextId = iterator.next();
			Item item = inMemoryDatabase.get(nextId);
			if(item.getTitle().equalsIgnoreCase(title)) {
				ret = inMemoryDatabase.remove(nextId);
				break;
			}
		}
		setChanged(true, 3, ret);
		return ret;
	}
	
	public static Set<Item> deleteAllItems() {
		// TODO Add a callback to delete the indexes on Elastic Search
		Set<Item> set = new HashSet<Item>();
		Set<Long> keySet = inMemoryDatabase.keySet();
		Iterator<Long> iterator = keySet.iterator();
		while(iterator.hasNext()) {
			Long nextId = iterator.next();
			set.add(inMemoryDatabase.remove(nextId)); 
		}
		setChanged(true, 3, null);
		return set;
	}
	
	public static Item updateItem(String oldTitle, String newTitle, String newBody) {
		Item ret = null;
		Set<Long> keySet = inMemoryDatabase.keySet();
		Iterator<Long> iterator = keySet.iterator();
		while(iterator.hasNext()) {
			Long nextId = iterator.next();
			Item item = inMemoryDatabase.get(nextId);
			String titleInDb = item.getTitle();
			String bodyInDb = item.getBody();
			if(oldTitle.equalsIgnoreCase(titleInDb)) {
				if(!titleInDb.equalsIgnoreCase(newTitle)) item.setTitle(newTitle);
				if(newBody != null && !bodyInDb.equalsIgnoreCase(newBody)) item.setBody(newBody);
				ret = item;
				break;
			}
		}
		return ret;
	}
	
	public static Item markItemAsDone(String title) {
		Item ret = null;
		Set<Long> keySet = inMemoryDatabase.keySet();
		Iterator<Long> iterator = keySet.iterator();
		while(iterator.hasNext()) {
			Long nextId = iterator.next();
			Item item = inMemoryDatabase.get(nextId);
			String titleInDb = item.getTitle();
			if(titleInDb.equalsIgnoreCase(title)) {
				item.setDone(true);
				ret = item;
				break;
			}
		}
		
		// Notify the observers about this change
		setChanged(true, 4, ret);
		//notifyService.update(ret, 4);
		return ret;
	}
	
	private static void initData() {
		lastIndex=0L;
		getInstance().getDatabaseInstance().put(lastIndex, new Item(lastIndex, "title1", "body1", true));
		lastIndex= lastIndex + 1L;
		getInstance().getDatabaseInstance().put(lastIndex, new Item(lastIndex, "title2", "body2", false));
		lastIndex= lastIndex + 1L;
		getInstance().getDatabaseInstance().put(lastIndex, new Item(lastIndex, "title3", "body2 title1", false));
	}
	
	 public static ToDoList getInstance() {
	      if(instance == null) {
	         instance = new ToDoList();
	         initData();
	      }
	      return instance;
	   }

	public long getLastIndex() {
		return lastIndex;
	}

	@Override
	public void addObserver(IObserver obs) {
		observers.add(obs);
	}

	public static boolean hasChanged() {
		return changed;
	}

	public static void setChanged(boolean hasChanged, int operation, Item lastModifiedItem) {
		ToDoList.changed = hasChanged;
		instance.notifyObservers(operation, lastModifiedItem);
	}
	
	@Override
	public void notifyObservers(int invokingOperation, Item lastModifiedItem) {
		Iterator<IObserver> iter = observers.iterator();
		while(iter.hasNext()) {
			IObserver observer = iter.next();
			observer.update(lastModifiedItem, invokingOperation);
		}
		changed = false;
	}

	public static Item getLastItem() {
		return inMemoryDatabase.get(lastIndex);
	}
}
