/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pulse.model.constant;

/**
 *
 * @author befast
 */
public enum VisitType {
    
    SCHEDULED("По записи", 1),
    CAMED("По приходу", 0);
    
    private VisitType(String label, int id) {
        this.label = label;
        this.id = id;
    }
    
    private String label;
    private int id;

    public String getLabel() {
        return label;
    }

    public int getId() {
        return id;
    }
    
    public static VisitType findBy(String label) {
        for (VisitType type : values()) {
            if (type.getLabel().equals(label))
                return type;
        }
        
        return null;
    }
    
    public static VisitType findBy(int id) {
        for (VisitType type : values()) {
            if (type.getId() == id)
                return type;
        }
        
        return null;
    }
}
