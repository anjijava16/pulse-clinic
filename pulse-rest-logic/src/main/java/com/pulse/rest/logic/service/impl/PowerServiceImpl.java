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
package com.pulse.rest.logic.service.impl;


import com.pulse.rest.logic.PowerService;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
@Service (value = "powerService")
public class PowerServiceImpl implements PowerService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    
    @Value("${power.username:server}")
    private String username;
    
    @Value("${powerpassword:server123}")
    private String password;
    
    public void poweroff(String username, String password) {
        if (username == null) return;
        if (password == null) return;
        
        if (username.equals(this.username) && password.equals(this.password)) {
            try {
                Runtime.getRuntime().exec(new String[] {"shutdown", "-s", "-t", "30"});
            } catch (IOException ioe) {
                this.LOGGER.error(ioe.getMessage());
            }
        }
    }    
}
