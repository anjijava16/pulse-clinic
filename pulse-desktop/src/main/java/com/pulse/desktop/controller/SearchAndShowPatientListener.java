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
public class SearchAndShowPatientListener implements ActionListener {

    private PatientClient patientService = new PatientClient();
    
    private SearchPatientPanel searchPatientPanel;

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (this.searchPatientPanel == null) {
            return;
        }

        ThreadPoolService.INSTANCE.execute(() -> {
            this.searchPatientPanel.getDefaultListModel().removeAllElements();
            
            final String pattern = this.searchPatientPanel.getPatientSearchField().getText().trim();
            
            if (pattern.isEmpty()) {
                ResultToolbarService.INSTANCE.showFailedStatus("Введите шаблон для поиска");
                return;
            }
            
            if (pattern.contains(" ")) {
                ResultToolbarService.INSTANCE.showFailedStatus("Шаблон не должен содержать пробелы");
                return;
            }
            
            try {
                final List<Patient> list = this.patientService.list(pattern);
                
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