package com.pulse.rest.service.resource;

import com.pulse.model.Appointment;
import java.util.List;

import javax.ws.rs.core.Response;


/**
 *
 * @author Vladimir Shin
 */
public interface AppointmentResource {
    
    public Appointment getBy(Long id);
    
    public Appointment getWithBinaryBy(long id);
    
    public List<Appointment> listByPatientId(Long patientId);
    
    public Response update(Appointment record);
            
    public Response delete(long id);
    
}
