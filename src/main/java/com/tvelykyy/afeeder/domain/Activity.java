package com.tvelykyy.afeeder.domain;

import org.apache.solr.client.solrj.beans.Field;

public class Activity extends BaseModel {
	private User user;
	private Group group;
	
	@Field
	private String text;
	
	public Activity() {
	}

	public Activity(Long id, User user, Group group, String text) {
		this.id = id;
		this.user = user;
		this.group = group;
		this.text = text;
	}
	
	public Activity(User user, Group group, String text) {
		this.user = user;
		this.group = group;
		this.text = text;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "Activity [user=" + user + ", group=" + group + ", text=" + text
				+ ", id=" + id + "]";
	}	
}
