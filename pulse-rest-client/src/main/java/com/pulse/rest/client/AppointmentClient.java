package com.pulse.rest.client;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;

import com.pulse.model.Appointment;

/**
 *
 * @author Vladimir Shin
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