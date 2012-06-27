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
	
	public List<Role> getUserRoles(String login) {
		return userDAO.getUserRolesByLogin(login);
	}
	
	public User getUserById(Long id){
		return userDAO.getUserById(id, false);
	}

}
