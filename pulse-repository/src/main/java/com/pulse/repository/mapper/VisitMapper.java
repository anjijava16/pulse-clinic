package com.pulse.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.dao.EmptyResultDataAccessException;

import com.pulse.model.Visit;


/**
 *
 * @author Vladimir Shin
 */
@Component (value = "visitMapper")
public class VisitMapper implements RowMapper<Visit> {
    
    @Override
    public Visit mapRow(ResultSet rs, int rowNum) throws SQLException, EmptyResultDataAccessException {
        final Visit visit = new Visit();
        
        visit.setId(rs.getLong("id"));
        visit.setVisitDate(rs.getTimestamp("visit_time"));
        visit.setPatientId(rs.getLong("patient_id"));
        visit.setDepartmentId(rs.getInt("department"));
        visit.setAnalysGroup(rs.getString("course_group_name"));
        visit.setAnalysName(rs.getString("course_analys_name"));
        visit.setPaymentStatus(rs.getInt("paying_status"));
        visit.setPatientType(rs.getInt("patient_type"));
        visit.setVisitType(rs.getInt("visit_type"));
        visit.setVisitStatus(rs.getInt("visit_status"));
        visit.setDoctorId(rs.getLong("doctor_id"));
        visit.setTillDate(rs.getString("till_date"));
        visit.setFromOrganisation(rs.getString("from_organisation"));
        visit.setFromDoctor(rs.getString("from_doctor"));
        visit.setDiscount(rs.getInt("discount"));
        visit.setCreatedBy(rs.getString("created_by"));
        visit.setPayback(rs.getBoolean("payback"));
        visit.setFilepath(rs.getString("filepath"));
        visit.setFilename(rs.getString("filename"));
        visit.setRoom(rs.getString("room"));
        
        return visit;
    }
}