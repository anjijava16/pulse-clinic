/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pulse.rest.logic.service.impl;

import com.pulse.rest.logic.NextVisitService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pulse.model.NextVisit;
import com.pulse.repository.NextVisitRepository;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;

/**
 *
 * @author Vladimir Shin <vladimir.shin@gmail.com>
 */
@Service (value = "nextVisitService")
public class NextVisitServiceImpl implements NextVisitService {
    
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private NextVisitRepository nextVisitRepository;
        

    @Override
    public NextVisit update(NextVisit visit) {
        try {
            return this.nextVisitRepository.update(visit);
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
    public NextVisit delete(long id) {
        try {
            return this.nextVisitRepository.delete(id);
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
    public List<NextVisit> findBy(String dateBuffer) {
        List<NextVisit> list = null;
        
        try {
            list = this.nextVisitRepository.findBy(dateBuffer);
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

