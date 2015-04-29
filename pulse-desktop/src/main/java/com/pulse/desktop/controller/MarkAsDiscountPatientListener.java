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


import com.pulse.desktop.controller.builder.MessageBuilder;
import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.table.TableService;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JOptionPane;
import com.pulse.model.Visit;
import com.pulse.model.constant.BonusStatus;
import com.pulse.rest.client.VisitClient;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class MarkAsDiscountPatientListener implements ActionListener {

    private VisitClient client = new VisitClient();

    private TableService.TableHolder tableHolder;

    public MarkAsDiscountPatientListener() {
    }

    public void setTableHolder(TableService.TableHolder tableHolder) {
        this.tableHolder = tableHolder;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ThreadPoolService.INSTANCE.execute(() -> {
            if (this.tableHolder != null && this.tableHolder.getTable() != null) {
                int row = this.tableHolder.getTable().getSelectedRow();

                if (row != -1) {
                    final int answer = MessageBuilder.getAnswerCode(null, "Изменить статус?",
                            "Изменение статуса");

                    // If answer = YES
                    if (answer == JOptionPane.YES_OPTION) {
                        final long visitId = Long.valueOf(this.tableHolder.getModel()
                                .getValueAt(row, TableService.VISIT_ID_FIELD).toString());

                        try {
                            final Visit visit = this.client.getBy(visitId);
                            visit.setDiscount(BonusStatus.YES.getId());
                            this.client.update(visit);
                            ResultToolbarService.INSTANCE.showSuccessStatus();
                        } catch (IOException ioe) {
                            ResultToolbarService.INSTANCE.showFailedStatus("Ошибка сети");
                        }
                    }
                } else {
                    ResultToolbarService.INSTANCE.showFailedStatus("Запись не выбрана");
                }
            }
        });
    }
}
