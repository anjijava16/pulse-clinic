package com.pulse.rest.logic.service.impl;

import com.pulse.rest.logic.OrganisationService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pulse.model.Organisation;
import com.pulse.repository.OrganisationRepository;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;

/**
 *
 * @author Vladimir Shin <vladimir.shin@gmail.com>
 */
@Service (value = "organisationService")
public class OrganisationServiceImpl implements OrganisationService {
    
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private OrganisationRepository organisationRepository;
        
    @Override
    public Organisation getById(long id) {
        Organisation record = null;
        
        try {
            record = this.organisationRepository.get(id);
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
    public List<Organisation> listAll() {
        List<Organisation> recordList = null;
        
        try {
            recordList = this.organisationRepository.listAll();
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
    public Organisation update(Organisation record) {
        try {
            return this.organisationRepository.update(record);
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
    public Organisation delete(long id) {
        try {
            return this.organisationRepository.delete(id);
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

    public Organisation delete(String name) {
        try {
            return this.organisationRepository.delete(name);
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



