package com.pulse.desktop.controller.service;

import com.pulse.model.Patient;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author befast
 */
public enum PatientFacade {
    INSTANCE;
    
    private LinkedBlockingQueue<Patient> patientsList = new LinkedBlockingQueue<>();
    
    private Patient lastSelectedPatient;
    
    private PatientFacade() {
    }

    public Patient getLastSelectedPatient() {
        return lastSelectedPatient;
    }

    public void setLastSelectedPatient(Patient lastSelectedPatient) {
        this.lastSelectedPatient = lastSelectedPatient;
    }

    public LinkedBlockingQueue<Patient> getPatientsList() {
        return patientsList;
    }

    public void setPatientsList(LinkedBlockingQueue<Patient> patientsList) {
        this.patientsList = patientsList;
    }    
            
    public void add(Patient patient) throws InterruptedException {
        this.patientsList.put(patient);
    }
    
    public Patient findBy(long id) {
        for (Patient patient : this.patientsList) {
            if (patient.getId() == id) {
                return patient;
            }
        }
        
        return null;
    }
    
    public Patient findBy(String name) {
        for (Patient patient : this.patientsList) {
            if (patient.getNfp().equals(name)) {
                return patient;
            }
        }
        
        return null;
    }
}

