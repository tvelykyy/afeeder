package com.tvelykyy.afeeder.service;

import java.util.List;

import com.tvelykyy.afeeder.domain.Role;
import com.tvelykyy.afeeder.domain.User;

public interface UserService {
	Long addUser(User user);
	List<Role> getUserRoles(String login);
	User getUserById(Long id);
}
