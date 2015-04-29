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
package com.pulse.rest.logic.service.impl;


import com.pulse.model.Patient;
import com.pulse.model.PatientRoom;
import com.pulse.repository.PatientRepository;
import com.pulse.repository.PatientRoomRepository;
import com.pulse.rest.logic.PatientService;
import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
@Service (value = "patientService")
public class PatientServiceImpl implements PatientService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private PatientRepository patientRepository;
    
    public Patient getBy(String token) {
        Patient patient = null;
        
        try {
            patient = this.patientRepository.get(token);
        } catch (SQLException sqle) {
            this.LOGGER.error(sqle.getMessage());
        } catch (EmptyResultDataAccessException erdae) {
            // This exception will be throwed when there will be no
            // records in the database table and ResultSet in RecordMapper
            // will be null
            this.LOGGER.error(erdae.getMessage());
        }
        
        return patient;
    }

    public Patient getBy(long id) {
        Patient patient = null;
        
        try {
            patient = this.patientRepository.get(id);
        } catch (SQLException sqle) {
            this.LOGGER.error(sqle.getMessage());
        } catch (EmptyResultDataAccessException erdae) {
            // This exception will be throwed when there will be no
            // records in the database table and ResultSet in RecordMapper
            // will be null
            this.LOGGER.error(erdae.getMessage());
        }
        
        return patient;
    }

    public boolean update(Patient patient) {        
        try {
            this.patientRepository.update(patient);
            
            return true;
        } catch (SQLException sqle) {
            this.LOGGER.error(sqle.getMessage());
        } catch (EmptyResultDataAccessException erdae) {
            // This exception will be throwed when there will be no
            // records in the database table and ResultSet in RecordMapper
            // will be null
            this.LOGGER.error(erdae.getMessage());
        }
        
        return false;
    }

    public boolean delete(String token) {
        try {
            this.patientRepository.delete(token);
            
            return true;
        } catch (SQLException sqle) {
            this.LOGGER.error(sqle.getMessage());
        } catch (EmptyResultDataAccessException erdae) {
            // This exception will be throwed when there will be no
            // records in the database table and ResultSet in RecordMapper
            // will be null
            this.LOGGER.error(erdae.getMessage());
        }
        
        return false;
    }

    public boolean delete(long id) {
        try {
            this.patientRepository.delete(id);
            
            return true;
        } catch (SQLException sqle) {
            this.LOGGER.error(sqle.getMessage());
        } catch (EmptyResultDataAccessException erdae) {
            // This exception will be throwed when there will be no
            // records in the database table and ResultSet in RecordMapper
            // will be null
            this.LOGGER.error(erdae.getMessage());
        }
        
        return false;
    }

    public List<Patient> list() {
        List<Patient> list = null;
        
        try {
            list = this.patientRepository.listAll();
        } catch (SQLException sqle) {
            this.LOGGER.error(sqle.getMessage());
        } catch (EmptyResultDataAccessException erdae) {
            // This exception will be throwed when there will be no
            // records in the database table and ResultSet in RecordMapper
            // will be null
            this.LOGGER.error(erdae.getMessage());
        }
        
        return list;
    }
    
    public List<Patient> list(String pattern) {
        List<Patient> list = null;
        
        try {
            list = this.patientRepository.listAll(pattern);
        } catch (SQLException sqle) {
            this.LOGGER.error(sqle.getMessage());
        } catch (EmptyResultDataAccessException erdae) {
            // This exception will be throwed when there will be no
            // records in the database table and ResultSet in RecordMapper
            // will be null
            this.LOGGER.error(erdae.getMessage());
        }
        
        return list;
    }
}
