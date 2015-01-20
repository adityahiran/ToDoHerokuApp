package com.mashape.interview.ToDoHerokuApp.factories;

import com.mashape.interview.ToDoHerokuApp.utilities.ToDoAppConstants;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

public class JestFactory {

	private static JestClient instance = null;

	public static JestClient getJestClient() {

		if (instance == null) {
			// Construct a new Jest client according to configuration via
			// factory (public static method in this case)
			String connectionUrl = ToDoAppConstants.getInstance().getJesturl();
			JestClientFactory factory = new JestClientFactory();
			factory.setHttpClientConfig(new HttpClientConfig.Builder(
					connectionUrl).multiThreaded(true).build());
			instance = factory.getObject();
		}
		return instance;
	}
}
