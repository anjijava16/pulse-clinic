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


import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import com.pulse.desktop.controller.CreatePatientRoomListener;
import com.pulse.desktop.controller.DeletePatientRoomListener;
import com.pulse.desktop.controller.UpdatePatientRoomListener;
import com.pulse.desktop.controller.table.TableService;
import com.pulse.model.constant.Privilege;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class PatientRoomFrame extends AbstractTabledChildFrame {
    
    private final TableService.TableHolder TABLE_HOLDER = TableService.INSTANCE.buildTable(TableService.PATIENT_ROOM_TABLE);
    private final List<JComponent> TOOLBAR_BTN_LIST = new ArrayList<>(3);
    
    private final JTextField ORGANISATION_NAME_FIELD = new JTextField(this.maxChars);  
    
    private final JButton ADD_ORGANISATION_BUTTON = new JButton(new ImageIcon("./pic/form_creation.png"));
    private final JButton DELETE_ORGANISATION_BUTTON = new JButton("", new ImageIcon("./pic/form_removing.png"));
    private final JButton REFRESH_BUTTON = new JButton("", new ImageIcon("./pic/update.png"));    
    
    private final SpinnerNumberModel NUMBER_MODEL = new SpinnerNumberModel(1, 1, 999, 1);
    private final JSpinner ROOM_CAPACITY_FIELD = new JSpinner(this.NUMBER_MODEL);
    private final JButton UPDATE_CAPACITY_BUTTON = new JButton("", new ImageIcon("./pic/edit.png"));
    private final JButton CLEAR_CAPACITY_BUTTON = new JButton("", new ImageIcon("./pic/free.png"));
    
    private final Privilege privilege = Privilege.PatientRoom;
    
    private void buildActionListeners() {
        final CreatePatientRoomListener aol = new CreatePatientRoomListener(this.privilege, this.TABLE_HOLDER, this.ORGANISATION_NAME_FIELD);
        final DeletePatientRoomListener dol = new DeletePatientRoomListener(this.privilege, this.TABLE_HOLDER);
        final UpdatePatientRoomListener uol = new UpdatePatientRoomListener(this.privilege, this.TABLE_HOLDER);
        
        this.ADD_ORGANISATION_BUTTON.addActionListener(aol);
        this.DELETE_ORGANISATION_BUTTON.addActionListener(dol);
        this.REFRESH_BUTTON.addActionListener(uol);
    }
    
    public PatientRoomFrame() {
        super.setPrivilege(privilege);
        super.setTableHolder(this.TABLE_HOLDER);
        
        buildActionListeners();
        
        this.ADD_ORGANISATION_BUTTON.setToolTipText("Добавить");
        this.DELETE_ORGANISATION_BUTTON.setToolTipText("Удалить");
        this.REFRESH_BUTTON.setToolTipText("Обновить");
        this.CLEAR_CAPACITY_BUTTON.setToolTipText("Сбросить вместимость");
        this.UPDATE_CAPACITY_BUTTON.setToolTipText("Изменить вместимость");
        
        this.TOOLBAR_BTN_LIST.add(this.REFRESH_BUTTON); 
        this.TOOLBAR_BTN_LIST.add(this.ORGANISATION_NAME_FIELD);     
        this.TOOLBAR_BTN_LIST.add(this.ADD_ORGANISATION_BUTTON);
        this.TOOLBAR_BTN_LIST.add(this.DELETE_ORGANISATION_BUTTON);
        this.TOOLBAR_BTN_LIST.add(this.CLEAR_CAPACITY_BUTTON);
        this.TOOLBAR_BTN_LIST.add(this.ROOM_CAPACITY_FIELD);
        this.TOOLBAR_BTN_LIST.add(this.UPDATE_CAPACITY_BUTTON);        
                
        super.setToolbarBtnList(TOOLBAR_BTN_LIST);        
        super.initializeFrame();
    }
}
