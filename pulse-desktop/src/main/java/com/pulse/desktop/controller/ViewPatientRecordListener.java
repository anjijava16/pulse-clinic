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
import com.pulse.desktop.controller.service.PatientFacade;
import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.table.PatientRecordTableService;
import com.pulse.desktop.controller.table.TableService;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

import com.pulse.desktop.view.manager.WindowManager;
import com.pulse.model.Patient;
import com.pulse.model.Record;
import com.pulse.model.constant.Privelegy;
import com.pulse.rest.client.RecordClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class ViewPatientRecordListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private TableService.TableHolder tableHolder;

    private final RecordClient RECORD_CLIENT = new RecordClient();

    private PatientRecordTableService tableService;

    public ViewPatientRecordListener(Privelegy privelegy, TableService.TableHolder tableHolder) {
        super(privelegy, tableHolder);
        this.tableHolder = tableHolder;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        ThreadPoolService.INSTANCE.execute(() -> {
            if (this.tableService == null) {
                this.tableService = new PatientRecordTableService(WindowManager.getInstance().getPatientRecordForm().getTableHolder());
            }

            int row = getTableHolder().getTable().getSelectedRow();

            if (row == -1) {
                ResultToolbarService.INSTANCE.showFailedStatus("Запись не выбрана");
                return;
            }

            if (! WindowManager.getInstance().getPatientRecordForm().isVisible()) {
                WindowManager.getInstance().getPatientRecordForm().setVisible(true);

                // Clear table data here, remote all elements in the table
                WindowManager.getInstance().getPatientRecordForm().getTableHolder().clear();

                final Patient patient = TableService.INSTANCE.getSelectedPatient(this.tableHolder);
                                
                this.LOGGER.debug("Patient: {}", patient);
                
                if (patient == null) {
                    this.LOGGER.error(getClass() + ": Patient instance is null");
                    ResultToolbarService.INSTANCE.showFailedStatus("Пациент не найден");
                    return;
                }

                PatientFacade.INSTANCE.setLastSelectedPatient(patient);
                
                try {
                    final List<Record> recordsList = this.RECORD_CLIENT.listBy(patient.getId());
                    if (recordsList != null && !recordsList.isEmpty()) {
                        recordsList.stream().forEach((record) -> {
                            this.tableService.add(record, patient);
                        });
                    }
                } catch (IOException ioe) {
                    this.LOGGER.error(getClass() + ": " + ioe.getMessage());
                    ResultToolbarService.INSTANCE.showFailedStatus("Ошибка сети");
                }
            } else {
                WindowManager.getInstance().getPatientRecordForm().setVisible(false);
            }
        });
    }
}
