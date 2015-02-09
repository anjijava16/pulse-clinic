package com.pulse.repository.mapper;

import com.pulse.model.Journal;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 *
 * @author befast
 */
@Component(value = "journalMapper")
public class JournalMapper implements RowMapper<Journal> {
    
    @Override
    public Journal mapRow(ResultSet rs, int rowNum) throws SQLException, EmptyResultDataAccessException {
        final Journal journal = new Journal();
        
        journal.setId(rs.getLong("id"));
        journal.setCreatedDate(rs.getDate("created_date"));
        journal.setCreatedBy(rs.getLong("created_by"));
        journal.setUpdatedBy(rs.getLong("updated_by"));
        journal.setName(rs.getString("name"));
        journal.setPath(rs.getString("path"));
        journal.setBusy(rs.getBoolean("busy"));
        
        return journal;
    }
}
