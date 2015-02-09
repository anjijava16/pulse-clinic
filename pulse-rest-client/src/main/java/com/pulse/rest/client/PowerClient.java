package com.pulse.rest.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;

/**
 *
 * @author Vladimir Shin
 * 
 */
public class PowerClient {
    
    private final SimpleClient<Object> PATIENT_CLIENT = new SimpleClient<>();
    private final String ENDPOINT = "power/";
    
    public HttpResponse powerOff(String username, String password) throws IOException {
        final String path = this.ENDPOINT.concat("off/");
        
        final Map<String, String> headerMap = new HashMap<>();
        headerMap.put("username", "server");
        headerMap.put("password", "server123");
        
        return this.PATIENT_CLIENT.executeGetWithHeader(path, headerMap);
    }
}
