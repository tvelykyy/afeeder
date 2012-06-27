package com.tvelykyy.afeeder.dao;

import java.util.List;

import com.tvelykyy.afeeder.domain.Role;
import com.tvelykyy.afeeder.domain.User;

public interface UserDAO {
	public User getUserByLogin(String login, boolean withRoles);
	public User getUserById(Long id, boolean withRoles);
	public List<Role> getUserRolesByLogin(String login);
	public List<Role> getUserRolesById(Long id);
	public Long addUser(User user);
}
