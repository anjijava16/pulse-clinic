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
import javax.swing.JTextField;
import com.pulse.desktop.controller.table.TableService;
import com.pulse.desktop.controller.AddOrganisationListener;
import com.pulse.desktop.controller.DeleteOrganisationListener;
import com.pulse.desktop.controller.UpdateOrganisationsListener;
import com.pulse.model.constant.Privilege;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class OrganisationsFrame extends AbstractTabledChildFrame {
    
    private final TableService.TableHolder TABLE_HOLDER = TableService.INSTANCE.buildTable(TableService.ORGANISATION_TABLE);
    private final List<JComponent> TOOLBAR_BTN_LIST = new ArrayList<>(3);
    
    private final JTextField ORGANISATION_NAME_FIELD = new JTextField(this.maxChars);  
    
    private final JButton ADD_ORGANISATION_BUTTON = new JButton(new ImageIcon("./pic/form_creation.png"));
    private final JButton DELETE_ORGANISATION_BUTTON = new JButton("", new ImageIcon("./pic/form_removing.png"));
    private final JButton UPDATE_BUTTON = new JButton("", new ImageIcon("./pic/update.png"));    
    
    private final Privilege privilege = Privilege.Organisation;
    
    private void buildActionListeners() {
        final AddOrganisationListener aol = new AddOrganisationListener(this.privilege, this.TABLE_HOLDER, this.ORGANISATION_NAME_FIELD);
        final DeleteOrganisationListener dol = new DeleteOrganisationListener(this.privilege, this.TABLE_HOLDER);
        final UpdateOrganisationsListener uol = new UpdateOrganisationsListener(this.privilege, this.TABLE_HOLDER);
        
        this.ADD_ORGANISATION_BUTTON.addActionListener(aol);
        this.DELETE_ORGANISATION_BUTTON.addActionListener(dol);
        this.UPDATE_BUTTON.addActionListener(uol);
    }
    
    public OrganisationsFrame() {
        super.setPrivilege(privilege);
        super.setTableHolder(this.TABLE_HOLDER);
        
        buildActionListeners();
        
        this.ADD_ORGANISATION_BUTTON.setToolTipText("Добавить");
        this.DELETE_ORGANISATION_BUTTON.setToolTipText("Удалить");
        this.UPDATE_BUTTON.setToolTipText("Обновить");
        
        this.TOOLBAR_BTN_LIST.add(this.UPDATE_BUTTON); 
        this.TOOLBAR_BTN_LIST.add(this.ORGANISATION_NAME_FIELD);     
        this.TOOLBAR_BTN_LIST.add(this.ADD_ORGANISATION_BUTTON);
        this.TOOLBAR_BTN_LIST.add(this.DELETE_ORGANISATION_BUTTON);        
        
        super.setToolbarBtnList(TOOLBAR_BTN_LIST);        
        super.initializeFrame();
    }
}
