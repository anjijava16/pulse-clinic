package com.pulse.rest.client;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;

import com.pulse.model.Journal;


/**
 *
 * @author Vladimir Shin
 */
public class JournalClient {
    
    private final SimpleClient<Journal> JOURNAL_CLIENT = new SimpleClient<>();
    private final String ENDPOINT = "journal/";    
    
    public HttpResponse delete(long id) throws IOException {
        final String path = this.ENDPOINT.concat("delete/").concat(String.valueOf(id));       
        
        return this.JOURNAL_CLIENT.executeGet(path);
    }
    
    public Journal getWithBinaryBy(long id) throws IOException {
        final String path = this.ENDPOINT.concat("get/binary/").concat(String.valueOf(id)); 
        
        return this.JOURNAL_CLIENT.executePojoGet(path, Journal.class);
    }
    
    public HttpResponse update(Journal record) throws IOException {
        final String path = this.ENDPOINT.concat("update/");        
        
        return this.JOURNAL_CLIENT.executePost(path, record);
    }
    
    public List<Journal> list() throws IOException {
        final String path = this.ENDPOINT.concat("list/");
        
        return this.JOURNAL_CLIENT.list(path, Journal.class);
    }
}
