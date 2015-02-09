package com.pulse.rest.client;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;

import com.pulse.model.PatientRoom;



/**
 *
 * @author Vladimir Shin
 * 
 */
public class PatientRoomClient {
    
    private final SimpleClient<PatientRoom> PATIENT_ROOM_CLIENT = new SimpleClient<>();
    private final String ENDPOINT = "room/";
    
    public HttpResponse update(PatientRoom room) throws IOException {
        final String path = this.ENDPOINT.concat("update/");
        
        return this.PATIENT_ROOM_CLIENT.executeGet(path);
    }
    
    public HttpResponse deleteBy(long id) throws IOException {
        final String path = this.ENDPOINT.concat("delete/").concat(String.valueOf(id));
                
        return this.PATIENT_ROOM_CLIENT.executeGet(path);
    }
    
    public List<PatientRoom> listAll() throws IOException {
        final String path = this.ENDPOINT.concat("list/");
        
        return this.PATIENT_ROOM_CLIENT.list(path, PatientRoom.class);
    }
    
    public HttpResponse deleteBy(String name) throws IOException {
        final String path = this.ENDPOINT.concat("delete/name/").concat(name);
        
        return this.PATIENT_ROOM_CLIENT.executeGet(path);
    }
}