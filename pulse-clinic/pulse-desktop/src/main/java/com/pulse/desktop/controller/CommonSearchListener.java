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


import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.table.TableProxy;
import com.pulse.desktop.controller.table.TableService;
import com.pulse.model.SearchTemplate;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JTextField;
import com.pulse.model.Visit;
import com.pulse.model.constant.Privilege;
import com.pulse.rest.client.VisitClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class CommonSearchListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(CommonSearchListener.class);

    private JTextField searchField;
    private SimpleDateFormat dateFormat;

    private VisitClient visitClient = new VisitClient();

    public CommonSearchListener(Privilege privilege, TableService.TableHolder tableHolder, JTextField searchField, SimpleDateFormat dateFormat) {
        super(privilege, tableHolder);
        this.searchField = searchField;
        this.dateFormat = dateFormat;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        ThreadPoolService.INSTANCE.execute(() -> {
            try {
                String pattern = this.searchField.getText();

                if (pattern == null || pattern.isEmpty()) {
                    return;
                }

                getTableHolder().clear();

                final SearchTemplate template = new SearchTemplate();
                template.setTemplate(pattern);

                final List<Visit> visitsList = this.visitClient.findByTemplate(template);
                visitsList.stream().forEach((visit) -> {
                    this.LOGGER.info("visit: " + visit);

                    final String[] data = TableProxy.INSTANCE.getRightBufferFrom(visit, getPrivilege(), this.dateFormat);
                    if (visit.getDepartmentId() == getPrivilege().getId()
                            || getPrivilege().getId() == Privilege.Registratur.getId()
                            || getPrivilege().getId() == Privilege.TicketWindow.getId()) {
                        getTableHolder().getModel().addRow(data);
                    }
                });

                ResultToolbarService.INSTANCE.showSuccessStatus();
            } catch (IOException ioe) {
                this.LOGGER.error("CommonSearchListener: " + ioe.getMessage());
                ResultToolbarService.INSTANCE.showFailedStatus("Ошибка сети");
            }
        });
    }
}
