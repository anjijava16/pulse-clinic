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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class EnableStatisticByDepartmentListener implements ActionListener {

    private JCheckBox enableDepCalc;

    private JComboBox<String> docNamesList;
    private JComboBox<String> departmentsList;

    public EnableStatisticByDepartmentListener(JCheckBox enableDepCalc) {
        this.enableDepCalc = enableDepCalc;
    }

    public void setDepartmentsList(JComboBox<String> departmentsList) {
        this.departmentsList = departmentsList;
    }

    public void setDocNamesList(JComboBox<String> docNamesList) {
        this.docNamesList = docNamesList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.enableDepCalc == null) {
            return;
        }
        if (this.docNamesList == null) {
            return;
        }
        if (this.departmentsList == null) {
            return;
        }

        ThreadPoolService.INSTANCE.execute(() -> {
            if (this.enableDepCalc.isSelected()) {
                this.departmentsList.setEnabled(true);
                this.docNamesList.setEnabled(false);
            } else {
                this.departmentsList.setEnabled(false);
                this.docNamesList.setEnabled(true);
            }
        });
    }
}
