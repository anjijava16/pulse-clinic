package com.pulse.rest.service.resource;

import com.pulse.model.FilteredVisit;
import com.pulse.model.SearchTemplate;
import com.pulse.model.Visit;
import java.util.List;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

/**
 *
 * @author befast
 */
public interface VisitResource {
    
    public List<Visit> listOrganisationsVisits(String from, String until);
    
    public List<Visit> listOrganisationsVisitsBy(String organisation, String from, String until);

    public Visit getById(long id);
    
    public Response update(Visit visit);    
    
    public List<Visit> findByDate(String dateBuffer);
    
    public List<Visit> findByTemplate(SearchTemplate template);
    
    public List<Visit> filterBy(int filterType, int type, String date);
    
    public List<Visit> filterBy(FilteredVisit filter);
    
    public Visit getWithBinaryById(long id);
    
    public Response delete(long id);
    
    public List<Visit> filterBy(int department, long doctor);
}