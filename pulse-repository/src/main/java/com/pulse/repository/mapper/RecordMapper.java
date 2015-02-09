package com.pulse.repository.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import com.pulse.model.Record;
import org.springframework.dao.EmptyResultDataAccessException;

/**
 *
 * @author befast
 */
@Component (value = "recordMapper")
public class RecordMapper implements RowMapper<Record> {
    
    @Override
    public Record mapRow(ResultSet rs, int rowNum) throws SQLException, EmptyResultDataAccessException {
        final Record record = new Record();
        
        record.setId(rs.getLong("id"));
        record.setCreatedBy(rs.getLong("createdBy"));
        record.setPatientId(rs.getLong("patientId"));
        record.setCreatedDate(rs.getDate("createdDate"));
        record.setUpdatedBy(rs.getLong("updatedBy"));
        record.setName(rs.getString("name"));
        record.setPath(rs.getString("path"));
        record.setBusy(rs.getBoolean("busy"));
        
        return record;
    }
}
