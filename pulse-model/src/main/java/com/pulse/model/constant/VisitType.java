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
public enum VisitType {
    
    SCHEDULED("По записи", 1),
    CAMED("По приходу", 0);
    
    private VisitType(String label, int id) {
        this.label = label;
        this.id = id;
    }
    
    private String label;
    private int id;

    public String getLabel() {
        return label;
    }

    public int getId() {
        return id;
    }
    
    public static VisitType findBy(String label) {
        for (VisitType type : values()) {
            if (type.getLabel().equals(label))
                return type;
        }
        
        return null;
    }
    
    public static VisitType findBy(int id) {
        for (VisitType type : values()) {
            if (type.getId() == id)
                return type;
        }
        
        return null;
    }
}
