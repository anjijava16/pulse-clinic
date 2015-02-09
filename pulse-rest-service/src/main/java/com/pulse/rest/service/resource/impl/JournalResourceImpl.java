/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pulse.rest.service.resource.impl;

import com.google.common.io.Files;
import com.pulse.rest.logic.JournalService;
import com.pulse.model.Journal;
import com.pulse.rest.service.resource.JournalResource;
import com.sun.jersey.core.util.Base64;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
@Path("/journal")
@Service (value = "journalResource")
public class JournalResourceImpl implements JournalResource {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private JournalService journalService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get/{id}")
    @Override
    public Journal getBy(@PathParam("id") Long id) {
        this.LOGGER.debug("getBy(), id: {}", id);

        if (id == null || id < 0) {
            return null;
        }

        final Journal appointment = this.journalService.getById(id);

        this.LOGGER.info("Appointment bean response: {}", appointment);

        return appointment;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get/binary/{id}")
    @Override
    public Journal getWithBinaryBy(@PathParam("id") long id) {
        this.LOGGER.info("getWithBinaryBy(), ", id);
        
        final Journal appointment = this.journalService.getById(id);        
        this.LOGGER.info("filePath: " + appointment.getPath());
        
        final File file = new File(appointment.getPath());
                
        if (file.exists()) {
            try {
                byte[] fileBuffer = Files.toByteArray(file);

                appointment.setBinary(new String(Base64.encode(fileBuffer), "UTF-8"));
                
                return appointment;
            } catch (IOException ioe) {
                return null;
            }                        
        }
        
        return null;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list/")
    @Override
    public List<Journal> list() {
        this.LOGGER.debug("listByPatientId(), patientId: {}");

        final List<Journal> recordsList = this.journalService.list();

        this.LOGGER.info("Appointment list response: {}", recordsList);

        return recordsList;
    }

    @POST
    @Path("/update")
    @Consumes(value = "application/json")
    @Override
    public void update(Journal journal) {
        this.LOGGER.debug("update(), record: {}", journal);

        if (journal == null) return;
        if (journal.getName() == null) return;
        if (journal.getPath() == null) return;

        if (journal.getBinary() != null && !journal.getBinary().isEmpty()) {
            final String encodedString = journal.getBinary();
            byte[] decodedBuffer = Base64.decode(encodedString);
                        
            File file = new File(journal.getPath().substring(journal.getPath().lastIndexOf("/")));
            if (! file.exists()) {
                file.mkdirs();
            }
                        
            try {
                FileOutputStream outstream = new FileOutputStream(journal.getPath());
                outstream.write(decodedBuffer);
                outstream.flush();
                outstream.close();
            } catch (FileNotFoundException fnfe) {
                this.LOGGER.error(fnfe.getMessage());
                Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            } catch (IOException ioe) {
                this.LOGGER.error(ioe.getMessage());
                Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        }
        
        final Journal updatedRecord = this.journalService.update(journal);
    }

    @GET
    @Path("/delete/{id}")
    @Override
    public Response delete(@PathParam("id") long id) {
        this.LOGGER.info("delete, ", id);
        
        final Journal record = this.journalService.delete(id);
        
        return Response.ok().build();
    }
}
