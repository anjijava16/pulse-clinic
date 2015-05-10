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

import com.pulse.desktop.controller.ReleasePatientListener;
import com.pulse.desktop.controller.UpdateStationaryVisitListener;
import com.pulse.desktop.controller.table.TableService;
import com.pulse.model.constant.Privilege;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class StationaryFrame extends AbstractTabledChildFrame {
    
    private final TableService.TableHolder TABLE_HOLDER = TableService.INSTANCE.buildTable(TableService.STATIONARY_TABLE);
    private final List<JComponent> TOOLBAR_BTN_LIST = new ArrayList<>(10);    

    private Privilege privilege = Privilege.Stationary;
    
    private final JButton SIGN_OUT_BUTTON = new JButton("", new ImageIcon("./pic/sign_out.png"));
    private final JButton UPDATE_BUTTON = new JButton("", new ImageIcon("./pic/update.png"));
    
    private void buildActionListeners() {
        final UpdateStationaryVisitListener usvl = new UpdateStationaryVisitListener(this.privilege, this.TABLE_HOLDER);
        final ReleasePatientListener rpl = new ReleasePatientListener(this.privilege, this.TABLE_HOLDER);
        
        this.SIGN_OUT_BUTTON.addActionListener(rpl);
        this.UPDATE_BUTTON.addActionListener(usvl);
    }
        
    public StationaryFrame() {
        super.setPrivilege(privilege);
        super.setTableHolder(this.TABLE_HOLDER);
        
        buildActionListeners();
        
        this.SIGN_OUT_BUTTON.setToolTipText("Выписать");                
        this.UPDATE_BUTTON.setToolTipText("Обновить");
        
        this.TOOLBAR_BTN_LIST.add(this.UPDATE_BUTTON);
        this.TOOLBAR_BTN_LIST.add(this.SIGN_OUT_BUTTON);
                
        super.setToolbarBtnList(TOOLBAR_BTN_LIST);        
        super.initializeFrame();
    }        
}