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

import com.pulse.model.Appointment;
import com.pulse.model.Patient;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class AppointmentTableService {
    private TableService.TableHolder holder;
    
    public AppointmentTableService(TableService.TableHolder holder) {
        this.holder = holder;        
    }
    
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");

    public void add(Appointment record, Patient patient) {
        final String[] data = new String[5];
        int ptr = 0;
        
        data[ptr++] = String.valueOf(patient.getId());
        data[ptr++] = String.valueOf(record.getId());
        data[ptr++] = patient.getNfp();
        
        data[ptr++] = formatter.format(record.getCreatedDate());
            
        data[ptr] = record.getName();

        holder.getModel().addRow(data);
    }

    public void deleteById(long id) {
        final int rowCount = holder.getModel().getRowCount();
        for (int x = 0; x < rowCount; x++) {
            if (holder.getModel().getValueAt(x, TableService.RECORD_ID_FIELD).equals(String.valueOf(id))) {
                holder.getModel().removeRow(x);
                break;
            }
        }
    }
}
