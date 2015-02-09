package com.pulse.desktop.controller.builder;

import com.pulse.model.constant.Filter;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author befast
 */
public class FilterBoxBuilder {
         
    private FilterBoxBuilder() {
    }
    
    public static DefaultComboBoxModel<String> build() {
        DefaultComboBoxModel<String> box = new DefaultComboBoxModel<>();
        
        for (Filter filter : Filter.values()) {
            box.addElement(filter.getName());
        }
        
        return box;
    }
}
