package com.pulse.repository;

import com.pulse.model.Journal;
import com.pulse.repository.dao.JournalDao;
import com.pulse.repository.mapper.JournalMapper;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author befast
 */
@Repository(value = "journalRepository")
public class JournalRepository implements JournalDao {

    @Autowired
    private JournalMapper journalMapper;
    
    @Value("${repository.journal.table.name:journal}")
    private String tableName;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Journal get(long id) throws SQLException, EmptyResultDataAccessException {
        final String query = "SELECT * FROM " + this.tableName + " WHERE id=?";
        
        final Journal journal = this.jdbcTemplate.queryForObject(query, new Long[] {id}, this.journalMapper);

        return journal;
    }

    @Override
    public List<Journal> list() throws SQLException, EmptyResultDataAccessException {
        final String query = "SELECT * FROM " + this.tableName;
        
        final List<Journal> list = this.jdbcTemplate.query(query, this.journalMapper);

        return list;
    }

    @Override
    public Journal update(Journal journal) throws SQLException, EmptyResultDataAccessException {
        if (journal == null) return null;
                
        final String updateQuery = "UPDATE " + this.tableName + " SET created_by=?,updated_by=?,"
                + "name=?,path=?,busy=? WHERE id=?";
        
        final String createQuery = "INSERT INTO " + this.tableName + " (created_by,updated_by,name,"
                + "path,busy) VALUES (?,?,?,?,?)";
        
        // Create record logic
        if (journal.getId() == -1) {
            int updated = this.jdbcTemplate.update(createQuery, 
                    journal.getCreatedBy(), 
                    journal.getUpdatedBy(), 
                    journal.getName(),
                    journal.getPath(),
                    journal.isBusy());
        }
        
        // Update record logic
        else {
            int updated = this.jdbcTemplate.update(updateQuery, 
                    journal.getCreatedBy(), 
                    journal.getUpdatedBy(), 
                    journal.getName(),
                    journal.getPath(),
                    journal.isBusy(),
                    journal.getId());
        }
        
        return journal;
    }

    @Override
    public Journal delete(long id) throws SQLException, EmptyResultDataAccessException {
        if (id < 0) return null;
        
        final String getQuery = "SELECT * FROM " + this.tableName + " WHERE id=?";
        final String deleteQuery = "DELETE FROM " + this.tableName + " WHERE id=?";
        
        final Journal journal = this.jdbcTemplate.queryForObject(getQuery, this.journalMapper, id);
        
        int result;
        if (journal != null) {        
            result = this.jdbcTemplate.update(deleteQuery, id);
        }
        
        return journal;
    }
    
}
