package com.tvelykyy.afeeder.service;

import java.util.List;

import com.tvelykyy.afeeder.domain.Role;
import com.tvelykyy.afeeder.domain.User;

public interface UserService {
	Long addUser(User user);
	List<Role> getUserRolesByLogin(String login);
	List<Role> getUserRolesById(Long id);
	User getUserById(Long id, boolean withRoles, boolean withPassword);
	User getUserByLogin(String login, boolean withRoles, boolean withPassword);
	User generateToken(User user);
	boolean isTokenValid(long userid);
	User getUserByToken(String token);
	void updateTokenUsage(long userId);
}
