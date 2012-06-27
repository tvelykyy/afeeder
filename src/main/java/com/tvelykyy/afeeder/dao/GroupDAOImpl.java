package com.tvelykyy.afeeder.dao;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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

	private final String getAllGroupsQuery = "SELECT * FROM `group`";
	private final String addGroupQuery = "INSERT INTO `group` (name) VALUES (:name)";
	private final String removeGroupQuery = "DELETE FROM `group` WHERE id = ?";
	private final String editGroupQuery = "UPDATE `group` SET name = (:name) WHERE id = (:id)";
	private final String getGroupQuery = "SELECT * from `group` WHERE id = ?";
	
	public List<Group> listGroups() {
		logger.info("Loading group list");
		
		return getJdbcTemplate().query(getAllGroupsQuery, new GroupRowMapper());
	}
	
	public Long addGroup(Group group) {
		logger.info("Adding new group");
		
		SqlParameterSource parameters = new BeanPropertySqlParameterSource(group);
	    KeyHolder keyHolder = new GeneratedKeyHolder();
	    getNamedParameterJdbcTemplate().update(addGroupQuery, parameters, keyHolder);
	    
	    return keyHolder.getKey().longValue();
	}
	
	public void removeGroup(Long id){
		logger.info("Removing group id = " + id);
		
		getJdbcTemplate().update(removeGroupQuery, id);
	}
	
	public void editGroup(Group group) {
		logger.info("Editing group = " + group);
		
		SqlParameterSource parameters = new BeanPropertySqlParameterSource(group);
	    getNamedParameterJdbcTemplate().update(editGroupQuery, parameters);
	}
	
	public Group getGroup(Long id) {
		logger.info("Retrieving group id = " + id);
		return getJdbcTemplate().queryForObject(getGroupQuery, new Object[] {id}, new GroupRowMapper());
	}
	
	

}
