package com.pulse.desktop.view.util;

/**
 *
 * @author befast
 */
public enum Values {
    
    Empty(""),
    Unknown("Неизвестно");
    
    private Values(String value) {
        this.value = value;
    }
    
    private final String value;

    public String getValue() {
        return this.value;
    }

}
