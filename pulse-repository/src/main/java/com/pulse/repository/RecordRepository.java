package com.pulse.repository;

import java.util.List;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.dao.EmptyResultDataAccessException;

import com.pulse.model.Record;
import com.pulse.repository.dao.RecordDao;
import com.pulse.repository.mapper.RecordMapper;


/**
 *
 * @author Vladimir Shin <vladimir.shin@gmail.com>
 */
@Repository (value = "recordRepository")
public class RecordRepository implements RecordDao {

    @Autowired
    private RecordMapper recordMapper;
    
    @Value("${repository.record.table.name:record}")
    private String tableName;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
        
    public RecordRepository() {        
    }
    
    @Override
    public Record get(long id) throws SQLException, EmptyResultDataAccessException {
        final String query = "SELECT * FROM " + this.tableName + " WHERE id=?";
        
        final Record record = this.jdbcTemplate.queryForObject(query, new Long[] {id}, this.recordMapper);

        return record;
    }

    @Override
    public List<Record> list(long patientId) throws SQLException, EmptyResultDataAccessException {
        final String query = "SELECT * FROM " + this.tableName + " WHERE patientId=?";
        
        final List<Record> recordList = this.jdbcTemplate.query(query, new Long[] {patientId}, this.recordMapper);

        return recordList;
    }

    @Override
    public Record update(Record record) throws SQLException, EmptyResultDataAccessException {
        if (record == null) return null;
                
        final String updateQuery = "UPDATE " + this.tableName + " SET createdBy=?,patientId=?,createdDate=?,"
                + "updatedBy=?,name=?,path=?,busy=? WHERE id=?";
        
        final String createQuery = "INSERT INTO " + this.tableName + " (createdBy,patientId,createdDate,"
                + "updatedBy,name,path,busy) VALUES (?,?,?,?,?,?,?)";
        
        // Create record logic
        if (record.getId() == -1) {
            int updated = this.jdbcTemplate.update(createQuery, 
                    record.getCreatedBy(), 
                    record.getPatientId(), 
                    record.getCreatedDate(),
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
                    record.getUpdatedBy(),
                    record.getName(),
                    record.getPath(),
                    record.isBusy(),
                    record.getId());
        }
        
        return record;
    }

    @Override
    public Record delete(long id) throws SQLException, EmptyResultDataAccessException {
        if (id < 0) return null;
        
        final String getQuery = "SELECT * FROM " + this.tableName + " WHERE id=?";
        final String deleteQuery = "DELETE FROM " + this.tableName + " WHERE id=?";
        
        final Record record = this.jdbcTemplate.queryForObject(getQuery, this.recordMapper, id);
        
        int result;
        if (record != null) {        
            result = this.jdbcTemplate.update(deleteQuery, id);
        }
        
        return record;
    }
    
}
