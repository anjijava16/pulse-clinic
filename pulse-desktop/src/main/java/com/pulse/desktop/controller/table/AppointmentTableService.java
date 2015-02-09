package com.pulse.desktop.controller.table;

import com.pulse.model.Appointment;
import java.text.SimpleDateFormat;

import com.pulse.desktop.controller.table.TableService;

import com.pulse.model.Patient;

/**
 *
 * @author befast
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
            
        data[ptr++] = record.getName();

        holder.getModel().addRow(data);
    }

    public void deleteById(long id) {
        int rowCount = holder.getModel().getRowCount();
        for (int x = 0; x < rowCount; x++) {
            if (holder.getModel().getValueAt(x, TableService.RECORD_ID_FIELD).equals(String.valueOf(id))) {
                holder.getModel().removeRow(x);
                break;
            }
        }
    }
}
