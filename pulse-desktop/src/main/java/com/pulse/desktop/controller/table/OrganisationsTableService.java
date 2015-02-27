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


import com.pulse.model.Organisation;
import com.pulse.desktop.controller.table.TableService;
import com.pulse.desktop.controller.table.TableService.TableHolder;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class OrganisationsTableService {

    private TableHolder holder;
    
    public OrganisationsTableService(TableHolder holder) {
        this.holder = holder;        
    }

    public void add(Organisation organisation) {
        final String[] data = new String[1];
        int ptr = 0;

        data[ptr++] = organisation.getName();

        holder.getModel().addRow(data);
    }

    public void deleteById(String name) {
        int rowCount = holder.getModel().getRowCount();
        for (int x = 0; x < rowCount; x++) {
            if (holder.getModel().getValueAt(x, TableService.ORGANISATION_NAME_FIELD).equals(name)) {
                holder.getModel().removeRow(x);
            }
        }
    }
}
