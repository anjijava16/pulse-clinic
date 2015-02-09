package com.pulse.repository.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;

import com.pulse.model.Patient;



/**
 *
 * @author Vladimir Shin
 */
public interface PatientDao {
    
    public Patient get(String token) throws SQLException, EmptyResultDataAccessException;
    
    public Patient get(long id) throws SQLException, EmptyResultDataAccessException;
    
    public List<Patient> listAll(String pattern) throws SQLException, EmptyResultDataAccessException;
    
    public List<Patient> listAll() throws SQLException, EmptyResultDataAccessException;
    
    public Patient update(Patient record) throws SQLException, EmptyResultDataAccessException;
        
    public Patient delete(long id) throws SQLException, EmptyResultDataAccessException;
    
    public Patient delete(String token) throws SQLException, EmptyResultDataAccessException;
}
