/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pulse.repository.dao;

import com.pulse.model.Appointment;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;

/**
 *
 * @author befast
 */
public interface AppointmentDao {
    
    public Appointment get(long id) throws SQLException, EmptyResultDataAccessException;
    
    public List<Appointment> list(long patientId) throws SQLException, EmptyResultDataAccessException;
    
    public Appointment update(Appointment record) throws SQLException, EmptyResultDataAccessException;
        
    public Appointment delete(long od) throws SQLException, EmptyResultDataAccessException;
    
}
