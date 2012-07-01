package com.tvelykyy.afeeder.dao;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private String getAllActivitiesQuery = "SELECT a.id, text, user_id, u.name as user_name, group_id, " +
			"g.name as group_name " +
			"FROM `activity` a " +
			"INNER JOIN `user` u ON u.id = a.user_id " +
			"INNER JOIN `group` g ON g.id = a.group_id ";
	
	private String getAllActivitiesSortedQuery = getAllActivitiesQuery +
			"ORDER BY a.id DESC";
	private String addActivityQuery = "INSERT INTO `activity` (text, user_id, group_id) VALUES(?, ?, ?)";
	private String getActivitiesAfterQuery = getAllActivitiesQuery +
			"WHERE a.id > ? ";
	private String getRangeActivitiesQuery = getActivitiesAfterQuery +
			"AND a.id < ?";
	
	public List<Activity> listAllActivities() {
		logger.info("Loading activity list");
		
		return getJdbcTemplate().query(getAllActivitiesSortedQuery, new ActivityRowMapper());
	}

	public List<Activity> listLatestActivities(Long afterId) {
		logger.info("Loading activity list after id = " + afterId);
		
		return getJdbcTemplate().query(getActivitiesAfterQuery, new ActivityRowMapper() , new Object[] {afterId});
	}

	public Long addActivity(final Activity activity) {
		logger.info("Adding activity " + activity);
		
		PreparedStatementCreator psc = new PreparedStatementCreator() {
			public java.sql.PreparedStatement createPreparedStatement(
					java.sql.Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(addActivityQuery);
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
		logger.info(String.format("Loading activity list start id = %s and end id = %s", startId, endId));
		
		return getJdbcTemplate().query(getRangeActivitiesQuery, new ActivityRowMapper() , new Object[] {startId, endId});
	}
	
	
	
}
