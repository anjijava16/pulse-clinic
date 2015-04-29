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
package com.pulse.rest.service.resource;


import com.pulse.model.FilteredVisit;
import com.pulse.model.SearchTemplate;
import com.pulse.model.Visit;
import java.util.List;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
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