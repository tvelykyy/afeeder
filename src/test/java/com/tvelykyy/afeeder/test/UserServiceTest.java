package com.tvelykyy.afeeder.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.tvelykyy.afeeder.domain.Role;
import com.tvelykyy.afeeder.domain.User;
import com.tvelykyy.afeeder.service.UserService;

@ContextConfiguration(locations={"/data.xml", "/component-scan.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@Transactional
public class UserServiceTest {
	private final String PASSWORD_HASH = "5f4dcc3b5aa765d61d8327deb882cf99";
	private final String USER_ROLE = "ROLE_USER";
	
	@Autowired
	private UserService userService;
	
	@Test
	public void addUserTest() {
		User user = new User("testuser", "password", "TestUser");
		user.hashPassword();
		
		//User shouldn't have id on this stage
		assertNull(user.getId());
		
		Long id = userService.addUser(user);
		//Id shouldn't be null
		assertNotNull(id);
	}

	@Test
	public void getUserRoles() {
		User user = new User("testuser", "password", "TestUser");
		user.hashPassword();
		Long id = userService.addUser(user);
		user.setId(id);
		
		//By default, user is assigned one 'ROLE_USER' role
		List<Role> roles = new ArrayList<Role>();
		
		//Testing userService.getRolesById
		roles = userService.getUserRolesById(user.getId());
		
		assertEquals(1, roles.size());
		assertEquals(USER_ROLE, roles.get(0).getName());
		
		//Testing userService.getRolesByLogin
		roles = userService.getUserRolesByLogin(user.getLogin());
		
		assertEquals(1, roles.size());
		assertEquals(USER_ROLE, roles.get(0).getName());
	}
	
	@Test
	public void getUser(){
		User user = new User("testuser", "password", "TestUser");
		user.hashPassword();
		Long id = userService.addUser(user);
		user.setId(id);
		
		User fetchedUser = null;
		//Testing userService.getUserById
		fetchedUser = userService.getUserById(user.getId(), false);
		testUsersEquality(user, fetchedUser);
		
		//Testing userService.getUserByLogin
		fetchedUser = userService.getUserByLogin(user.getLogin(), false);
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