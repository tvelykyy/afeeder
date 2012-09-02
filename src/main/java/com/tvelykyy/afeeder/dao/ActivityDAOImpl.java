package com.tvelykyy.afeeder.dao;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import com.tvelykyy.afeeder.domain.Activity;
import com.tvelykyy.afeeder.domain.mapper.ActivityRowMapper;

/**
 * ActivityDAO implementation
 */
@Repository
public class ActivityDAOImpl extends AbstractDAO implements ActivityDAO {	
	private static final Logger logger = LoggerFactory.getLogger(ActivityDAOImpl.class);

	private static final String GET_ALL_ACTIVITIES_QUERY = "SELECT a.id, text, user_id, u.name as user_name, group_id, " +
			"g.name as group_name " +
			"FROM `activity` a " +
			"INNER JOIN `user` u ON u.id = a.user_id " +
			"INNER JOIN `group` g ON g.id = a.group_id ";
	
	private static final String GET_ALL_ACTIVITIES_SORTED_QUERY = GET_ALL_ACTIVITIES_QUERY +
			"ORDER BY a.id DESC";
	private static final String ADD_ACTIVITY_QUERY = "INSERT INTO `activity` (text, user_id, group_id) VALUES(?, ?, ?)";
	private static final String GET_ALL_ACTIVITIES_AFTER_QUERY = GET_ALL_ACTIVITIES_QUERY +
			"WHERE a.id > ? ";
	private static final String GET_RANGE_ACTIVITIES_QUERY = GET_ALL_ACTIVITIES_AFTER_QUERY +
			"AND a.id < ?";
	private static final String FIND_ACTIVITIES_QUERY = GET_ALL_ACTIVITIES_QUERY +
			"WHERE a.text LIKE ?";
	
	private static final String GET_ACTIVITY_BY_ID = GET_ALL_ACTIVITIES_QUERY +
			"WHERE a.id = ?";
	
	public List<Activity> listAllActivities() {
		logger.debug("Loading activity list");
		
		return getJdbcTemplate().query(GET_ALL_ACTIVITIES_SORTED_QUERY, new ActivityRowMapper());
	}

	public List<Activity> listLatestActivities(Long afterId) {
		logger.debug(MessageFormatter.format("Loading activity list after id = {}", afterId));
		
		return getJdbcTemplate().query(GET_ALL_ACTIVITIES_AFTER_QUERY, new ActivityRowMapper() , new Object[] {afterId});
	}

	public Long addActivity(final Activity activity) {
		logger.debug(MessageFormatter.format("Adding activity {}", activity));
		
		PreparedStatementCreator psc = new PreparedStatementCreator() {
			public java.sql.PreparedStatement createPreparedStatement(
					java.sql.Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(ADD_ACTIVITY_QUERY);
				ps.setString(1, activity.getText());
				ps.setLong(2, activity.getUser().getId());
				ps.setLong(3, activity.getGroup().getId());
				return ps;
			}
		};
		
	    KeyHolder keyHolder = new GeneratedKeyHolder();
	    getJdbcTemplate().update(psc, keyHolder);	    
	    
	    return keyHolder.getKey().longValue();
	}
	
	public List<Activity> listRangeActivities(Long startId, Long endId) {
		logger.debug(MessageFormatter.format("Loading activity list start id = {} and end id = {}", startId, endId));
		
		return getJdbcTemplate().query(GET_RANGE_ACTIVITIES_QUERY, new ActivityRowMapper() , new Object[] {startId, endId});
	}
	
	public List<Activity> findActivities(String pattern) {
		logger.debug(MessageFormatter.format("Finding activities by pattern = {}", pattern));
		
		return getJdbcTemplate().query(FIND_ACTIVITIES_QUERY, new ActivityRowMapper(), new Object[] {"%" + pattern + "%"});
	}
	
	public Activity getActivityById(Long id) {
		logger.debug(MessageFormatter.format("Retrieving activity by id = {}", id));
		
		return getJdbcTemplate().queryForObject(GET_ACTIVITY_BY_ID, new ActivityRowMapper(), new Object[] {id});
	}
	
}
