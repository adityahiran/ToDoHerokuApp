package com.mashape.interview.ToDoHerokuApp.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.elasticsearch.index.query.FilteredQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import static org.elasticsearch.index.query.QueryBuilders.filteredQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import com.mashape.interview.ToDoHerokuApp.daos.ToDoListDao;
import com.mashape.interview.ToDoHerokuApp.daos.ToDoListDaoImplementation;
import com.mashape.interview.ToDoHerokuApp.domains.Item;
import com.mashape.interview.ToDoHerokuApp.factories.JestFactory;
import com.mashape.interview.ToDoHerokuApp.inmemorydatabase.ToDoList;
import com.mashape.interview.ToDoHerokuApp.inmemorydatabase.ToDoListMongo;
import com.mashape.interview.ToDoHerokuApp.observable.IObservable;
import com.mashape.interview.ToDoHerokuApp.observable.IObserver;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.Bulk.Builder;
import io.searchbox.core.Delete;
import io.searchbox.core.DeleteByQuery;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.IndicesExists;

public class SearchService implements IObserver {

	private static SearchService instance = null;
	private static ToDoListDao dao = ToDoListDaoImplementation.getInstance(); //ToDoListMongo.getInstance(); 
	private static JestClient jestClient = JestFactory.getJestClient();
	private static boolean initialized = false;

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
			JestResult result = jestClient.execute(new Index.Builder(source).index("items").type("item").id(s).build());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*public static void indexSampleItems() {

		
		
		
		 

		try {
			// Delete articles index if it is exists
			// DeleteIndex deleteIndex = new
			// DeleteIndex.Builder("articles").build();
			// jestClient.execute(deleteIndex);

			//DeleteIndex deleteIndex = new DeleteIndex.Builder("items").build();
			//jestClient.execute(deleteIndex);
			
			IndicesExists indicesExists = new IndicesExists.Builder("itemsNew")
					.build();
			JestResult result = jestClient.execute(indicesExists);

			if (!result.isSucceeded()) {
				// Create items index
				CreateIndex createIndex = new CreateIndex.Builder("itemsNew")
						.build();
				jestClient.execute(createIndex);
			}

			*//**
			 * if you don't want to use bulk api use below code in a loop.
			 *
			 * Index index = new
			 * Index.Builder(source).index("items").type("item").build();
			 * jestClient.execute(index);
			 *
			 *//*

			Item[] array = (Item[])allItems.toArray();
			Item x=array[0];
			Item y=array[1];
			
			
			Bulk bulk = new Bulk.Builder()
					.addAction(
							new Index.Builder(x).index("itemsNew")
									.type("item").build())
					.addAction(
							new Index.Builder(y).index("itemsNew")
									.type("item").build()).build();

			result = jestClient.execute(bulk);
			
			//Builder bulkBuilder = new Bulk.Builder();
			for(Object source: allItems) {		
				Index index = new Index.Builder(source).index("items").type("item").build();
				jestClient.execute(index);
				//bulkBuilder.addAction(index);
			}
			//Bulk bulk = new Bulk(bulkBuilder);
			//result = jestClient.execute(bulk);

			initialized=true;
		} catch (IOException e) {
			// logger.error("Indexing error", e);
			e.printStackTrace();
		} catch (Exception e) {
			// logger.error("Indexing error", e);
			e.printStackTrace();
		}

	}*/

	public Set<Item> searchItems(String param) {
		try {
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
			Set<Item> itemsSet = new HashSet(items);
			return itemsSet;

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void update(Item itemLastModified, int invokingOperation) {
		// invokingOperation=1 means a new record has been added to the database
		// invokingOperation=2 means a record has been updated in the database
		// invokingOperation=3 means an existing record has been deleted from the database
		// invokingOperation=3 + itemLastModified==null means all existing records have been deleted from the database
		switch(invokingOperation) {
		case 1: indexAnItem(itemLastModified); break;
		case 2: updateIndexOf(itemLastModified); break;
		case 3: if(itemLastModified == null) deleteAllIndexes();
				else deleteIndexOf(itemLastModified); 
				break;
		case 4: updateIndexOf(itemLastModified); break;
		default: break;
		}
	}

	private void deleteIndexOf(Item itemLastModified) {
		String id = String.valueOf(itemLastModified.getId());
		try {
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.query(QueryBuilders.matchQuery("title", itemLastModified.getTitle()));
			jestClient.execute(new DeleteByQuery.Builder(searchSourceBuilder.toString()).build());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void deleteAllIndexes() {
		// TODO Auto-generated method stub
		
	}

	private void updateIndexOf(Item itemLastModified) {
		indexAnItem(itemLastModified);
	}
}
