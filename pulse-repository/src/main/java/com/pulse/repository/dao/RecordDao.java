/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pulse.repository.dao;

import java.util.List;

import com.pulse.model.Record;
import java.sql.SQLException;
import org.springframework.dao.EmptyResultDataAccessException;

/**
 *
 * @author befast
 */
public interface RecordDao {
    
    public Record get(long id) throws SQLException, EmptyResultDataAccessException;
    
    public List<Record> list(long patientId) throws SQLException, EmptyResultDataAccessException;
    
    public Record update(Record record) throws SQLException, EmptyResultDataAccessException;
        
    public Record delete(long od) throws SQLException, EmptyResultDataAccessException;
}
