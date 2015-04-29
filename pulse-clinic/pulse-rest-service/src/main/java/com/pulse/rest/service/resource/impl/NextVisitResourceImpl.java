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


import com.pulse.rest.logic.NextVisitService;
import java.util.List;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pulse.model.NextVisit;
import com.pulse.rest.service.resource.NextVisitResource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
@Path("/next_visit")
@Service(value = "nextVisitResource")
public class NextVisitResourceImpl implements NextVisitResource {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private NextVisitService nextVisitService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/update/")
    @Override
    public Response update(NextVisit visit) {
        this.LOGGER.info("update()");
        
        if (visit == null) return Response.status(Status.BAD_REQUEST).build();        
        if (visit.getPatientId() < 0) return Response.status(Status.BAD_REQUEST).build();
        if (visit.getDoctorId() < 0) return Response.status(Status.BAD_REQUEST).build();
                
        this.nextVisitService.update(visit);
        
        return Response.status(Status.OK).build();
    }
        
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/find/date/{datebuffer}")
    @Override
    public List<NextVisit> findBy(@PathParam("datebuffer") String datebuffer) {
        this.LOGGER.info("findByDate(), {}" + datebuffer);
        
        return this.nextVisitService.findBy(datebuffer);
    }

    @GET
    @Path("/delete/{id}")
    @Override
    public Response delete(@PathParam("id") long id) {
        this.LOGGER.info("delete, ", id);
        
        final NextVisit visit =  this.nextVisitService.delete(id);
        
        return Response.ok().build();
    }
}