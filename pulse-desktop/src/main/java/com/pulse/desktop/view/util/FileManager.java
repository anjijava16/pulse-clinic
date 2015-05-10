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


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class FileManager {
    
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    public void copyFile(String from, String to) {        
        // Current parent path
        final File root = new File("");
        
        final String sourceRoot = root.getAbsolutePath() + "/" + from;
        final String destRoot = root.getAbsolutePath() + "/" + to;
        
        this.LOGGER.info("from sourceRoot: " + sourceRoot);
        this.LOGGER.info("to destRoot: " + destRoot);
        
        final File sourceFile = new File(sourceRoot);
        final File destFile = new File(destRoot);                
        
        try {
            if (sourceFile.exists()) {
                FileUtils.copyFile(sourceFile, destFile);
            }
        } catch (IOException ioe) {
            this.LOGGER.error(ExceptionUtils.getFullStackTrace(ioe));
        }        
    }
    
    public void copyToTemp(String fromDir, String toDir, String filename) {        
        // Current parent path
        final File root = new File("");
        
        final String sourceRoot = root.getAbsolutePath()+ File.separator + fromDir + filename;
        final String destRoot = root.getAbsolutePath()+ File.separator + toDir + filename;
        
        this.LOGGER.info("from sourceRoot: " + sourceRoot);
        this.LOGGER.info("to destRoot: " + destRoot);
        
        final File sourceFile = new File(sourceRoot);
        final File destFile = new File(destRoot);                
        
        try {
            if (sourceFile.exists()) {
                FileUtils.copyFile(sourceFile, destFile);
            }
        } catch (IOException ioe) {
            this.LOGGER.error(ExceptionUtils.getFullStackTrace(ioe));
        }        
    }

    public byte[] readFile(final String filename) {
        byte[] buffer = null;
        
        try {
            final File file = new File(filename);
            if (file.exists()) {
                final long size = file.length();
                buffer = new byte[(int) size];
                
                final FileInputStream instream = new FileInputStream(file);
                instream.read(buffer);
                instream.close();
            }
        } catch (IOException ioe) {
            this.LOGGER.error(ExceptionUtils.getFullStackTrace(ioe));
        }
            
        return buffer;
    }
}