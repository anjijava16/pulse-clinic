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


import java.util.List;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pulse.rest.logic.PatientRoomService;
import com.pulse.model.PatientRoom;
import com.pulse.rest.service.resource.PatientRoomResource;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
@Path("/room")
@Service(value = "patientRoomResource")
public class PatientRoomResourceImpl implements PatientRoomResource {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private PatientRoomService patientRoomService;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get/{id}")
    @Override
    public PatientRoom getById(@PathParam("id") Long id) {
        this.LOGGER.debug("getById(), id: {}", id);

        if (id == null || id < 0) {
            return null;
        }

        final PatientRoom record = this.patientRoomService.getById(id);

        this.LOGGER.info("Record bean response: {}", record);

        return record;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    @Override
    public List<PatientRoom> listAll() {
        this.LOGGER.debug("listAll()");

        final List<PatientRoom> recordsList = this.patientRoomService.listAll();

        this.LOGGER.info("Records list response: {}", recordsList);

        return recordsList;
    }

    @POST
    @Consumes(value = "application/json")
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update")
    @Override
    public PatientRoom update(PatientRoom record) {
        this.LOGGER.debug("update(), record: {}", record);

        if (record == null) {
            return null;
        }

        final PatientRoom savedInstance = this.patientRoomService.update(record);

        return savedInstance;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete/{id}")
    @Override
    public PatientRoom deleteBy(@PathParam("id") Long id) {
        this.LOGGER.debug("getBy(), id: {}", id);

        if (id == null || id < 0) {
            return null;
        }

        final PatientRoom organisation = this.patientRoomService.delete(id);

        return organisation;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete/name/{name}")
    @Override
    public PatientRoom deleteBy(@PathParam("name") String name) {
        this.LOGGER.debug("getBy(), name: {}", name);

        if (name == null) {
            return null;
        }

        final PatientRoom organisation = this.patientRoomService.delete(name);

        return organisation;
    }

}



