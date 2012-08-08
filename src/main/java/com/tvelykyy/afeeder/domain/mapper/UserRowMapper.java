package com.tvelykyy.afeeder.domain.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
 
import org.springframework.jdbc.core.RowMapper;

import com.tvelykyy.afeeder.domain.User;
 
public class UserRowMapper implements RowMapper<User>
{
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setId(rs.getLong("ID"));
		user.setName(rs.getString("NAME"));
		user.setPassword(rs.getString("PASSWORD"));
		user.setLogin(rs.getString("LOGIN"));
		user.setToken(rs.getString("TOKEN"));
		user.setLastTokenUsage(rs.getTimestamp("LAST_TOKEN_USAGE"));
		return user;
	}
}