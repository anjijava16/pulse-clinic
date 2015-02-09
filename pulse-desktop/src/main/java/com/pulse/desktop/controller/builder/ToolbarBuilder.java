package com.pulse.desktop.controller.builder;

import javax.swing.JToolBar;

/**
 *
 * @author befast
 */
public class ToolbarBuilder {
    
    private ToolbarBuilder() {
    }
    
    public static JToolBar build() {
        final JToolBar toolbar = new JToolBar();
        
        toolbar.setFloatable(false);
        toolbar.setVisible(true);
        
        return toolbar;
    }
}
