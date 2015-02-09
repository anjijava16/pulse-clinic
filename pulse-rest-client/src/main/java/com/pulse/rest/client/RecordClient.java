package com.pulse.rest.client;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;

import com.pulse.model.Record;


/**
 *
 * @author Vladimir Shin
 * 
 */
public class RecordClient {
    
    private final SimpleClient<Record> RECORD_CLIENT = new SimpleClient<>();
    private final String ENPOINT = "record/";
    
    public HttpResponse delete(long id) throws IOException {
        final String path = this.ENPOINT.concat("delete/").concat(String.valueOf(id));
                
        return this.RECORD_CLIENT.executeGet(path);
    }
    
    public Record getWithBinaryBy(long id) throws IOException {
        final String path = this.ENPOINT.concat("binary/read/").concat(String.valueOf(id));
                
        return this.RECORD_CLIENT.executePojoGet(path, Record.class);
    }
    
    public HttpResponse updateRecord(Record record) throws IOException {
        final String path = this.ENPOINT.concat("update/");
                
        return this.RECORD_CLIENT.executePost(path, record);
    }
    
    public List<Record> listBy(long patientId) throws IOException {        
        final String path = this.ENPOINT.concat("patientId/").concat(String.valueOf(patientId));
                
        return this.RECORD_CLIENT.list(path, Record.class);
    }    
}