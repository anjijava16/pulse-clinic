package com.pulse.rest.logic;

import com.pulse.model.Journal;
import java.util.List;

/**
 *
 * @author befast
 */
public interface JournalService {
    public Journal getById(long id);
    
    public List<Journal> list();
    
    public Journal update(Journal record);
    
    public Journal delete(long id);
}
