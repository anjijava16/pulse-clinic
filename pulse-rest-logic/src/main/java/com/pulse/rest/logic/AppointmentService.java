/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pulse.rest.logic;

import com.pulse.model.Appointment;
import java.util.List;

/**
 *
 * @author Vladimir Shin <vladimir.shin@gmail.com>
 */
public interface AppointmentService {
        
    public Appointment getById(long id);
    
    public List<Appointment> listBy(long patientId);
    
    public Appointment update(Appointment record);
    
    public Appointment delete(long id);
    
}
