package com.tvelykyy.afeeder.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tvelykyy.afeeder.dao.UserDAO;
import com.tvelykyy.afeeder.domain.Role;
import com.tvelykyy.afeeder.domain.User;
import com.tvelykyy.afeeder.domain.utils.UserUtils;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserDAO userDAO;
	
	public UserDetails loadUserByUsername(String login)
			throws UsernameNotFoundException {
		
		//Get user with roles
		User user = userDAO.getUserByLogin(login, true);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		} else {
			List<GrantedAuthorityImpl> authorities = new ArrayList<GrantedAuthorityImpl>();
			for (Role role : user.getRoles()) {
				authorities.add(new GrantedAuthorityImpl(role.getName()));
			}
			return UserUtils.userToSecurityUser(user, authorities);
		}
	}	
}
