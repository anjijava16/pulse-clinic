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


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import com.pulse.model.Record;
import org.springframework.dao.EmptyResultDataAccessException;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
@Component (value = "recordMapper")
public class RecordMapper implements RowMapper<Record> {
    
    @Override
    public Record mapRow(ResultSet rs, int rowNum) throws SQLException, EmptyResultDataAccessException {
        final Record record = new Record();
        
        record.setId(rs.getLong("id"));
        record.setCreatedBy(rs.getLong("createdBy"));
        record.setPatientId(rs.getLong("patientId"));
        record.setCreatedDate(rs.getDate("createdDate"));
        record.setUpdatedBy(rs.getLong("updatedBy"));
        record.setName(rs.getString("name"));
        record.setPath(rs.getString("path"));
        record.setBusy(rs.getBoolean("busy"));
        
        return record;
    }
}
