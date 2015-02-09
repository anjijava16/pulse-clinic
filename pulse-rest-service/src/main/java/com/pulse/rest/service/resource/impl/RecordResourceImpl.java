package com.pulse.rest.service.resource.impl;

import com.google.common.io.Files;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pulse.rest.logic.RecordService;
import com.pulse.model.Record;
import com.pulse.rest.service.resource.RecordResource;
import com.sun.jersey.core.util.Base64;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author Vladimir Shin <vladimir.shin@gmail.com>
 */
@Path("/record")
@Service(value = "recordResource")
public class RecordResourceImpl implements RecordResource {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private RecordService recordService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/id/{id}")
    @Override
    public Record getBy(@PathParam("id") Long id) {
        this.LOGGER.debug("getById(), id: {}", id);

        if (id == null || id < 0) {
            return null;
        }

        final Record record = this.recordService.getById(id);

        this.LOGGER.info("Record bean response: {}", record);

        return record;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/patientId/{patientId}")
    @Override
    public List<Record> listByPatientId(@PathParam("patientId") Long patientId) {
        this.LOGGER.debug("listByPatientId(), patientId: {}", patientId);

        if (patientId == null || patientId < 0) {
            return null;
        }

        final List<Record> recordsList = this.recordService.listBy(patientId);

        this.LOGGER.info("Records list response: {}", recordsList);

        return recordsList;
    }

    @POST
    @Path("/update")
    @Consumes(value = "application/json")
    @Override
    public void update(Record record) {
        this.LOGGER.debug("update(), record: {}", record);

        if (record == null) return;
        if (record.getName() == null) return;
        if (record.getPath() == null) return;

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
        
        final Record updatedRecord = this.recordService.update(record);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/binary/read/{id}")
    @Override
    public Record getWithBinaryBy(@PathParam("id") long id) {
        this.LOGGER.info("getWithBinaryBy()");
        
        final Record record = this.recordService.getById(id);
        final String filePath = record.getPath().concat(record.getName());        
        
        this.LOGGER.info("filePath: " + filePath);
        
        final File file = new File(filePath);
        this.LOGGER.info(""+file.exists());
        
        if (file.exists()) {
            try {
                byte[] fileBuffer = Files.toByteArray(file);

                record.setBinary(new String(Base64.encode(fileBuffer), "UTF-8"));
                
                return record;
            } catch (IOException ioe) {
                return null;
            }                        
        }
        
        return null;
    }

    @GET
    @Path("/delete/{id}")
    @Override
    public Response delete(@PathParam("id") long id) {
        this.LOGGER.info("delete, ", id);
        
        final Record record = this.recordService.delete(id);
        
        return Response.ok().build();
    }

}
