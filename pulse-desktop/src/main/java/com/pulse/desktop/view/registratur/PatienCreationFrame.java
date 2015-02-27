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


import com.pulse.desktop.controller.CreatePatientListener;
import java.awt.GridBagConstraints;
import java.text.SimpleDateFormat;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import com.pulse.desktop.view.panel.PatientInfoPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public final class PatienCreationFrame  {
    
    private final boolean RESIZABLE   = false;
    private final boolean CLOSABLE    = true;
    private final boolean MAXIMIZABLE = false;
    private final boolean ICONFIABLE  = false;

    protected final String FRAME_DESCRIPTION = "Пациент";
    
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
    
    private final PatientInfoPanel PATIENT_INFO_PANEL = new PatientInfoPanel();
    
    private final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
        
    private String lastSelectedPatientNfp;
    
    private final JButton SAVE_PATIENT_BUTTON = new JButton("Сохранить");
    
    public PatienCreationFrame(String title) {        
        this.frame.setTitle(title);
        setAllSettings();       
        addAllActionListeners();
        intializeFrame();
    }
    
    public String getLastSelectedPatientNfp() {
        return this.lastSelectedPatientNfp;
    }
    
    public void hidePanel() {
        setFrameVisible(false);
        
        this.PATIENT_INFO_PANEL.getNameField().setText("");
        this.PATIENT_INFO_PANEL.getFamilyNameField().setText("");
        this.PATIENT_INFO_PANEL.getSexBox().setSelectedIndex(0);
        this.PATIENT_INFO_PANEL.getClientTypeBox().setSelectedIndex(0);
        this.PATIENT_INFO_PANEL.getBirthdayField().setText("1900-01-01");
        this.PATIENT_INFO_PANEL.getClientTypeBox().setSelectedIndex(0);
        this.PATIENT_INFO_PANEL.getEmailField().setText("");
        this.PATIENT_INFO_PANEL.getMobileField().setText("");
    }
                    
    public void intializeFrame() {
        initializeRootPanel();
        
        this.width = 440;
        this.height = 210;
        
        this.frame.setSize(this.width, this.height);

        this.frame.add(this.ROOT_PANEL);
    }
    
    public void initializeRootPanel() {    
        this.ROOT_PANEL.setLayout(new BorderLayout());
        
        this.ROOT_PANEL.add(this.PATIENT_INFO_PANEL.getRootPanel(), BorderLayout.CENTER);
        this.ROOT_PANEL.add(this.SAVE_PATIENT_BUTTON, BorderLayout.SOUTH);
    }
    
    public void setAllSettings() {
        this.frame.setVisible(false);
    }

    public void addAllActionListeners() {
        final CreatePatientListener cpl = new CreatePatientListener();
        cpl.setPatientInfoPanel(this.PATIENT_INFO_PANEL);
        
        this.SAVE_PATIENT_BUTTON.addActionListener(cpl);
        
        this.frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.frame.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                setFrameVisible(false);
            }
        });
    }
}