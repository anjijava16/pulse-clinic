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
package com.pulse.desktop.controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.pulse.desktop.controller.table.TableService;
import com.pulse.model.constant.Privilege;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public abstract class AbstractTableListener implements ActionListener {

    private final Privilege privilege;
    private final TableService.TableHolder tableHolder;
    
    public AbstractTableListener(Privilege privilege, TableService.TableHolder tableHolder) {
        this.tableHolder = tableHolder;
        this.privilege = privilege;
    }
    
    public Privilege getPrivilege() {
        return this.privilege;
    }
    
    public TableService.TableHolder getTableHolder() {
        return this.tableHolder;
    }
    
    @Override
    public abstract void actionPerformed(ActionEvent e);
}
