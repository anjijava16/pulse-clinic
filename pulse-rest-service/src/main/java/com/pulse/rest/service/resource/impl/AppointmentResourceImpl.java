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


import com.google.common.io.Files;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pulse.model.Appointment;
import com.pulse.rest.service.resource.AppointmentResource;
import com.pulse.rest.logic.AppointmentService;
import com.sun.jersey.core.util.Base64;
import java.io.FileOutputStream;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
@Path("/appointment")
@Service(value = "appointmentResource")
public class AppointmentResourceImpl implements AppointmentResource {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private AppointmentService appointmentService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get/{id}")
    @Override
    public Appointment getBy(@PathParam("id") Long id) {
        this.LOGGER.debug("getBy(), id: {}", id);

        if (id == null || id < 0) {
            return null;
        }

        final Appointment appointment = this.appointmentService.getById(id);

        this.LOGGER.info("Appointment bean response: {}", appointment);

        return appointment;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get/binary/{id}")
    @Override
    public Appointment getWithBinaryBy(@PathParam("id") long id) {
        this.LOGGER.info("getWithBinaryBy(), ", id);
        
        final Appointment appointment = this.appointmentService.getById(id);
        final String filePath = appointment.getPath().concat(appointment.getName());        
        
        this.LOGGER.info("filePath: " + filePath);
        
        final File file = new File(filePath);
                
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
    @Path("/list/{patientId}")
    @Override
    public List<Appointment> listByPatientId(@PathParam("patientId") Long patientId) {
        this.LOGGER.debug("listByPatientId(), patientId: {}", patientId);

        if (patientId == null || patientId < 0) {
            return null;
        }

        final List<Appointment> recordsList = this.appointmentService.listBy(patientId);

        this.LOGGER.info("Appointment list response: {}", recordsList);

        return recordsList;
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    public Response update(Appointment record) {
        this.LOGGER.debug("update(), record: {}", record);

        if (record == null) return Response.status(Status.BAD_REQUEST).build();
        if (record.getName() == null) return Response.status(Status.BAD_REQUEST).build();
        if (record.getPath() == null) return Response.status(Status.BAD_REQUEST).build();

        if (record.getBinary() != null && !record.getBinary().isEmpty()) {
            final String encodedString = record.getBinary();
            byte[] decodedBuffer = Base64.decode(encodedString);
                        
            File file = new File(record.getPath());
            if (! file.exists()) {
                file.mkdirs();
            }
                        
            try {
                FileOutputStream outstream = new FileOutputStream(record.getPath().concat(record.getName()));
                outstream.write(decodedBuffer);
                outstream.flush();
                outstream.close();
            } catch (FileNotFoundException fnfe) {
                this.LOGGER.error(fnfe.getMessage());
                Response.status(Status.INTERNAL_SERVER_ERROR).build();
            } catch (IOException ioe) {
                this.LOGGER.error(ioe.getMessage());
                Response.status(Status.INTERNAL_SERVER_ERROR).build();
            }
        }
        
        final Appointment updatedRecord = this.appointmentService.update(record);
        
        return Response.ok().build();
    }

    @GET
    @Path("/delete/{id}")
    @Override
    public Response delete(@PathParam("id") long id) {
        this.LOGGER.info("delete, ", id);
        
        final Appointment record = this.appointmentService.delete(id);
        
        return Response.ok().build();
    }
}