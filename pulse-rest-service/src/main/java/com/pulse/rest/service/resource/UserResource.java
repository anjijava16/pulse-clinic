package com.pulse.rest.service.resource;

import java.util.List;

import com.pulse.model.User;
import javax.ws.rs.core.HttpHeaders;

/**
 *
 * @author befast
 */
public interface UserResource {
    public User update(User user);
    
    public User delete(long id);

    public User get(long id);
    
    public List<User> list();
    
    public User find(String username);
    
    public User find(HttpHeaders header);
}
