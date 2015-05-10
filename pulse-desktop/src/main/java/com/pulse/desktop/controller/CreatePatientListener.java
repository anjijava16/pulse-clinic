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


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.view.manager.WindowManager;
import com.pulse.desktop.view.panel.PatientInfoPanel;
import com.pulse.desktop.view.util.HashBuilder;
import com.pulse.desktop.view.util.NameValidator;
import com.pulse.model.Patient;
import com.pulse.model.constant.PatientSex;
import com.pulse.model.constant.PatientType;
import com.pulse.rest.client.PatientClient;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class CreatePatientListener implements ActionListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private final PatientClient patientService = new PatientClient();
    
    private PatientInfoPanel patientInfoPanel;
    
    @Override
    public void actionPerformed(ActionEvent e) {
        ThreadPoolService.INSTANCE.execute(() -> {
            if (this.patientInfoPanel == null) return;
            
            final String name = this.patientInfoPanel.getNameField().getText().trim();
            final String familyName = this.patientInfoPanel.getFamilyNameField().getText().trim();
            final String birthday = this.patientInfoPanel.getBirthdayField().getText().trim();
            final String mobile = this.patientInfoPanel.getMobileField().getText().trim();
            final String email = this.patientInfoPanel.getEmailField().getText().trim();
            
            if (name.isEmpty()) {
                ResultToolbarService.INSTANCE.showFailedStatus("Введите имя");
                return;
            }
            
            if (! NameValidator.INSTANCE.isValid(name)) {
                ResultToolbarService.INSTANCE.showFailedStatus("Некорректное имя");
                return;
            }
            
            if (familyName.isEmpty()) {
                ResultToolbarService.INSTANCE.showFailedStatus("Введите фамилию");
                return;
            }
            
            if (! NameValidator.INSTANCE.isValid(familyName)) {
                ResultToolbarService.INSTANCE.showFailedStatus("Некорректная фамилия");
                return;
            }
            
            if (birthday.isEmpty()) {
                ResultToolbarService.INSTANCE.showFailedStatus("Введите день рождения");
                return;
            }
            
            if (PatientSex.getBy(this.patientInfoPanel.getSexBox().getSelectedItem().toString()) == null)
                return;
            
            if (PatientType.getBy(this.patientInfoPanel.getClientTypeBox().getSelectedItem().toString()) == null)
                return;
            
            final String nfp = String.format("%s %s", familyName, name);
            Date birthdayDate = null;
            
            try {
                birthdayDate = this.sdf.parse(birthday);
            } catch (ParseException pe) {
                ResultToolbarService.INSTANCE.showFailedStatus("Неверный формат даты. Пример: 1988-06-12");
                return;
            }
            
            try {
                final String token = HashBuilder.INSTANCE.token(nfp);
                Patient patient = this.patientService.get(token);
                
                if (patient != null) {
                    ResultToolbarService.INSTANCE.showFailedStatus("Данный пациент уже существует");
                    return;
                }

                final PatientSex sex = PatientSex.getBy(this.patientInfoPanel.getSexBox().getSelectedItem().toString());
                final PatientType patientType = PatientType.getBy(this.patientInfoPanel.getClientTypeBox().getSelectedItem().toString());

                if (sex == null) {
                    ResultToolbarService.INSTANCE.showFailedStatus("Пол указан неверно");
                    return;
                }

                if (patientType == null) {
                    ResultToolbarService.INSTANCE.showFailedStatus("Неизвестный тип пациента");
                    return;
                }
                
                patient = new Patient();
                patient.setBirthday(birthdayDate);
                patient.setEmail(email);
                patient.setMobile(mobile);
                patient.setNfp(nfp);
                patient.setSex(sex.getId());
                patient.setToken(token);
                patient.setType(patientType.getId());
                
                this.patientService.update(patient);
                ResultToolbarService.INSTANCE.showSuccessStatus();
                
                WindowManager.getInstance().getPatientCreationFrame().hidePanel();
            } catch (IOException ioe) {
                this.LOGGER.error(ioe.getMessage());
                ResultToolbarService.INSTANCE.showFailedStatus("Ошибка сети");
            }
        });
    }

    public void setPatientInfoPanel(PatientInfoPanel patientInfoPanel) {
        this.patientInfoPanel = patientInfoPanel;
    }    
}