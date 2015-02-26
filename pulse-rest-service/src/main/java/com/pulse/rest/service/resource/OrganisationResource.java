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


import com.pulse.model.Organisation;

import java.util.List;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public interface OrganisationResource {
    
    public Organisation getById(Long id);
    
    public List<Organisation> listAll();
    
    public Organisation update(Organisation record);
    
    public Organisation deleteBy(Long id);
    
    public Organisation deleteBy(String name);
}
