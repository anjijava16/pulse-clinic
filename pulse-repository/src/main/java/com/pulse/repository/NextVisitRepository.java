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


import com.pulse.model.NextVisit;
import com.pulse.repository.dao.NextVisitDao;
import com.pulse.repository.mapper.NextVisitMapper;
import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
@Repository(value = "nextVisitRepository")
public class NextVisitRepository implements NextVisitDao {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private NextVisitMapper nextVisitMapper;
    
    @Value("${repository.next.visit.table.name:next_visit}")
    private String tableName;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public NextVisit update(NextVisit visit) throws SQLException, EmptyResultDataAccessException {
        if (visit == null) return null;
                
        final String updateQuery = "UPDATE " + this.tableName + " SET patient_id=?, doctor_id=?, status=? WHERE id=?";
        
        final String createQuery = "INSERT INTO " + this.tableName + " (patient_id,doctor_id,status,visit_date) VALUES (?,?,?,?)";
        
        // Create record logic
        if (visit.getId() == -1) {
            int updated = this.jdbcTemplate.update(createQuery, 
                    visit.getPatientId(),
                    visit.getDoctorId(),
                    visit.isHandled(),
                    visit.getVisitDate());
        }
        
        // Update record logic
        else {
            int updated = this.jdbcTemplate.update(updateQuery, 
                    visit.getPatientId(),
                    visit.getDoctorId(),
                    visit.isHandled(),
                    visit.getId());
        }
        
        return visit;
    }

    @Override
    public List<NextVisit> findBy(String dateBuffer) throws SQLException, EmptyResultDataAccessException {
        final String query = "SELECT * FROM " + this.tableName + " WHERE DATE(visit_date)=? ORDER BY id";
        
        final List<NextVisit> list = this.jdbcTemplate.query(query, this.nextVisitMapper, dateBuffer);
        
        return list;
    }

    @Override
    public NextVisit delete(long id) throws SQLException, EmptyResultDataAccessException {
        if (id < 0) return null;
        
        final String getQuery = "SELECT * FROM " + this.tableName + " WHERE id=?";
        final String deleteQuery = "DELETE FROM " + this.tableName + " WHERE id=?";
        
        final NextVisit visit = this.jdbcTemplate.queryForObject(getQuery, this.nextVisitMapper, id);
        
        int result;
        if (visit != null) {        
            result = this.jdbcTemplate.update(deleteQuery, id);
        }
        
        return visit;
    }
    
        
}
