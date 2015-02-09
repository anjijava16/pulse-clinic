/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pulse.desktop.view.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author befast
 */
public class FileManager {
    
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    
    public FileManager() {
    }
    
    public void copyFile(String from, String to) {        
        // Current parent path
        File root = new File("");
        
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
            ioe.printStackTrace();
        }        
    }
    
    public void copyToTemp(String fromDir, String toDir, String filename) {        
        // Current parent path
        File root = new File("");
        
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
            ioe.printStackTrace();
        }        
    }
    
    public void saveAnalys(byte[] buffer, String analysFilePath) {
        try {
            File file = new File(analysFilePath);
            
            FileOutputStream outstream = new FileOutputStream(file);
            outstream.write(buffer);
            outstream.flush();
            outstream.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    public void deleteFile(String filename) {        
        File file = new File(filename);        
        FileUtils.deleteQuietly(file);
    }
    
    public byte[] readFile(String filename) {
        byte[] buffer = null;
        
        try {
            File file = new File(filename);
            if (file.exists()) {
                long size = file.length();
                buffer = new byte[(int) size];
                
                FileInputStream instream = new FileInputStream(file);
                instream.read(buffer);
                instream.close();
                return buffer;
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
            
        return buffer;
    }
}