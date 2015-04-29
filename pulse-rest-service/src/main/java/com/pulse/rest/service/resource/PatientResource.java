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
package com.pulse.rest.service.resource;


import com.pulse.model.Patient;
import java.util.List;
import javax.ws.rs.core.Response;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public interface PatientResource {
    public Patient getBy(Long id);
    
    public Patient getBy(String token);
    
    public List<Patient> listAll();
    
    public List<Patient> listAll(String pattern);
    
    public Response update(Patient patient);
    
    public Response deleteBy(Long id);
    
    public Response deleteBy(String token);
}
