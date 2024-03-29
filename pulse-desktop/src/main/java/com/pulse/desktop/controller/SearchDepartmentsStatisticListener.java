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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;

import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.service.UserFacade;
import com.pulse.desktop.controller.table.BookKeepingTableService;
import com.pulse.desktop.controller.table.TableService.TableHolder;
import com.pulse.model.FilteredVisit;
import com.pulse.model.User;
import com.pulse.model.Visit;
import com.pulse.model.constant.Privilege;
import com.pulse.rest.client.VisitClient;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class SearchDepartmentsStatisticListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private JCheckBox enableSearchByDepartment;

    private JComboBox<String> doctorsList;
    private JComboBox<String> departmentsList;

    private JDatePickerImpl fromDatePicker;
    private JDatePickerImpl untilDatePicker;

    private VisitClient visitClient = new VisitClient();

    private SimpleDateFormat originFormat;
    private SimpleDateFormat requestFormat = new SimpleDateFormat("yyyy.MM.dd");

    private BookKeepingTableService tableService;

    private TableHolder holder;

    public SearchDepartmentsStatisticListener(
            SimpleDateFormat originFormat,
            Privilege privilege,
            TableHolder holder,
            JCheckBox enableSearchByDepartment,
            JComboBox<String> doctorsList,
            JComboBox<String> departmentsList,
            JDatePickerImpl fromDatePicker,
            JDatePickerImpl untilDatePicker
    ) {
        super(privilege, null);
        this.holder = holder;
        this.originFormat = originFormat;
        this.doctorsList = doctorsList;
        this.departmentsList = departmentsList;
        this.enableSearchByDepartment = enableSearchByDepartment;
        this.fromDatePicker = fromDatePicker;
        this.untilDatePicker = untilDatePicker;
        tableService = new BookKeepingTableService(holder);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        ThreadPoolService.INSTANCE.execute(() -> {
            this.holder.clear();
            try {
                final String selectedDoctor = this.doctorsList.getSelectedItem().toString();
                final String selectedDepartment = this.departmentsList.getSelectedItem().toString();

                Date originFromDate = this.originFormat.parse(this.fromDatePicker.getJFormattedTextField().getText());
                Date originUntilDate = this.originFormat.parse(this.untilDatePicker.getJFormattedTextField().getText());

                final String fromDate = this.requestFormat.format(originFromDate);
                final String untilDate = this.requestFormat.format(originUntilDate);

                if (selectedDoctor == null) {
                    return;
                }
                if (selectedDepartment == null) {
                    return;
                }
                if (Privilege.findByName(selectedDepartment) == null) {
                    return;
                }
                if (UserFacade.INSTANCE.findBy(selectedDoctor) == null) {
                    return;
                }

                if (fromDate.equals(untilDate)) {
                    ResultToolbarService.INSTANCE.showFailedStatus("Даты совпадают. Временной "
                            + "промежуток должен быть больше одного дня.");
                    return;
                }

                final Privilege privilege = Privilege.findByName(selectedDepartment);
                final User user = UserFacade.INSTANCE.findBy(selectedDoctor);

                if (privilege == null || user == null) {
                    ResultToolbarService.INSTANCE.showFailedStatus("Пользователь не найден");
                    return;
                }

                final int department = privilege.getId();
                final long doctorId = user.getId();

                final FilteredVisit visit = new FilteredVisit();
                visit.setDepartmentId(department);
                visit.setDoctorId(doctorId);
                visit.setFromDate(fromDate);
                visit.setUntilDate(untilDate);
                visit.setSearchByDepartment(this.enableSearchByDepartment.isSelected());

                final List<Visit> list = this.visitClient.filterBy(visit);
                tableService.proxyFrom(list);
            } catch (IOException | ParseException e) {
                this.LOGGER.error(getClass() + ": " + e.getMessage());
            }
        });
    }
}
