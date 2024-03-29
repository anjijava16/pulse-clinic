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
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pulse.desktop.controller.builder.MessageBuilder;
import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.table.TableService;
import com.pulse.desktop.view.manager.WindowManager;
import com.pulse.model.Visit;
import com.pulse.model.constant.Privilege;
import com.pulse.rest.client.VisitClient;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class ReleasePatientListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private VisitClient client = new VisitClient();

    public ReleasePatientListener(Privilege privilege, TableService.TableHolder tableHolder) {
        super(privilege, tableHolder);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        ThreadPoolService.INSTANCE.execute(() -> {
            WindowManager.getInstance().getAppointmentFrame().getTableHolder().clear();

            int row = getTableHolder().getTable().getSelectedRow();

            if (row == -1) {
                ResultToolbarService.INSTANCE.showFailedStatus("Запись не выбрана");
                return;
            }

            final int answer = MessageBuilder.getAnswerCode(null, "Вы действительно хотите выписать пациента?",
                    "Выписка");

            // If answer = YES
            if (answer == JOptionPane.YES_OPTION) {
                final String visitIdBuffer = getTableHolder().getTable().getValueAt(row, TableService.VISIT_ID_FIELD).toString();

                if (visitIdBuffer == null) {
                    return;
                }

                try {
                    final Visit visit = this.client.getBy(Long.valueOf(visitIdBuffer));
                    if (visit == null) {
                        ResultToolbarService.INSTANCE.showFailedStatus("Информация о визите не найдена");
                        return;
                    }

                    visit.setReleased(true);

                    this.client.update(visit);

                    ResultToolbarService.INSTANCE.showSuccessStatus();
                } catch (IOException ioe) {
                    this.LOGGER.error(getClass() + ": " + ioe.getMessage());
                    ResultToolbarService.INSTANCE.showFailedStatus("Ошибка сети");
                }
            }
        });
    }

}
