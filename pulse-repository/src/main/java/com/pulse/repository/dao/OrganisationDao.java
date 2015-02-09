package com.pulse.repository.dao;

import com.pulse.model.Organisation;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;

/**
 *
 * @author befast
 */
public interface OrganisationDao {
    
    public Organisation get(long id) throws SQLException, EmptyResultDataAccessException;
    
    public List<Organisation> listAll() throws SQLException, EmptyResultDataAccessException;
    
    public Organisation update(Organisation record) throws SQLException, EmptyResultDataAccessException;
        
    public Organisation delete(long id) throws SQLException, EmptyResultDataAccessException;
    
    public Organisation delete(String name) throws SQLException, EmptyResultDataAccessException;
}
