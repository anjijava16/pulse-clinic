package com.pulse.desktop.controller;

import com.pulse.desktop.controller.service.ThreadPoolService;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

/**
 *
 * @author Vladimir Shin <vladimir.shin@gmail.com>
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
