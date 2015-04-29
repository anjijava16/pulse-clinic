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
import javax.swing.JComboBox;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.pulse.model.constant.Privilege;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pulse.model.Visit;
import com.pulse.rest.client.VisitClient;
import com.pulse.desktop.view.manager.UIHandlerFacade;
import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.table.TableProxy;
import com.pulse.desktop.controller.table.TableService;

import net.sourceforge.jdatepicker.impl.JDatePickerImpl;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class SearchByDateListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private JComboBox<String> filterList;
    private JDatePickerImpl calendar;
    private SimpleDateFormat originFormat;

    private final VisitClient visitClient = new VisitClient();

    private final SimpleDateFormat REQUEST_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public SearchByDateListener(Privilege privilege, JComboBox<String> filterList, JDatePickerImpl calendar, TableService.TableHolder holder, SimpleDateFormat originFormat) {
        super(privilege, holder);
        this.filterList = filterList;
        this.calendar = calendar;
        this.originFormat = originFormat;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        ThreadPoolService.INSTANCE.execute(() -> {
            UIHandlerFacade.getInstance().updateLaboratoryFrameIterator();

            getTableHolder().clear();

            try {
                Date originDate = this.originFormat.parse(this.calendar.getJFormattedTextField().getText());
                String dateBuffer = this.REQUEST_FORMAT.format(originDate);

                final List<Visit> visitsList = this.visitClient.findByDate(dateBuffer);
                visitsList.stream().forEach((visit) -> {
                    this.LOGGER.debug("visit: " + visit);

                    final String[] data = TableProxy.INSTANCE.getRightBufferFrom(visit, getPrivilege(), this.originFormat);
                    if (visit.getDepartmentId() == getPrivilege().getId()
                            || getPrivilege().getId() == Privilege.Registratur.getId()
                            || getPrivilege().getId() == Privilege.TicketWindow.getId()) {
                        getTableHolder().getModel().addRow(data);
                    }
                });
                ResultToolbarService.INSTANCE.showSuccessStatus();
            } catch (IOException ioe) {
                this.LOGGER.error("SearchByDateListener: " + ioe.getMessage());
                ResultToolbarService.INSTANCE.showFailedStatus("Ошибка сети");
            } catch (ParseException pe) {
                this.LOGGER.error("SearchByDateListener: " + pe.getMessage());
                ResultToolbarService.INSTANCE.showFailedStatus("Неверный формат даты");
            }
        });
    }
}
