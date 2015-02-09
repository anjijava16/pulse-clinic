package com.pulse.desktop.view.util;

/**
 *
 * @author befast
 */
public enum NameValidator {
    INSTANCE;
    
    private final String NAME_PATTERN = "\\p{L}+";
    
    private NameValidator() {
    }
    
    public boolean isValid(String name) {
        return name.matches(this.NAME_PATTERN);
    } 
    
    public String getExtension(String filename) {
        if (filename == null || filename.trim().isEmpty()) return ".doc";
        
        String extension = null;
        final int index = filename.lastIndexOf(".");
        if (index > 0) {
            return extension = filename.substring(index);
        } else {
            return ".doc";
        }
    }
}
