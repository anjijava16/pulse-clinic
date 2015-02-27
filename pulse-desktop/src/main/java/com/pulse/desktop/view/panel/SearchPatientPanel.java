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
package com.pulse.desktop.view.panel;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import com.pulse.desktop.view.util.Settings;
import com.pulse.model.Patient;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public final class SearchPatientPanel {
    
    private final JPanel ROOT_PANEL = new JPanel();
    private final GridBagConstraints gbc = new GridBagConstraints();
    
    private final DefaultListModel<String> DLM = new DefaultListModel<>();
    private final JList<String> PATIENTLIST = new JList<>(this.DLM);
    private final JScrollPane PATIENT_LIST_SCROLL_PANE = new JScrollPane(this.PATIENTLIST);
        
    private final JTextField PATIENT_SEARCH_FIELD = new JTextField(Settings.TEXT_FIELD_MAX_CHARS);
    
    private final JButton LOADD_ALL_BUTTON = new JButton("", new ImageIcon("./pic/update_mini.png"));
    private final JButton SEARCH_BUTTON = new JButton("Поиск");
        
    public SearchPatientPanel() {
        initializeRootPanel();
    }
    
    public void addPatientToList(Patient patient) {
        this.DLM.addElement(patient.getNfp());
    }
    
    public void removePatientFromList(String nfp) {
        this.DLM.removeElement(nfp);
    }       
         
    private void initializeRootPanel() {
        this.LOADD_ALL_BUTTON.setBorder(BorderFactory.createEmptyBorder());
        this.LOADD_ALL_BUTTON.setBorderPainted(false);
        this.LOADD_ALL_BUTTON.setFocusPainted(false);
        this.LOADD_ALL_BUTTON.setFocusable(false);
        this.LOADD_ALL_BUTTON.setOpaque(false);
        
        this.PATIENTLIST.setVisibleRowCount(6);
        
        this.ROOT_PANEL.setVisible(true);
        this.ROOT_PANEL.setLayout(new GridBagLayout());
        this.ROOT_PANEL.setBorder(new TitledBorder(new EtchedBorder(), "Поиск пациента"));
            
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 0;
        this.gbc.gridy = 0;
        this.gbc.gridwidth = 1;
        this.gbc.weightx = 0;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        this.ROOT_PANEL.add(this.LOADD_ALL_BUTTON, this.gbc);
        
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 1;
        this.gbc.gridy = 0;
        this.gbc.gridwidth = 2;
        this.gbc.weightx = 0.5;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        this.ROOT_PANEL.add(this.PATIENT_SEARCH_FIELD, this.gbc);
        
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 3;
        this.gbc.gridy = 0;
        this.gbc.gridwidth = 1;
        this.gbc.weightx = 0;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        this.ROOT_PANEL.add(this.SEARCH_BUTTON, this.gbc);
                
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 0;
        this.gbc.gridy = 1;
        this.gbc.gridwidth = 4;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        this.ROOT_PANEL.add(this.PATIENT_LIST_SCROLL_PANE, this.gbc);
    }
    
    public JTextField getPatientSearchField() {
        return this.PATIENT_SEARCH_FIELD;
    }
    
    public JButton getSearchButton() {
        return this.SEARCH_BUTTON;
    }
    
    public JButton getLoadAllButton() {
        return this.LOADD_ALL_BUTTON;
    }
    
    public DefaultListModel<String> getDefaultListModel() {
        return this.DLM;
    }
    
    public JList<String> getPatientList() {
        return this.PATIENTLIST;
    }
    
    public JPanel getRootPanel() {
        return this.ROOT_PANEL;
    }
}
