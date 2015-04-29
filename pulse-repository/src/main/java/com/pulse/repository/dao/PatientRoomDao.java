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


import com.pulse.model.PatientRoom;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public interface PatientRoomDao {
    
    public PatientRoom get(String label) throws SQLException, EmptyResultDataAccessException;
    
    public List<PatientRoom> listFree() throws SQLException, EmptyResultDataAccessException;
    
    public boolean isFree() throws SQLException, EmptyResultDataAccessException;
    
    public boolean increment(String label) throws SQLException, EmptyResultDataAccessException;
    
    public boolean decrement(String label) throws SQLException, EmptyResultDataAccessException;
    
    public PatientRoom get(long id) throws SQLException, EmptyResultDataAccessException;
    
    public List<PatientRoom> listAll() throws SQLException, EmptyResultDataAccessException;
    
    public PatientRoom update(PatientRoom record) throws SQLException, EmptyResultDataAccessException;
        
    public PatientRoom delete(long id) throws SQLException, EmptyResultDataAccessException;
    
    public PatientRoom delete(String name) throws SQLException, EmptyResultDataAccessException;
}

