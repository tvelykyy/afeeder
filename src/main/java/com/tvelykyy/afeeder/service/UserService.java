package com.tvelykyy.afeeder.service;

import java.util.List;

import com.tvelykyy.afeeder.domain.Role;
import com.tvelykyy.afeeder.domain.User;

public interface UserService {
	Long addUser(User user);
	List<Role> getUserRolesByLogin(String login);
	List<Role> getUserRolesById(Long id);
	User getUserById(Long id, boolean withRoles);
	User getUserByLogin(String login, boolean withRoles);
}
