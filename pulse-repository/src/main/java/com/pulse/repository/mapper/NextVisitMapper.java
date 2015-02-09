/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pulse.repository.mapper;

import com.pulse.model.NextVisit;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 *
 * @author befast
 */
@Component(value = "nextVisitMapper")
public class NextVisitMapper implements RowMapper<NextVisit> {

    @Override
    public NextVisit mapRow(ResultSet rs, int i) throws SQLException {
        final NextVisit visit = new NextVisit();
        
        visit.setId(rs.getLong("id"));
        visit.setVisitDate(rs.getTimestamp("visit_date"));
        visit.setDoctorId(rs.getLong("doctor_id"));
        visit.setPatientId(rs.getLong("patient_id"));
        visit.setHandled(rs.getBoolean("status"));
        
        return visit;
    }
    
}
