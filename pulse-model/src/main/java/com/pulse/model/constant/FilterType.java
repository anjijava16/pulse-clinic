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
public enum FilterType {
    
    ALL(0),
    VISIT_TYPE_FILTER(1),
    VISIT_STATUS_FILTER(2);
    
    private FilterType(int id) {
        this.id = id;
    }
    
    private final int id;

    public int getId() {
        return id;
    }
    
}
