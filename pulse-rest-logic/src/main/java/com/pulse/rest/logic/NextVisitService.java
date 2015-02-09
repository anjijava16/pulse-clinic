package com.pulse.rest.logic;

import com.pulse.model.NextVisit;
import java.util.List;

/**
 *
 * @author befast
 */
public interface NextVisitService {
    
    public NextVisit delete(long id);
    
    public NextVisit update(NextVisit visit);
    
    public List<NextVisit> findBy(String dateBuffer);
}
