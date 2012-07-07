package com.tvelykyy.afeeder.dao;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final String getAllGroupsQuery = "SELECT * FROM `group`";
	private static final String addGroupQuery = "INSERT INTO `group` (name) VALUES (:name)";
	private static final String removeGroupQuery = "DELETE FROM `group` WHERE id = ?";
	private static final String removeActivitiesByGroupQuery = "DELETE FROM `activity` WHERE group_id = ?";
	private static final String editGroupQuery = "UPDATE `group` SET name = (:name) WHERE id = (:id)";
	private static final String getGroupQuery = "SELECT * from `group` WHERE id = ?";
	
	@Transactional
	public List<Group> listGroups() {
		logger.info("Loading group list");
		
		return getJdbcTemplate().query(getAllGroupsQuery, new GroupRowMapper());
	}
	
	@Transactional
	public Long addGroup(Group group) {
		logger.info("Adding new group");
		
		SqlParameterSource parameters = new BeanPropertySqlParameterSource(group);
	    KeyHolder keyHolder = new GeneratedKeyHolder();
	    getNamedParameterJdbcTemplate().update(addGroupQuery, parameters, keyHolder);
	    
	    return keyHolder.getKey().longValue();
	}
	
	@Transactional(readOnly = false)
	public void removeGroup(Long id){
		logger.info("Removing group id = " + id);
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		jdbcTemplate.update(removeActivitiesByGroupQuery, id);
		jdbcTemplate.update(removeGroupQuery, id);
	}
	
	@Transactional
	public void editGroup(Group group) {
		logger.info("Editing group = " + group);
		
		SqlParameterSource parameters = new BeanPropertySqlParameterSource(group);
	    getNamedParameterJdbcTemplate().update(editGroupQuery, parameters);
	}
	
	@Transactional
	public Group getGroup(Long id) {
		logger.info("Retrieving group id = " + id);
		try {
			return getJdbcTemplate().queryForObject(getGroupQuery, new Object[] {id}, new GroupRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		
	}
}
