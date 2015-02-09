package com.pulse.desktop.view;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.pulse.desktop.view.frame.AuthentificationFrame;

/**
 * @author befast
 */
public abstract class StartPoint {

    private static void enableNimbusTheme() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * This function is the start point.
     * @param args - This is the command line arguments.
     */
    public static void main(String[] args) {        
        enableNimbusTheme();
        
        SwingUtilities.invokeLater(() -> {            
            AuthentificationFrame authFrame = new AuthentificationFrame();
        });
    }
}
