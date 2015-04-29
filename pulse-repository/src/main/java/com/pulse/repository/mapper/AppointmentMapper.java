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


import com.pulse.model.Appointment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import org.springframework.dao.EmptyResultDataAccessException;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
@Component (value = "appointmentMapper")
public class AppointmentMapper implements RowMapper<Appointment> {
    
    @Override
    public Appointment mapRow(ResultSet rs, int rowNum) throws SQLException, EmptyResultDataAccessException {
        final Appointment appointment = new Appointment();
        
        appointment.setId(rs.getLong("id"));
        appointment.setCreatedBy(rs.getLong("createdBy"));
        appointment.setPatientId(rs.getLong("patientId"));
        appointment.setCreatedDate(rs.getDate("createdDate"));
        appointment.setUpdatedDate(rs.getDate("updatedDate"));
        appointment.setUpdatedBy(rs.getLong("updatedBy"));
        appointment.setName(rs.getString("name"));
        appointment.setPath(rs.getString("path"));
        appointment.setBusy(rs.getBoolean("busy"));
        
        return appointment;
    }
}
