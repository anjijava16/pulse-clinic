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
package com.pulse.desktop.view.frame.childframes.template;


import java.io.File;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class TemplateService {
    
    protected String root;
      
    public File[] listFiles(String root) {
        final File templatesLocation = new File(root);
        
        if (templatesLocation.exists()) {
            return templatesLocation.listFiles();
        }
        
        return null;
    }

    public String getRoot() {
        return root;
    }
}
