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
public interface Settings {
    public static final String APPLICATION_VERSION = "Prometheus v.1.0.0a - ";
    
    public static final int TEXT_FIELD_MAX_CHARS = 10;
    
    public static final String M_OFFICE_PATH = ConfigParser.getWordPath();
    public static final String E_OFFICE_PATH = ConfigParser.getExcelPath();

    public static final byte NOT_VISITED = 0;
    public static final byte VISIT_TYPE_CAMED = 0;
    public static final byte STATUS_TYPE_VIEW = 1;
}