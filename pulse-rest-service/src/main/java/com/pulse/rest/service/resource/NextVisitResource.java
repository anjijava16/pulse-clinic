package com.pulse.rest.service.resource;

import com.pulse.model.NextVisit;
import java.util.List;
import javax.ws.rs.core.Response;

/**
 *
 * @author befast
 */
public interface NextVisitResource {
    
    public Response update(NextVisit visit);    
    
    public List<NextVisit> findBy(String dateBuffer);
    
    public Response delete(long id);
}
