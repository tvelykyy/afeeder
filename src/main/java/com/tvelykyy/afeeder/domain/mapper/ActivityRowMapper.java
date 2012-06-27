package com.tvelykyy.afeeder.domain.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
 
import org.springframework.jdbc.core.RowMapper;

import com.tvelykyy.afeeder.domain.Activity;
import com.tvelykyy.afeeder.domain.Group;
import com.tvelykyy.afeeder.domain.User;
 
public class ActivityRowMapper implements RowMapper<Activity>
{
	public Activity mapRow(ResultSet rs, int rowNum) throws SQLException {
		Group group = new Group();
		group.setId(rs.getLong("GROUP_ID"));
		group.setName(rs.getString("GROUP_NAME"));
		
		User user = new User();
		user.setId(rs.getLong("USER_ID"));
		user.setName(rs.getString("USER_NAME"));
		
		Activity activity = new Activity();
		activity.setId(rs.getLong("ID"));
		activity.setText(rs.getString("TEXT"));
		activity.setGroup(group);
		activity.setUser(user);
		
		return activity;
	}
}