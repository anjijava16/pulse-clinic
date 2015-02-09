package com.pulse.rest.service.resource;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

/**
 *
 * @author befast
 */
public interface PowerResource {
    
    public Response poweroff(@Context HttpHeaders header);
}
