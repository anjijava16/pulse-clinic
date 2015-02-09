/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pulse.repository;

import java.util.List;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.dao.EmptyResultDataAccessException;

import com.pulse.model.PatientRoom;
import com.pulse.repository.dao.PatientRoomDao;
import com.pulse.repository.mapper.PatientRoomMapper;

/**
 *
 * @author Vladimir Shin <vladimir.shin@gmail.com>
 */
@Repository (value = "patientRoomRepository")
public class PatientRoomRepository implements PatientRoomDao {

    @Autowired
    private PatientRoomMapper patientRoomMapper;
    
    @Value("${repository.room.table.name:room}")
    private String tableName;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
        
    public PatientRoomRepository() {        
    }
    
    @Override
    public PatientRoom get(long id) throws SQLException, EmptyResultDataAccessException {
        final String query = "SELECT * FROM " + this.tableName + " WHERE id=?";
        
        final PatientRoom record = this.jdbcTemplate.queryForObject(query, new Long[] {id}, this.patientRoomMapper);

        return record;
    }

    @Override
    public List<PatientRoom> listAll() throws SQLException, EmptyResultDataAccessException {
        final String query = "SELECT * FROM " + this.tableName;
        
        final List<PatientRoom> recordList = this.jdbcTemplate.query(query, this.patientRoomMapper);

        return recordList;
    }

    @Override
    public PatientRoom update(PatientRoom record) throws SQLException, EmptyResultDataAccessException {
        if (record == null) return null;
                
        final String updateQuery = "UPDATE " + this.tableName + " SET name=?,capacity=?,limitation=? WHERE id=?";
        
        final String createQuery = "INSERT INTO " + this.tableName + " (name) VALUES (?)";
                
        // Create record logic
        if (record.getId() == -1) {
            int updated = this.jdbcTemplate.update(createQuery, 
                    record.getName());
        }
        
        // Update record logic
        else {
            int updated = this.jdbcTemplate.update(updateQuery, 
                    record.getName(),
                    record.getCapacity(),
                    record.getLimitation(),
                    record.getId());
        }
        
        return record;
    }

    @Override
    public PatientRoom delete(long id) throws SQLException, EmptyResultDataAccessException {
        if (id < 0) return null;
        
        final String getQuery = "SELECT * FROM " + this.tableName + " WHERE id=?";
        final String deleteQuery = "DELETE FROM " + this.tableName + " WHERE id=?";
        
        final PatientRoom record = this.jdbcTemplate.queryForObject(getQuery, this.patientRoomMapper, id);
        
        int result;
        if (record != null) {        
            result = this.jdbcTemplate.update(deleteQuery, id);
        }
        
        return record;
    }

    @Override
    public PatientRoom delete(String name) throws SQLException, EmptyResultDataAccessException {
        if (name == null) return null;
        
        final String getQuery = "SELECT * FROM " + this.tableName + " WHERE name=?";
        final String deleteQuery = "DELETE FROM " + this.tableName + " WHERE name=?";
        
        final PatientRoom record = this.jdbcTemplate.queryForObject(getQuery, this.patientRoomMapper, name);
        
        int result;
        if (record != null) {        
            result = this.jdbcTemplate.update(deleteQuery, name);
        }
        
        return record;
    }

    @Override
    public PatientRoom get(String label) throws SQLException, EmptyResultDataAccessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PatientRoom> listFree() throws SQLException, EmptyResultDataAccessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isFree() throws SQLException, EmptyResultDataAccessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean increment(String label) throws SQLException, EmptyResultDataAccessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean decrement(String label) throws SQLException, EmptyResultDataAccessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}


