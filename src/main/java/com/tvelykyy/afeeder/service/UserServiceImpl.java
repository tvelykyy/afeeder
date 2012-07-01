package com.tvelykyy.afeeder.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tvelykyy.afeeder.dao.UserDAO;
import com.tvelykyy.afeeder.domain.Role;
import com.tvelykyy.afeeder.domain.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDAO userDAO;
	
	public Long addUser(User user) {
		return userDAO.addUser(user);
	}
	
	public List<Role> getUserRolesByLogin(String login) {
		return userDAO.getUserRolesByLogin(login);
	}
	
	public List<Role> getUserRolesById(Long id) {
		return userDAO.getUserRolesById(id);
	}
	
	public User getUserById(Long id, boolean withRoles){
		return userDAO.getUserById(id, withRoles);
	}
	
	public User getUserByLogin(String login, boolean withRoles){
		return userDAO.getUserByLogin(login, withRoles);
	}

}
