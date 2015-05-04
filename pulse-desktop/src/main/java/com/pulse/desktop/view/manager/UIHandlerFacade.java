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
package com.pulse.desktop.view.manager;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public final class UIHandlerFacade {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private static UIHandlerFacade UI_HANDLER_FACADE_IMPL = new UIHandlerFacade();

    private volatile int laboratoryFrameIterator  = 1;
        
    private UIHandlerFacade() {
    }

    public void updateLaboratoryFrameIterator() {
        this.laboratoryFrameIterator = 1;
    }
    
    public static UIHandlerFacade getInstance() {
        return UI_HANDLER_FACADE_IMPL;
    }

}