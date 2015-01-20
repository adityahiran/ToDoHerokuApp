package com.mashape.interview.ToDoHerokuApp.strategies;

import java.io.File;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;

import com.mashape.interview.ToDoHerokuApp.databases.ToDoList;
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
		//String url = ToDoAppConstants.getInstance().getTwilioSmsUrl();
		String url = "https://api.twilio.com/2010-04-01/Accounts/ACad0b05acb0ff8d54e96a5684cdeed25d/Messages.json";
		
		//HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder().credentials(ToDoAppConstants.getInstance().getTwilioAccountSid(), ToDoAppConstants.getInstance().getTwilioAccountPassword()).build();
		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder().credentials("ACad0b05acb0ff8d54e96a5684cdeed25d", "0263a083588152039b192a5ff9143ff1").build();
		client.register(feature);
		
		// Step2
		WebTarget target = client.target(url);
				
		MultivaluedHashMap<String, String> postForm = new MultivaluedHashMap<String, String>();
		postForm.add("From", ToDoAppConstants.getInstance().getTwilioFromNumber());
		postForm.add("To", ToDoAppConstants.getInstance().getTwilioToNumber());
		postForm.add("Body", lastModiefiedItem.getTitle() + " has been marked as done on the todo list.");
		/*postForm.add("From", "+19164321120");
		postForm.add("To", "+19168137782");
		postForm.add("Body", "Hello body");*/
		
		// Step3
		target.request().post(Entity.form(postForm));
	}
}
