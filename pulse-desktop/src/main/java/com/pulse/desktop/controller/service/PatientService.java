package com.pulse.desktop.controller.service;

import com.pulse.desktop.view.util.HashBuilder;
import java.util.concurrent.LinkedBlockingQueue;
import com.pulse.model.Patient;
import com.pulse.rest.client.PatientClient;
import java.io.IOException;

/**
 *
 * @author Vladimir Shin <vladimir.shin@gmail.com>
 */
public enum PatientService {
    
    INSTANCE;
    
    private PatientClient patientService = new PatientClient();
    
    private PatientService() {
    }
    
    private LinkedBlockingQueue<Patient> patientsList 
            = new LinkedBlockingQueue<Patient>();
    
    public LinkedBlockingQueue<Patient> getPatientList() {
        return this.patientsList;
    }
    
    public boolean exists(String nfp) {
        for (Patient tempPatient : patientsList) {
            if (tempPatient.getNfp().equals(nfp)) {
                return true;
            }
        }
        
        return false;
    }
    
    public long getIdByName(String nfp) {
        for (Patient tempPatient : this.patientsList) {
            if (tempPatient.getNfp().equals(nfp)) {                
                return tempPatient.getId();
            }
        }
        
        return 0;
    }
    
    public Patient getByName(String nfp) {
        if (nfp == null || nfp.isEmpty()) return null;
        
        for (Patient tempPatient : this.patientsList) {
            if (tempPatient.getNfp().equals(nfp)) {                
                return  tempPatient;
            }
        }
        
        try {
            final String token = HashBuilder.INSTANCE.token(nfp);
            return this.patientService.get(token);
        } catch (IOException ioe) {
            return null;
        }
    }
    
    public Patient getById(long id) {
        for (Patient tempPatient : this.patientsList) {
            if (tempPatient.getId() == id) {                
                return  tempPatient;
            }
        }
        
        try {
            return this.patientService.get(id);
        } catch (IOException ioe) {
            return null;
        }
    }
    
    public boolean deleteById(long patientId) {
        for (Patient tempPatient : this.patientsList) {
            if (tempPatient.getId() == patientId) {                
                this.patientsList.remove(tempPatient);                
                return true;
            }
        }
        
        return false;
    }
    
    public boolean update(Patient patient) {
        for (Patient tempPatient : this.patientsList) {
            if (tempPatient.getId() == patient.getId()) {
                tempPatient.setBirthday(patient.getBirthday());
                tempPatient.setEmail(patient.getEmail());
                tempPatient.setMobile(patient.getMobile());
                tempPatient.setNfp(patient.getNfp());
                tempPatient.setSex(patient.getSex());
                tempPatient.setType(patient.getType());
                
                return true;
            }
        }
        
        return false;
    }
}
