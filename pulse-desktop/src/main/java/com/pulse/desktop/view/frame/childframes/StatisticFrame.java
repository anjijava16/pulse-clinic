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
package com.pulse.desktop.view.frame.childframes;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;

import com.pulse.desktop.controller.StatisticSearchListener;
import com.pulse.desktop.controller.table.TableService;
import com.pulse.desktop.view.util.ConstantValues;
import com.pulse.desktop.view.util.DateLabelFormatter;
import com.pulse.model.constant.Privilege;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class StatisticFrame extends AbstractTabledChildFrame {

    private final TableService.TableHolder TABLE_HOLDER = TableService.INSTANCE.buildTable(TableService.STATISTIC_TABLE);
    private final List<JComponent> TOOLBAR_BTN_LIST = new ArrayList<>(10);

    private final JComboBox<String> ORGANISATIONS_LIST = new JComboBox<>();
    private final JButton SEARCH_PATTERN_BUTTON = new JButton("", new ImageIcon("./pic/update.png"));

    private final Privilege privilege = Privilege.Statistic;

    private final SimpleDateFormat FORMATTER = new SimpleDateFormat(ConstantValues.SEARCH_DATE_FIELD_TEMPLATE);
    private final JDatePickerImpl SEARCH_FROM_DATE_PICKER = new JDatePickerImpl(new JDatePanelImpl(null), new DateLabelFormatter());
    private final JDatePickerImpl SEARCH_UNTIL_DATE_PICKER = new JDatePickerImpl(new JDatePanelImpl(null), new DateLabelFormatter());

    private void buildActionListeners() {
        this.SEARCH_FROM_DATE_PICKER.getJFormattedTextField().setText(this.FORMATTER.format(new Date()));
        this.SEARCH_UNTIL_DATE_PICKER.getJFormattedTextField().setText(this.FORMATTER.format(new Date()));

        final StatisticSearchListener ssl = new StatisticSearchListener(
                this.privilege, this.TABLE_HOLDER, this.ORGANISATIONS_LIST, this.SEARCH_FROM_DATE_PICKER, this.SEARCH_UNTIL_DATE_PICKER
        );

        this.SEARCH_PATTERN_BUTTON.addActionListener(ssl);
    }

    public StatisticFrame() {
        super.setPrivilege(privilege);
        super.setTableHolder(this.TABLE_HOLDER);

        buildActionListeners();

        this.SEARCH_PATTERN_BUTTON.setToolTipText("Поиск");

        this.TOOLBAR_BTN_LIST.add(this.ORGANISATIONS_LIST);
        this.TOOLBAR_BTN_LIST.add(this.SEARCH_FROM_DATE_PICKER);
        this.TOOLBAR_BTN_LIST.add(this.SEARCH_UNTIL_DATE_PICKER);
        this.TOOLBAR_BTN_LIST.add(this.SEARCH_PATTERN_BUTTON);

        super.setToolbarBtnList(TOOLBAR_BTN_LIST);
        super.initializeFrame();
    }
}
