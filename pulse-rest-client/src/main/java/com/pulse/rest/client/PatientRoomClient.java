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

import com.pulse.model.PatientRoom;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class PatientRoomClient {
    
    private final SimpleClient<PatientRoom> PATIENT_ROOM_CLIENT = new SimpleClient<>();
    private final String ENDPOINT = "room/";
    
    public HttpResponse update(PatientRoom room) throws IOException {
        final String path = this.ENDPOINT.concat("update/");
        
        return this.PATIENT_ROOM_CLIENT.executeGet(path);
    }
    
    public HttpResponse deleteBy(long id) throws IOException {
        final String path = this.ENDPOINT.concat("delete/").concat(String.valueOf(id));
                
        return this.PATIENT_ROOM_CLIENT.executeGet(path);
    }
    
    public List<PatientRoom> listAll() throws IOException {
        final String path = this.ENDPOINT.concat("list/");
        
        return this.PATIENT_ROOM_CLIENT.list(path, PatientRoom.class);
    }
    
    public HttpResponse deleteBy(String name) throws IOException {
        final String path = this.ENDPOINT.concat("delete/name/").concat(name);
        
        return this.PATIENT_ROOM_CLIENT.executeGet(path);
    }
}