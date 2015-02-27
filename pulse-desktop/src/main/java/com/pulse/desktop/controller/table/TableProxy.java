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
package com.pulse.desktop.controller.table;


import com.pulse.model.Visit;
import com.pulse.model.constant.Privelegies;
import java.text.SimpleDateFormat;
import com.pulse.model.constant.Privelegy;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public enum TableProxy {
    INSTANCE;
    
    private TableProxy() {
    }
    
    private final RegistryTableService REGISTRY_SERVICE = new RegistryTableService();
    private final LaboratoryTableService LABORATORY_SERVICE = new LaboratoryTableService();
    
    public String[] getRightBufferFrom(Visit visit, Privelegy privelegy, SimpleDateFormat dateFormat) {
        final String[] buffer;
        
        switch (privelegy.getId()) {                
            case Privelegies.LABORATORY:
                buffer = LABORATORY_SERVICE.proxyFrom(visit, dateFormat);
                return buffer;
                
            default:
                buffer = REGISTRY_SERVICE.proxyFrom(visit, dateFormat);
                return buffer;
        }
    }
}
