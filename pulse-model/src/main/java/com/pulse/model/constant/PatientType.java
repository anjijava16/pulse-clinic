/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pulse.model.constant;

import static com.pulse.model.constant.PatientSex.values;

/**
 *
 * @author befast
 */
public enum PatientType {
    
    VIP("VIP", 1),
    GENERAL("Обычный", 0);
    
    private final String label;
    private final int id;
    
    private PatientType(String label, int id) {
        this.label = label;
        this.id = id;
    }
    
    public static PatientType getBy(String label) {
        for (PatientType type : values()) {
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
