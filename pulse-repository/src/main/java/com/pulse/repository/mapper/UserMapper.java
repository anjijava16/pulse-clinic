package com.pulse.repository.mapper;

import com.pulse.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 *
 * @author Vladimir Shin
 */
@Component(value = "userMapper")
public class UserMapper implements RowMapper<User> {
    
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException, EmptyResultDataAccessException {
        final User user = new User();
        
        user.setId(rs.getLong("id"));
        user.setBirthday(rs.getDate("birthday"));
        user.setNfp(rs.getString("nfp"));
        user.setPassword(rs.getString("password"));
        user.setPrivelegy(rs.getInt("privelegy"));
        user.setUsername(rs.getString("username"));
        
        return user;
    }
}

