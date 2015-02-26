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
package com.pulse.repository.mapper;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.dao.EmptyResultDataAccessException;

import com.pulse.model.Visit;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
@Component (value = "visitMapper")
public class VisitMapper implements RowMapper<Visit> {
    
    @Override
    public Visit mapRow(ResultSet rs, int rowNum) throws SQLException, EmptyResultDataAccessException {
        final Visit visit = new Visit();
        
        visit.setId(rs.getLong("id"));
        visit.setVisitDate(rs.getTimestamp("visit_time"));
        visit.setPatientId(rs.getLong("patient_id"));
        visit.setDepartmentId(rs.getInt("department"));
        visit.setAnalysGroup(rs.getString("course_group_name"));
        visit.setAnalysName(rs.getString("course_analys_name"));
        visit.setPaymentStatus(rs.getInt("paying_status"));
        visit.setPatientType(rs.getInt("patient_type"));
        visit.setVisitType(rs.getInt("visit_type"));
        visit.setVisitStatus(rs.getInt("visit_status"));
        visit.setDoctorId(rs.getLong("doctor_id"));
        visit.setTillDate(rs.getString("till_date"));
        visit.setFromOrganisation(rs.getString("from_organisation"));
        visit.setFromDoctor(rs.getString("from_doctor"));
        visit.setDiscount(rs.getInt("discount"));
        visit.setCreatedBy(rs.getString("created_by"));
        visit.setPayback(rs.getBoolean("payback"));
        visit.setFilepath(rs.getString("filepath"));
        visit.setFilename(rs.getString("filename"));
        visit.setRoom(rs.getString("room"));
        
        return visit;
    }
}