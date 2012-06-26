package com.tvelykyy.afeeder.domain.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
 
import org.springframework.jdbc.core.RowMapper;

import com.tvelykyy.afeeder.domain.Group;
 
public class GroupRowMapper implements RowMapper<Group>
{
	public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
		Group group = new Group();
		group.setId(rs.getLong("ID"));
		group.setName(rs.getString("NAME"));
		return group;
	}
}