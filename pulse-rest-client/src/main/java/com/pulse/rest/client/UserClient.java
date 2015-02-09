package com.pulse.rest.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;

import com.pulse.model.User;

/**
 *
 * @author Vladimir Shin
 * 
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