package com.pulse.rest.service.resource;

import com.pulse.model.Patient;
import java.util.List;
import javax.ws.rs.core.Response;

/**
 *
 * @author befast
 */
public interface PatientResource {
    public Patient getBy(Long id);
    
    public Patient getBy(String token);
    
    public List<Patient> listAll();
    
    public List<Patient> listAll(String pattern);
    
    public Response update(Patient patient);
    
    public Response deleteBy(Long id);
    
    public Response deleteBy(String token);
}
