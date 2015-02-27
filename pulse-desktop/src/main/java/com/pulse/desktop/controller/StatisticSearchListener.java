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
import com.pulse.desktop.controller.table.OrganisationsTableService;
import com.pulse.desktop.controller.table.TableService;
import com.pulse.desktop.controller.table.VisitTableService;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JComboBox;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import com.pulse.model.Organisation;
import com.pulse.model.Visit;
import com.pulse.model.constant.Privelegy;
import com.pulse.rest.client.OrganisationClient;
import com.pulse.rest.client.VisitClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class StatisticSearchListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    
    private final OrganisationClient ORGANISATION_SERVICE = new OrganisationClient();
    private final VisitClient VISIT_SERVICE = new VisitClient();
    
    private VisitTableService visitService ;
    
    private OrganisationsTableService tableService;
    
    private JComboBox<String> organisationsBox;
    
    private final SimpleDateFormat ORIGINAL_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    private final SimpleDateFormat REQUEST_FORMAT = new SimpleDateFormat("yyyy.MM.dd");
    
    private final JDatePickerImpl fromDatePicker;
    private final JDatePickerImpl untilDatePicker;
    
    public StatisticSearchListener(Privelegy privelegy, TableService.TableHolder tableHolder, JComboBox<String> organisationsBox,
            JDatePickerImpl fromDatePicker,
            JDatePickerImpl untilDatePicker) {
        super(privelegy, tableHolder);
        this.fromDatePicker = fromDatePicker;
        this.untilDatePicker = untilDatePicker;
        this.tableService = new OrganisationsTableService(tableHolder);
        this.visitService = new VisitTableService(tableHolder);
        this.organisationsBox = organisationsBox;
        
        this.organisationsBox.addItem("Все");
        
        try {
            List<Organisation> organisationsList = this.ORGANISATION_SERVICE.listAll();
            organisationsList.stream().forEach((organistaion) -> {
                this.organisationsBox.addItem(organistaion.getName());
            });
        } catch (IOException ioe) {
            this.LOGGER.error(ioe.getMessage());
        } 
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        ThreadPoolService.INSTANCE.execute(() -> {
            try {
            getTableHolder().clear();
            
            if (this.organisationsBox.getSelectedItem() == null) return;
            
            String name = this.organisationsBox.getSelectedItem().toString();

            if (name == null || name.isEmpty()) return;
            
            Date originFromDate = this.ORIGINAL_FORMAT.parse(this.fromDatePicker.getJFormattedTextField().getText());
            Date originUntilDate = this.ORIGINAL_FORMAT.parse(this.untilDatePicker.getJFormattedTextField().getText());          
            
            final String fromDate = this.REQUEST_FORMAT.format(originFromDate);
            final String untilDate = this.REQUEST_FORMAT.format(originUntilDate);
            
            if (fromDate == null) return;
            if (untilDate == null) return;
            
            if (fromDate.equals(untilDate)) {
                ResultToolbarService.INSTANCE.showFailedStatus("Даты совпадают. Временной "
                        + "промежуток должен быть больше одного дня.");
                return;
            }
            
            if (name.equals("Все")) {
                List<Visit> visitList = this.VISIT_SERVICE.listAll(fromDate, untilDate);
                this.visitService.add(visitList);
            } else {
                List<Visit> visitList = this.VISIT_SERVICE.listBy(name, fromDate, untilDate);
                this.visitService.add(visitList);
            }
            
            ResultToolbarService.INSTANCE.showSuccessStatus();
        } catch (IOException ioe) {
            this.LOGGER.error(ioe.getMessage());
            ResultToolbarService.INSTANCE.showFailedStatus("Ошибка сети");
        } catch (ParseException pe) {
            this.LOGGER.error(pe.getMessage());
            ResultToolbarService.INSTANCE.showFailedStatus("Ошибка");
        }
        });
    }
}
