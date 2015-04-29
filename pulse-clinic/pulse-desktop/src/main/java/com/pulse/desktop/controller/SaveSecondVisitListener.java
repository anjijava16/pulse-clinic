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
import com.pulse.desktop.controller.table.TableService;
import java.awt.event.ActionEvent;
import com.pulse.desktop.view.manager.WindowManager;
import com.pulse.model.User;
import com.pulse.model.constant.Privilege;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class SaveSecondVisitListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    public SaveSecondVisitListener(Privilege privilege, TableService.TableHolder tableHolder) {
        super(privilege, tableHolder);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        ThreadPoolService.INSTANCE.execute(() -> {
            if (UserFacade.INSTANCE.getApplicationUser() == null) {
                return;
            }

            int row = getTableHolder().getTable().getSelectedRow();

            if (row == -1) {
                ResultToolbarService.INSTANCE.showFailedStatus("Запись не выбрана");
                return;
            }

            String doctorNfp = UserFacade.INSTANCE.getApplicationUser().getNfp();
            long patientId = Long.valueOf(getTableHolder().getTable().getValueAt(row, TableService.PATIENT_ID_FIELD).toString());

            User account = UserFacade.INSTANCE.findBy(doctorNfp);

            WindowManager.getInstance().getCreateVisitDateFrame().setPatientId(patientId);

            try {
                WindowManager.getInstance().getCreateVisitDateFrame().setDoctorId(account.getId());
            } catch (NullPointerException npe) {
                LOGGER.error(npe.getMessage());
                ResultToolbarService.INSTANCE.showFailedStatus("Ошибка");
                return;
            }

            if (WindowManager.getInstance().getCreateVisitDateFrame().frameIsVisible()) {
                WindowManager.getInstance().getCreateVisitDateFrame().setFrameVisible(false);
            } else {
                WindowManager.getInstance().getCreateVisitDateFrame().setFrameVisible(true);
            }
        });
    }
}
