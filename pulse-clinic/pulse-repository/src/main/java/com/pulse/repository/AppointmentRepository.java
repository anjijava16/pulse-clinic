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

import com.pulse.repository.dao.AppointmentDao;
import com.pulse.repository.mapper.AppointmentMapper;
import com.pulse.model.Appointment;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
@Repository (value = "appointmentRepository")
public class AppointmentRepository implements AppointmentDao {

    @Autowired
    private AppointmentMapper appointmentMapper;
    
    @Value("${repository.appointment.table.name:appointment}")
    private String tableName;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
        
    public AppointmentRepository() {        
    }
    
    @Override
    public Appointment get(long id) throws SQLException, EmptyResultDataAccessException {
        final String query = "SELECT * FROM " + this.tableName + " WHERE id=?";
        
        final Appointment record = this.jdbcTemplate.queryForObject(query, new Long[] {id}, this.appointmentMapper);

        return record;
    }

    @Override
    public List<Appointment> list(long patientId) throws SQLException, EmptyResultDataAccessException {
        final String query = "SELECT * FROM " + this.tableName + " WHERE patientId=?";
        
        final List<Appointment> recordList = this.jdbcTemplate.query(query, new Long[] {patientId}, this.appointmentMapper);

        return recordList;
    }

    @Override
    public Appointment update(Appointment record) throws SQLException, EmptyResultDataAccessException {
        if (record == null) return null;
                
        final String updateQuery = "UPDATE " + this.tableName + " SET createdBy=?,patientId=?,createdDate=?,updatedDate=?,"
                + "updatedBy=?,name=?,path=?,busy=? WHERE id=?";
        
        final String createQuery = "INSERT INTO " + this.tableName + " (createdBy,patientId,createdDate,updatedDate,"
                + "updatedBy,name,path,busy) VALUES (?,?,?,?,?,?,?,?)";
        
        // Create record logic
        if (record.getId() == -1) {
            int updated = this.jdbcTemplate.update(createQuery, 
                    record.getCreatedBy(), 
                    record.getPatientId(), 
                    record.getCreatedDate(), 
                    record.getUpdatedDate(), 
                    record.getUpdatedBy(),
                    record.getName(),
                    record.getPath(),
                    record.isBusy());
        }
        
        // Update record logic
        else {
            int updated = this.jdbcTemplate.update(updateQuery, 
                    record.getCreatedBy(), 
                    record.getPatientId(), 
                    record.getCreatedDate(), 
                    record.getUpdatedDate(), 
                    record.getUpdatedBy(),
                    record.getName(),
                    record.getPath(),
                    record.isBusy(),
                    record.getId());
        }
        
        return record;
    }

    @Override
    public Appointment delete(long id) throws SQLException, EmptyResultDataAccessException {
        if (id < 0) return null;
        
        final String getQuery = "SELECT * FROM " + this.tableName + " WHERE id=?";
        final String deleteQuery = "DELETE FROM " + this.tableName + " WHERE id=?";
        
        final Appointment record = this.jdbcTemplate.queryForObject(getQuery, this.appointmentMapper, id);
        
        int result;
        if (record != null) {        
            result = this.jdbcTemplate.update(deleteQuery, id);
        }
        
        return record;
    }
    
}

