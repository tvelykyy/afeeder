package com.tvelykyy.afeeder.domain;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.security.crypto.codec.Hex;

public class User extends BaseModel {
	private String login;
	private String password;
	private String name;
	private List<Role> roles;
	
	public User() {
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

	@Override
	public String toString() {
		return "User [login=" + login + ", password=" + password + ", name="
				+ name + ", roles=" + roles + ", id=" + id + "]";
	}
	
	public void hashPassword() {
		byte[] bytesOfPassword = null;
		try {
			bytesOfPassword = password.getBytes("UTF-8");
			MessageDigest md = null;
			try {
				md = MessageDigest.getInstance("MD5");
				byte[] thedigest = md.digest(bytesOfPassword);
				password = new String(Hex.encode(thedigest));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
	}
}
