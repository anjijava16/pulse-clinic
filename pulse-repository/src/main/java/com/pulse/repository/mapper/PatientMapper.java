/*
 * Copyright 2015 Vladimir Shin
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.pulse.repository.mapper;


import com.pulse.model.Patient;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import org.springframework.dao.EmptyResultDataAccessException;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
@Component (value = "patientMapper")
public class PatientMapper implements RowMapper<Patient> {
    
    @Override
    public Patient mapRow(ResultSet rs, int rowNum) throws SQLException, EmptyResultDataAccessException {
        final Patient patient = new Patient();
        
        patient.setId(rs.getLong("id"));
        patient.setNfp(rs.getString("nfp"));
        patient.setSex(rs.getByte("sex"));
        patient.setBirthday(rs.getDate("birthday"));
        patient.setType(rs.getByte("type"));
        patient.setEmail(rs.getString("email"));
        patient.setMobile(rs.getString("mobile_phone"));
        patient.setDiscount(rs.getInt("discount"));
        patient.setToken(rs.getString("token"));
        
        return patient;
    }
}
