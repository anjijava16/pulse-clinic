/*
 * Copyright 2015 Vladimir Shin
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.pulse.rest.client;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
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
