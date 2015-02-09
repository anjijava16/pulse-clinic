package com.pulse.rest.logic;

import com.pulse.model.Patient;
import java.util.List;

/**
 *
 * @author Vladimir Shin <vladimir.shin@gmail.com>
 */
public interface PatientService {
    
    public Patient getBy(String token);
    
    public Patient getBy(long id);
    
    public List<Patient> list();
    
    public List<Patient> list(String pattern);
    
    public boolean update(Patient patient);
        
    public boolean delete(String token);
    
    public boolean delete(long id);
}
