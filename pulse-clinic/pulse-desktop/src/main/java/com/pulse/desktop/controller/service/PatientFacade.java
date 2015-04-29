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
package com.pulse.desktop.controller.service;


import com.pulse.model.Patient;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
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

