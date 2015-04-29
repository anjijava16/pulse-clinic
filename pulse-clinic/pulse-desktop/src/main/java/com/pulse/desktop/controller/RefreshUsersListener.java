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


import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.service.UserFacade;
import com.pulse.desktop.controller.table.TableService.TableHolder;
import com.pulse.desktop.controller.table.UsersTableService;
import com.pulse.model.User;
import com.pulse.model.constant.Privilege;
import com.pulse.rest.client.UserClient;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class RefreshUsersListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    
    private final UserClient visitClient = new UserClient();
    private UsersTableService tableService;
        
    public RefreshUsersListener(Privilege privilege, TableHolder tableHolder) {
        super(privilege, tableHolder);
        this.tableService = new UsersTableService(tableHolder);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        ThreadPoolService.INSTANCE.execute(() -> {
            try {                
                getTableHolder().clear();

                List<User> list = this.visitClient.list();
                
                ResultToolbarService.INSTANCE.showSuccessStatus();
                
                this.tableService.add(list);
                
                UserFacade.INSTANCE.updateAll(list);
            } catch (IOException ioe) {
                this.LOGGER.error(ioe.getMessage());
                ResultToolbarService.INSTANCE.showFailedStatus("Ошибка сети");                
            }
        });
    }
}
