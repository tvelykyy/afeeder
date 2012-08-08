package com.tvelykyy.afeeder.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tvelykyy.afeeder.dao.UserDAO;
import com.tvelykyy.afeeder.domain.Role;
import com.tvelykyy.afeeder.domain.User;
import com.tvelykyy.afeeder.domain.utils.UserUtils;

public class UserDAOTest extends BaseTest {
	private final String PASSWORD_HASH = "5f4dcc3b5aa765d61d8327deb882cf99";
	private final String USER_ROLE = "ROLE_USER";
	
	@Autowired
	private UserDAO userDAO;
	
	@Test
	public void addUserTest() {
		User user = new User("testuser", "password", "TestUser");
		user.setPassword(UserUtils.hashMD5(user.getPassword()));
		
		//User shouldn't have id on this stage
		assertNull(user.getId());
		
		Long id = userDAO.addUser(user);
		//Id shouldn't be null
		assertNotNull(id);
	}

	@Test
	public void getUserRoles() {
		User user = new User("testuser", "password", "TestUser");
		user.setPassword(UserUtils.hashMD5(user.getPassword()));
		Long id = userDAO.addUser(user);
		user.setId(id);
		
		//By default, user is assigned one 'ROLE_USER' role
		List<Role> roles = new ArrayList<Role>();
		
		//Testing userDAO.getRolesById
		roles = userDAO.getUserRolesById(user.getId());
		
		assertEquals(1, roles.size());
		assertEquals(USER_ROLE, roles.get(0).getName());
		
		//Testing userDAO.getRolesByLogin
		roles = userDAO.getUserRolesByLogin(user.getLogin());
		
		assertEquals(1, roles.size());
		assertEquals(USER_ROLE, roles.get(0).getName());
	}
	
	@Test
	public void getUser(){
		User user = new User("testuser", "password", "TestUser");
		user.setPassword(UserUtils.hashMD5(user.getPassword()));
		Long id = userDAO.addUser(user);
		user.setId(id);
		
		User fetchedUser = null;
		//Testing userDAO.getUserById
		fetchedUser = userDAO.getUserById(user.getId(), false);
		testUsersEquality(user, fetchedUser);
		
		//Testing userDAO.getUserByLogin
		fetchedUser = userDAO.getUserByLogin(user.getLogin(), false);
		testUsersEquality(user, fetchedUser);
	}

	private void testUsersEquality(User user, User fetchedUser) {
		assertEquals(user.getId(), fetchedUser.getId());
		assertEquals(user.getPassword(), fetchedUser.getPassword());
		assertEquals(PASSWORD_HASH, fetchedUser.getPassword());
		assertEquals(user.getLogin(), fetchedUser.getLogin());
		assertEquals(user.getName(), fetchedUser.getName());
	}
}