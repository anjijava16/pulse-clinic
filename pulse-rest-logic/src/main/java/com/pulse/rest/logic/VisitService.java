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
package com.pulse.rest.logic;


import com.pulse.model.FilteredVisit;
import com.pulse.model.Visit;
import java.util.List;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
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
