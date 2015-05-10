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
public class UpdatePatientRoomListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final PatientRoomClient ORGANISATION_SERVICE = new PatientRoomClient();
    private PatientRoomTableService tableService;

    public UpdatePatientRoomListener(Privilege privilege, TableService.TableHolder tableHolder) {
        super(privilege, tableHolder);
        this.tableService = new PatientRoomTableService(tableHolder);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        ThreadPoolService.INSTANCE.execute(() -> {
            try {
                getTableHolder().clear();

                final List<PatientRoom> list = this.ORGANISATION_SERVICE.listAll();
                list.stream().forEach(this.tableService::add);

                ResultToolbarService.INSTANCE.showSuccessStatus();
            } catch (IOException ioe) {
                this.LOGGER.error(ioe.getMessage());
                ResultToolbarService.INSTANCE.showFailedStatus("Ошибка сети");
            }
        });
    }
}
