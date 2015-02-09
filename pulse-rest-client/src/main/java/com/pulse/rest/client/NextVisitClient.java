package com.pulse.rest.client;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;

import com.pulse.model.NextVisit;


/**
 *
 * @author Vladimir Shin
 * 
 */
public class NextVisitClient {
    
    private final SimpleClient<NextVisit> NEXT_VISIT_CLIENT = new SimpleClient<>();
    private final String ENDPOINT = "next_visit/";
    
    public List<NextVisit> findByDate(String dateBuffer) throws IOException {
        final String path = this.ENDPOINT.concat("find/date/").concat(dateBuffer);
        
        return this.NEXT_VISIT_CLIENT.list(path, NextVisit.class);
    }
        
    public HttpResponse delete(long id) throws IOException {
        final String path = this.ENDPOINT.concat("delete/").concat(String.valueOf(id));
                
        return this.NEXT_VISIT_CLIENT.executeGet(path);
    }
    
    public HttpResponse update(NextVisit visit) throws IOException {
        final String path = this.ENDPOINT.concat("update/");
        
        return this.NEXT_VISIT_CLIENT.executePost(path, visit);
    }
}