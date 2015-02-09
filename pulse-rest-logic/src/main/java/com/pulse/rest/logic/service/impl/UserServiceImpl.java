package com.pulse.rest.logic.service.impl;

import com.pulse.rest.logic.UserService;
import com.pulse.model.User;
import com.pulse.repository.UserRepository;
import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author befast
 */
@Service (value = "userService")
public class UserServiceImpl implements UserService {
    
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public User update(User user) {
        User updatedUser = null;
        
        try {
            updatedUser = this.userRepository.update(user);
        } catch (SQLException sqle) {
            this.LOGGER.error(sqle.getMessage());
        } catch (EmptyResultDataAccessException erdae) {
            // This exception will be throwed when there will be no
            // records in the database table and ResultSet in RecordMapper
            // will be null
            this.LOGGER.error(erdae.getMessage());
        }
        
        return updatedUser;
    }

    @Override
    public User delete(long id) {
        User user = null;
        
        try {
            user = this.userRepository.delete(id);
        } catch (SQLException sqle) {
            this.LOGGER.error(sqle.getMessage());
        } catch (EmptyResultDataAccessException erdae) {
            // This exception will be throwed when there will be no
            // records in the database table and ResultSet in RecordMapper
            // will be null
            this.LOGGER.error(erdae.getMessage());
        }
        
        return user;
    }

    @Override
    public User get(long id) {
        User user = null;
        
        try {
            user = this.userRepository.get(id);
        } catch (SQLException sqle) {
            this.LOGGER.error(sqle.getMessage());
        } catch (EmptyResultDataAccessException erdae) {
            // This exception will be throwed when there will be no
            // records in the database table and ResultSet in RecordMapper
            // will be null
            this.LOGGER.error(erdae.getMessage());
        }
        
        return user;
    }

    @Override
    public List<User> list() {
        List<User> list = null;
        
        try {
            list = this.userRepository.list();
        } catch (SQLException sqle) {
            this.LOGGER.error(sqle.getMessage());
        } catch (EmptyResultDataAccessException erdae) {
            // This exception will be throwed when there will be no
            // records in the database table and ResultSet in RecordMapper
            // will be null
            this.LOGGER.error(erdae.getMessage());
        }
        
        return list;
    }

    @Override
    public User find(String username, String password) {        
        try {
            return this.userRepository.find(username, password);
        } catch (SQLException sqle) {
            this.LOGGER.error(sqle.getMessage());
        } catch (EmptyResultDataAccessException erdae) {
            // This exception will be throwed when there will be no
            // records in the database table and ResultSet in RecordMapper
            // will be null
            this.LOGGER.error(erdae.getMessage());
        }
        
        return null;
    }

    public User find(String username) {
        try {
            return this.userRepository.find(username);
        } catch (SQLException sqle) {
            this.LOGGER.error(sqle.getMessage());
        } catch (EmptyResultDataAccessException erdae) {
            // This exception will be throwed when there will be no
            // records in the database table and ResultSet in RecordMapper
            // will be null
            this.LOGGER.error(erdae.getMessage());
        }
        
        return null;
    }

}


