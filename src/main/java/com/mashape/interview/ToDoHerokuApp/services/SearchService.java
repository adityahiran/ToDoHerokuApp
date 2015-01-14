package com.mashape.interview.ToDoHerokuApp.services;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import static org.elasticsearch.index.query.QueryBuilders.filteredQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import com.mashape.interview.ToDoHerokuApp.daos.ToDoListDao;
import com.mashape.interview.ToDoHerokuApp.daos.ToDoListDaoImplementation;
import com.mashape.interview.ToDoHerokuApp.domains.Item;
import com.mashape.interview.ToDoHerokuApp.factories.JestFactory;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.Bulk.Builder;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.IndicesExists;

public class SearchService {

	private static SearchService instance = null;
	private static ToDoListDao dao = ToDoListDaoImplementation.getInstance();
	private static JestClient jestClient = JestFactory.getJestClient();
	private static boolean initialized = false;

	public static SearchService getInstance() {
		if (instance == null) {
			instance = new SearchService();
			indexSampleItems();
		}
		return instance;
	}

	public static void indexSampleItems() {

		// Initialize data for creating a search index
		Set<Item> allItems = dao.getAllItems();
		
		/*
		 * Item article1 = new Item(); article1.setId(6);
		 * article1.setDone(false);
		 * article1.setTitle("Develop the sample app given by Mashape");
		 * article1.setBody(
		 * "Homeland follows the story of Drizzt from around the time and circumstances of his birth and his upbringing amongst the drow (dark elves). "
		 * +
		 * "The book takes the reader into Menzoberranzan, the drow home city. From here, the reader follows Drizzt on his quest to follow his principles in a land where such "
		 * +
		 * "feelings are threatened by all his family including his mother Matron Malice. In an essence, the book introduces Drizzt Do'Urden,"
		 * +
		 * " one of Salvatore's more famous characters from the Icewind Dale Trilogy."
		 * );
		 * 
		 * Item article2 = new Item(); article2.setId(7);
		 * article2.setDone(true);
		 * article2.setTitle("Complete a brief tutorial on maven and heroku");
		 * article2.setBody(
		 * "The Lord of the Rings is an epic high fantasy novel written by English philologist and University of Oxford professor J. R. R. Tolkien. "
		 * +
		 * "The story began as a sequel to Tolkien's 1937 children's fantasy novel The Hobbit, but eventually developed into a much larger work. "
		 * +
		 * "It was written in stages between 1937 and 1949, much of it during World War II.[1] It is the third best-selling novel ever written, with over 150 million copies sold"
		 * );
		 */

		try {
			// Delete articles index if it is exists
			// DeleteIndex deleteIndex = new
			// DeleteIndex.Builder("articles").build();
			// jestClient.execute(deleteIndex);

			//DeleteIndex deleteIndex = new DeleteIndex.Builder("items").build();
			//jestClient.execute(deleteIndex);
			
			IndicesExists indicesExists = new IndicesExists.Builder("items")
					.build();
			JestResult result = jestClient.execute(indicesExists);

			if (!result.isSucceeded()) {
				// Create items index
				CreateIndex createIndex = new CreateIndex.Builder("items")
						.build();
				jestClient.execute(createIndex);
			}

			/**
			 * if you don't want to use bulk api use below code in a loop.
			 *
			 * Index index = new
			 * Index.Builder(source).index("items").type("item").build();
			 * jestClient.execute(index);
			 *
			 */

			Item[] array = (Item[])allItems.toArray();
			Item x=array[0];
			Item y=array[1];
			
			
			Bulk bulk = new Bulk.Builder()
					.addAction(
							new Index.Builder(x).index("items")
									.type("item").build())
					.addAction(
							new Index.Builder(y).index("items")
									.type("item").build()).build();

			result = jestClient.execute(bulk);
			
			//Builder bulkBuilder = new Bulk.Builder();
			/*for(int i = 0 ; i< allItems.size(); i++) {
				
				
				Index index = new Index.Builder(source).index("items").type("item").build();
				jestClient.execute(index);
				//bulkBuilder.addAction(index);
			}*/
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

	}

	public List<Item> searchItems(String param) {
		try {
			
			// Initialize index
			//indexSampleItems();
			if(!initialized) indexSampleItems();
			
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.query(QueryBuilders.queryString(param));

			// QueryBuilder queryBuilder = filteredQuery(termQuery("brief",
			// "jazz"));
			Search search = (Search)new Search.Builder(searchSourceBuilder.toString())
					.addIndex("items").addType("item").build();

			JestResult result = jestClient.execute(search);
			return result.getSourceAsObjectList(Item.class);

		} catch (IOException e) {
			// logger.error("Search error", e);
			e.printStackTrace();
		} catch (Exception e) {
			// logger.error("Search error", e);
			e.printStackTrace();
		}
		return null;
	}
}
