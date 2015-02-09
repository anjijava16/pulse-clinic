/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 * 
 * @author befast
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
