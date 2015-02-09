package com.pulse.rest.service.resource.impl;

import com.google.common.io.Files;
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

import com.pulse.rest.logic.VisitService;
import com.pulse.model.FilteredVisit;
import com.pulse.model.SearchTemplate;
import com.pulse.model.Visit;
import com.pulse.rest.service.resource.VisitResource;
import com.sun.jersey.core.util.Base64;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;


/**
 *
 * @author Vladimir Shin <vladimir.shin@gmail.com>
 */
@Path("/visit")
@Service(value = "visitResource")
public class VisitResourceImpl implements VisitResource {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private VisitService visitService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/update/")
    @Override
    public Response update(Visit visit) {
        this.LOGGER.info("update()");
        
        if (visit == null) return Response.status(Status.BAD_REQUEST).build();        
        if (visit.getFilepath() == null) return Response.status(Status.BAD_REQUEST).build();
        if (visit.getFilename() == null ) return Response.status(Status.BAD_REQUEST).build();
        
        if (visit.getBinary() != null && !visit.getBinary().isEmpty()) {
            final String encodedString = visit.getBinary();
            byte[] decodedBuffer = Base64.decode(encodedString);
                        
            File file = new File(visit.getFilepath());
            if (! file.exists()) {
                file.mkdirs();
            }
                        
            try {
                FileOutputStream outstream = new FileOutputStream(visit.getFilepath().concat(visit.getFilename()));
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
        
        this.visitService.update(visit);
        
        return Response.status(Status.OK).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/visit/id/{id}")
    @Override
    public Visit getById(@PathParam("id") long id) {
        this.LOGGER.info("getById()");
                
        return this.visitService.getById(id);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/binaryvisit/id/{id}")
    @Override
    public Visit getWithBinaryById(@PathParam("id") long id) {
        this.LOGGER.info("getWithBinaryById()");
        
        final Visit visit = this.visitService.getById(id);
        final String filePath = visit.getFilepath().concat(visit.getFilename());        
        
        this.LOGGER.info("filePath: " + filePath);
        
        final File file = new File(filePath);
        this.LOGGER.info(""+file.exists());
        
        if (file.exists()) {
            try {
                byte[] fileBuffer = Files.toByteArray(file);

                visit.setBinary(new String(Base64.encode(fileBuffer), "UTF-8"));
                
                return visit;
            } catch (IOException ioe) {
                return null;
            }                        
        }
        
        return null;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/organisation/list/{from}/{until}")
    @Override
    public List<Visit> listOrganisationsVisits(@PathParam("from") String from, @PathParam("until") String until) {
        this.LOGGER.info("listOrganisationsVisits()");
        
        return this.visitService.listOrganisationsVisits(from, until);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/organisation/list/{organisation}/{from}/{until}")
    @Override
    public List<Visit> listOrganisationsVisitsBy(@PathParam("organisation") String organisation, @PathParam("from") String from, @PathParam("until") String until) {
        this.LOGGER.info("listOrganisationsVisits(), {}" + organisation);
        
        return this.visitService.listOrganisationVisitBy(organisation, from, until);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/bydate/{datebuffer}")
    @Override
    public List<Visit> findByDate(@PathParam("datebuffer") String datebuffer) {
        this.LOGGER.info("findByDate(), {}" + datebuffer);
        
        return this.visitService.findByDate(datebuffer);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/bytemplate/")
    @Override
    public List<Visit> findByTemplate(SearchTemplate template) {        
        this.LOGGER.info("findByTemplate(), {}" + template);
        
        return this.visitService.findByTemplate(template.getTemplate());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/filter/{filterType}/{type}/{date}")
    @Override
    public List<Visit> filterBy(@PathParam("filterType") int filterType, @PathParam("type") int type, @PathParam("date") String date) {
        this.LOGGER.info("filterBy(): {}, {}, {} ", filterType, type, date);
        
        return this.visitService.filterBy(filterType, type, date);
    }

    @GET
    @Path("/delete/{id}")
    @Override
    public Response delete(@PathParam("id") long id) {
        this.LOGGER.info("delete, ", id);
        
        final Visit visit =  this.visitService.delete(id);
        
        return Response.ok().build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/filter/statistic")
    @Override
    public List<Visit> filterBy(FilteredVisit filter) {
        this.LOGGER.info("filterBy(): {}", filter);
        
        return this.visitService.filterBy(filter);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/filter/by/{department}/{doctor}")
    @Override
    public List<Visit> filterBy(@PathParam("department") int department, @PathParam("doctor") long doctor) {
        this.LOGGER.info("filterBy(): {}, {}", department, doctor);
        
        return this.visitService.filterBy(department, doctor);
    }
}

