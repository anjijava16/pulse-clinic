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
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;

import com.pulse.model.User;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class UserClient {
    
    private final SimpleClient<User> USER_CLIENT = new SimpleClient<>();
    private final String ENDPOINT = "user/";
    
    public HttpResponse delete(long id) throws IOException {
        final String path = this.ENDPOINT.concat("delete/id/").concat(String.valueOf(id));
                
        return this.USER_CLIENT.executeGet(path);
    }
    
    public User find(String username) throws IOException {
        final String path = this.ENDPOINT.concat("find/username/").concat(username);
                
        return this.USER_CLIENT.executePojoGet(path, User.class);
    }
    
    public User find(String username, String password) throws IOException {
        final String path = this.ENDPOINT.concat("find/");
        
        final Map<String, String> headerMap = new HashMap<>();
        headerMap.put("username", username);
        headerMap.put("password", password);
        
        return this.USER_CLIENT.executeGetWithHeader(path, headerMap, User.class);
    }
        
    public HttpResponse update(User user) throws IOException {
        final String path = this.ENDPOINT.concat("update/");
                
        return this.USER_CLIENT.executePost(path, user);
    }
    
    public User get(long id) throws IOException {
        final String path = this.ENDPOINT.concat("get/id/").concat(String.valueOf(id));
                
        return this.USER_CLIENT.executePojoGet(path, User.class);
    }
    
    public List<User> list() throws IOException {
        final String path = this.ENDPOINT.concat("list/");
                
        return this.USER_CLIENT.list(path, User.class);
    }
}