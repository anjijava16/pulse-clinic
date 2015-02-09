package com.pulse.repository.dao;

import com.pulse.model.User;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;

/**
 *
 * @author Vladimir Shin
 */
public interface UserDao {
    
    public User update(User user) throws SQLException, EmptyResultDataAccessException;
    
    public User delete(long id) throws SQLException, EmptyResultDataAccessException;

    public User get(long id) throws SQLException, EmptyResultDataAccessException;
    
    public List<User> list() throws SQLException, EmptyResultDataAccessException;
    
    public User find(String username, String password) throws SQLException, EmptyResultDataAccessException;
    
    public User find(String username) throws SQLException, EmptyResultDataAccessException;
}
