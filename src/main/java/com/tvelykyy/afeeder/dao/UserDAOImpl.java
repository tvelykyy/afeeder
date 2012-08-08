package com.tvelykyy.afeeder.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tvelykyy.afeeder.domain.Role;
import com.tvelykyy.afeeder.domain.User;
import com.tvelykyy.afeeder.domain.mapper.RoleRowMapper;
import com.tvelykyy.afeeder.domain.mapper.UserRowMapper;
import com.tvelykyy.afeeder.domain.utils.UserUtils;

/**
 * UserDAO implementation
 */
@Repository
public class UserDAOImpl extends AbstractDAO implements UserDAO {	
	private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
	
	private static final String getUserByLoginQuery = "SELECT * FROM `user` WHERE login = ?";
	private static final String getUserByIdQuery = "SELECT * FROM `user` WHERE id = ?";
	private static final String getUserRolesByLogin = "SELECT r.id, r.name FROM `role` r " +
			"INNER JOIN `user_role` ur on r.id = ur.role_id " +
			"INNER JOIN `user` u on u.id = ur.user_id " +
			"WHERE u.login = ?";
	private static final String getUserRolesById = "SELECT r.id, r.name FROM `role` r " +
			"INNER JOIN `user_role` ur on r.id = ur.role_id " +
			"INNER JOIN `user` u on u.id = ur.user_id " +
			"WHERE u.id = ?";
	private static final String addUserQuery = "INSERT INTO `user` (login, password, name) " +
			"VALUES (:login, :password, :name, NULL, NULL)";
	private static final String assignRoleForUserQuery = "INSERT INTO `user_role` (user_id, role_id) " +
			"VALUES (?, ?)";
	private static final String generateTokenQuery = "UPDATE `user` SET token = ?, last_token_usage = now() " +
			"WHERE id = ?";
	private static final String getTokenLastUsageQuery = "SELECT last_token_usage FROM user " +
			"WHERE id = ?";
	
	private static final String getTokenQuery = "SELECT token FROM user " +
			"WHERE id = ?";
	
	//Hardcoded
	private static final String getUserRoleId = "SELECT id FROM `role` WHERE name = 'ROLE_USER'";

	@Override
	public User getUserByLogin(String login, boolean withRoles) {
		logger.debug(MessageFormatter.format("Retrieving user login = {}", login));
		User user = getJdbcTemplate().queryForObject(getUserByLoginQuery, new Object[]{login}, 
				new UserRowMapper());
		if (withRoles) {
			List<Role> roles = getUserRolesByLogin(login);
			user.setRoles(roles);
		}
		return user;
	}
	
	@Override
	public User getUserById(Long id, boolean withRoles) {
		logger.debug(MessageFormatter.format("Retrieving user id = {}", id));
		User user = getJdbcTemplate().queryForObject(getUserByIdQuery, new Object[]{id}, 
				new UserRowMapper());
		if (withRoles) {
			List<Role> roles = getUserRolesById(id);
			user.setRoles(roles);
		}
		return user;
	}

	@Override
	public List<Role> getUserRolesByLogin(String login) {
		logger.debug(MessageFormatter.format("Retrieving user roles login = {}",login));
		List<Role> roles = getJdbcTemplate().query(getUserRolesByLogin, new Object[]{login}, 
				new RoleRowMapper());
		return roles;
	}
	
	@Override
	public List<Role> getUserRolesById(Long id) {
		logger.debug(MessageFormatter.format("Retrieving user roles id {} ", id));
		List<Role> roles = getJdbcTemplate().query(getUserRolesById, new Object[]{id}, 
				new RoleRowMapper());
		return roles;
	}
	
	@Transactional(readOnly = false)
	public Long addUser(User user) {
		logger.debug(MessageFormatter.format("Adding user = ", user));
		SqlParameterSource parameters = new BeanPropertySqlParameterSource(user);
	    KeyHolder keyHolder = new GeneratedKeyHolder();
	    
	    
	    getNamedParameterJdbcTemplate().update(addUserQuery, parameters, keyHolder);
	    Long userId = keyHolder.getKey().longValue();
	    
	    JdbcTemplate jdbcTemplate = getJdbcTemplate();
	    Long roleId = jdbcTemplate.queryForLong(getUserRoleId);
	    jdbcTemplate.update(assignRoleForUserQuery, new Object[]{userId, roleId});
	    
	    return keyHolder.getKey().longValue();
	}

	@Override
	public String generateToken(User user) {
		StringBuilder digest = new StringBuilder();
		digest.append(user.getId());
		digest.append(user.getPassword());
		digest.append(new Date());
		String token = UserUtils.hashMD5(digest.toString());
		
		getJdbcTemplate().update(generateTokenQuery, new Object[]{token, user.getId()});
		
		return token;
	}

	@Override
	public Timestamp getTokenLastUsage(long userId) {
		return  getJdbcTemplate().queryForObject(getTokenLastUsageQuery, 
				new Object[]{userId}, Timestamp.class);
	}

	@Override
	public String getToken(long userId) {
		return  getJdbcTemplate().queryForObject(getTokenQuery, 
				new Object[]{userId}, String.class);
	}

	@Override
	public User getUserByToken(String token) {
		return null;
	}	
	
	
}
