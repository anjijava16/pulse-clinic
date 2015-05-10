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
package com.pulse.desktop.view.registratur;


import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import com.pulse.desktop.controller.DeletePatientListener;
import com.pulse.desktop.view.panel.SearchAndShowPatientInfoPanel;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public final class PatientRemovingFrame {
    
    private final boolean RESIZABLE   = false;
    private final boolean CLOSABLE    = true;
    private final boolean MAXIMIZABLE = false;
    private final boolean ICONFIABLE  = false;

    protected final String FRAME_DESCRIPTION = "Информация";
    
    protected JInternalFrame frame = new JInternalFrame(this.FRAME_DESCRIPTION,
                                                        this.RESIZABLE,
                                                        this.CLOSABLE,
                                                        this.MAXIMIZABLE,
                                                        this.ICONFIABLE);

    protected final GridBagConstraints gbc = new GridBagConstraints();
    protected final JPanel ROOT_PANEL = new JPanel();
    
    protected int width  = 340;
    protected int height = 320;
    
    protected int maxChars = 13;
    
    protected final JToolBar TOOLBAR = new JToolBar(); 
    
    public JInternalFrame getInternalFrame() {
        return this.frame;    
    }
    
    protected void setToolbarSettings() {
        this.TOOLBAR.setFloatable(false);
        this.TOOLBAR.setVisible(true);
    }
    
    protected void addToolbarButton(JComponent toolbarButton, boolean addSeparator) {
        this.TOOLBAR.add(toolbarButton);
        
        if (addSeparator) {
            this.TOOLBAR.addSeparator();
        }
    }    
    
    public boolean frameIsVisible() {
        return this.frame.isVisible();
    }
    
    public void setFrameVisible(boolean visibility) {
        this.frame.setVisible(visibility);
    }
    
    private final SearchAndShowPatientInfoPanel SHOW_PATIENT_INFO_PANEL = new SearchAndShowPatientInfoPanel();
    private final JButton REMOVE_BUTTON = new JButton("Удалить");
    
    public PatientRemovingFrame(String title) {        
        this.frame.setTitle(title);
        setAllSettings();       
        addAllActionListeners();
        initializeFrame();
    }

    public void initializeFrame() {
        initializeRootPanel();
        
        this.width = 470;
        this.height = 400;
        
        this.frame.setSize(this.width, this.height);
        this.frame.setLayout(new BorderLayout());
        
        this.frame.add(this.ROOT_PANEL);
    }
    
    public void initializeRootPanel() {        
        this.ROOT_PANEL.setLayout(new BorderLayout()); 
        
        this.ROOT_PANEL.add(this.SHOW_PATIENT_INFO_PANEL.getRootPanel(), BorderLayout.CENTER);
        this.ROOT_PANEL.add(this.REMOVE_BUTTON, BorderLayout.SOUTH);
    }
    

    public void setAllSettings() {
        this.SHOW_PATIENT_INFO_PANEL.getPatientInfoPanel().denyEdit();
        
        this.frame.setVisible(false);
        
        this.frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.frame.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                setFrameVisible(false);
            }
        });
    }
    
    public void hidePanel() {
        setFrameVisible(false);
        
        this.SHOW_PATIENT_INFO_PANEL.getPatientInfoPanel().getNameField().setText("");
        this.SHOW_PATIENT_INFO_PANEL.getPatientInfoPanel().getFamilyNameField().setText("");
        this.SHOW_PATIENT_INFO_PANEL.getPatientInfoPanel().getSexBox().setSelectedIndex(0);
        this.SHOW_PATIENT_INFO_PANEL.getPatientInfoPanel().getClientTypeBox().setSelectedIndex(0);
        this.SHOW_PATIENT_INFO_PANEL.getPatientInfoPanel().getBirthdayField().setText("1900-01-01");
        this.SHOW_PATIENT_INFO_PANEL.getPatientInfoPanel().getClientTypeBox().setSelectedIndex(0);
        this.SHOW_PATIENT_INFO_PANEL.getPatientInfoPanel().getEmailField().setText("");
        this.SHOW_PATIENT_INFO_PANEL.getPatientInfoPanel().getMobileField().setText("");
    }
    
    public void addAllActionListeners() {
        final DeletePatientListener dpl = new DeletePatientListener();
        dpl.setSearchPatientPanel(this.SHOW_PATIENT_INFO_PANEL.getSearchPatientPanel());
        dpl.setPatientInfoPanel(this.SHOW_PATIENT_INFO_PANEL.getPatientInfoPanel());
        
        this.REMOVE_BUTTON.addActionListener(dpl);
    }
}