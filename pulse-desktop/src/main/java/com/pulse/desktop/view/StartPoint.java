/*
 * Copyright 2015 Vladimir Shin
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.pulse.desktop.view;


import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.pulse.desktop.view.frame.AuthenticationFrame;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public abstract class StartPoint {

    /**
     * This function is the start point.
     * @param args - This is the command line arguments.
     */
    public static void main(String[] args) {        
        // Change look and feel from default one to "Nimbus"
        enableNimbusTheme();
        
        SwingUtilities.invokeLater(() -> {            
            // We should show "Login" window first
            final AuthenticationFrame authFrame = new AuthenticationFrame();
        });
    }

    /**
     * This function enables "Nimbus" look and feel.
     */
    private static void enableNimbusTheme() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
