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


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pulse.desktop.controller.builder.MessageBuilder;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.service.UserFacade;
import com.pulse.desktop.view.frame.AuthenticationFrame;
import com.pulse.desktop.view.frame.MainFrame;
import com.pulse.desktop.view.manager.PrivilegeService;
import com.pulse.desktop.view.manager.WindowManager;
import com.pulse.model.User;
import com.pulse.rest.client.UserClient;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class LoginListener implements ActionListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private UserClient userClient = new UserClient();

    private AuthenticationFrame authFrame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField serverIpField;

    public LoginListener(JTextField usernameField, JPasswordField passwordField, JTextField serverIpField, AuthenticationFrame authFrame) {
        this.usernameField = usernameField;
        this.passwordField = passwordField;
        this.serverIpField = serverIpField;
        this.authFrame = authFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ThreadPoolService.INSTANCE.execute(() -> {
            this.LOGGER.debug("actionPerformed()");

            if (this.usernameField == null) {
                return;
            }
            if (this.passwordField == null) {
                return;
            }
            if (this.serverIpField == null) {
                return;
            }

            final String username = this.usernameField.getText().trim();
            final String password = String.valueOf(this.passwordField.getPassword()).trim();
            final String serverIp = this.serverIpField.getText().trim();

            if (username.isEmpty()) {
                return;
            }
            if (password.isEmpty()) {
                return;
            }
            if (serverIp.isEmpty()) {
                return;
            }

            try {
                final User user = this.userClient.find(username, Base64.encodeBase64String(password.getBytes()));

                if (user != null) {
                    this.authFrame.dispose();

                    MainFrame mainFrame = new MainFrame();
                    mainFrame.setApplicationUser(user.getUsername());

                    UserFacade.INSTANCE.setApplicationUser(user);

                    WindowManager.getInstance().setMainFrame(mainFrame);

                    PrivilegeService.INSTANCE.enablePrivileges(user.getPrivelegy());

                    mainFrame.setVisible(true);
                    
                    final List<User> usersList = this.userClient.list();
                    usersList.stream().forEach((User usr) -> {
                        try {
                            UserFacade.INSTANCE.add(usr);
                        } catch (InterruptedException ie) {
                            this.LOGGER.error(ie.getMessage());
                        }
                        
                        WindowManager.getInstance().getPatientCommingRegistrationFrame().getVisitCoursePanel().fillDoctorsList(usr);
                        WindowManager.getInstance().getBookKeepingFrame().addDoctor(usr);
                    });
                } else {
                    MessageBuilder.showErrorMessage(
                            null, "Неверный логин или пароль", "Ошибка аутентификации"
                    );
                }
            } catch (IOException ioe) {
                MessageBuilder.showErrorMessage(
                        null, "Сервер недоступен", "Ошибка сети"
                );
            }
        });
    }
}
