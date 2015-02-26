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

import com.pulse.model.Appointment;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class AppointmentClient {
    
    private final SimpleClient<Appointment> PATIENT_CLIENT = new SimpleClient<>();
    private final String ENDPOINT = "appointment/";    
    
    public HttpResponse delete(long id) throws IOException {
        final String path = this.ENDPOINT.concat("delete/").concat(String.valueOf(id));
        
        return this.PATIENT_CLIENT.executeGet(path);
    }
    
    public Appointment getWithBinaryBy(long id) throws IOException {
        final String path = this.ENDPOINT.concat("get/binary/").concat(String.valueOf(id));
        
        return this.PATIENT_CLIENT.executePojoGet(path, Appointment.class);
    }
    
    public HttpResponse update(Appointment record) throws IOException {
        final String path = this.ENDPOINT.concat("update/");
        
        return this.PATIENT_CLIENT.executePost(path, record);
    }
    
    public List<Appointment> listBy(long patientId) throws IOException {
        final String path = this.ENDPOINT.concat("list/").concat(String.valueOf(patientId));
        
        return this.PATIENT_CLIENT.list(path, Appointment.class);
    }
}