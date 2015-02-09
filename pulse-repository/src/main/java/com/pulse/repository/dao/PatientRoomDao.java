/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pulse.repository.dao;

import com.pulse.model.PatientRoom;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;

/**
 *
 * @author befast
 */
public interface PatientRoomDao {
    
    public PatientRoom get(String label) throws SQLException, EmptyResultDataAccessException;
    
    public List<PatientRoom> listFree() throws SQLException, EmptyResultDataAccessException;
    
    public boolean isFree() throws SQLException, EmptyResultDataAccessException;
    
    public boolean increment(String label) throws SQLException, EmptyResultDataAccessException;
    
    public boolean decrement(String label) throws SQLException, EmptyResultDataAccessException;
    
    public PatientRoom get(long id) throws SQLException, EmptyResultDataAccessException;
    
    public List<PatientRoom> listAll() throws SQLException, EmptyResultDataAccessException;
    
    public PatientRoom update(PatientRoom record) throws SQLException, EmptyResultDataAccessException;
        
    public PatientRoom delete(long id) throws SQLException, EmptyResultDataAccessException;
    
    public PatientRoom delete(String name) throws SQLException, EmptyResultDataAccessException;
}

