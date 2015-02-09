/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pulse.rest.service.resource;

import com.pulse.model.Journal;
import java.util.List;
import javax.ws.rs.core.Response;

/**
 *
 * @author befast
 */
public interface JournalResource {
    public Journal getBy(Long id);
    
    public Journal getWithBinaryBy(long id);
    
    public List<Journal> list();
    
    public void update(Journal record);
            
    public Response delete(long id);
}
