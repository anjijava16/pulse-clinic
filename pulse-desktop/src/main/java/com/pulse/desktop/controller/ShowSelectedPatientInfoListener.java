package com.pulse.desktop.controller;

import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.view.panel.PatientInfoPanel;
import com.pulse.desktop.view.panel.SearchPatientPanel;
import com.pulse.model.Patient;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

/**
 *
 * @author befast
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
                return;
            }
        });
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public void setPatientInfo(PatientInfoPanel patientInfo) {
        this.patientInfo = patientInfo;
    }

    public void setSearchPatientPanel(SearchPatientPanel searchPatientPanel) {
        this.searchPatientPanel = searchPatientPanel;
    }
}
