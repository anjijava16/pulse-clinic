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


import com.pulse.rest.logic.PowerService;
import com.pulse.rest.service.resource.PowerResource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
@Path("/power")
@Service(value = "powerResource")
public class PowerResourceImpl implements PowerResource {

    @Autowired
    private PowerService powerService;
    
    @GET
    @Path("/off")
    @Override
    public Response poweroff(@Context HttpHeaders header) {
        MultivaluedMap<String, String> headerMap = header.getRequestHeaders();
        
        if (headerMap.get("username").size() > 1) return Response.status(Status.BAD_REQUEST).build();
        if (headerMap.get("password").size() > 1) return Response.status(Status.BAD_REQUEST).build();
        
        final String username = headerMap.get("username").remove(0);
        final String password = headerMap.get("password").remove(0);
                        
        if (username == null) return Response.status(Status.BAD_REQUEST).build();
        if (password == null) return Response.status(Status.BAD_REQUEST).build();
                        
        this.powerService.poweroff(username, password);
        
        return Response.ok().build();
    }
    
}
