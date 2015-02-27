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
package com.pulse.desktop.controller;


import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.view.panel.SearchPatientPanel;
import com.pulse.model.Patient;
import com.pulse.rest.client.PatientClient;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class LoadPatientsListListener implements ActionListener {

    private PatientClient patientService = new PatientClient();
    
    private SearchPatientPanel searchPatientPanel;

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (this.searchPatientPanel == null) {
            return;
        }

        ThreadPoolService.INSTANCE.execute(() -> {
            this.searchPatientPanel.getDefaultListModel().removeAllElements();
                        
            try {
                final List<Patient> list = this.patientService.list();
                
                list.stream().forEach((patient) -> {
                    this.searchPatientPanel.getDefaultListModel().addElement(patient.getNfp());
                });
                
                ResultToolbarService.INSTANCE.showSuccessStatus();
            } catch (IOException ioe) {
                ResultToolbarService.INSTANCE.showFailedStatus("Ошибка сети");
                return;
            }
        });
    }

    public void setSearchPatientPanel(SearchPatientPanel searchPatientPanel) {
        this.searchPatientPanel = searchPatientPanel;
    }    
}
