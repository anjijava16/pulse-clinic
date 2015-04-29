/*
 * Copyright 2015 Vladimir Shin
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.pulse.repository;


import java.util.List;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.dao.EmptyResultDataAccessException;

import com.pulse.model.Patient;
import com.pulse.repository.dao.PatientDao;
import com.pulse.repository.mapper.PatientMapper;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
@Repository (value = "patientRepository")
public class PatientRepository implements PatientDao {

    @Autowired
    private PatientMapper patientMapper;
    
    @Value("${repository.patient.table.name:patient}")
    private String tableName;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
        
    public PatientRepository() {        
    }
    
    @Override
    public Patient get(long id) throws SQLException, EmptyResultDataAccessException {
        final String query = "SELECT * FROM " + this.tableName + " WHERE id=?";
        
        final Patient record = this.jdbcTemplate.queryForObject(query, new Long[] {id}, this.patientMapper);

        return record;
    }

    @Override
    public List<Patient> listAll() throws SQLException, EmptyResultDataAccessException {
        final String query = "SELECT * FROM " + this.tableName;
        
        final List<Patient> recordList = this.jdbcTemplate.query(query, this.patientMapper);

        return recordList;
    }

    @Override
    public Patient update(Patient record) throws SQLException, EmptyResultDataAccessException {
        if (record == null) return null;
                
        final String updateQuery = "UPDATE " + this.tableName + " SET nfp=?,sex=?,birthday=?,type=?,"
                + "email=?,mobile_phone=?,discount=?,token=? WHERE id=?";
        
        final String createQuery = "INSERT INTO " + this.tableName + " (nfp,sex,birthday,type,"
                + "email,mobile_phone,discount,token) VALUES (?,?,?,?,?,?,?,?)";
        
        // Create record logic
        if (record.getId() == -1) {
            int updated = this.jdbcTemplate.update(createQuery, 
                    record.getNfp(), 
                    record.getSex(), 
                    record.getBirthday(), 
                    record.getType(), 
                    record.getEmail(),
                    record.getMobile(),
                    record.getDiscount(),
                    record.getToken());
        }
        
        // Update record logic
        else {
            int updated = this.jdbcTemplate.update(updateQuery, 
                    record.getNfp(), 
                    record.getSex(), 
                    record.getBirthday(), 
                    record.getType(), 
                    record.getEmail(),
                    record.getMobile(),
                    record.getDiscount(),
                    record.getToken(),
                    record.getId());
        }
        
        return record;
    }

    @Override
    public Patient delete(long id) throws SQLException, EmptyResultDataAccessException {
        if (id < 0) return null;
        
        final String getQuery = "SELECT * FROM " + this.tableName + " WHERE id=?";
        final String deleteQuery = "DELETE FROM " + this.tableName + " WHERE id=?";
        
        final Patient record = this.jdbcTemplate.queryForObject(getQuery, this.patientMapper, id);
        
        int result;
        if (record != null) {        
            result = this.jdbcTemplate.update(deleteQuery, id);
        }
        
        return record;
    }

    @Override
    public Patient get(String token) throws SQLException, EmptyResultDataAccessException {
        final String query = "SELECT * FROM " + this.tableName + " WHERE token=?";
        
        final Patient record = this.jdbcTemplate.queryForObject(query, new String[] {token}, this.patientMapper);

        return record;
    }

    @Override
    public Patient delete(String token) throws SQLException, EmptyResultDataAccessException {
        if (token == null) return null;
        if (token.isEmpty()) return null;
        
        final String getQuery = "SELECT * FROM " + this.tableName + " WHERE token=?";
        final String deleteQuery = "DELETE FROM " + this.tableName + " WHERE token=?";
        
        final Patient patient = this.jdbcTemplate.queryForObject(getQuery, this.patientMapper, token);
        
        int result;
        if (patient != null) {        
            result = this.jdbcTemplate.update(deleteQuery, token);
        }
        
        return patient;
    }

    @Override
    public List<Patient> listAll(String pattern) throws SQLException, EmptyResultDataAccessException {
        final String query = "SELECT * FROM " + this.tableName + " WHERE nfp like ?";
        
        final List<Patient> recordList = this.jdbcTemplate.query(query, this.patientMapper, "%" + pattern + "%");

        return recordList;
    }
    
}
