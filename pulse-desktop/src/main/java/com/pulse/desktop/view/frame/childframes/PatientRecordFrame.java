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
import com.pulse.desktop.controller.CreatePatientRecordListener;
import com.pulse.desktop.controller.DeletePatientRecordListener;
import com.pulse.desktop.controller.OpenPatientRecordListener;
import com.pulse.desktop.controller.table.TableService.TableHolder;
import com.pulse.desktop.view.manager.WindowManager;
import com.pulse.model.Patient;
import com.pulse.model.constant.Privelegy;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class PatientRecordFrame extends AbstractTabledChildFrame {
    
    private final TableService.TableHolder TABLE_HOLDER = TableService.INSTANCE.buildTable(TableService.PATIENT_RECORD_TABLE);
    private final List<JComponent> TOOLBAR_BTN_LIST = new ArrayList<>(10);
    private final JDatePickerImpl SRCH_DATE_PICKER = new JDatePickerImpl(new JDatePanelImpl(null));  
    
    private final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd.MM.yyyy");
    
    private final Privelegy privelegy = Privelegy.PatientRecord;
    
    private final JButton CREATE_PATIENT_RECORD_BUTTON = new JButton("", new ImageIcon("./pic/form_creation.png"));
    private final JButton REMOVE_PATIENT_RECORD_BUTTON = new JButton("", new ImageIcon("./pic/form_removing.png"));
    private final JButton VIEW_PATIENT_RECORD_BUTTON = new JButton("", new ImageIcon("./pic/view.png"));
    
    private Patient patient;
        
    private void buildActionListeners() {
        CreatePatientRecordListener cerl = new CreatePatientRecordListener(this.privelegy, this.TABLE_HOLDER);
        DeletePatientRecordListener dprc = new DeletePatientRecordListener(this.privelegy, this.TABLE_HOLDER);
        OpenPatientRecordListener oprl = new OpenPatientRecordListener(this.privelegy, this.TABLE_HOLDER);
        
        this.CREATE_PATIENT_RECORD_BUTTON.addActionListener(cerl);
        this.REMOVE_PATIENT_RECORD_BUTTON.addActionListener(dprc);
        this.VIEW_PATIENT_RECORD_BUTTON.addActionListener(oprl);
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }    
    
    public PatientRecordFrame() {
        super.setPrivelegy(privelegy);
        super.setTableHolder(this.TABLE_HOLDER);
        
        buildActionListeners();
                
        this.CREATE_PATIENT_RECORD_BUTTON.setToolTipText("Добавить");        
        this.REMOVE_PATIENT_RECORD_BUTTON.setToolTipText("Удалить");
        this.VIEW_PATIENT_RECORD_BUTTON.setToolTipText("Просмотр");
        
        this.SRCH_DATE_PICKER.getJFormattedTextField().setText(this.FORMATTER.format(new Date()));
        
        this.TOOLBAR_BTN_LIST.add(this.CREATE_PATIENT_RECORD_BUTTON);
        this.TOOLBAR_BTN_LIST.add(this.REMOVE_PATIENT_RECORD_BUTTON);     
        this.TOOLBAR_BTN_LIST.add(this.VIEW_PATIENT_RECORD_BUTTON);
                
        super.setToolbarBtnList(TOOLBAR_BTN_LIST);        
        super.initializeFrame();
    }
}
