package com.tvelykyy.afeeder.dao;

import java.sql.Timestamp;
import java.util.List;

import com.tvelykyy.afeeder.domain.Role;
import com.tvelykyy.afeeder.domain.User;

public interface UserDAO {
	User getUserByLogin(String login, boolean withRoles);
	User getUserById(Long id, boolean withRoles);
	List<Role> getUserRolesByLogin(String login);
	List<Role> getUserRolesById(Long id);
	Long addUser(User user);
	String generateToken(User user);
	Timestamp getTokenLastUsage(long userid);
	User getUserByToken(String token);
	String getToken(long userId);
}
