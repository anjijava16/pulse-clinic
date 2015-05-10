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


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.view.panel.PatientInfoPanel;
import com.pulse.desktop.view.panel.SearchPatientPanel;
import com.pulse.model.Patient;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class ShowSelectedPatientInfoListener implements MouseListener {

    private PatientInfoPanel patientInfo;
    private SearchPatientPanel searchPatientPanel;

    public ShowSelectedPatientInfoListener() {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (this.patientInfo == null) {
            return;
        }
        
        if (this.searchPatientPanel == null) {
            return;
        }

        ThreadPoolService.INSTANCE.execute(() -> {
            final String nfp = this.searchPatientPanel.getPatientList().getSelectedValue();
            
            if (nfp == null || nfp.isEmpty()) {
                return;
            }

            try {
                final Patient patient = this.patientInfo.findAndShowBy(nfp);

                if (patient == null) {
                    ResultToolbarService.INSTANCE.showFailedStatus("Пациент был удален");
                    return;
                }
                
                this.patientInfo.setCurrentPatient(patient);
            } catch (IOException ioe) {
                ResultToolbarService.INSTANCE.showFailedStatus("Пациент был удален");
            }
        });
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    public void setPatientInfo(PatientInfoPanel patientInfo) {
        this.patientInfo = patientInfo;
    }

    public void setSearchPatientPanel(SearchPatientPanel searchPatientPanel) {
        this.searchPatientPanel = searchPatientPanel;
    }
}
