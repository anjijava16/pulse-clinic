package com.pulse.model.constant;

/**
 *
 * @author befast
 */
public enum Status {
    
    NOT_HANDLED("Не осмотрен", 0),
    HANDLED("Осмотрен", 1);
    
    private Status(String name, int code) {
        this.name = name;
        this.code = code;
    }
    
    private String name;
    private int code;

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }
        
    public static Status findBy(int code) {
        for (Status status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        } 
        
        return null;
    }
}
