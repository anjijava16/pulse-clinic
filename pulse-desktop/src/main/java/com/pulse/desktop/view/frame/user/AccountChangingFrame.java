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


import java.awt.BorderLayout;
import javax.swing.JButton;

import com.pulse.desktop.controller.UpdateUserListener;
import com.pulse.desktop.controller.table.TableService.TableHolder;
import com.pulse.model.constant.Privilege;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public final class AccountChangingFrame
        extends AbstractInternalFrame {

    private final UserInfoPanel USER_INFO_PANEL = new UserInfoPanel();

    private final JButton UPDATE_BUTTON = new JButton("Изменить");
    
    private TableHolder holder;
    
    public AccountChangingFrame(String title, TableHolder holder) {
        this.frame.setTitle(title);
        this.holder = holder;

        setAllSettings();
        initializeFrame();
        addAllActionListeners();
    }

    public void clear() {
        this.USER_INFO_PANEL.clearAllFields();
        this.frame.setVisible(false);
    }
    
    public UserInfoPanel getUserInfoPanel() {
        return this.USER_INFO_PANEL;
    }

    @Override
    public void addAllActionListeners() {
        final UpdateUserListener uul = new UpdateUserListener(Privilege.None, this.holder);
        uul.setBirthdayField(USER_INFO_PANEL.getBirthdayField());
        uul.setFamilyNameField(USER_INFO_PANEL.getFamilyNameField());
        uul.setFatherNameField(USER_INFO_PANEL.getFatherNameField());
        uul.setNameField(USER_INFO_PANEL.getNameField());
        uul.setPasswordField(USER_INFO_PANEL.getPasswordField());
        uul.setPrivelegyField(USER_INFO_PANEL.getPrivelegyField());
        uul.setUsernameField(USER_INFO_PANEL.getUsernameField());
        
        this.UPDATE_BUTTON.addActionListener(uul);
    }

    @Override
    public void initializeRootPanel() {
        this.ROOT_PANEL.setVisible(true);
        this.ROOT_PANEL.setLayout(new BorderLayout());

        this.ROOT_PANEL.add(this.USER_INFO_PANEL.getRootPanel(), BorderLayout.CENTER);
        this.ROOT_PANEL.add(this.UPDATE_BUTTON, BorderLayout.SOUTH);
    }

    @Override
    public void initializeFrame() {
        initializeRootPanel();

        this.width = 310;
        this.height = 305;

        this.frame.setLayout(new BorderLayout());
        this.frame.setSize(this.width, this.height);

        this.frame.add(this.ROOT_PANEL);
    }

    @Override
    public void setAllSettings() {
        this.USER_INFO_PANEL.getUsernameField().setEditable(false);
    }
}
