/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pulse.repository.mapper;

import com.pulse.model.Organisation;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 *
 * @author befast
 */
@Component (value = "organisationMapper")
public class OrganisationMapper implements RowMapper<Organisation> {
    
    @Override
    public Organisation mapRow(ResultSet rs, int rowNum) throws SQLException, EmptyResultDataAccessException {
        final Organisation patient = new Organisation();
        
        patient.setId(rs.getLong("id"));
        patient.setName(rs.getString("name"));
        
        return patient;
    }
}

