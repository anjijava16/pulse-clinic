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

import com.pulse.desktop.controller.table.TableService.TableHolder;
import com.pulse.model.User;
import com.pulse.model.constant.Privilege;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class UsersTableService {
    
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private TableHolder holder;
    
    public UsersTableService(TableHolder holder) {
        this.holder = holder;
    }
    
    public void add(final User user) {
        if (Privilege.findById(user.getPrivelegy()) == null) return;
        
        final String[] data = new String[TableService.INSTANCE.USERS_TABLE_HEADER.length];
        int ptr = 0;        

        data[ptr++] = String.valueOf(user.getId());
        data[ptr++] = user.getNfp();
        data[ptr++] = Privilege.findById(user.getPrivelegy()).getName();
        data[ptr++] = dateFormat.format(user.getBirthday());
        data[ptr] = user.getUsername();

        holder.getModel().addRow(data);
    }
    
    public void add(List<User> list) {
        list.stream().forEach(this::add);
    }
}
