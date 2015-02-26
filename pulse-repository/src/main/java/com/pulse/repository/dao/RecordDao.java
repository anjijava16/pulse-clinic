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
package com.pulse.repository.dao;


import java.util.List;

import com.pulse.model.Record;
import java.sql.SQLException;
import org.springframework.dao.EmptyResultDataAccessException;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public interface RecordDao {
    
    public Record get(long id) throws SQLException, EmptyResultDataAccessException;
    
    public List<Record> list(long patientId) throws SQLException, EmptyResultDataAccessException;
    
    public Record update(Record record) throws SQLException, EmptyResultDataAccessException;
        
    public Record delete(long od) throws SQLException, EmptyResultDataAccessException;
}
