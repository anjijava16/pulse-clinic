package com.pulse.desktop.controller.builder;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author befast
 */
public class MessageBuilder {
    
    public MessageBuilder() {
    }
    
    public static int getAnswerCode(JInternalFrame frame, String message, String header) {
        return JOptionPane.showOptionDialog(frame, 
                                           message, 
                                           header, 
                                           JOptionPane.YES_NO_CANCEL_OPTION, 
                                           JOptionPane.WARNING_MESSAGE, 
                                           null, 
                                           new String[]{"Да", "Нет"}, 
                                           "Да");
    }
    
    public static void showErrorMessage(JInternalFrame frame, String message, String header) {
        JOptionPane.showMessageDialog(frame, 
                                      message, 
                                      header, 
                                      JOptionPane.ERROR_MESSAGE);
    }
}
