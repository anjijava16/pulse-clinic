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


import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pulse.desktop.controller.LoadPatientsListListener;
import com.pulse.desktop.controller.SearchAndShowPatientListener;
import com.pulse.desktop.controller.ShowSelectedPatientInfoListener;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public final class SearchAndShowPatientInfoPanel {
    
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    
    private final JPanel ROOT_PANEL = new JPanel();
        
    private final SearchPatientPanel SEARCH_PATIENT_PANEL = new SearchPatientPanel();
    private final PatientInfoPanel PATIENT_INFO_PANEL = new PatientInfoPanel();
        
    public SearchAndShowPatientInfoPanel() {
        configurePanel();
        addAllActionListeners(); 
    }
    
    public void removeLastSelectedPatientFromList(String oldNfp) {
        this.SEARCH_PATIENT_PANEL.removePatientFromList(oldNfp);
    }
        
    public void addAllActionListeners() {
        final SearchAndShowPatientListener saspl = new SearchAndShowPatientListener();
        saspl.setSearchPatientPanel(this.SEARCH_PATIENT_PANEL);
        
        this.SEARCH_PATIENT_PANEL.getSearchButton().addActionListener(saspl);
        
        final ShowSelectedPatientInfoListener sspil = new ShowSelectedPatientInfoListener();
        sspil.setPatientInfo(this.PATIENT_INFO_PANEL);
        sspil.setSearchPatientPanel(this.SEARCH_PATIENT_PANEL);
        
        this.SEARCH_PATIENT_PANEL.getPatientList().addMouseListener(sspil); 
        
        final LoadPatientsListListener lpll = new LoadPatientsListListener();
        lpll.setSearchPatientPanel(this.SEARCH_PATIENT_PANEL);
        
        this.SEARCH_PATIENT_PANEL.getLoadAllButton().addActionListener(lpll);
    }
    
    private void configurePanel() {
        this.ROOT_PANEL.setLayout(new BoxLayout(this.ROOT_PANEL, BoxLayout.Y_AXIS)); 
        
        this.ROOT_PANEL.add(this.SEARCH_PATIENT_PANEL.getRootPanel());
        this.ROOT_PANEL.add(this.PATIENT_INFO_PANEL.getRootPanel());
    }
    
    public JPanel getRootPanel() {
        return this.ROOT_PANEL;
    }
    
    public SearchPatientPanel getSearchPatientPanel() {
        return this.SEARCH_PATIENT_PANEL;
    }
    
    public PatientInfoPanel getPatientInfoPanel() {
        return this.PATIENT_INFO_PANEL;
    }
}