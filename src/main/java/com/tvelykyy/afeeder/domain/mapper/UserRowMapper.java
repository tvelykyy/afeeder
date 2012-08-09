package com.tvelykyy.afeeder.domain.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.tvelykyy.afeeder.domain.User;
 
public class UserRowMapper implements RowMapper<User>
{
	private static final Logger logger = LoggerFactory.getLogger(UserRowMapper.class);
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		User user = new User();
		try {
			user.setId(rs.getLong("ID"));
			user.setName(rs.getString("NAME"));
			user.setPassword(rs.getString("PASSWORD"));
			user.setLogin(rs.getString("LOGIN"));
			user.setToken(rs.getString("TOKEN"));
			user.setLastTokenUsage(rs.getTimestamp("LAST_TOKEN_USAGE"));
		//handling unfound columns names in result set
		} catch (SQLException e) {
			logger.debug(e.getMessage());
		}
		return user;
	}
}