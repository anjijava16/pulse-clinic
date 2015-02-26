package com.pulse.desktop.controller;

import com.pulse.desktop.controller.builder.MessageBuilder;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.service.UserFacade;

import com.pulse.desktop.view.frame.AuthentificationFrame;
import com.pulse.desktop.view.frame.MainFrame;
import com.pulse.desktop.view.manager.PrivelegyService;
import com.pulse.desktop.view.manager.WindowManager;

import com.pulse.model.User;
import com.pulse.rest.client.UserClient;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.apache.commons.codec.binary.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author befast
 */
public class LoginListener implements ActionListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private UserClient userClient = new UserClient();

    private AuthentificationFrame authFrame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField serverIpField;

    public LoginListener(JTextField usernameField, JPasswordField passwordField, JTextField serverIpField, AuthentificationFrame authFrame) {
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

            if (username == null || username.isEmpty()) {
                return;
            }
            if (password == null || password.isEmpty()) {
                return;
            }
            if (serverIp == null || serverIp.isEmpty()) {
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

                    PrivelegyService.INSTANCE.enablePrivelegies(user.getPrivelegy());

                    mainFrame.setVisible(true);
                    
                    final List<User> usersList = this.userClient.list();
                    usersList.stream().forEach((User usr) -> {
                        try {
                            UserFacade.INSTANCE.add(usr);
                        } catch (InterruptedException ie) {}
                        
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