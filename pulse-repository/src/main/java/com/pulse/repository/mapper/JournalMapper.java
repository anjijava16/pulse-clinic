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


import com.pulse.model.Journal;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
@Component(value = "journalMapper")
public class JournalMapper implements RowMapper<Journal> {
    
    @Override
    public Journal mapRow(ResultSet rs, int rowNum) throws SQLException, EmptyResultDataAccessException {
        final Journal journal = new Journal();
        
        journal.setId(rs.getLong("id"));
        journal.setCreatedDate(rs.getDate("created_date"));
        journal.setCreatedBy(rs.getLong("created_by"));
        journal.setUpdatedBy(rs.getLong("updated_by"));
        journal.setName(rs.getString("name"));
        journal.setPath(rs.getString("path"));
        journal.setBusy(rs.getBoolean("busy"));
        
        return journal;
    }
}
