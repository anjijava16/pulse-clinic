package com.pulse.rest.logic.service.impl;

import com.pulse.rest.logic.PowerService;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author befast
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
