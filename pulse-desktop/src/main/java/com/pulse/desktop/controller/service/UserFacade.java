package com.pulse.desktop.controller.service;

import com.pulse.model.User;
import com.pulse.rest.client.UserClient;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author befast
 */
public enum UserFacade {
    INSTANCE;
    
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private UserClient userService = new UserClient();
    
    private LinkedBlockingQueue<User> usersList = new LinkedBlockingQueue<>();
    
    private User applicationUser;
    
    private UserFacade() {
    }

    public User getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(User applicationUser) {
        this.applicationUser = applicationUser;
    }
    
    public void clear() {
        this.usersList.clear();
    }
    
    public void updateAll(List<User> list) {
        list.clear();
        
        list.stream().forEach((user) -> {
            try {
                this.usersList.put(user);
            } catch (InterruptedException ie) {
                this.LOGGER.error(ie.getMessage());
            }
        });        
    }
    
    public void add(User user) throws InterruptedException {
        this.usersList.put(user);
    }
    
    public User findBy(long id) {
        for (User user : this.usersList) {
            if (user.getId() == id) {
                return user;
            }
        }
        
        try {
            return this.userService.get(id);
        } catch (IOException ioe) {
            return null;
        }
    }
    
    public User findBy(String name) {
        for (User user : this.usersList) {
            if (user.getNfp().equals(name)) {
                return user;
            }
        }
        
        return null;
    }
}
