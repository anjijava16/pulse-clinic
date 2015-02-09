package com.pulse.repository;

import com.pulse.model.User;
import com.pulse.repository.dao.UserDao;
import com.pulse.repository.mapper.UserMapper;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author befast
 */
@Repository (value = "userRepository")
public class UserRepository implements UserDao {

    @Autowired
    private UserMapper userMapper;
    
    @Value("${repository.user.table.name:user}")
    private String tableName;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
        
    public UserRepository() {        
    }
    
    @Override
    public User update(User user) throws SQLException, EmptyResultDataAccessException {
        if (user == null) return null;
                
        final String updateQuery = "UPDATE " + this.tableName + " SET nfp=?,birthday=?,username=?,"
                + "password=?,privelegy=? WHERE id=?";
        
        final String createQuery = "INSERT INTO " + this.tableName + " (nfp,birthday,username,"
                + "password,privelegy) VALUES (?,?,?,?,?)";
        
        // Create record logic
        if (user.getId() == -1) {
            int updated = this.jdbcTemplate.update(createQuery, 
                    user.getNfp(), 
                    user.getBirthday(), 
                    user.getUsername(),
                    user.getPassword(),
                    user.getPrivelegy());
        }
        
        // Update record logic
        else {
            int updated = this.jdbcTemplate.update(updateQuery, 
                    user.getNfp(), 
                    user.getBirthday(), 
                    user.getUsername(),
                    user.getPassword(),
                    user.getPrivelegy(),
                    user.getId());
        }
        
        return user;
    }

    @Override
    public User delete(long id) throws SQLException, EmptyResultDataAccessException {
        final String getQuery = "SELECT * FROM " + this.tableName + " WHERE id=?";
        final String deleteQuery = "DELETE FROM " + this.tableName + " WHERE id=?";
        
        final User user = this.jdbcTemplate.queryForObject(getQuery, new Long[] {id}, this.userMapper);

        if (user != null) {
            this.jdbcTemplate.update(deleteQuery, id);
        }
        
        return user;
    }

    @Override
    public User get(long id) throws SQLException, EmptyResultDataAccessException {
        final String query = "SELECT * FROM " + this.tableName + " WHERE id=?";
        
        final User user = this.jdbcTemplate.queryForObject(query, new Long[] {id}, this.userMapper);

        return user;
    }

    @Override
    public List<User> list() throws SQLException, EmptyResultDataAccessException {
        final String query = "SELECT * FROM " + this.tableName;
        
        final List<User> list = this.jdbcTemplate.query(query, this.userMapper);

        return list;
    }

    @Override
    public User find(String username, String password) throws SQLException, EmptyResultDataAccessException {
        final String query = "SELECT * FROM " + this.tableName + " WHERE username=? AND password=?";
        
        final User user = this.jdbcTemplate.queryForObject(query, new String[] {username, password}, this.userMapper);

        return user;
    }   
    
    @Override
    public User find(String username) throws SQLException, EmptyResultDataAccessException {
        final String query = "SELECT * FROM " + this.tableName + " WHERE username=?";
        
        final User user = this.jdbcTemplate.queryForObject(query, new String[] {username}, this.userMapper);

        return user;
    }   
}