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
import javax.swing.JComponent;
import javax.swing.JTextField;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import com.pulse.desktop.controller.table.TableService;
import com.pulse.desktop.controller.CreateJournalListener;
import com.pulse.desktop.controller.DeleteJournalListener;
import com.pulse.desktop.controller.OpenJournalListener;
import com.pulse.desktop.controller.UpdateJournalsListener;
import com.pulse.model.Patient;
import com.pulse.model.constant.Privelegy;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class JournalFrame extends AbstractTabledChildFrame {
    
    private final TableService.TableHolder TABLE_HOLDER = TableService.INSTANCE.buildTable(TableService.JOURNALS_TABLE);
    private final List<JComponent> TOOLBAR_BTN_LIST = new ArrayList<>(10);    
    private final JTextField SEARCH_PATTERN_FIELD = new JTextField(this.maxChars);
    private final JDatePickerImpl SRCH_DATE_PICKER = new JDatePickerImpl(new JDatePanelImpl(null));  
    
    private final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd.MM.yyyy");
    
    private final Privelegy privelegy = Privelegy.Journal;
    
    private final JButton CREATE_PATIENT_RECORD_BUTTON = new JButton("", new ImageIcon("./pic/form_creation.png"));
    private final JButton REMOVE_PATIENT_RECORD_BUTTON = new JButton("", new ImageIcon("./pic/form_removing.png"));
    private final JButton VIEW_PATIENT_RECORD_BUTTON = new JButton("", new ImageIcon("./pic/view.png"));
    
    private final JButton UPDATE_BUTTON = new JButton("", new ImageIcon("./pic/update.png"));    
    
    private Patient patient;
    
    private void buildActionListeners() {
        CreateJournalListener cjl = new CreateJournalListener(this.privelegy, this.TABLE_HOLDER);
        DeleteJournalListener djl = new DeleteJournalListener(this.privelegy, this.TABLE_HOLDER);
        OpenJournalListener ojl = new OpenJournalListener(this.privelegy, this.TABLE_HOLDER);
        
        UpdateJournalsListener ujl = new UpdateJournalsListener(this.privelegy, this.TABLE_HOLDER);
        
        this.CREATE_PATIENT_RECORD_BUTTON.addActionListener(cjl);
        this.REMOVE_PATIENT_RECORD_BUTTON.addActionListener(djl);
        this.VIEW_PATIENT_RECORD_BUTTON.addActionListener(ojl);
        this.UPDATE_BUTTON.addActionListener(ujl);
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }    
    
    public JournalFrame() {
        super.setPrivelegy(privelegy);
        super.setTableHolder(this.TABLE_HOLDER);
        
        buildActionListeners();
        
        this.SEARCH_PATTERN_FIELD.setText("Поиск");
        
        this.CREATE_PATIENT_RECORD_BUTTON.setToolTipText("Создать");        
        this.REMOVE_PATIENT_RECORD_BUTTON.setToolTipText("Удалить");
        this.VIEW_PATIENT_RECORD_BUTTON.setToolTipText("Просмотр");
        
        this.SRCH_DATE_PICKER.getJFormattedTextField().setText(this.FORMATTER.format(new Date()));
        this.UPDATE_BUTTON.setToolTipText("Обновить");
        
        this.TOOLBAR_BTN_LIST.add(this.UPDATE_BUTTON); 
        this.TOOLBAR_BTN_LIST.add(this.CREATE_PATIENT_RECORD_BUTTON);
        this.TOOLBAR_BTN_LIST.add(this.REMOVE_PATIENT_RECORD_BUTTON);     
        this.TOOLBAR_BTN_LIST.add(this.VIEW_PATIENT_RECORD_BUTTON);
        
        
        super.setToolbarBtnList(TOOLBAR_BTN_LIST);        
        super.initializeFrame();
    }
}
