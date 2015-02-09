package com.pulse.desktop.view.panel;

import com.pulse.desktop.controller.LoadPatientsListListener;
import com.pulse.desktop.controller.SearchAndShowPatientListener;
import com.pulse.desktop.controller.ShowSelectedPatientInfoListener;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author befast
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