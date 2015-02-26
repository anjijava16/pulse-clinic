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


import com.pulse.model.Organisation;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public interface OrganisationDao {
    
    public Organisation get(long id) throws SQLException, EmptyResultDataAccessException;
    
    public List<Organisation> listAll() throws SQLException, EmptyResultDataAccessException;
    
    public Organisation update(Organisation record) throws SQLException, EmptyResultDataAccessException;
        
    public Organisation delete(long id) throws SQLException, EmptyResultDataAccessException;
    
    public Organisation delete(String name) throws SQLException, EmptyResultDataAccessException;
}
