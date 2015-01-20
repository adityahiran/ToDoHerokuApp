package com.mashape.interview.ToDoHerokuApp.strategies;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;

import com.mashape.interview.ToDoHerokuApp.domains.Item;
import com.mashape.interview.ToDoHerokuApp.utilities.ToDoAppConstants;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

public class TwilioStrategy implements INotifyStrategy {

	private static TwilioStrategy instance = null;

	public static TwilioStrategy getInstance() {
		if (instance == null) {
			instance = new TwilioStrategy();
		}
		return instance;
	}

	@Override
	public void sendNotification(Item lastModiefiedItem) {
		
		// Step1
		Client client = ClientBuilder.newClient();
		String url = ToDoAppConstants.getInstance().getTwilioSmsUrl();
		
		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder().credentials(ToDoAppConstants.getInstance().getTwilioAccountSid(), ToDoAppConstants.getInstance().getTwilioAccountPassword()).build();
		client.register(feature);
		
		// Step2
		WebTarget target = client.target(url);
				
		MultivaluedHashMap<String, String> postForm = new MultivaluedHashMap<String, String>();
		postForm.add("From", ToDoAppConstants.getInstance().getTwilioFromNumber());
		postForm.add("To", ToDoAppConstants.getInstance().getTwilioToNumber());
		postForm.add("Body", lastModiefiedItem.getTitle() + " has been marked as done on the todo list.");
		
		// Step3
		target.request().post(Entity.form(postForm));
	}
}
