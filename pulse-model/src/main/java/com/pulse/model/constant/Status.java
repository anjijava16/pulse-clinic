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
public enum Status {
    
    NOT_HANDLED("Не осмотрен", 0),
    HANDLED("Осмотрен", 1);
    
    Status(String name, int code) {
        this.name = name;
        this.code = code;
    }
    
    private String name;
    private int code;

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }
        
    public static Status findBy(final int code) {
        for (Status status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        } 
        
        throw new IllegalArgumentException("unknown status code");
    }
}
