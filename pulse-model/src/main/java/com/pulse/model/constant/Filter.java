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
public enum Filter {        
        ALL         ("Все",             (byte) 0),
        //EVENT_BASED ("По приходу",      (byte) 1),
        //REGISTERED  ("По записи",       (byte) 2),
        NOT_OBSERVED("Осмотренные", (byte) 3),
        OBSERVED    ("Неосмотренные",   (byte) 4);
        
        private Filter(String name, byte id) {
            this.name = name;
            this.id = id;
        }
        
        private String name;
        private byte id;
        
        public String getName() {
            return this.name;
        }        
    }
