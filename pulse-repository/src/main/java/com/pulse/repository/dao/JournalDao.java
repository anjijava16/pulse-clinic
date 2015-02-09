/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pulse.repository.dao;

import com.pulse.model.Journal;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;

/**
 *
 * @author befast
 */
public interface JournalDao {
    public Journal get(long id) throws SQLException, EmptyResultDataAccessException;
    
    public List<Journal> list() throws SQLException, EmptyResultDataAccessException;
    
    public Journal update(Journal journal) throws SQLException, EmptyResultDataAccessException;
        
    public Journal delete(long id) throws SQLException, EmptyResultDataAccessException;
}
