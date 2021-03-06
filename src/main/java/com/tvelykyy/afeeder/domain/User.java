package com.tvelykyy.afeeder.domain;

import java.sql.Timestamp;
import java.util.List;

public class User extends BaseModel {
	private String login;
	private String password;
	private String name;
	private List<Role> roles;
	private String token;
	private Timestamp lastTokenUsage;
	
	public User() {
	}
	
	public User(Long id) {
		this.id = id;
	}

	public User(Long id, String login, String password, String name) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.name = name;
	}
	
	public User(String login, String password, String name) {
		this.login = login;
		this.password = password;
		this.name = name;
	}
	
	public User(Long id, String login, String password, String name, List<Role> roles) {
		this(id, login, password, name);
		this.roles = roles;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Timestamp getLastTokenUsage() {
		return lastTokenUsage;
	}

	public void setLastTokenUsage(Timestamp lastTokenUsage) {
		this.lastTokenUsage = lastTokenUsage;
	}

	@Override
	public String toString() {
		return "User [login=" + login + ", password=" + password + ", name="
				+ name + ", roles=" + roles + ", token=" + token
				+ ", lastTokenUsage=" + lastTokenUsage + "]";
	}
}
