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
import com.pulse.desktop.controller.table.OrganisationsTableService;
import com.pulse.desktop.controller.table.TableService;
import com.pulse.model.Organisation;
import com.pulse.model.constant.Privilege;
import com.pulse.rest.client.OrganisationClient;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class AddOrganisationListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(CommonSearchListener.class);

    private final OrganisationClient ORGANISATION_SERVICE = new OrganisationClient();
    private OrganisationsTableService tableService;

    private JTextField nameField;

    public AddOrganisationListener(Privilege privilege, TableService.TableHolder tableHolder, JTextField nameField) {
        super(privilege, tableHolder);
        this.tableService = new OrganisationsTableService(tableHolder);
        this.nameField = nameField;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (this.nameField == null) {
            return;
        }

        ThreadPoolService.INSTANCE.execute(() -> {
            try {
                String name = this.nameField.getText().trim();

                if (name.contains(" ")) {
                    ResultToolbarService.INSTANCE.showFailedStatus("Название не должно содержать пробелов");
                    return;
                }

                if (name.isEmpty()) {
                    ResultToolbarService.INSTANCE.showFailedStatus("Введите название организации");
                    return;
                }

                final Organisation organisation = new Organisation();
                organisation.setName(name);

                this.ORGANISATION_SERVICE.update(organisation);
                this.tableService.add(organisation);
                ResultToolbarService.INSTANCE.showSuccessStatus();
            } catch (IOException ioe) {
                this.LOGGER.error(ioe.getMessage());
                ResultToolbarService.INSTANCE.showFailedStatus("Ошибка сети");
            }
        });
    }
}
