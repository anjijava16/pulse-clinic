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
package com.pulse.rest.service.resource.impl;


import com.pulse.rest.logic.UserService;
import com.pulse.model.User;
import com.pulse.rest.logic.util.LicenseHandler;
import com.pulse.rest.service.resource.UserResource;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
@Path("/user")
@Service(value = "userResource")
public class UserResourceImpl implements UserResource {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @POST
    @Path("/update")
    @Consumes(value = "application/json")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public User update(User user) {
        this.LOGGER.info("update(): {}", user);
        
        return this.userService.update(user);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete/id/{id}")
    @Override
    public User delete(@PathParam(value="id") long id) {
        this.LOGGER.info("delete(): ", id);
        
        return this.userService.delete(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get/id/{id}")
    @Override
    public User get(@PathParam(value="id") long id) {
        this.LOGGER.info("get(): ", id);
        
        return this.userService.get(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    @Override
    public List<User> list() {
        this.LOGGER.info("list()");
        return this.userService.list();
    }
    
    @GET
    @Path("/find")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public User find(@Context HttpHeaders header) {
        MultivaluedMap<String, String> headerMap = header.getRequestHeaders();
        //Map<String, Cookie> cookiesMap = header.getCookies();
        
        if (headerMap.get("username").size() > 1) return null;
        if (headerMap.get("password").size() > 1) return null;

        this.LOGGER.info("find(): {}, {}",
                headerMap.get("username").get(0), headerMap.get("password").get(0));

        try {
            this.LOGGER.info("access enabled: {}",
                    LicenseHandler.INSTANCE.isEnabled());

            if (! LicenseHandler.INSTANCE.isEnabled())
                return null;
        } catch (IOException ioe) {
            this.LOGGER.error("IOException: ", ioe);
            return null;
        } catch (ParseException pe) {
            this.LOGGER.info("ParseException: ", pe);
            return null;
        }

        final String username = headerMap.get("username").remove(0);
        final String password = headerMap.get("password").remove(0);
        
        this.LOGGER.info("exists(): {}, {}", username, password);
        
        if (username == null) return null;
        if (password == null) return null;
                        
        return this.userService.find(username, password);
    }

    @GET
    @Path("/find/username/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public User find(@PathParam("username") String username) {
        this.LOGGER.info("get(): ", username);
        
        return this.userService.find(username);
    }
}