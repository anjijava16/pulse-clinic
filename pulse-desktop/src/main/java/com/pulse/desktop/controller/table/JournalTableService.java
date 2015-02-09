package com.pulse.desktop.controller.table;

import com.pulse.model.Journal;
import java.text.SimpleDateFormat;

/**
 *
 * @author befast
 */
public class JournalTableService {
    private TableService.TableHolder holder;
    
    public JournalTableService(TableService.TableHolder holder) {
        this.holder = holder;        
    }
    
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");

    public void add(Journal record) {
        final String[] data = new String[3];
        int ptr = 0;

        //final Account account = AccountsManagementFacade.getInstance().findAccountById(record.getCreatedBy());
        data[ptr++] = formatter.format(record.getCreatedDate());
        data[ptr++] = String.valueOf(record.getId());
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
