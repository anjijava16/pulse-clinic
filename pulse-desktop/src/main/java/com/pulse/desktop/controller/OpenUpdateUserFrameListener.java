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
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.table.TableService;
import com.pulse.desktop.view.manager.WindowManager;
import com.pulse.model.User;
import com.pulse.model.constant.Privilege;
import com.pulse.rest.client.UserClient;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class OpenUpdateUserFrameListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final UserClient userService = new UserClient();

    public OpenUpdateUserFrameListener(Privilege privilege, TableService.TableHolder tableHolder) {
        super(privilege, tableHolder);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        ThreadPoolService.INSTANCE.execute(() -> {
            if (WindowManager.getInstance().getAccountChangingFrame().isVisible()) {
                WindowManager.getInstance().getAccountChangingFrame().setVisible(false);
            } else {
                int row = getTableHolder().getTable().getSelectedRow();

                this.LOGGER.debug("row: {}", row);

                if (row < 0) {
                    ResultToolbarService.INSTANCE.showFailedStatus("Пользователь не выбран");
                    return;
                }

                final long userId = Long.valueOf(
                        TableService.getValueAt(getTableHolder(), row, TableService.USER_ID_FIELD).toString()
                );

                this.LOGGER.debug("userId: {}", userId);

                try {
                    final User user = userService.get(userId);

                    if (user != null) {
                        WindowManager.getInstance().getAccountChangingFrame().getUserInfoPanel().showAccountsData(user);

                        WindowManager.getInstance().getAccountChangingFrame().setVisible(true);
                    } else {
                        ResultToolbarService.INSTANCE.showFailedStatus("Пользователь не найден");
                    }
                } catch (IOException ioe) {
                    ResultToolbarService.INSTANCE.showFailedStatus("Ошибка сети");
                }
            }
        });
    }
}
