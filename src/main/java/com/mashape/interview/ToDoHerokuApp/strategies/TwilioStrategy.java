package com.mashape.interview.ToDoHerokuApp.strategies;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;

import com.mashape.interview.ToDoHerokuApp.domains.Item;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

public class TwilioStrategy implements INotifyStrategy {

	private static TwilioStrategy instance = null;
	public static final String ACCOUNT_SID = "AC712fe2e4e8f5620f46d435a6dae8ab3e";//"ACad0b05acb0ff8d54e96a5684cdeed25d";
	public static final String AUTH_TOKEN = "e5f01f52d8d37dbb142366a095819875";//"0263a083588152039b192a5ff9143ff1";

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
		String url = "https://api.twilio.com/2010-04-01/Accounts/ACad0b05acb0ff8d54e96a5684cdeed25d/Messages.json";
		
		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder().credentials("ACad0b05acb0ff8d54e96a5684cdeed25d", "0263a083588152039b192a5ff9143ff1").build();
		client.register(feature);
		
		// Step2
		WebTarget target = client.target(url);
				
		MultivaluedHashMap<String, String> postForm = new MultivaluedHashMap<String, String>();
		postForm.add("From", "+19164321120");
		postForm.add("To", "+19167698514");
		postForm.add("Body", "Hello body");
		
		// Step3
		Response response = target.request().post(Entity.form(postForm));
		
		/*if((response.getStatus() == 200) || (response.getStatus() == 201)) return response.toString()+" successfully sent the notification.";
		return "Error sending the notification";*/
	}
}
