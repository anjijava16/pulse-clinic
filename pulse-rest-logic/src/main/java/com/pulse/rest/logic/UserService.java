package com.pulse.rest.logic;

import com.pulse.model.User;
import java.util.List;

/**
 *
 * @author befast
 */
public interface UserService {
    public User update(User user);
    
    public User delete(long id);

    public User get(long id);
    
    public List<User> list();
    
    public User find(String username);
    
    public User find(String username, String password);
}
