package com.mashape.interview.ToDoHerokuApp.factories;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

public class JestFactory {

	private static JestClient instance = null;

	public static JestClient getJestClient() {

		if (instance == null) {
			// Construct a new Jest client according to configuration via
			// factory
			String connectionUrl = "https://site:feadc2f4d2e573c710cad584185a8965@bofur-us-east-1.searchly.com";
			JestClientFactory factory = new JestClientFactory();
			factory.setHttpClientConfig(new HttpClientConfig.Builder(
					connectionUrl).multiThreaded(false).build());
			JestClient client = factory.getObject();
			return client;
		}
		return instance;
	}
}
