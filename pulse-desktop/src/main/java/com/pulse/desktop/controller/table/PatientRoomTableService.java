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


import com.pulse.desktop.controller.table.TableService.TableHolder;
import com.pulse.model.PatientRoom;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class PatientRoomTableService {

    private TableHolder holder;
    
    public PatientRoomTableService(TableHolder holder) {
        this.holder = holder;        
    }

    public void add(PatientRoom organisation) {
        final String[] data = new String[3];
        int ptr = 0;

        data[ptr++] = organisation.getName();
        data[ptr++] = String.valueOf(organisation.getLimitation());
        data[ptr++] = String.valueOf(organisation.getCapacity());

        holder.getModel().addRow(data);
    }

    public void deleteById(String name) {
        int rowCount = holder.getModel().getRowCount();
        for (int x = 0; x < rowCount; x++) {
            if (holder.getModel().getValueAt(x, TableService.ORGANISATION_NAME_FIELD).equals(name)) {
                holder.getModel().removeRow(x);
                return;
            }
        }
    }
}

