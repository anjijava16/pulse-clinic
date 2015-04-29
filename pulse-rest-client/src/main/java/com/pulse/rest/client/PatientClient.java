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


import com.pulse.model.Patient;
import java.io.IOException;
import java.util.List;
import org.apache.http.HttpResponse;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class PatientClient {
    
    private final SimpleClient<Patient> PATIENT_CLIENT = new SimpleClient<>();
    
    public boolean delete(long id) throws IOException { 
        final String getByIdPath = "patient/delete/".concat(String.valueOf(id));
        HttpResponse response = this.PATIENT_CLIENT.executeGet(getByIdPath);
        
        return response.getStatusLine().getStatusCode() == 200;
    }   
    
    public Patient get(long id) throws IOException { 
        final String getByIdPath = "patient/get/".concat(String.valueOf(id));
        
        return this.PATIENT_CLIENT.executePojoGet(getByIdPath, Patient.class);
    }
    
    public Patient get(String token) throws IOException { 
        final String getByIdPath = "patient/get/token/".concat(token);
        
        return this.PATIENT_CLIENT.executePojoGet(getByIdPath, Patient.class);
    }
    
    public List<Patient> list() throws IOException { 
        final String getByIdPath = "patient/list/";
        
        return this.PATIENT_CLIENT.list(getByIdPath, Patient.class);
    }
    
    public List<Patient> list(String pattern) throws IOException { 
        final String getByIdPath = "patient/list/pattern/".concat(pattern);
        
        return this.PATIENT_CLIENT.list(getByIdPath, Patient.class);
    }
    
    public boolean update(Patient patient) throws IOException { 
        final String getByIdPath = "patient/";
        
        final HttpResponse response = this.PATIENT_CLIENT.executePost(getByIdPath, patient);
        
        return response.getStatusLine().getStatusCode() == 200;
    }
}