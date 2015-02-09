package com.pulse.rest.client;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;

import com.pulse.model.FilteredVisit;
import com.pulse.model.SearchTemplate;
import com.pulse.model.Visit;


/**
 *
 * @author Vladimir Shin
 * 
 */
public class VisitClient {
    
    private final SimpleClient<Visit> VISIT_CLIENT = new SimpleClient<>();
    private final String ENDPOINT = "visit/";
        
    public List<Visit> filterBy(int filterType, int type, String date) throws IOException {
        final String path = this.ENDPOINT
                .concat("filter/").concat(String.valueOf(filterType))
                .concat("/").concat(String.valueOf(type))
                .concat("/").concat(String.valueOf(date));
        
        return this.VISIT_CLIENT.list(path, Visit.class);
    }
    
    public List<Visit> findByTemplate(SearchTemplate template) throws IOException {
        final String path = this.ENDPOINT.concat("bytemplate/");
        
        return this.VISIT_CLIENT.listPost(path, template, Visit.class);
    }
    
    public List<Visit> findByDate(String dateBuffer) throws IOException {
        final String path = this.ENDPOINT.concat("bydate/");
        
        return this.VISIT_CLIENT.list(path, Visit.class);
    }
           
    public Visit getWithBinaryBy(long id) throws IOException {
        final String path = this.ENDPOINT.concat("binaryvisit/id/").concat(String.valueOf(id));
        
        return this.VISIT_CLIENT.executePojoGet(path, Visit.class);
    }
    
    public Visit getBy(long id) throws IOException {        
        final String path = this.ENDPOINT.concat("visit/id/").concat(String.valueOf(id));
        
        return this.VISIT_CLIENT.executePojoGet(path, Visit.class);
    }
    
    public List<Visit> listBy(String organisation, String from, String until) throws IOException {
        final String path = this.ENDPOINT
                .concat("organisation/list/")
                .concat(organisation).concat("/")
                .concat(from).concat("/")
                .concat(until);
        
        return this.VISIT_CLIENT.list(path, Visit.class);
    }
    
    public List<Visit> listAll(String from, String until) throws IOException {
        final String path = this.ENDPOINT.concat("organisation/list/").concat(from).concat("/").concat(until);
        
        return this.VISIT_CLIENT.list(path, Visit.class);
    }
    
    public HttpResponse delete(long id) throws IOException {
        final String path = this.ENDPOINT.concat("delete/").concat(String.valueOf(id));
                
        return this.VISIT_CLIENT.executeGet(path);
    }
    
    public HttpResponse update(Visit visit) throws IOException {
        final String path = this.ENDPOINT.concat("update/");
                
        return this.VISIT_CLIENT.executePost(path, visit);
    }
    
    public List<Visit> filterBy(FilteredVisit filter) throws IOException {
        final String path = this.ENDPOINT.concat("filter/statistic");
        
        return this.VISIT_CLIENT.listPost(path, filter, Visit.class);
    }
    
    public List<Visit> filterBy(int department, long doctor) throws IOException {
        final String path = this.ENDPOINT.concat("filter/by/")
                .concat(String.valueOf(department)).concat("/")
                .concat(String.valueOf(doctor));
        
        return this.VISIT_CLIENT.list(path, Visit.class);
    }
}