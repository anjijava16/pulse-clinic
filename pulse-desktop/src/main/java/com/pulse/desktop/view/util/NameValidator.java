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
package com.pulse.desktop.view.util;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public enum NameValidator {
    INSTANCE;
    
    private final String NAME_PATTERN = "\\p{L}+";
    
    private NameValidator() {
    }
    
    public boolean isValid(String name) {
        return name.matches(this.NAME_PATTERN);
    } 
    
    public String getExtension(String filename) {
        if (filename == null || filename.trim().isEmpty()) return ".doc";
        
        String extension = null;
        final int index = filename.lastIndexOf(".");
        if (index > 0) {
            return extension = filename.substring(index);
        } else {
            return ".doc";
        }
    }
}
