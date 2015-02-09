/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pulse.rest.service.resource;

import com.pulse.model.Organisation;

import java.util.List;

/**
 *
 * @author befast
 */
public interface OrganisationResource {
    
    public Organisation getById(Long id);
    
    public List<Organisation> listAll();
    
    public Organisation update(Organisation record);
    
    public Organisation deleteBy(Long id);
    
    public Organisation deleteBy(String name);
}
