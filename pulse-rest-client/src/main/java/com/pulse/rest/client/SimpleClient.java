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


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import com.pulse.rest.client.util.JsonService;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class SimpleClient<T> {
    
    private final ObjectMapper MAPPER = new ObjectMapper();
    private final String CONFIG_PATH = "./data/config/config.ini";
    private String REST_URL = "http://%s:8080/%s";
    
    public SimpleClient() {
        initializeUrl();
    }
    
    private void initializeUrl() {
        try {
            final FileInputStream instream = new FileInputStream(CONFIG_PATH);
            final Properties properites = new Properties();
            properites.load(instream);

            REST_URL = String.format(
                    REST_URL, 
                    properites.getProperty("server_ip", "127.0.0.1"), 
                    properites.getProperty("path", "pulse-rest-service/rest/")
            );
            
            instream.close();
            properites.clear();
        } catch (FileNotFoundException fnfe) {
            REST_URL = String.format(REST_URL, "127.0.0.1", "pulse-rest-service/rest/");
        } catch (IOException ioe) {
            REST_URL = String.format(REST_URL, "127.0.0.1", "pulse-rest-service/rest/");
        }
    }
    
    public HttpResponse executeGetWithHeader(String link, Map<String, String> header) throws IOException {
        final CloseableHttpClient client = HttpClientBuilder.create().build();
        
        final Set<String> keys = header.keySet();
        final Collection<String> values = header.values();
        
        final HttpGet request = new HttpGet(this.REST_URL.concat(link));
        
        final Iterator<String> keysIterator = keys.iterator();
        final Iterator<String> valuesIterator = values.iterator();
        
        while (keysIterator.hasNext() && valuesIterator.hasNext()) {
            final String key = keysIterator.next();
            final String value = valuesIterator.next();
            
            request.addHeader(key, value);
        }
        
        final HttpResponse response = client.execute(request);
        
        client.close();
        
        return response;
    }
    
    public T executeGetWithHeader(String link, Map<String, String> header, Class type) throws IOException {
        final CloseableHttpClient client = HttpClientBuilder.create().build();
        
        final Set<String> keys = header.keySet();
        final Collection<String> values = header.values();
        
        final HttpGet request = new HttpGet(this.REST_URL.concat(link));
        
        final Iterator<String> keysIterator = keys.iterator();
        final Iterator<String> valuesIterator = values.iterator();
        
        while (keysIterator.hasNext() && valuesIterator.hasNext()) {
            final String key = keysIterator.next();
            final String value = valuesIterator.next();
            
            request.addHeader(key, value);
        }
        
        final HttpResponse response = client.execute(request);
        
        final HttpEntity entity = response.getEntity();
        
        if (entity == null) return null;
        
        final InputStream instream = entity.getContent();
        byte[] buffer = new byte[8*1024];
        
        int wasread = -1;
        final StringBuilder jsonBuilder = new StringBuilder();
        
        while ((wasread = instream.read(buffer)) > 0) {
            String chunk = new String(buffer, 0, wasread, "UTF-8");
            jsonBuilder.append(chunk);
        }
        
        client.close();
        
        T t = (T) this.MAPPER.readValue(jsonBuilder.toString(), type);
        
        return t;
    }
    
    public HttpResponse executeGet(String link) throws IOException {
        final CloseableHttpClient client = HttpClientBuilder.create().build();
                
        final HttpGet request = new HttpGet(this.REST_URL.concat(link));
                
        final HttpResponse response = client.execute(request);
        
        client.close();
        
        return response;
    }
    
    public T executePojoGet(String link, Class type) throws IOException {
        final CloseableHttpClient client = HttpClientBuilder.create().build();
        
        final HttpGet request = new HttpGet(this.REST_URL.concat(link));
                
        final HttpResponse response = client.execute(request);
        final HttpEntity entity = response.getEntity();
        
        if (entity == null) return null;
        
        final InputStream instream = entity.getContent();
        byte[] buffer = new byte[8*1024];
        
        int wasread = -1;
        final StringBuilder jsonBuilder = new StringBuilder();
        
        while ((wasread = instream.read(buffer)) > 0) {
            String chunk = new String(buffer, 0, wasread, "UTF-8");
            jsonBuilder.append(chunk);
        }
        
        client.close();
        
        T t = (T) this.MAPPER.readValue(jsonBuilder.toString(), type);
        
        return t;
    }
    
    public HttpResponse executePost(String link, T instance) throws IOException {
        final CloseableHttpClient client = HttpClientBuilder.create().build();
                
        final HttpPost request = new HttpPost(this.REST_URL.concat(link));
        
        final String jsonBuffer = JsonService.INSTANCE.toJsonString(instance);
                
        final StringEntity entity = new StringEntity(jsonBuffer, "UTF-8");
        request.setHeader("Content-type", "application/json;charset=UTF-8");
        request.setEntity(entity);
        
        final HttpResponse response = client.execute(request);
        
        client.close();
        
        return response;
    }
    
    public List<T> list(String link, Class type) throws IOException {        
        final CloseableHttpClient client = HttpClientBuilder.create().build();
                
        final HttpGet request = new HttpGet(this.REST_URL.concat(link));
        final HttpResponse response = client.execute(request);
                
        final String jsonBody = EntityUtils.toString(response.getEntity(), "UTF-8");
                
        List<T> recordsList = 
                this.MAPPER.readValue(
                        jsonBody, TypeFactory.defaultInstance().constructCollectionType(List.class, type)
                );
                
        return recordsList;
    }
    
    public List<T> listPost(String link, Object instance, Class type) throws IOException {        
        final CloseableHttpClient client = HttpClientBuilder.create().build();
                
        final HttpPost request = new HttpPost(this.REST_URL.concat(link));
        final String jsonBuffer = JsonService.INSTANCE.toJsonString(instance);
                
        final StringEntity entity = new StringEntity(jsonBuffer, "UTF-8");
        request.setHeader("Content-type", "application/json;charset=UTF-8");
        request.setEntity(entity);
        
        final HttpResponse response = client.execute(request);
                
        final String jsonBody = EntityUtils.toString(response.getEntity(), "UTF-8");
                
        List<T> recordsList = 
                this.MAPPER.readValue(
                        jsonBody, TypeFactory.defaultInstance().constructCollectionType(List.class, type)
                );
                
        return recordsList;
    }
}
