/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pulse.repository.mapper;

import com.pulse.model.Appointment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import org.springframework.dao.EmptyResultDataAccessException;

/**
 *
 * @author befast
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
