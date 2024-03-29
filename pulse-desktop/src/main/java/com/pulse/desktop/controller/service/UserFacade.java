/*
 * Copyright 2015 Vladimir Shin
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.pulse.desktop.controller.service;


import java.io.IOException;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pulse.model.User;
import com.pulse.rest.client.UserClient;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public enum UserFacade {
    INSTANCE;
    
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final UserClient userService = new UserClient();
    private final LinkedBlockingQueue<User> usersList = new LinkedBlockingQueue<>();
    
    private User applicationUser;

    public User getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(User applicationUser) {
        this.applicationUser = applicationUser;
    }
    
    public void clear() {
        this.usersList.clear();
    }
    
    public void updateAll(final List<User> list) {
        list.clear();
        
        list.stream().forEach((user) -> {
            try {
                this.usersList.put(user);
            } catch (InterruptedException ie) {
                this.LOGGER.error(ie.getMessage());
            }
        });        
    }
    
    public void add(final User user) throws InterruptedException {
        this.usersList.put(user);
    }
    
    public User findBy(final long id) {
        for (final User user : this.usersList) {
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
    
    public User findBy(final String name) {
        for (final User user : this.usersList) {
            if (user.getNfp().equals(name)) {
                return user;
            }
        }
        
        return null;
    }
}
