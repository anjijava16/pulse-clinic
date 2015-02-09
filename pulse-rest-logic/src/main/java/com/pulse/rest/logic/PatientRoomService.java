package com.pulse.rest.logic;

import com.pulse.model.PatientRoom;
import java.util.List;

/**
 *
 * @author befast
 */
public interface PatientRoomService {
    public PatientRoom getById(long id);
    
    public List<PatientRoom> listAll();
    
    public PatientRoom update(PatientRoom record);
    
    public PatientRoom delete(long id);
 
    public PatientRoom delete(String name);
}

