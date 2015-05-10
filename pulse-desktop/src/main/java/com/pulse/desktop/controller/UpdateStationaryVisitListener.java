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
import com.pulse.desktop.controller.service.UserFacade;
import com.pulse.desktop.controller.table.StationaryTableService;
import com.pulse.desktop.controller.table.TableService;
import com.pulse.model.User;
import com.pulse.model.Visit;
import com.pulse.model.constant.Privilege;
import com.pulse.rest.client.VisitClient;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class UpdateStationaryVisitListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final VisitClient visitClient = new VisitClient();
    private StationaryTableService tableService;

    public UpdateStationaryVisitListener(Privilege privilege, TableService.TableHolder tableHolder) {
        super(privilege, tableHolder);
        this.tableService = new StationaryTableService(tableHolder);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        ThreadPoolService.INSTANCE.execute(() -> {
            try {
                getTableHolder().clear();

                long doctor;

                User appUser = UserFacade.INSTANCE.getApplicationUser();

                if (appUser == null) {
                    return;
                }

                if (appUser.getPrivelegy() == Privilege.Admin.getId()) {
                    doctor = 0;
                } else {
                    doctor = appUser.getId();
                }

                List<Visit> list = this.visitClient.filterBy(Privilege.Stationary.getId(), doctor);
                this.tableService.add(list);
                
                ResultToolbarService.INSTANCE.showSuccessStatus();
            } catch (IOException ioe) {
                this.LOGGER.error(ioe.getMessage());
                ResultToolbarService.INSTANCE.showFailedStatus("Ошибка сети");
            }
        });
    }
}
