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
package com.pulse.desktop.view.frame.user;


import com.pulse.desktop.controller.AddUserListener;
import com.pulse.desktop.controller.DeleteUserListener;
import com.pulse.desktop.controller.RefreshUsersListener;
import com.pulse.desktop.controller.OpenUpdateUserFrameListener;
import com.pulse.desktop.controller.table.TableService;
import com.pulse.desktop.controller.table.TableService.TableHolder;
import com.pulse.model.constant.Privilege;

import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public final class AccountInternalFrame 
    extends AbstractInternalFrame {

    private final TableService.TableHolder TABLE_HOLDER = TableService.INSTANCE.buildTable(TableService.USERS_TABLE);
    
    private final JButton UPDATE_USERS_BUTTON = new JButton("", new ImageIcon("./pic/update.png"));
    private final JButton ADD_USER_BUTTON = new JButton("", new ImageIcon("./pic/add_user.png"));
    private final JButton EDIT_USER_BUTTON = new JButton("", new ImageIcon("./pic/edit_user.png"));
    private final JButton DELETE_USER_BUTTON = new JButton("", new ImageIcon("./pic/delete_user.png"));
    
    
    public AccountInternalFrame(String title) {
        this.frame.setTitle(title);
        
        this.frame.setResizable(true);
        this.frame.setMaximizable(true);
                
        setAllSettings();        
        initializeFrame();
    }
    
    public TableHolder getTableHolder() {
        return this.TABLE_HOLDER;
    }
    
    private void initializeToolBar() {
        setToolbarSettings();
        
        addToolbarButton(this.UPDATE_USERS_BUTTON, true);
        addToolbarButton(this.ADD_USER_BUTTON, true);
        addToolbarButton(this.EDIT_USER_BUTTON, true);
        addToolbarButton(this.DELETE_USER_BUTTON, true);
        
        this.UPDATE_USERS_BUTTON.setToolTipText("Обновить");
        this.ADD_USER_BUTTON.setToolTipText("Создать");
        this.EDIT_USER_BUTTON.setToolTipText("Изменить");
        this.DELETE_USER_BUTTON.setToolTipText("Удалить");
    }
    
    @Override
    public void initializeFrame() {
        initializeRootPanel();
        
        this.width = 800;
        this.height = 300;
        
        this.frame.setLayout(new BorderLayout());
        this.frame.setSize(this.width, this.height);
        this.frame.add(this.ROOT_PANEL);        
    }

    @Override
    public void initializeRootPanel() {        
        this.ROOT_PANEL.setLayout(new BorderLayout());
        this.ROOT_PANEL.add(this.TOOLBAR, BorderLayout.NORTH);
        this.ROOT_PANEL.add(this.TABLE_HOLDER.getScrollPane(), BorderLayout.CENTER);
    }

    @Override
    public void setAllSettings() {
        addAllActionListeners();
        initializeToolBar();
        
        this.ROOT_PANEL.setVisible(true);
    }

    @Override
    public void addAllActionListeners() {
        final RefreshUsersListener rul = new RefreshUsersListener(Privilege.None, this.TABLE_HOLDER);
        final AddUserListener aul = new AddUserListener(Privilege.None, this.TABLE_HOLDER);
        final OpenUpdateUserFrameListener suufl = new OpenUpdateUserFrameListener(Privilege.None, this.TABLE_HOLDER);
        final DeleteUserListener dul = new DeleteUserListener(Privilege.None, this.TABLE_HOLDER);
        
        this.UPDATE_USERS_BUTTON.addActionListener(rul);
        this.ADD_USER_BUTTON.addActionListener(aul);        
        this.EDIT_USER_BUTTON.addActionListener(suufl);        
        this.DELETE_USER_BUTTON.addActionListener(dul);
    }
}