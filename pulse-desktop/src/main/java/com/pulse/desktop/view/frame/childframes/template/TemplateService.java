package com.pulse.desktop.view.frame.childframes.template;

import java.io.File;

/**
 *
 * @author befast
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
