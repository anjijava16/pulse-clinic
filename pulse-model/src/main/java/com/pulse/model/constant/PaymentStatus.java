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
public enum PaymentStatus {

    NOT_PAYED("Не оплачено", 0),
    PAYED("Оплачено", 1),
    DEBT("Долг", 2),
    BACK("Возврат", 3);
    
    private PaymentStatus(String name, int id) {
        this.name = name;
        this.id = id;
    }
    
    private final String name;
    private final int id;

    public static PaymentStatus findBy(String name) {
        for (PaymentStatus status : values()) {
            if (status.getName().equals(name)) return status;
        }
        
        return null;
    }
    
    public static PaymentStatus findBy(int id) {
        for (PaymentStatus status : values()) {
            if (status.getId() == id) return status;
        }
        
        return null;
    }
    
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
    
}
