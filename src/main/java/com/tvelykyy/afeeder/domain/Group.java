package com.tvelykyy.afeeder.domain;

public class Group extends BaseModel {
	private String name;
	
	public Group() {
	}

	public Group(String name) {
		this.name = name;
	}
	
	public Group(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Group [name=" + name + ", id=" + id + "]";
	}
}
