package com.mashape.interview.ToDoHerokuApp.inmemorydatabase;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.mashape.interview.ToDoHerokuApp.daos.ToDoListDao;
import com.mashape.interview.ToDoHerokuApp.domains.Item;
import com.mashape.interview.ToDoHerokuApp.utilities.ToDoAppConstants;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class ToDoListMongo implements ToDoListDao {

	private static ToDoListMongo instance = null;
	private static MongoClient client = null;
	
	public static ToDoListMongo getInstance() {
	      if(instance == null) {
	         instance = new ToDoListMongo();
	      }
	      
	      if(client == null) {
	    	  try{
	    		  client = new MongoClient(new ServerAddress("todo-mongodb-0.todo-mashape-test.2487.mongodbdns.com"));
	    	  } catch(Exception e) {
	    		  e.printStackTrace();
	    	  }
	      }
	      
	      return instance;
	   }

	@Override
	public Set<Item> getAllItems() {
		String ret="";
		Set<Item> items = new HashSet<Item>();
		DB db = client.getDB(ToDoAppConstants.getDbName());
		DBCollection dbCollection = db.getCollection(ToDoAppConstants.getCollectionName());
		DBCursor allItems = dbCollection.find(new BasicDBObject(), new BasicDBObject("title",1).append("_id", 0));
		try {
			while(allItems.hasNext()) {
				BasicDBObject next = (BasicDBObject) 
				allItems.next();
				
				String idString = next.getString("id");
				long id = Long.parseLong(idString);
				String title = next.getString("title");
				String body = next.getString("body");
				String doneString = next.getString("done");
				boolean done = Boolean.parseBoolean(doneString);
				
				Item item = new Item(id,title,body,done);
				items.add(item);
			}
		} catch(Exception e) {
			e.printStackTrace();
			Item item = new Item(2L,"title1",e.getMessage(),false);
			items.add(item);
			return items;
		}
		finally {
			allItems.close();
		}
		Item item = new Item(1L,"title1","body1",false);
		items.add(item);
		return items;
	}

	@Override
	public Item getItemById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item addItem(String title, String body, boolean done) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item updateItemById(long id, Item item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item deleteItemByKey(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item searchItemById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item getItemByTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Item> getItemsByStatus(boolean status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item deleteItemByTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Item> deleteAllItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item updateItem(String oldTitle, String newTitle, String newBody) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item markItemAsDone(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item getLastItem() {
		// TODO Auto-generated method stub
		return null;
	}
}
