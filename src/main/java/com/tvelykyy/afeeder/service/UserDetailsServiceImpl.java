package com.tvelykyy.afeeder.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tvelykyy.afeeder.dao.UserDAO;
import com.tvelykyy.afeeder.domain.Role;
import com.tvelykyy.afeeder.domain.SecurityUser;
import com.tvelykyy.afeeder.domain.User;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserDAO userDAO;
	
	@SuppressWarnings("unchecked")
	public UserDetails loadUserByUsername(String login)
			throws UsernameNotFoundException {
		
		//Get user with roles
		User user = userDAO.getUserByLogin(login, true);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		} else {
			Collection authorities = new ArrayList();
			for (Role role : user.getRoles()) {
				authorities.add(new GrantedAuthorityImpl(role.getName()));
			}
			return new SecurityUser(user.getLogin(), user.getPassword(), true, true, true, true,
					authorities, user.getName(), user.getId());
		}
	}	
}
