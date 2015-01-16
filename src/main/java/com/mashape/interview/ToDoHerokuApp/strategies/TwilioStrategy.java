package com.mashape.interview.ToDoHerokuApp.strategies;

import java.util.ArrayList;
import java.util.List;

import com.mashape.interview.ToDoHerokuApp.domains.Item;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class TwilioStrategy implements INotifyStrategy {

	private static TwilioStrategy instance = null;
	public static final String ACCOUNT_SID = "ACad0b05acb0ff8d54e96a5684cdeed25d";
	public static final String AUTH_TOKEN = "0263a083588152039b192a5ff9143ff1";

	public static TwilioStrategy getInstance() {
		if (instance == null) {
			instance = new TwilioStrategy();
		}
		return instance;
	}

	@Override
	public void sendNotification(Item lastModiefiedItem) {

		TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

		// Build the parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("Body", lastModiefiedItem.getTitle() + " task has been marked done."));
	    params.add(new BasicNameValuePair("To", "+19167698514"));
		params.add(new BasicNameValuePair("From", "+19164321120"));

		MessageFactory messageFactory = client.getAccount().getMessageFactory();
		Message message;
		try {
			message = messageFactory.create(params);
		} catch (TwilioRestException e) {
			e.printStackTrace();
		}
	}
}
