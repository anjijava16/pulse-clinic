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
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.table.PatientRoomTableService;
import com.pulse.desktop.controller.table.TableService;
import com.pulse.model.PatientRoom;
import com.pulse.model.constant.Privilege;
import com.pulse.rest.client.PatientRoomClient;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class CreatePatientRoomListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private PatientRoomClient patientRoomClient = new PatientRoomClient();
    private PatientRoomTableService tableService;

    private JTextField nameField;

    public CreatePatientRoomListener(Privilege privilege, TableService.TableHolder tableHolder, JTextField nameField) {
        super(privilege, tableHolder);
        this.tableService = new PatientRoomTableService(tableHolder);
        this.nameField = nameField;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        ThreadPoolService.INSTANCE.execute(() -> {
            try {
                String name = this.nameField.getText().trim();

                if (name.contains(" ")) {
                    ResultToolbarService.INSTANCE.showFailedStatus("Номер палаты не должен содержать пробелов");
                    return;
                }

                if (name.isEmpty()) {
                    ResultToolbarService.INSTANCE.showFailedStatus("Введите номер палаты");
                    return;
                }

                final PatientRoom patientRoom = new PatientRoom();
                patientRoom.setName(name);

                this.patientRoomClient.update(patientRoom);
                this.tableService.add(patientRoom);
                ResultToolbarService.INSTANCE.showSuccessStatus();
            } catch (IOException ioe) {
                this.LOGGER.error(ioe.getMessage());
                ResultToolbarService.INSTANCE.showFailedStatus("Ошибка сети");
            }
        });
    }
}
