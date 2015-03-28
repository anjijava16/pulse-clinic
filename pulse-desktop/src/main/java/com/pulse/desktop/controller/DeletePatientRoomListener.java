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


import com.pulse.desktop.controller.builder.MessageBuilder;
import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.table.PatientRoomTableService;
import com.pulse.desktop.controller.table.TableService;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.JOptionPane;
import com.pulse.model.constant.Privilege;
import com.pulse.rest.client.PatientRoomClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class DeletePatientRoomListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final PatientRoomClient ORGANISATION_SERVICE = new PatientRoomClient();

    private PatientRoomTableService tableService;

    public DeletePatientRoomListener(Privilege privilege, TableService.TableHolder holder) {
        super(privilege, holder);
        this.tableService = new PatientRoomTableService(holder);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ThreadPoolService.INSTANCE.execute(() -> {
            if (getTableHolder() != null && getTableHolder().getTable() != null) {
                final int row = getTableHolder().getTable().getSelectedRow();

                if (row == -1) {
                    ResultToolbarService.INSTANCE.showFailedStatus("Запись не выбрана");
                    return;
                }
                
                final int answer = MessageBuilder.getAnswerCode(null, "Вы действительно хотите удалить запись?",
                        "Удаление");

                // If answer = YES
                if (answer == JOptionPane.YES_OPTION) {
                    try {
                        final String name = getTableHolder().getModel()
                                .getValueAt(row, TableService.PATIENT_ROOM_NAME_FIELD).toString();
                        
                        if (name != null) {
                            try {
                                this.ORGANISATION_SERVICE.deleteBy(name);
                                this.tableService.deleteById(name);
                                ResultToolbarService.INSTANCE.showSuccessStatus();
                            } catch (IOException ioe) {
                                this.LOGGER.error(getClass() + ": " + ioe.getMessage());
                                ResultToolbarService.INSTANCE.showFailedStatus("Ошибка сети");
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException aioobe) {
                        aioobe.printStackTrace();
                        this.LOGGER.error(getClass() + ": " + aioobe.getMessage());
                        ResultToolbarService.INSTANCE.showFailedStatus("Неверный формат");
                    }
                }
            }
        });
    }
}
