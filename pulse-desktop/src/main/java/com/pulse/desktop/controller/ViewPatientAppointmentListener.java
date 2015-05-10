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
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pulse.desktop.controller.service.PatientFacade;
import com.pulse.desktop.controller.service.PatientService;
import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.table.AppointmentTableService;
import com.pulse.desktop.controller.table.TableService;
import com.pulse.desktop.view.manager.WindowManager;
import com.pulse.model.Appointment;
import com.pulse.model.Patient;
import com.pulse.model.constant.Privilege;
import com.pulse.rest.client.AppointmentClient;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class ViewPatientAppointmentListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private AppointmentClient appointmentClient = new AppointmentClient();

    private AppointmentTableService tableService;

    public ViewPatientAppointmentListener(Privilege privilege, TableService.TableHolder tableHolder) {
        super(privilege, tableHolder);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        ThreadPoolService.INSTANCE.execute(() -> {

            if (this.tableService == null) {
                this.tableService = new AppointmentTableService(WindowManager.getInstance().getAppointmentFrame().getTableHolder());
            }

            WindowManager.getInstance().getAppointmentFrame().getTableHolder().clear();

            int row = getTableHolder().getTable().getSelectedRow();

            if (row == -1) {
                ResultToolbarService.INSTANCE.showFailedStatus("Запись не выбрана");
                return;
            }

            if (!WindowManager.getInstance().getAppointmentFrame().isVisible()) {
                WindowManager.getInstance().getAppointmentFrame().setVisible(true);

                final String patientIdBuffer = getTableHolder().getTable().getValueAt(row, TableService.PATIENT_ID_FIELD).toString();
//                String doctorNfp = getTableHolder().getTable().getValueAt(row, TableService.DOCTOR_NAME_FIELD).toString();
                final Patient patient = PatientService.INSTANCE.getById(Long.valueOf(patientIdBuffer));

                if (patient == null) {
                    this.LOGGER.error(getClass() + ": Patient instance is null");
                    return;
                }

                PatientFacade.INSTANCE.setLastSelectedPatient(patient);
                
                try {
                    final List<Appointment> recordsList = this.appointmentClient.listBy(patient.getId());
                    if (recordsList != null && !recordsList.isEmpty()) {
                        recordsList.stream().forEach((appointment) -> this.tableService.add(appointment, patient));
                    }
                } catch (IOException ioe) {
                    this.LOGGER.error(getClass() + ": " + ioe.getMessage());
                    ResultToolbarService.INSTANCE.showFailedStatus("Ошибка сети");
                }
            } else {
                WindowManager.getInstance().getAppointmentFrame().setVisible(false);
            }
        });
    }

}
