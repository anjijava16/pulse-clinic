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

import com.pulse.model.Record;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class RecordClient {
    
    private final SimpleClient<Record> RECORD_CLIENT = new SimpleClient<>();
    private final String ENPOINT = "record/";
    
    public HttpResponse delete(long id) throws IOException {
        final String path = this.ENPOINT.concat("delete/").concat(String.valueOf(id));
                
        return this.RECORD_CLIENT.executeGet(path);
    }
    
    public Record getWithBinaryBy(long id) throws IOException {
        final String path = this.ENPOINT.concat("binary/read/").concat(String.valueOf(id));
                
        return this.RECORD_CLIENT.executePojoGet(path, Record.class);
    }
    
    public HttpResponse updateRecord(Record record) throws IOException {
        final String path = this.ENPOINT.concat("update/");
                
        return this.RECORD_CLIENT.executePost(path, record);
    }
    
    public List<Record> listBy(long patientId) throws IOException {        
        final String path = this.ENPOINT.concat("patientId/").concat(String.valueOf(patientId));
                
        return this.RECORD_CLIENT.list(path, Record.class);
    }    
}