package com.pulse.repository.mapper;

import com.pulse.model.Patient;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import org.springframework.dao.EmptyResultDataAccessException;

/**
 *
 * @author befast
 */
@Component (value = "patientMapper")
public class PatientMapper implements RowMapper<Patient> {
    
    @Override
    public Patient mapRow(ResultSet rs, int rowNum) throws SQLException, EmptyResultDataAccessException {
        final Patient patient = new Patient();
        
        patient.setId(rs.getLong("id"));
        patient.setNfp(rs.getString("nfp"));
        patient.setSex(rs.getByte("sex"));
        patient.setBirthday(rs.getDate("birthday"));
        patient.setType(rs.getByte("type"));
        patient.setEmail(rs.getString("email"));
        patient.setMobile(rs.getString("mobile_phone"));
        patient.setDiscount(rs.getInt("discount"));
        patient.setToken(rs.getString("token"));
        
        return patient;
    }
}
