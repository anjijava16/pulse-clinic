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


import com.pulse.rest.logic.AppointmentService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pulse.model.Appointment;
import com.pulse.repository.AppointmentRepository;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
@Service (value = "appointmentService")
public class AppointmentServiceImpl implements AppointmentService {
    
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private AppointmentRepository appointmentRepository;
        
    @Override
    public Appointment getById(long id) {
        Appointment record = null;
        
        try {
            record = this.appointmentRepository.get(id);
        } catch (SQLException sqle) {
            this.LOGGER.error(sqle.getMessage());
        } catch (EmptyResultDataAccessException erdae) {
            // This exception will be throwed when there will be no
            // records in the database table and ResultSet in RecordMapper
            // will be null
            this.LOGGER.error(erdae.getMessage());
        }
        
        return record;
    }

    @Override
    public List<Appointment> listBy(long patientId) {
        List<Appointment> recordList = null;
        
        try {
            recordList = this.appointmentRepository.list(patientId);
        } catch (SQLException sqle) {
            this.LOGGER.error(sqle.getMessage());
        } catch (EmptyResultDataAccessException erdae) {
            // This exception will be throwed when there will be no
            // records in the database table and ResultSet in RecordMapper
            // will be null
            this.LOGGER.error(erdae.getMessage());
        }
        
        return recordList;
    }

    @Override
    public Appointment update(Appointment record) {
        try {
            return this.appointmentRepository.update(record);
        } catch (SQLException sqle) {
            this.LOGGER.error(sqle.getMessage());
        } catch (EmptyResultDataAccessException erdae) {
            // This exception will be throwed when there will be no
            // records in the database table and ResultSet in RecordMapper
            // will be null
            this.LOGGER.error(erdae.getMessage());
        }
        
        return null;
    }

    @Override
    public Appointment delete(long id) {
        try {
            return this.appointmentRepository.delete(id);
        } catch (SQLException sqle) {
            this.LOGGER.error(sqle.getMessage());
        } catch (EmptyResultDataAccessException erdae) {
            // This exception will be throwed when there will be no
            // records in the database table and ResultSet in RecordMapper
            // will be null
            this.LOGGER.error(erdae.getMessage());
        }
        
        return null;
    }

}

