package com.pulse.repository.mapper;

import com.pulse.model.PatientRoom;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 *
 * @author befast
 */
@Component (value = "patientRoomMapper")
public class PatientRoomMapper implements RowMapper<PatientRoom> {
    
    @Override
    public PatientRoom mapRow(ResultSet rs, int rowNum) throws SQLException, EmptyResultDataAccessException {
        final PatientRoom patient = new PatientRoom();
        
        patient.setId(rs.getLong("id"));
        patient.setName(rs.getString("name"));
        patient.setCapacity(rs.getInt("capacity"));
        patient.setLimitation(rs.getInt("limitation"));
        
        return patient;
    }
}