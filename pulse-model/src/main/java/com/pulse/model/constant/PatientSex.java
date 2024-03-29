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
package com.pulse.model.constant;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public enum PatientSex {
    
    MEN("Мужской", 1),
    WOMAN("Женский", 0);
    
    private final String label;
    private final int id;
    
    PatientSex(String label, int id) {
        this.label = label;
        this.id = id;
    }
    
    public static PatientSex getBy(String label) {
        for (PatientSex type : values()) {
            if (type.getLabel().equals(label)) {
                return type;
            }
        }
        
        return null;
    }

    public String getLabel() {
        return label;
    }

    public int getId() {
        return id;
    }    
}