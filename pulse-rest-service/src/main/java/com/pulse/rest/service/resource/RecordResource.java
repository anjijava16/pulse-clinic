package com.pulse.rest.service.resource;

import java.util.List;

import javax.ws.rs.core.Response;

import com.pulse.model.Record;


/**
 *
 * @author Vladimir Shin <vladimir.shin@gmail.com>
 */ 
public interface RecordResource {
    
    public Record getBy(Long id);
    
    public List<Record> listByPatientId(Long patientId);
    
    public void update(Record record);
    
    public Record getWithBinaryBy(long id);
    
    public Response delete(long id);
}