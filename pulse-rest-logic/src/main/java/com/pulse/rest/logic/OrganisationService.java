package com.pulse.rest.logic;

import com.pulse.model.Organisation;
import java.util.List;

/**
 *
 * @author befast
 */
public interface OrganisationService {
    public Organisation getById(long id);
    
    public List<Organisation> listAll();
    
    public Organisation update(Organisation record);
    
    public Organisation delete(long id);
 
    public Organisation delete(String name);
}
