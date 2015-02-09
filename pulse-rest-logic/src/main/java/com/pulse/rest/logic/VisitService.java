package com.pulse.rest.logic;

import com.pulse.model.FilteredVisit;
import com.pulse.model.Visit;
import java.util.List;

/**
 *
 * @author befast
 */
public interface VisitService {
    
    public Visit getById(long id);
    
    public List<Visit> listBy(long patientId);
    
    public Visit update(Visit record);
    
    public Visit delete(long id);
    
    public List<Visit> listOrganisationVisitBy(String organisation, String from, String until);
    
    public List<Visit> listOrganisationsVisits(String from, String until);
    
    public List<Visit> findByDate(String dateBuffer);
    
    public List<Visit> findByTemplate(String template);
    
    public List<Visit> filterBy(int filterType, int type, String date);
    
    public List<Visit> filterBy(FilteredVisit filter);
    
    public List<Visit> filterBy(int department, long doctor);
}
