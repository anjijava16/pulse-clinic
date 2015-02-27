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


import com.google.common.io.Files;
import com.pulse.desktop.controller.service.PatientFacade;
import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.service.UserFacade;
import com.pulse.desktop.controller.table.AppointmentTableService;
import com.pulse.desktop.controller.table.TableService;
import com.pulse.desktop.controller.table.TableService.TableHolder;
import com.pulse.model.Appointment;
import com.pulse.rest.client.AppointmentClient;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import javax.swing.JOptionPane;

import com.pulse.desktop.view.util.FileManager;
import com.pulse.desktop.view.util.HashBuilder;
import com.pulse.desktop.view.util.NameValidator;
import com.pulse.desktop.view.util.Settings;
import com.pulse.model.Patient;
import com.pulse.model.User;
import com.pulse.model.constant.Privelegy;
import com.pulse.model.constant.PrivelegyDir;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class CreatePatientAppointmentListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private Privelegy privelegy;
    private TableHolder tableHolder;

    private final AppointmentClient appointmentClient = new AppointmentClient();
    private final FileManager FILE_MANAGER = new FileManager();

    private AppointmentTableService tableService;

    public CreatePatientAppointmentListener(Privelegy privelegy, TableHolder tableHolder) {
        super(privelegy, tableHolder);
        this.privelegy = privelegy;
        this.tableHolder = tableHolder;
        this.tableService = new AppointmentTableService(tableHolder);
    }

    private String getSelectedForm() {
        final File recordsFolder = new File(PrivelegyDir.APPOINTMENTS_PATH.getTemplatePath());

        this.LOGGER.info("Review folder: " + PrivelegyDir.APPOINTMENTS_PATH.getTemplatePath());

        if (recordsFolder.exists()) {
            final String[] recordsNames = recordsFolder.list();

            final String selectedTemplate = (String) JOptionPane.showInputDialog(
                    null,
                    "Выберете шаблон",
                    "Шаблоны",
                    JOptionPane.NO_OPTION,
                    null,
                    recordsNames,
                    null);

            return selectedTemplate;
        }

        return null;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (UserFacade.INSTANCE.getApplicationUser() == null) {
            return;
        }

        ThreadPoolService.INSTANCE.execute(() -> {
            final Patient patient = PatientFacade.INSTANCE.getLastSelectedPatient();

            this.LOGGER.info(getClass() + ": actionPerformed(), patientNfp: " + ((patient != null) ? patient.getNfp() : null));

            if (patient != null) {
                final String templateName = getSelectedForm();

                if (templateName == null) {
                    return;
                }

                final String applicationUsername = UserFacade.INSTANCE.getApplicationUser().getNfp();
                final User applicationUser = UserFacade.INSTANCE.findBy(applicationUsername);

                FILE_MANAGER.copyFile(PrivelegyDir.APPOINTMENTS_PATH.getTemplatePath() + templateName,
                        PrivelegyDir.APPOINTMENTS_PATH.getTemporaryPath() + templateName);

                try {
                    File file = new File(PrivelegyDir.APPOINTMENTS_PATH.getTemporaryPath() + templateName);

                    if (file.exists()) {
                        String appPath;
                        if (templateName.endsWith("doc") || templateName.endsWith("docx")) {
                            appPath = Settings.M_OFFICE_PATH;
                        } else {
                            appPath = Settings.E_OFFICE_PATH;
                        }

                        Process process = Runtime.getRuntime().exec(appPath + " " + file.getAbsolutePath());
                        int result = process.waitFor();

                        byte[] buffer = Files.toByteArray(file);

                        String hash = HashBuilder.INSTANCE.calculate();

                        final Appointment record = new Appointment();
                        record.setCreatedBy(applicationUser.getId());
                        record.setCreatedDate(new Date());
                        record.setName(hash.concat(NameValidator.INSTANCE.getExtension(templateName)));
                        record.setPath("./appointment/".concat(String.valueOf(patient.getId())).concat("/"));
                        record.setPatientId(patient.getId());
                        record.setUpdatedBy(applicationUser.getId());
                        record.setBinary(new String(Base64.encodeBase64(buffer), "UTF-8"));

                        this.appointmentClient.update(record);
                        this.tableService.add(record, patient);
                        ResultToolbarService.INSTANCE.showSuccessStatus();
                    } else {
                        ResultToolbarService.INSTANCE.showFailedStatus("Файл не найден");
                    }
                } catch (IOException ioe) {
                    LOGGER.error(getClass().getName() + ": " + ioe.getMessage());
                    ResultToolbarService.INSTANCE.showFailedStatus("Ошибка сети");
                } catch (InterruptedException ie) {
                    LOGGER.error(getClass().getName() + ": " + ie.getMessage());
                    ResultToolbarService.INSTANCE.showFailedStatus("Ошибка");
                }
            }
        });
    }

}
