package com.tvelykyy.afeeder.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.util.ReflectionTestUtils;

import com.tvelykyy.afeeder.dao.UserDAO;
import com.tvelykyy.afeeder.domain.Role;
import com.tvelykyy.afeeder.domain.User;
import com.tvelykyy.afeeder.domain.utils.UserUtils;
import com.tvelykyy.afeeder.service.UserDetailsServiceImpl;

public class UserDetailsServiceTest {
	@Mock
	private UserDAO userDAO;
	
	@Before
	public void setup() {
		//This must be called for the @Mock annotations above to be processed.
		MockitoAnnotations.initMocks( this );
	}
	
	@Test
	public void testLoadUserByUsername() {
		User user = new User(new Long(1), "testLogin", "testPassword", "testName", new ArrayList<Role>());
		
		//First call returns user object, second call produces exception
		when(userDAO.getUserByLogin(user.getLogin(), true))
				.thenReturn(user)
				.thenThrow(new UsernameNotFoundException("User not found"));
		
		UserDetailsService service = new UserDetailsServiceImpl();
		//Manually setting services
		ReflectionTestUtils.setField(service, "userDAO", userDAO);
		
		//Testing successfull retrieving
		assertEquals(UserUtils.userToSecurityUser(user, new ArrayList<GrantedAuthorityImpl>()),
				service.loadUserByUsername(user.getLogin()));
		
		try {
			service.loadUserByUsername(user.getLogin());
			//Should never be executed
			assertEquals(1, 2);
		} catch (UsernameNotFoundException e) {
			//Just do nothing
		}
	}
}
