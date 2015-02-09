package com.pulse.rest.client;

import com.pulse.model.Patient;
import java.io.IOException;
import java.util.List;
import org.apache.http.HttpResponse;

/**
 *
 * @author Vladimir Shin
 *
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