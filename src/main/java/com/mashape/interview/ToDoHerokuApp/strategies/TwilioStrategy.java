package com.mashape.interview.ToDoHerokuApp.strategies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		
		 // Get the account and call factory class
        Account acct = client.getAccount();
        SmsFactory smsFactory = acct.getSmsFactory();

        //build map of server admins
        Map<String,String> admins = new HashMap<String,String>();
        admins.put("9164321120", "Poonam");

        String fromNumber = "916-432-1120";

    	// Iterate over all our server admins
        for (String toNumber : admins.keySet()) {
            
            //build map of post parameters 
            Map<String,String> params = new HashMap<String,String>();
            params.put("From", fromNumber);
            params.put("To", toNumber);
            params.put("Body", "Bad news " + admins.get(toNumber) + ", the server is down and it needs your help");

            try {
                // send an sms a call  
                // ( This makes a POST request to the SMS/Messages resource)
                Sms sms = smsFactory.create(params);
                System.out.println("Success sending SMS: " + sms.getSid());
            }
            catch (TwilioRestException e) {
                e.printStackTrace();
            }
        }
	}
}
