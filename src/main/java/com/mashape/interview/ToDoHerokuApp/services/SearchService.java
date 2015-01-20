package com.mashape.interview.ToDoHerokuApp.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.mashape.interview.ToDoHerokuApp.daos.ToDoListDao;
import com.mashape.interview.ToDoHerokuApp.daos.ToDoListDaoImplementation;
import com.mashape.interview.ToDoHerokuApp.databases.ToDoList;
import com.mashape.interview.ToDoHerokuApp.domains.Item;
import com.mashape.interview.ToDoHerokuApp.factories.JestFactory;
import com.mashape.interview.ToDoHerokuApp.observable.IObserver;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.DeleteByQuery;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.IndicesExists;

public class SearchService implements IObserver {

	private static SearchService instance = null;
	private static ToDoListDao dao = ToDoListDaoImplementation.getInstance();  
	private static JestClient jestClient = JestFactory.getJestClient();

	// The search index in elastic search is created during the creation of this class's instance
	public static SearchService getInstance() {
		if (instance == null) {
			instance = new SearchService();
			ToDoList.getInstance().addObserver(instance);
			createElasticSearchIndex();
		}
		return instance;
	}

	private static void createElasticSearchIndex() {
		try {
			// Create a new index if there is no index already
			IndicesExists indicesExists = new IndicesExists.Builder("items").build();
			JestResult result = jestClient.execute(indicesExists);
			
			if (!result.isSucceeded()) {
				// Create items index
				CreateIndex createIndex = new CreateIndex.Builder("items").build();
				jestClient.execute(createIndex);
	
				// Add data to be indexed
				addDataToBeIndexed();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void addDataToBeIndexed() {
		Set<Item> allItems = dao.getAllItems();
		Iterator<Item> iterator = allItems.iterator();
		while(iterator.hasNext()) {
			Item next = iterator.next();
			indexAnItem(next);
		}
	}
	
	public static void indexAnItem(Item source) {
		try {
			long id = source.getId();
			String s = String.valueOf(id);
			// Jest Id should be specified for building the index as id is of type long. String types needn't be specified explicitly. 
			jestClient.execute(new Index.Builder(source).index("items").type("item").id(s).build());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Set<Item> searchItems(String param) {
		try {
			/* This call makes sure the indices are in sync even if the index on the elastic search is deleted while the database has other items that are not added to the index.*/
			createElasticSearchIndex();
			
			// Search in the title first
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.query(QueryBuilders.matchQuery("title", param));
			Search search = (Search)new Search.Builder(searchSourceBuilder.toString())
					.addIndex("items").addType("item").build();
			JestResult result = jestClient.execute(search);
			List<Item> searchInTitleResultList = result.getSourceAsObjectList(Item.class);
			
			// Search in the body
			SearchSourceBuilder searchSourceBuilder2 = new SearchSourceBuilder();
			searchSourceBuilder2.query(QueryBuilders.matchQuery("body", param));
			Search search2 = (Search)new Search.Builder(searchSourceBuilder2.toString())
					.addIndex("items").addType("item").build();
			result = jestClient.execute(search2);
			List<Item> searchInBodyResultList = result.getSourceAsObjectList(Item.class);
			
			List<Item> items = new ArrayList<Item>();
			items.addAll(searchInTitleResultList);
			items.addAll(searchInBodyResultList);
			Set<Item> itemsSet = new HashSet<Item>(items);
			return itemsSet;

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String update(Item itemLastModified, int invokingOperation) {
		// invokingOperation=1 means a new record has been added to the database
		// invokingOperation=2 means a record has been updated in the database
		// invokingOperation=3 means an existing record has been deleted from the database
		// invokingOperation=3 + itemLastModified==null means all existing records have been deleted from the database
		// invokingOperation=4 means a record is marked as done
		switch(invokingOperation) {
		case 1: indexAnItem(itemLastModified); break;
		case 2: updateIndexOf(itemLastModified); break;
		case 3: if(itemLastModified == null) deleteAllIndexes();
				else deleteIndexOf(itemLastModified); 
				break;
		case 4: updateIndexOf(itemLastModified); break;
		default: break;
		}
		return "SEARCH";
	}

	private void deleteIndexOf(Item itemLastModified) {
		try {
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.query(QueryBuilders.matchQuery("title", itemLastModified.getTitle()));
			jestClient.execute(new DeleteByQuery.Builder(searchSourceBuilder.toString()).build());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void deleteAllIndexes() {
		
	}

	private void updateIndexOf(Item itemLastModified) {
		indexAnItem(itemLastModified);
	}
}
