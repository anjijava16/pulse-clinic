/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pulse.rest.service.resource;

import com.pulse.model.PatientRoom;
import java.util.List;

/**
 *
 * @author befast
 */
public interface PatientRoomResource {
    public PatientRoom getById(Long id);
    
    public List<PatientRoom> listAll();
    
    public PatientRoom update(PatientRoom record);
    
    public PatientRoom deleteBy(Long id);
    
    public PatientRoom deleteBy(String name);
}
