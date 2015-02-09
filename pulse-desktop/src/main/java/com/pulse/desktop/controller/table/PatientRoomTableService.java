package com.pulse.desktop.controller.table;

import com.pulse.desktop.controller.table.TableService.TableHolder;
import com.pulse.model.PatientRoom;

/**
 *
 * @author befast
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

