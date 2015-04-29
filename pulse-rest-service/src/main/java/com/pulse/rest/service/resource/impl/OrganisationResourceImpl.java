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

import com.pulse.rest.logic.OrganisationService;
import com.pulse.model.Organisation;
import com.pulse.rest.service.resource.OrganisationResource;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
@Path("/organisation")
@Service(value = "organisationResource")
public class OrganisationResourceImpl implements OrganisationResource {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private OrganisationService organisationService;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get/{id}")
    @Override
    public Organisation getById(@PathParam("id") Long id) {
        this.LOGGER.debug("getById(), id: {}", id);

        if (id == null || id < 0) {
            return null;
        }

        final Organisation record = this.organisationService.getById(id);

        this.LOGGER.info("Record bean response: {}", record);

        return record;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    @Override
    public List<Organisation> listAll() {
        this.LOGGER.debug("listAll()");

        final List<Organisation> recordsList = this.organisationService.listAll();

        this.LOGGER.info("Records list response: {}", recordsList);

        return recordsList;
    }

    @POST
    @Consumes(value = "application/json")
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update")
    @Override
    public Organisation update(Organisation record) {
        this.LOGGER.debug("update(), record: {}", record);

        if (record == null) {
            return null;
        }

        final Organisation savedInstance = this.organisationService.update(record);

        return savedInstance;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete/{id}")
    @Override
    public Organisation deleteBy(@PathParam("id") Long id) {
        this.LOGGER.debug("getBy(), id: {}", id);

        if (id == null || id < 0) {
            return null;
        }

        final Organisation organisation = this.organisationService.delete(id);

        return organisation;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete/name/{name}")
    @Override
    public Organisation deleteBy(@PathParam("name") String name) {
        this.LOGGER.debug("getBy(), name: {}", name);

        if (name == null) {
            return null;
        }

        final Organisation organisation = this.organisationService.delete(name);

        return organisation;
    }

}

