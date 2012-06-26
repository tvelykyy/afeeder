package com.tvelykyy.afeeder.dao;

import java.util.List;

import com.tvelykyy.afeeder.domain.Role;
import com.tvelykyy.afeeder.domain.User;

public interface UserDAO {
	public User getUser(String login);
	public List<Role> getUserRoles(String login);
	public Long addUser(User user);
}
