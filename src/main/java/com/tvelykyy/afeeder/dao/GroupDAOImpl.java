package com.tvelykyy.afeeder.dao;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tvelykyy.afeeder.domain.Group;
import com.tvelykyy.afeeder.domain.mapper.GroupRowMapper;

/**
 * UserDAO implementation
 */
@Repository
public class GroupDAOImpl extends AbstractDAO implements GroupDAO {	
	private static final Logger logger = LoggerFactory.getLogger(GroupDAOImpl.class);

	private static final String GET_ALL_GROUPS_QUERY = "SELECT * FROM `group`";
	private static final String ADD_GROUP_QUERY = "INSERT INTO `group` (name) VALUES (:name)";
	private static final String REMOVE_GROUP_QUERY = "DELETE FROM `group` WHERE id = ?";
	private static final String REMOVE_ACTIVITIES_BY_GROUP_QUERY = "DELETE FROM `activity` WHERE group_id = ?";
	private static final String EDIT_GROUP_QUERY = "UPDATE `group` SET name = (:name) WHERE id = (:id)";
	private static final String GET_GROUP_QUERY = "SELECT * from `group` WHERE id = ?";
	
	@Transactional
	public List<Group> listGroups() {
		logger.debug("Loading group list");
		
		return getJdbcTemplate().query(GET_ALL_GROUPS_QUERY, new GroupRowMapper());
	}
	
	@Transactional
	public Long addGroup(Group group) {
		logger.debug(MessageFormatter.format("Adding new group {}", group));
		
		SqlParameterSource parameters = new BeanPropertySqlParameterSource(group);
	    KeyHolder keyHolder = new GeneratedKeyHolder();
	    getNamedParameterJdbcTemplate().update(ADD_GROUP_QUERY, parameters, keyHolder);
	    
	    return keyHolder.getKey().longValue();
	}
	
	@Transactional(readOnly = false)
	public void removeGroup(Long id){
		logger.debug(MessageFormatter.format("Removing group id = {}", id));
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		jdbcTemplate.update(REMOVE_ACTIVITIES_BY_GROUP_QUERY, id);
		jdbcTemplate.update(REMOVE_GROUP_QUERY, id);
	}
	
	@Transactional
	public void editGroup(Group group) {
		logger.debug(MessageFormatter.format("Editing group = {}",group));
		
		SqlParameterSource parameters = new BeanPropertySqlParameterSource(group);
	    getNamedParameterJdbcTemplate().update(EDIT_GROUP_QUERY, parameters);
	}
	
	@Transactional
	public Group getGroup(Long id) {
		logger.debug(MessageFormatter.format("Retrieving group id = {}", id));
		try {
			return getJdbcTemplate().queryForObject(GET_GROUP_QUERY, new Object[] {id}, new GroupRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		
	}
}
