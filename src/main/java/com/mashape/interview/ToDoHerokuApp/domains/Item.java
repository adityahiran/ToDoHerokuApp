package com.mashape.interview.ToDoHerokuApp.domains;

import io.searchbox.annotations.JestId;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;

@XmlRootElement
public class Item {

	@JsonIgnore
	@JestId
	private long id;
	private String title;
	private String body;
	private boolean done;
	
	public Item() {}
	
	public Item(long id, String title, String body, boolean done) {
		this.id = id;
		this.title = title;
		this.body = body;
		this.done = done;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
	
	@Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
            // if deriving: appendSuper(super.hashCode()).
            append(id).
            append(title).
            append(body).
            append(done).
            toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
       if (!(obj instanceof Item))
            return false;
        if (obj == this)
            return true;

        Item that = (Item) obj;
        return new EqualsBuilder().
            // if deriving: appendSuper(super.equals(obj)).
            append(this.id, that.id).
            append(this.title, that.title).
            append(this.body, that.body).
            append(this.done, that.done).
            isEquals();
    }
	
}
