package com.pulse.desktop.controller;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;

/**
 *
 * @author befast
 */
public class SearchFieldFocusListener implements FocusListener {

    public SearchFieldFocusListener() {
    }
    
    @Override
    public void focusGained(FocusEvent e) {
        ((JTextField) e.getSource()).setText("");
    }

    @Override
    public void focusLost(FocusEvent e) {
        
    }
}
