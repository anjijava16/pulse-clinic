package com.pulse.desktop.view.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 *
 * @author befast
 */
public class ConfigParser {
    
    public ConfigParser() {
    }
    
    public static String getWordPath() {
        try {
            final String path = "data/config/office.ini";
            
            final FileInputStream finstream = new FileInputStream(path);
            final Properties properties = new Properties();
            properties.load(finstream);
                      
            final String value = properties.getProperty("m_office_path");
            
            finstream.close();
            properties.clear();
            
            return value;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        
        return null;
    }
    
    public static String getExcelPath() {
        try {
            final String path = "data/config/office.ini";
            
            final FileInputStream finstream = new FileInputStream(path);
            final Properties properties = new Properties();
            properties.load(finstream);
                      
            final String value = properties.getProperty("e_office_path");
            
            finstream.close();
            properties.clear();
            
            return value;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        
        return null;
    }
}
