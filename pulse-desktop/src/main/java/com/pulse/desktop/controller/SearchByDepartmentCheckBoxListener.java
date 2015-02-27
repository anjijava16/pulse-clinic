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
import com.pulse.model.constant.Privelegy;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class SearchByDepartmentCheckBoxListener extends AbstractTableListener {

    private JCheckBox enableSearchByDepartment;

    private JComboBox<String> doctorsList;
    private JComboBox<String> departmentsList;

    public SearchByDepartmentCheckBoxListener(Privelegy privelegy, JCheckBox enableSearchByDepartment, JComboBox<String> doctorsList, JComboBox<String> departmentsList) {
        super(privelegy, null);
        this.doctorsList = doctorsList;
        this.departmentsList = departmentsList;
        this.enableSearchByDepartment = enableSearchByDepartment;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        ThreadPoolService.INSTANCE.execute(() -> {
            if (this.enableSearchByDepartment.isSelected()) {
                this.doctorsList.setEnabled(false);
                this.departmentsList.setEnabled(true);
            } else {
                this.doctorsList.setEnabled(true);
                this.departmentsList.setEnabled(false);
            }
        });
    }
}
