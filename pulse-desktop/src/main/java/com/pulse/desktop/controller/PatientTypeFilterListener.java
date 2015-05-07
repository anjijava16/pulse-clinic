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
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JComboBox;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import com.pulse.model.Visit;
import com.pulse.model.constant.Privilege;
import com.pulse.rest.client.VisitClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class PatientTypeFilterListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(PatientTypeFilterListener.class);

    private JComboBox<String> filterList;

    private SimpleDateFormat dateFormat;

    private VisitClient visitClient = new VisitClient();

    private JDatePickerImpl calendar;

    private SimpleDateFormat originFormat = new SimpleDateFormat("yyyy.MM.dd");

    public PatientTypeFilterListener(Privilege privilege, JComboBox<String> filterList, TableService.TableHolder holder, SimpleDateFormat dateFormat, JDatePickerImpl calendar) {
        super(privilege, holder);
        this.filterList = filterList;
        this.dateFormat = dateFormat;
        this.calendar = calendar;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        ThreadPoolService.INSTANCE.execute(() -> {
            getTableHolder().clear();

            String filterString = this.filterList.getSelectedItem().toString();

            int filterType = 0;
            int value = 0;

            switch (filterString) {
                case "Все":
                    filterType = 0;
                    break;

                case "Осмотренные":
                    filterType = 2;
                    break;

                case "Неосмотренные":
                    filterType = 2;
                    break;

                default:
                    filterType = 0;
                    break;
            }

            switch (filterString) {
                case "Все":
                    value = 0;
                    break;

                case "Неосмотренные":
                    value = 0;
                    break;

                case "Осмотренные":
                    value = 1;
                    break;

                default:
                    value = 0;
                    break;
            }

            try {
                Date date = dateFormat.parse(calendar.getJFormattedTextField().getText());
                String dateBuffer = originFormat.format(date);

                final List<Visit> visitsList = this.visitClient.filterBy(filterType, value, dateBuffer);
                visitsList.stream().forEach((visit) -> {
                    this.LOGGER.info("visit: " + visit);

                    final String[] data = TableProxy.INSTANCE.getRightBufferFrom(visit, getPrivilege(), this.dateFormat);

                    if (visit.getDepartmentId() == getPrivilege().getId()
                            || getPrivilege().getId() == Privilege.Registratur.getId()
                            || getPrivilege().getId() == Privilege.TicketWindow.getId()) {
                        getTableHolder().getModel().addRow(data);
                    }
                });
            } catch (IOException ioe) {
                this.LOGGER.error("PatientTypeFilterListener: " + ioe.getMessage());
                ResultToolbarService.INSTANCE.showFailedStatus("Ошибка сети");
            } catch (ParseException pe) {
                this.LOGGER.error("PatientTypeFilterListener: " + pe.getMessage());
                ResultToolbarService.INSTANCE.showFailedStatus("Неверный формат даты");
            }
        });
    }

}
