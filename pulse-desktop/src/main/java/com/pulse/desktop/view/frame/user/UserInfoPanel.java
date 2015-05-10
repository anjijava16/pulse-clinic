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


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.pulse.model.User;
import com.pulse.model.constant.Privilege;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class UserInfoPanel {

    private final JPanel ROOT_PANEL = new JPanel();
    private final GridBagConstraints GBC = new GridBagConstraints();

    private final JLabel PERSON_NAME_LABEL = new JLabel("Имя:");
    private final JLabel PERSON_FAMILY_NAME_LABEL = new JLabel("Фамилия:");
    private final JLabel PERSON_FATHER_NAME_LABEL = new JLabel("Отчество:");
    private final JLabel PERSON_BIRTHDAY_LABEL = new JLabel("Дата рождения:");
    private final JLabel NICK_NAME_LABEL = new JLabel("Логин:");
    private final JLabel PRIVELEGY_LABEL = new JLabel("Привилегии:");
    private final JLabel PASSWORD_LABEL = new JLabel("Пароль");
    private final JPasswordField passwordField = new JPasswordField(10);

    private final JTextField nameField = new JTextField(10);
    private final JTextField familyNameField = new JTextField(10);
    private final JTextField fatherNameField = new JTextField(10);
    private final JTextField birthdayField = new JTextField(10);
    private final JTextField usernameField = new JTextField(10);
    private final JComboBox privelegyField;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @SuppressWarnings("unchecked")
    public UserInfoPanel() {
        this.privelegyField = new JComboBox();

        for (Privilege p : Privilege.values()) {
            if (p.isShowAsPrivelegy()) {
                this.privelegyField.addItem(p.getName());
            }
        }

        configurePanel();
    }

    public void showAccountsData(User user) {
        String[] nameTokens = user.getNfp().split(" ");

        if (nameTokens.length >= 1) {
            this.familyNameField.setText(nameTokens[0]);
        }

        if (nameTokens.length >= 2) {
            this.nameField.setText(nameTokens[1]);
        }

        if (nameTokens.length >= 3) {
            this.fatherNameField.setText(nameTokens[2]);
        }

        this.birthdayField.setText(sdf.format(user.getBirthday()));
        this.usernameField.setText(user.getUsername());
        this.passwordField.setText("********");
        this.privelegyField.setSelectedItem(Privilege.findById(user.getPrivelegy()).getName());
    }

    public void clearAllFields() {
        this.nameField.setText("");
        this.familyNameField.setText("");
        this.fatherNameField.setText("");
        this.birthdayField.setText("");
        this.usernameField.setText("");
        this.passwordField.setText("");
        this.privelegyField.setSelectedIndex(0);
    }

    private void configurePanel() {
        this.ROOT_PANEL.setVisible(true);
        this.ROOT_PANEL.setBorder(new TitledBorder(new EtchedBorder(), "Информация"));
        this.ROOT_PANEL.setLayout(new GridBagLayout());

        this.GBC.fill = GridBagConstraints.HORIZONTAL;
        this.GBC.gridx = 0;
        this.GBC.gridy = 0;
        this.GBC.insets = new Insets(0, 0, 0, 0);
        this.ROOT_PANEL.add(this.PERSON_FAMILY_NAME_LABEL, this.GBC);

        this.GBC.fill = GridBagConstraints.HORIZONTAL;
        this.GBC.gridx = 1;
        this.GBC.gridy = 0;
        this.GBC.insets = new Insets(0, 0, 0, 0);
        this.ROOT_PANEL.add(this.familyNameField, this.GBC);

        this.GBC.fill = GridBagConstraints.HORIZONTAL;
        this.GBC.gridx = 0;
        this.GBC.gridy = 1;
        this.GBC.insets = new Insets(0, 0, 0, 0);
        this.ROOT_PANEL.add(this.PERSON_NAME_LABEL, this.GBC);

        this.GBC.fill = GridBagConstraints.HORIZONTAL;
        this.GBC.gridx = 1;
        this.GBC.gridy = 1;
        this.GBC.insets = new Insets(0, 0, 0, 0);
        this.ROOT_PANEL.add(this.nameField, this.GBC);

        this.GBC.fill = GridBagConstraints.HORIZONTAL;
        this.GBC.gridx = 0;
        this.GBC.gridy = 2;
        this.GBC.insets = new Insets(0, 0, 0, 0);
        this.ROOT_PANEL.add(this.PERSON_FATHER_NAME_LABEL, this.GBC);

        this.GBC.fill = GridBagConstraints.HORIZONTAL;
        this.GBC.gridx = 1;
        this.GBC.gridy = 2;
        this.GBC.insets = new Insets(0, 0, 0, 0);
        this.ROOT_PANEL.add(this.fatherNameField, this.GBC);

        this.GBC.fill = GridBagConstraints.HORIZONTAL;
        this.GBC.gridx = 0;
        this.GBC.gridy = 3;
        this.GBC.insets = new Insets(0, 0, 0, 0);
        this.ROOT_PANEL.add(this.PERSON_BIRTHDAY_LABEL, this.GBC);

        this.GBC.fill = GridBagConstraints.HORIZONTAL;
        this.GBC.gridx = 1;
        this.GBC.gridy = 3;
        this.GBC.insets = new Insets(0, 0, 0, 0);
        this.ROOT_PANEL.add(this.birthdayField, this.GBC);

        this.GBC.fill = GridBagConstraints.HORIZONTAL;
        this.GBC.gridx = 0;
        this.GBC.gridy = 4;
        this.GBC.insets = new Insets(0, 0, 0, 0);
        this.ROOT_PANEL.add(this.NICK_NAME_LABEL, this.GBC);

        this.GBC.fill = GridBagConstraints.HORIZONTAL;
        this.GBC.gridx = 1;
        this.GBC.gridy = 4;
        this.GBC.insets = new Insets(0, 0, 0, 0);
        this.ROOT_PANEL.add(this.usernameField, this.GBC);

        this.GBC.fill = GridBagConstraints.HORIZONTAL;
        this.GBC.gridx = 0;
        this.GBC.gridy = 5;
        this.GBC.insets = new Insets(0, 0, 0, 0);
        this.ROOT_PANEL.add(this.PASSWORD_LABEL, this.GBC);

        this.GBC.fill = GridBagConstraints.HORIZONTAL;
        this.GBC.gridx = 1;
        this.GBC.gridy = 5;
        this.GBC.insets = new Insets(0, 0, 0, 0);
        this.ROOT_PANEL.add(this.passwordField, this.GBC);

        this.GBC.fill = GridBagConstraints.HORIZONTAL;
        this.GBC.gridx = 0;
        this.GBC.gridy = 6;
        this.GBC.insets = new Insets(0, 0, 0, 0);
        this.ROOT_PANEL.add(this.PRIVELEGY_LABEL, this.GBC);

        this.GBC.fill = GridBagConstraints.HORIZONTAL;
        this.GBC.gridx = 1;
        this.GBC.gridy = 6;
        this.GBC.insets = new Insets(0, 0, 0, 0);
        this.ROOT_PANEL.add(this.privelegyField, this.GBC);

//        this.GBC.fill = GridBagConstraints.HORIZONTAL;
//        this.GBC.gridx = 1;
//        this.GBC.gridy = 7;
//        this.GBC.insets = new Insets(0, 0, 0, 0);
//        this.ROOT_PANEL.add(this.SAVE_BUTTON, this.GBC);
    }

    public JPanel getRootPanel() {
        return this.ROOT_PANEL;
    }

    public JTextField getNameField() {
        return nameField;
    }

    public JTextField getFamilyNameField() {
        return familyNameField;
    }

    public JTextField getFatherNameField() {
        return fatherNameField;
    }

    public JTextField getBirthdayField() {
        return birthdayField;
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JComboBox getPrivelegyField() {
        return privelegyField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }
        
}