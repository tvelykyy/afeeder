package com.tvelykyy.afeeder.dao;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import com.tvelykyy.afeeder.domain.Activity;
import com.tvelykyy.afeeder.domain.Group;
import com.tvelykyy.afeeder.domain.mapper.ActivityRowMapper;
import com.tvelykyy.afeeder.domain.mapper.GroupRowMapper;

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
			"INNER JOIN `group` g ON g.id = a.group_id;";
	private String addActivityQuery = "INSERT INTO `activity` (text, user_id, group_id) VALUES(?, ?, ?)";
	
	@Override
	public List<Activity> listAllActivities() {
		logger.info("Loading activity list");
		
		return getJdbcTemplate().query(getAllActivitiesQuery, new ActivityRowMapper());
	}

	@Override
	public List<Activity> listLatestActivities(Long afterId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long addActivity(final Activity activity) {
		logger.info("Adding activity " + activity);
		
		PreparedStatementCreator psc = new PreparedStatementCreator() {
			@Override
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
	
	
	
}
