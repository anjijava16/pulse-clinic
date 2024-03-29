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
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;

import com.pulse.desktop.controller.DemarkAsDiscountPatientListener;
import com.pulse.desktop.controller.MarkAsDebtListener;
import com.pulse.desktop.controller.MarkAsDiscountPatientListener;
import com.pulse.desktop.controller.MarkAsPayListener;
import com.pulse.desktop.controller.MarkAsPayedBackListener;
import com.pulse.desktop.controller.MarkAsUnpayListener;
import com.pulse.desktop.controller.SearchByDateListener;
import com.pulse.desktop.controller.builder.FilterBoxBuilder;
import com.pulse.desktop.controller.table.TableService;
import com.pulse.desktop.view.util.ConstantValues;
import com.pulse.desktop.view.util.DateLabelFormatter;
import com.pulse.model.constant.Privilege;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class TicketWindowFrame extends AbstractTabledChildFrame {
    
    private final TableService.TableHolder TABLE_HOLDER = TableService.INSTANCE.buildTable(TableService.SIMPLE_TABLE);
    
    private final List<JComponent> TOOLBAR_BTN_LIST = new ArrayList<>(10);
    
    private Privilege privilege = Privilege.TicketWindow;
    
    private final JButton MARK_AS_PAYED_BTN = new JButton("", new ImageIcon("./pic/pay.png"));
    private final JButton MARK_AS_UNPAYED_BTN = new JButton("", new ImageIcon("./pic/no_pay_btn.png")); 
    private final JButton MARK_AS_DEBT_BTN = new JButton("", new ImageIcon("./pic/mark_debt_btn.png")); 
    private final JButton MARK_AS_BONUS_BTN = new JButton("", new ImageIcon("./pic/mark_bonus_btn.png"));
    private final JButton DEMARK_AS_BONUS_BTN = new JButton("", new ImageIcon("./pic/demark_bonus_btn.png"));
    
    private final JButton PAYBACK_BUTTON = new JButton("", new ImageIcon("./pic/payback.png"));
    
    private final JButton DATE_FILTER_BUTTON = new JButton("", new ImageIcon("./pic/update.png"));
    private final JDatePickerImpl SRCH_BY_DATE_PICKER = new JDatePickerImpl(new JDatePanelImpl(null), new DateLabelFormatter());

    private final SimpleDateFormat FORMATTER = new SimpleDateFormat(ConstantValues.SEARCH_DATE_FIELD_TEMPLATE);
    
    private final DefaultComboBoxModel<String> FILTER_BOX_MODEL = FilterBoxBuilder.build();
    private final JComboBox<String> PATIENT_LIST_BOX = new JComboBox<>(FILTER_BOX_MODEL);
    
    public TicketWindowFrame() {
        super.setPrivilege(Privilege.TicketWindow);
        super.setTableHolder(this.TABLE_HOLDER);
        
        buildActionListeners();
        
        this.MARK_AS_PAYED_BTN.setToolTipText("Оплачено");
        this.MARK_AS_UNPAYED_BTN.setToolTipText("Не оплачено");
        this.MARK_AS_DEBT_BTN.setToolTipText("Должник");
        this.MARK_AS_BONUS_BTN.setToolTipText("Скидка");
        this.DEMARK_AS_BONUS_BTN.setToolTipText("Нет скидки");
        this.PAYBACK_BUTTON.setToolTipText("Возврат");
        this.DATE_FILTER_BUTTON.setToolTipText("Поиск");
        
        this.SRCH_BY_DATE_PICKER.getJFormattedTextField().setText(this.FORMATTER.format(new Date()));
        
        this.TOOLBAR_BTN_LIST.add(this.MARK_AS_PAYED_BTN);        
        this.TOOLBAR_BTN_LIST.add(this.MARK_AS_UNPAYED_BTN);
        this.TOOLBAR_BTN_LIST.add(this.PAYBACK_BUTTON);
        this.TOOLBAR_BTN_LIST.add(this.MARK_AS_DEBT_BTN);
        this.TOOLBAR_BTN_LIST.add(this.MARK_AS_BONUS_BTN);
        this.TOOLBAR_BTN_LIST.add(this.DEMARK_AS_BONUS_BTN);
        
        this.TOOLBAR_BTN_LIST.add(this.SRCH_BY_DATE_PICKER);
        this.TOOLBAR_BTN_LIST.add(this.DATE_FILTER_BUTTON);
        
                
        super.setToolbarBtnList(TOOLBAR_BTN_LIST);        
        super.initializeFrame();
    }
    
    private void buildActionListeners() {
        final MarkAsPayListener pbl = new MarkAsPayListener();
        pbl.setTableHolder(this.TABLE_HOLDER);

        final MarkAsUnpayListener upbl = new MarkAsUnpayListener();
        upbl.setTableHolder(this.TABLE_HOLDER);

        final MarkAsDebtListener dpbl = new MarkAsDebtListener();
        dpbl.setTableHolder(this.TABLE_HOLDER);

        final MarkAsDiscountPatientListener mdpbl = new MarkAsDiscountPatientListener();
        mdpbl.setTableHolder(this.TABLE_HOLDER);

        final DemarkAsDiscountPatientListener dadpl = new DemarkAsDiscountPatientListener();
        dadpl.setTableHolder(this.TABLE_HOLDER);

        final MarkAsPayedBackListener mapbl = new MarkAsPayedBackListener();
        mapbl.setTableHolder(this.TABLE_HOLDER);

        final SearchByDateListener sbdl = new SearchByDateListener(
                privilege, this.PATIENT_LIST_BOX, this.SRCH_BY_DATE_PICKER, this.TABLE_HOLDER, this.FORMATTER
        );
        this.DATE_FILTER_BUTTON.addActionListener(sbdl);
                        
        this.PAYBACK_BUTTON.addActionListener(mapbl);
        this.DEMARK_AS_BONUS_BTN.addActionListener(dadpl);
        this.MARK_AS_PAYED_BTN.addActionListener(pbl);
        this.MARK_AS_DEBT_BTN.addActionListener(dpbl);
        this.MARK_AS_UNPAYED_BTN.addActionListener(upbl);
        this.MARK_AS_BONUS_BTN.addActionListener(mdpbl);
    }
}
