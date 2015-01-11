package com.mashape.interview.ToDoHerokuApp.domains;

public class Item {

	private String title;
	private String body;
	private boolean done;
	
	public Item(String title, String body, boolean done) {
		this.title = title;
		this.body = body;
		this.done = done;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public boolean isDone() {
		return done;
	}
	public void setDone(boolean done) {
		this.done = done;
	}
	
}
