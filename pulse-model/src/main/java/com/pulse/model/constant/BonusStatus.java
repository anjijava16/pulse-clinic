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
public enum BonusStatus {
    
    NO("Нет", 0),
    YES("Есть", 1),;
    
    BonusStatus(String name, int id) {
        this.name = name;
        this.id = id;
    }
    
    private final String name;
    private final int id;

    public static BonusStatus findBy(final String name) {
        for (BonusStatus status : values()) {
            if (status.getName().equals(name)) return status;
        }
        
        throw new IllegalArgumentException("unknown bonus status name");
    }
    
    public static BonusStatus findBy(final int id) {
        for (BonusStatus status : values()) {
            if (status.getId() == id) return status;
        }
        
        throw new IllegalArgumentException("unknown bonus status id");
    }
    
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
