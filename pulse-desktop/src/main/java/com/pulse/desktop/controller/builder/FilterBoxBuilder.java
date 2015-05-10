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
package com.pulse.desktop.controller.builder;


import javax.swing.DefaultComboBoxModel;

import com.pulse.model.constant.Filter;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class FilterBoxBuilder {
         
    private FilterBoxBuilder() {
    }
    
    public static DefaultComboBoxModel<String> build() {
        DefaultComboBoxModel<String> box = new DefaultComboBoxModel<>();
        
        for (Filter filter : Filter.values()) {
            box.addElement(filter.getName());
        }
        
        return box;
    }
}
