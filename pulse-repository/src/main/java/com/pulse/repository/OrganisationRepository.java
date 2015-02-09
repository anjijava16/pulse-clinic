package com.pulse.repository;

import java.util.List;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.dao.EmptyResultDataAccessException;

import com.pulse.model.Organisation;
import com.pulse.repository.dao.OrganisationDao;
import com.pulse.repository.mapper.OrganisationMapper;

/**
 *
 * @author Vladimir Shin <vladimir.shin@gmail.com>
 */
@Repository (value = "organisationRepository")
public class OrganisationRepository implements OrganisationDao {

    @Autowired
    private OrganisationMapper organisationMapper;
    
    @Value("${repository.organisation.table.name:organisation}")
    private String tableName;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
        
    public OrganisationRepository() {        
    }
    
    @Override
    public Organisation get(long id) throws SQLException, EmptyResultDataAccessException {
        final String query = "SELECT * FROM " + this.tableName + " WHERE id=?";
        
        final Organisation record = this.jdbcTemplate.queryForObject(query, new Long[] {id}, this.organisationMapper);

        return record;
    }

    @Override
    public List<Organisation> listAll() throws SQLException, EmptyResultDataAccessException {
        final String query = "SELECT * FROM " + this.tableName;
        
        final List<Organisation> recordList = this.jdbcTemplate.query(query, this.organisationMapper);

        return recordList;
    }

    @Override
    public Organisation update(Organisation record) throws SQLException, EmptyResultDataAccessException {
        if (record == null) return null;
                
        final String updateQuery = "UPDATE " + this.tableName + " SET name=? WHERE id=?";
        
        final String createQuery = "INSERT INTO " + this.tableName + " (name) VALUES (?)";
                
        // Create record logic
        if (record.getId() == -1) {
            int updated = this.jdbcTemplate.update(createQuery, 
                    record.getName());
        }
        
        // Update record logic
        else {
            int updated = this.jdbcTemplate.update(updateQuery, 
                    record.getName(),
                    record.getId());
        }
        
        return record;
    }

    @Override
    public Organisation delete(long id) throws SQLException, EmptyResultDataAccessException {
        if (id < 0) return null;
        
        final String getQuery = "SELECT * FROM " + this.tableName + " WHERE id=?";
        final String deleteQuery = "DELETE FROM " + this.tableName + " WHERE id=?";
        
        final Organisation record = this.jdbcTemplate.queryForObject(getQuery, this.organisationMapper, id);
        
        int result;
        if (record != null) {        
            result = this.jdbcTemplate.update(deleteQuery, id);
        }
        
        return record;
    }

    @Override
    public Organisation delete(String name) throws SQLException, EmptyResultDataAccessException {
        if (name == null) return null;
        
        final String getQuery = "SELECT * FROM " + this.tableName + " WHERE name=?";
        final String deleteQuery = "DELETE FROM " + this.tableName + " WHERE name=?";
        
        final Organisation record = this.jdbcTemplate.queryForObject(getQuery, this.organisationMapper, name);
        
        int result;
        if (record != null) {        
            result = this.jdbcTemplate.update(deleteQuery, name);
        }
        
        return record;
    }
    
}

