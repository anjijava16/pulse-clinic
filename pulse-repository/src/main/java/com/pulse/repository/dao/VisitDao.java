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
package com.pulse.repository.dao;


import com.pulse.model.FilteredVisit;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;

import com.pulse.model.Visit;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
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
