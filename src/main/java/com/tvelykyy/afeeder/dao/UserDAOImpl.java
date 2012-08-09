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
	
	private static final String GET_USER_BY_LOGIN_QUERY = "SELECT id, name, login, token, last_token_usage " +
			"FROM `user` WHERE login = ?";
	private static final String GET_USER_BY_ID_QUERY = "SELECT id, name, login, token, last_token_usage " +
			"FROM `user` WHERE id = ?";
	private static final String GET_USER_BY_LOGIN_WITH_PASSWORD_QUERY = "SELECT * FROM `user` WHERE login = ?";
	private static final String GET_USER_BY_ID_WITH_PASSWORD_QUERY = "SELECT * FROM `user` WHERE id = ?";
	private static final String GET_ROLES_BY_LOGIN = "SELECT r.id, r.name FROM `role` r " +
			"INNER JOIN `user_role` ur on r.id = ur.role_id " +
			"INNER JOIN `user` u on u.id = ur.user_id " +
			"WHERE u.login = ?";
	private static final String GET_ROLES_BY_ID = "SELECT r.id, r.name FROM `role` r " +
			"INNER JOIN `user_role` ur on r.id = ur.role_id " +
			"INNER JOIN `user` u on u.id = ur.user_id " +
			"WHERE u.id = ?";
	private static final String ADD_USER_QUERY = "INSERT INTO `user` (login, password, name) " +
			"VALUES (:login, :password, :name, NULL, NULL)";
	private static final String ASSIGN_ROLE_FOR_USER_QUERY = "INSERT INTO `user_role` (user_id, role_id) " +
			"VALUES (?, ?)";
	private static final String GENERATE_TOKEN_QUERY = "UPDATE `user` SET token = ?, last_token_usage = now() " +
			"WHERE id = ?";
	private static final String GET_LAST_TOKEN_USAGE_QUERY = "SELECT last_token_usage FROM user " +
			"WHERE id = ?";
	
	private static final String GET_TOKEN_QUERY = "SELECT token FROM user " +
			"WHERE id = ?";
	
	private static final String GET_USER_BY_TOKEN_QUERY = "SELECT id, login FROM `user` " +
			"WHERE token = ?";
	
	private static final String UPDATE_TOKEN_USAGE_QUERY = "UPDATE `user` SET last_token_usage = now() " +
			"WHERE id = ?";
	
	//Hardcoded
	private static final String GET_USER_ROLE_ID = "SELECT id FROM `role` WHERE name = 'ROLE_USER'";

	@Override
	public User getUserByLogin(String login, boolean withRoles, boolean withPassword) {
		logger.debug(MessageFormatter.format("Retrieving user login = {}", login));
		String query = withPassword ? GET_USER_BY_LOGIN_WITH_PASSWORD_QUERY : GET_USER_BY_LOGIN_QUERY;
		User user = getJdbcTemplate().queryForObject(query, new Object[]{login}, 
				new UserRowMapper());
		if (withRoles) {
			List<Role> roles = getUserRolesByLogin(login);
			user.setRoles(roles);
		}
		return user;
	}
	
	@Override
	public User getUserById(Long id, boolean withRoles, boolean withPassword) {
		logger.debug(MessageFormatter.format("Retrieving user id = {}", id));
		String query = withPassword ? GET_USER_BY_ID_WITH_PASSWORD_QUERY : GET_USER_BY_ID_QUERY;
		User user = getJdbcTemplate().queryForObject(query, new Object[]{id}, 
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
		List<Role> roles = getJdbcTemplate().query(GET_ROLES_BY_LOGIN, new Object[]{login}, 
				new RoleRowMapper());
		return roles;
	}
	
	@Override
	public List<Role> getUserRolesById(Long id) {
		logger.debug(MessageFormatter.format("Retrieving user roles id {} ", id));
		List<Role> roles = getJdbcTemplate().query(GET_ROLES_BY_ID, new Object[]{id}, 
				new RoleRowMapper());
		return roles;
	}
	
	@Transactional(readOnly = false)
	public Long addUser(User user) {
		logger.debug(MessageFormatter.format("Adding user = ", user));
		SqlParameterSource parameters = new BeanPropertySqlParameterSource(user);
	    KeyHolder keyHolder = new GeneratedKeyHolder();
	    
	    
	    getNamedParameterJdbcTemplate().update(ADD_USER_QUERY, parameters, keyHolder);
	    Long userId = keyHolder.getKey().longValue();
	    
	    JdbcTemplate jdbcTemplate = getJdbcTemplate();
	    Long roleId = jdbcTemplate.queryForLong(GET_USER_ROLE_ID);
	    jdbcTemplate.update(ASSIGN_ROLE_FOR_USER_QUERY, new Object[]{userId, roleId});
	    
	    return keyHolder.getKey().longValue();
	}

	@Override
	public String generateToken(User user) {
		StringBuilder digest = new StringBuilder();
		digest.append(user.getId());
		digest.append(user.getPassword());
		digest.append(new Date());
		String token = UserUtils.hashMD5(digest.toString());
		
		getJdbcTemplate().update(GENERATE_TOKEN_QUERY, new Object[]{token, user.getId()});
		
		return token;
	}

	@Override
	public Timestamp getTokenLastUsage(long userId) {
		return  getJdbcTemplate().queryForObject(GET_LAST_TOKEN_USAGE_QUERY, 
				new Object[]{userId}, Timestamp.class);
	}

	@Override
	public String getToken(long userId) {
		return  getJdbcTemplate().queryForObject(GET_TOKEN_QUERY, 
				new Object[]{userId}, String.class);
	}

	@Override
	public User getUserByToken(String token) {
		User user;
		try {
			user = getJdbcTemplate().queryForObject(GET_USER_BY_TOKEN_QUERY, new Object[]{token}, new UserRowMapper());
		} catch (Exception e) {
			user = null;
		}
		return user;
	}
	
	@Override
	public void updateTokenUsage(long userId) {
		getJdbcTemplate().update(UPDATE_TOKEN_USAGE_QUERY, new Object[]{userId});
	}
}
