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
package com.pulse.desktop.controller.table;


import java.text.SimpleDateFormat;
import java.util.List;

import com.pulse.desktop.controller.service.PatientService;
import com.pulse.desktop.controller.service.UserFacade;
import com.pulse.model.NextVisit;
import com.pulse.model.Patient;
import com.pulse.model.User;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class NextVisitTableService {
    
    private final TableService.TableHolder holder;
    
    private final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy.MM.dd");
    
    public NextVisitTableService(TableService.TableHolder holder) {
        this.holder = holder;        
    }
    
    public void add(final List<NextVisit> list) {
        final String[] data = new String[TableService.INSTANCE.NEXT_VISIT_TABLE_HEADER.length];
        
        list.stream().forEach((visit) -> {
            int ptr = 0;
            
            final Patient patient = PatientService.INSTANCE.getById(visit.getPatientId());
            final User account = UserFacade.INSTANCE.findBy(visit.getDoctorId());

            if (patient == null || account == null) {
                throw new NullPointerException("Patient or account is null");
            }

            data[ptr++] = FORMATTER.format(visit.getVisitDate());            
            data[ptr++] = String.valueOf(visit.getId());            
            data[ptr++] = patient.getNfp();
            data[ptr++] = account.getNfp();
            data[ptr] = patient.getMobile();
            
            holder.getModel().addRow(data);
        });
    }
}
