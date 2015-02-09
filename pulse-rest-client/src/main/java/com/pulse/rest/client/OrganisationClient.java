package com.pulse.rest.client;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;

import com.pulse.model.Organisation;


/**
 *
 * @author Vladimir Shin
 * 
 */
public class OrganisationClient {
    
    private final SimpleClient<Organisation> ORGANISATION_CLIENT = new SimpleClient<>();
    private final String ENDPOINT = "organisation/";
        
    public HttpResponse update(Organisation organisation) throws IOException {
        final String path = this.ENDPOINT.concat("update/");
        
        return this.ORGANISATION_CLIENT.executePost(path, organisation);
    }
    
    public HttpResponse deleteBy(long id) throws IOException {
        final String path = this.ENDPOINT.concat("delete/").concat(String.valueOf(id));
                
        return this.ORGANISATION_CLIENT.executeGet(path);
    }
            
    public HttpResponse deleteBy(String name) throws IOException {
        final String path = this.ENDPOINT.concat("delete/name/").concat(name);
        
        return this.ORGANISATION_CLIENT.executeGet(path);
    }
    
    public List<Organisation> listAll() throws IOException {
        final String path = this.ENDPOINT.concat("list/");
                
        return this.ORGANISATION_CLIENT.list(path, Organisation.class);
    }
}