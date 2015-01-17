package com.mashape.interview.ToDoHerokuApp.strategies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.mashape.interview.ToDoHerokuApp.domains.Item;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.factory.SmsFactory;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.Message;
import com.twilio.sdk.resource.instance.Sms;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
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

	/*@Override
	public boolean sendNotification(Item lastModiefiedItem) {

		TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
	    // Build a filter for the MessageList
	    List<NameValuePair> params = new ArrayList<NameValuePair>();
	    params.add(new BasicNameValuePair("Body", "Jenny please?! I love you <3"));
	    params.add(new BasicNameValuePair("To", "+19167698514"));
	    params.add(new BasicNameValuePair("From", "+15005550006"));

	    try {
	    	MessageFactory messageFactory = client.getAccount().getMessageFactory();
	        Message message = messageFactory.create(params);
	        System.out.println(message.getSid());
	        boolean status = false;
	        //message.getStatus().equalsIgnoreCase("queued");
			if(message.getSid()!=null) status = true;
	        return status;
		} catch (TwilioRestException e) {
			e.printStackTrace();
			return false;
		}
	}*/
	
	@Override
	public boolean sendNotification(Item lastModiefiedItem) {
		
		// Step1
		Client client = ClientBuilder.newClient();
		String url = "https://api.twilio.com/2010-04-01/Accounts/AC712fe2e4e8f5620f46d435a6dae8ab3e/Messages.json";
		
		
		
		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder().credentials("AC712fe2e4e8f5620f46d435a6dae8ab3e", "e5f01f52d8d37dbb142366a095819875").build();
		client.register(feature);
		
		// Step2
		WebTarget target = client.target(url);
				
		MultivaluedHashMap<String, String> postForm = new MultivaluedHashMap<String, String>();
		postForm.add("From", "+15005550006");
		postForm.add("To", "+19168137782");
		postForm.add("Body", "Hello body");
		
		// Step3
		Response response = target.request().post(Entity.form(postForm));
		
		
		if((response.getStatus() == 200) || (response.getStatus() == 201)) return true;
		return false;
	}
}
