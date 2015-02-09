package com.pulse.desktop.controller.table;

import com.pulse.model.Organisation;
import com.pulse.desktop.controller.table.TableService;
import com.pulse.desktop.controller.table.TableService.TableHolder;

/**
 *
 * @author befast
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
