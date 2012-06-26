package com.tvelykyy.afeeder.domain;

public class Role extends BaseModel {
	private String name;

	public Role() {
	}

	public Role(Long id, String name) {
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
		return "Role [name=" + name + ", id=" + id + "]";
	}
}
