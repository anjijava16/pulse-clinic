/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author Vladimir Shin <vladimir.shin@gmail.com>
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