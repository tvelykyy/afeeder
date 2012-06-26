package com.tvelykyy.afeeder.dao;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.tvelykyy.afeeder.domain.Role;
import com.tvelykyy.afeeder.domain.User;
import com.tvelykyy.afeeder.domain.mapper.RoleRowMapper;
import com.tvelykyy.afeeder.domain.mapper.UserRowMapper;

/**
 * UserDAO implementation
 */
@Repository
public class UserDAOImpl extends AbstractDAO implements UserDAO {	
	private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
	private final String getUserByLoginQuery = "SELECT * FROM `user` WHERE login = ?";
	private final String getUserRolesByLogin = "SELECT r.id, r.name FROM `role` r " +
			"INNER JOIN `user_role` ur on r.id = ur.role_id " +
			"INNER JOIN `user` u on u.id = ur.user_id " +
			"WHERE u.login = ?";
	private final String addUserQuery = "INSERT INTO `user` (login, password, name)" +
			"VALUES (:login, :password, :name)";
	private final String assignRoleForUserQuery = "INSERT INTO `user_role` (user_id, role_id)" +
			"VALUES (?, ?)";
	//Hardcoded
	private final String getUserRoleId = "SELECT id FROM `role` WHERE name = 'ROLE_USER'";

	public User getUser(String login) {
		logger.info("UserDAO: retrieving user login = " + login);
		User user = getJdbcTemplate().queryForObject(getUserByLoginQuery, new Object[]{login}, 
				new UserRowMapper());
		List<Role> roles = getUserRoles(login);
		user.setRoles(roles);
		return user;
	}

	public List<Role> getUserRoles(String login) {
		logger.info("UserDAO: retrieving user roles login = " + login);
		List<Role> roles = getJdbcTemplate().query(getUserRolesByLogin, new Object[]{login}, 
				new RoleRowMapper());
		return roles;
	}
	
	public Long addUser(User user) {
		logger.info("UserDAO: adding user = " + user);
		SqlParameterSource parameters = new BeanPropertySqlParameterSource(user);
	    KeyHolder keyHolder = new GeneratedKeyHolder();
	    
	    
	    getNamedParameterJdbcTemplate().update(addUserQuery, parameters, keyHolder);
	    Long userId = keyHolder.getKey().longValue();
	    
	    JdbcTemplate jdbcTemplate = getJdbcTemplate();
	    Long roleId = jdbcTemplate.queryForLong(getUserRoleId);
	    jdbcTemplate.update(assignRoleForUserQuery, new Object[]{userId, roleId});
	    
	    return keyHolder.getKey().longValue();
	}
	
	
}
