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

import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.table.TableService;
import java.awt.event.ActionEvent;
import com.pulse.desktop.view.manager.WindowManager;
import com.pulse.model.constant.Privelegy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class EditPatientListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    public EditPatientListener(Privelegy privelegy, TableService.TableHolder holder) {
        super(privelegy, holder);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        ThreadPoolService.INSTANCE.execute(() -> {
            if (!WindowManager.getInstance().getPatientEditFrame().frameIsVisible()) {
                WindowManager.getInstance().getPatientEditFrame().setFrameVisible(true);
            } else {
                WindowManager.getInstance().getPatientEditFrame().setFrameVisible(false);
            }
        });
    }

}
