package com.pulse.model.constant;

/**
 *
 * @author befast
 */
public enum PatientSex {
    
    MEN("Мужской", 1),
    WOMAN("Женский", 0);
    
    private final String label;
    private final int id;
    
    private PatientSex(String label, int id) {
        this.label = label;
        this.id = id;
    }
    
    public static PatientSex getBy(String label) {
        for (PatientSex type : values()) {
            if (type.getLabel().equals(label)) {
                return type;
            }
        }
        
        return null;
    }

    public String getLabel() {
        return label;
    }

    public int getId() {
        return id;
    }    
}