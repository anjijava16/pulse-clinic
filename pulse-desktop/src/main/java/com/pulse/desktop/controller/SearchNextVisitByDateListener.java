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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;

import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.table.NextVisitTableService;
import com.pulse.desktop.controller.table.TableService;
import com.pulse.desktop.view.manager.UIHandlerFacade;
import com.pulse.model.NextVisit;
import com.pulse.model.constant.Privilege;
import com.pulse.rest.client.NextVisitClient;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class SearchNextVisitByDateListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private JDatePickerImpl calendar;
    private SimpleDateFormat originFormat;

    private NextVisitClient client = new NextVisitClient();

    private SimpleDateFormat requestFormat = new SimpleDateFormat("yyyy.MM.dd");

    private NextVisitTableService tableService;

    public SearchNextVisitByDateListener(Privilege privilege, JDatePickerImpl calendar, TableService.TableHolder holder, SimpleDateFormat originFormat) {
        super(privilege, holder);
        this.calendar = calendar;
        this.originFormat = originFormat;
        this.tableService = new NextVisitTableService(holder);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        ThreadPoolService.INSTANCE.execute(() -> {
            UIHandlerFacade.getInstance().updateLaboratoryFrameIterator();

            getTableHolder().clear();

            try {
                Date originDate = this.originFormat.parse(this.calendar.getJFormattedTextField().getText());
                String dateBuffer = this.requestFormat.format(originDate);

                final List<NextVisit> list = this.client.findByDate(dateBuffer);
                this.tableService.add(list);
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
