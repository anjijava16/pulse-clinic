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
package com.pulse.desktop.controller;


import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.table.TableService.TableHolder;
import com.pulse.desktop.view.manager.WindowManager;
import com.pulse.model.User;
import com.pulse.model.constant.Privilege;
import com.pulse.rest.client.UserClient;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class UpdateUserListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final SimpleDateFormat BIRTHDAY_DAY_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private final UserClient userService = new UserClient();
    
    private JTextField nameField;
    private JTextField familyNameField;
    private JTextField fatherNameField;
    private JTextField birthdayField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox privelegyField;
        
    public UpdateUserListener(Privilege privilege, TableHolder holder) {
        super(privilege, holder);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        ThreadPoolService.INSTANCE.execute(() -> {
            this.LOGGER.debug("actionPerformed()");
            
            if (nameField == null) return;
            if (familyNameField == null) return;
            if (fatherNameField == null) return;
            if (birthdayField == null) return;
            if (usernameField == null) return;
            if (passwordField == null) return;
            if (privelegyField == null) return;
            
            final String name = nameField.getText().trim();
            final String family = familyNameField.getText().trim();
            final String father = fatherNameField.getText().trim();
            final String birthday = birthdayField.getText().trim();
            final String username = usernameField.getText().trim();
            final String password = String.valueOf(passwordField.getPassword()).trim();
            final String privelegy = privelegyField.getSelectedItem().toString();
            
            if (name.isEmpty()) {
                ResultToolbarService.INSTANCE.showFailedStatus("Введите имя");
                return;
            }
            if (family.isEmpty()) {
                ResultToolbarService.INSTANCE.showFailedStatus("Введите фамилию");
                return;
            }
            if (father.isEmpty()) {
                ResultToolbarService.INSTANCE.showFailedStatus("Введите отчество");
                return;
            }
            
            if (birthday.isEmpty()) {
                ResultToolbarService.INSTANCE.showFailedStatus("Введите дату рождения");
                return;
            }
            if (username.isEmpty()) {
                ResultToolbarService.INSTANCE.showFailedStatus("Введите логин");
                return;
            }
            if (password.isEmpty()) {
                ResultToolbarService.INSTANCE.showFailedStatus("Введите пароль");
                return;
            }
            
            Date birthdayDate = null;
            try {
                birthdayDate = this.BIRTHDAY_DAY_FORMAT.parse(birthday);
            } catch (ParseException pe) {
                ResultToolbarService.INSTANCE.showFailedStatus("Неверный формат. Пример: 1988-06-12");
                return;
            }
                        
            final String nfp = String.format("%s %s %s", name, family, father);
            final String base64Password = Base64.encodeBase64String(password.getBytes());            
                                    
            try {
                final User user = userService.find(username);
                
                if (user == null) {
                    ResultToolbarService.INSTANCE.showFailedStatus(
                            "Данный пользователь был удален"
                    );
                    return;
                }
                
                user.setNfp(nfp);                        
                user.setBirthday(birthdayDate);
                user.setPassword(base64Password);
                user.setUsername(username);
                user.setPrivelegy(Privilege.findByName(privelegy).getId());
                
                userService.update(user);                
                WindowManager.getInstance().getAccountChangingFrame().clear();
                
                ResultToolbarService.INSTANCE.showSuccessStatus();                
            } catch (IOException ioe) {
                ResultToolbarService.INSTANCE.showFailedStatus("Ошибка при создании пользователя");
            }
        });
    }

    public void setNameField(JTextField nameField) {
        this.nameField = nameField;
    }

    public void setFamilyNameField(JTextField familyNameField) {
        this.familyNameField = familyNameField;
    }

    public void setFatherNameField(JTextField fatherNameField) {
        this.fatherNameField = fatherNameField;
    }

    public void setBirthdayField(JTextField birthdayField) {
        this.birthdayField = birthdayField;
    }

    public void setUsernameField(JTextField usernameField) {
        this.usernameField = usernameField;
    }

    public void setPasswordField(JPasswordField passwordField) {
        this.passwordField = passwordField;
    }

    public void setPrivelegyField(JComboBox privelegyField) {
        this.privelegyField = privelegyField;
    }
}
