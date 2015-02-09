package com.pulse.desktop.controller.table;

import com.pulse.desktop.controller.table.TableService.TableHolder;
import com.pulse.model.User;
import com.pulse.model.constant.Privelegy;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 *
 * @author befast
 */
public class UsersTableService {
    
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private TableHolder holder;
    
    public UsersTableService(TableHolder holder) {
        this.holder = holder;
    }
    
    public void add(User user) {
        if (Privelegy.findById(user.getPrivelegy()) == null) return;
        
        final String[] data = new String[TableService.INSTANCE.USERS_TABLE_HEADER.length];
        int ptr = 0;        

        data[ptr++] = String.valueOf(user.getId());
        data[ptr++] = user.getNfp();
        data[ptr++] = Privelegy.findById(user.getPrivelegy()).getName();
        data[ptr++] = dateFormat.format(user.getBirthday());
        data[ptr++] = user.getUsername();

        holder.getModel().addRow(data);
    }
    
    public void add(List<User> list) {
        list.stream().forEach((user) -> {
            add(user);
        });
    }
}
