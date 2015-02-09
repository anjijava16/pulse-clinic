package com.pulse.rest.client.util;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author befast
 */
public enum JsonService {
    INSTANCE;
    
    private final ObjectMapper MAPPER = new ObjectMapper();
    
    private JsonService() {
    }
    
    public String toJsonString(Object obj) throws IOException {
        return this.MAPPER.writeValueAsString(obj);
    }
    
    public Object toJsonObject(String buffer, Class instance) throws IOException {
        return this.MAPPER.readValue(buffer, instance);
    }
}
