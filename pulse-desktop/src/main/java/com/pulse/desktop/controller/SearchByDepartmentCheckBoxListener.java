package com.pulse.desktop.controller;

import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.model.constant.Privelegy;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

/**
 *
 * @author befast
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
