package com.pulse.rest.service.resource.impl;

import ch.qos.logback.core.status.Status;
import com.pulse.model.Patient;
import com.pulse.rest.logic.PatientService;
import com.pulse.rest.service.resource.PatientResource;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author befast
 */
@Path("/patient")
@Service(value = "patientResource")
public class PatientResourceImpl implements PatientResource {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private PatientService patientService;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get/{id}")
    @Override
    public Patient getBy(@PathParam("id") Long id) {
        this.LOGGER.debug("getById(), id: {}", id);
        return this.patientService.getBy(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get/token/{token}")
    @Override
    public Patient getBy(@PathParam("token") String token) {
        this.LOGGER.debug("getById(), token: {}", token);
        return this.patientService.getBy(token);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    @Override
    public List<Patient> listAll() {
        this.LOGGER.debug("listAll()");
        return this.patientService.list();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    public Response update(Patient patient) {
        this.LOGGER.debug("update()");
        boolean ok = this.patientService.update(patient);
        
        if (ok) return Response.ok().build();
        
        return Response.status(Status.ERROR).build();        
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete/{id}")
    @Override
    public Response deleteBy(@PathParam("id") Long id) {
        this.LOGGER.debug("update()");
        boolean ok = this.patientService.delete(id);
        
        if (ok) return Response.ok().build();
        
        return Response.status(Status.ERROR).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete/token/{token}")
    public Response deleteBy(@PathParam("token") String token) {
        this.LOGGER.debug("update()");
        boolean ok = this.patientService.delete(token);
        
        if (ok) return Response.ok().build();
        
        return Response.status(Status.ERROR).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list/pattern/{pattern}")
    @Override
    public List<Patient> listAll(@PathParam("pattern") String pattern) {
        this.LOGGER.debug("listAll(), {}", pattern);
        return this.patientService.list(pattern);
    }
}