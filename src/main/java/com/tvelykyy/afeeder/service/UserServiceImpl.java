package com.tvelykyy.afeeder.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tvelykyy.afeeder.dao.UserDAO;
import com.tvelykyy.afeeder.domain.Role;
import com.tvelykyy.afeeder.domain.User;

@Service
public class UserServiceImpl implements UserService {
	//30 minutes in milliseconds
	private static final int EXPIRATION_TIME = 1800000;
	
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
	
	public User getUserById(Long id, boolean withRoles, boolean withPassword){
		return userDAO.getUserById(id, withRoles, withPassword);
	}
	
	public User getUserByLogin(String login, boolean withRoles, boolean withPassword){
		return userDAO.getUserByLogin(login, withRoles, withPassword);
	}

	@Override
	public User generateToken(User user) {
		String token = userDAO.generateToken(user);
		Timestamp lastTokenUsage = userDAO.getTokenLastUsage(user.getId());
		User lightUser = new User(user.getId());
		lightUser.setToken(token);
		lightUser.setLastTokenUsage(lastTokenUsage);
		return lightUser;
	}

	@Override
	public boolean isTokenValid(long userId) {
		String token = userDAO.getToken(userId);
		if (token != null) {
			Timestamp tokenLastUsage = userDAO.getTokenLastUsage(userId);
			Date currentTime = new Date();
			if (tokenLastUsage == null || Math.abs(currentTime.getTime() - tokenLastUsage.getTime()) < EXPIRATION_TIME) {
				return true;
			}
		}
		return false;
	}

	@Override
	public User getUserByToken(String token) {
		return userDAO.getUserByToken(token);
	}

}
