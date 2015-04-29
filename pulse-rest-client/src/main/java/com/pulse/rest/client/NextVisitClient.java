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
package com.pulse.rest.client;


import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;

import com.pulse.model.NextVisit;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class NextVisitClient {
    
    private final SimpleClient<NextVisit> NEXT_VISIT_CLIENT = new SimpleClient<>();
    private final String ENDPOINT = "next_visit/";
    
    public List<NextVisit> findByDate(String dateBuffer) throws IOException {
        final String path = this.ENDPOINT.concat("find/date/").concat(dateBuffer);
        
        return this.NEXT_VISIT_CLIENT.list(path, NextVisit.class);
    }
        
    public HttpResponse delete(long id) throws IOException {
        final String path = this.ENDPOINT.concat("delete/").concat(String.valueOf(id));
                
        return this.NEXT_VISIT_CLIENT.executeGet(path);
    }
    
    public HttpResponse update(NextVisit visit) throws IOException {
        final String path = this.ENDPOINT.concat("update/");
        
        return this.NEXT_VISIT_CLIENT.executePost(path, visit);
    }
}