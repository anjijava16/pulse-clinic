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
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pulse.desktop.controller.builder.MessageBuilder;
import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.table.PatientRecordTableService;
import com.pulse.desktop.controller.table.TableService;
import com.pulse.desktop.controller.table.TableService.TableHolder;
import com.pulse.model.constant.Privilege;
import com.pulse.rest.client.RecordClient;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class DeletePatientRecordListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private Privilege privilege;
    private TableHolder tableHolder;

    private RecordClient recordClient = new RecordClient();

    private PatientRecordTableService tableService;

    public DeletePatientRecordListener(Privilege privilege, TableService.TableHolder tableHolder) {
        super(privilege, tableHolder);
        this.privilege = privilege;
        this.tableHolder = tableHolder;
        this.tableService = new PatientRecordTableService(tableHolder);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        this.LOGGER.info(getClass() + ": actionPerformed()");

        ThreadPoolService.INSTANCE.execute(() -> {
            if (getTableHolder() != null && getTableHolder().getTable() != null) {
                int row = getTableHolder().getTable().getSelectedRow();

                if (row == -1) {
                    ResultToolbarService.INSTANCE.showFailedStatus("Запись не выбрана");
                    return;
                }

                final int answer = MessageBuilder.getAnswerCode(null, "Удалить запись?",
                        "Удаление");

                // If answer = YES
                if (answer == JOptionPane.YES_OPTION) {
                    final long recordId = Long.valueOf(getTableHolder().getModel()
                            .getValueAt(row, TableService.RECORD_ID_FIELD).toString());

                    try {
                        this.recordClient.delete(recordId);
                        this.tableService.deleteById(recordId);

                        ResultToolbarService.INSTANCE.showSuccessStatus();
                    } catch (IOException ioe) {
                        this.LOGGER.error(getClass() + ": " + ioe.getMessage());
                        ResultToolbarService.INSTANCE.showFailedStatus("Ошибка сети");
                    }
                }
            }
        });
    }

}
