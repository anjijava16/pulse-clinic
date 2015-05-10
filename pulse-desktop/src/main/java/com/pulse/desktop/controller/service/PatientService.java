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


import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

import com.pulse.desktop.view.util.HashBuilder;
import com.pulse.model.Patient;
import com.pulse.rest.client.PatientClient;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public enum PatientService {
    
    INSTANCE;
    
    private final PatientClient patientService = new PatientClient();

    private final LinkedBlockingQueue<Patient> patientsList = new LinkedBlockingQueue<>();

    public Patient getByName(String nfp) {
        if (nfp == null || nfp.isEmpty()) return null;
        
        for (final Patient tempPatient : this.patientsList) {
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
        for (final Patient tempPatient : this.patientsList) {
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
