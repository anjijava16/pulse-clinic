package com.pulse.repository.dao;

import com.pulse.model.FilteredVisit;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;

import com.pulse.model.Visit;


/**
 *
 * @author befast
 */
public interface VisitDao {
    
    public List<Visit> findByDate(String dateBuffer) throws SQLException, EmptyResultDataAccessException;
    
    public Visit get(long id) throws SQLException, EmptyResultDataAccessException;
    
    public List<Visit> list(long patientId) throws SQLException, EmptyResultDataAccessException;
    
    public Visit update(Visit record) throws SQLException, EmptyResultDataAccessException;
        
    public Visit delete(long idd) throws SQLException, EmptyResultDataAccessException;
    
    public List<Visit> listOrganisationsVisits(String from, String until) throws SQLException, EmptyResultDataAccessException;
    
    public List<Visit> listOrganisationVisitsBy(String organisation, String from, String until) throws SQLException, EmptyResultDataAccessException;
    
    public List<Visit> findByTemplate(String template) throws SQLException, EmptyResultDataAccessException;
    
    public List<Visit> filterBy(int filterType, int type, String date) throws SQLException, EmptyResultDataAccessException;
    
    public List<Visit> filterBy(FilteredVisit filter) throws SQLException, EmptyResultDataAccessException;
    
    public List<Visit> filterBy(int department, long doctor) throws SQLException, EmptyResultDataAccessException;
}
