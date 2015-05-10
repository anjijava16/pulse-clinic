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
package com.pulse.desktop.view.frame;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pulse.desktop.controller.LoginListener;
import com.pulse.desktop.view.panel.ImagePanel;


/**
 * This is login frame window. This window will be the first, which user will see. It will be open
 * after launch "Prometheus" application. The main purpose of this window is authentication in
 * the system.
 *
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class AuthenticationFrame {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final String LOGIN_BCKG_PICTURE = "./bckg/login_bckg.png";
    
    private final int MAX_CHARS = 11;            // This is the maximum chars number limit for the field.
    private final boolean NOT_RESIZABLE = false; // This is the resizability flag. If this flag will be set on, frame will be unresizable.
    private final boolean VISIBLE = true;        // This is visibility flag. If this flag will be set on, frame will be visible.
    private final boolean NO_BORDER = true;
    
    private final int FRAME_WIDTH = 400;         // This is the width of the login frame.
    private final int FRAME_HEIGHT = 300;        // This is the height of the login frame.
    
    private final int FRAME_FONT_SIZE = 12;
    private final Font FRAME_FONT = new Font("Dialog", Font.BOLD, this.FRAME_FONT_SIZE);
            
    private final Color FONT_COLOR = new Color(115, 164, 209);
    private final Color LOGIN_BCKG_COLOR = new Color(75, 65, 56); // This is RGB background color for the login frame.
    
    private final GridBagConstraints GBC = new GridBagConstraints(); // This object is keeping coordinates and position paramaters of each
                                                                     // element in the MAIN_PANEL.
    
    private final JFrame MAIN_FRAME = new JFrame("Аутентификация");                // This is the main frame instance.
    private final ImagePanel MAIN_PANEL = new ImagePanel(this.LOGIN_BCKG_PICTURE); // This is the main panel, which will "hold" all fields and labels.
    
    private final JButton LOGIN_BUTTON = new JButton("Войти"); // This is the button for the login action.
    
    private final JLabel SERVER_IP_LABEL = new JLabel("Адрес сервера : ");     // This is the label for server ipv4 field.
    private final JLabel USERNAME_LABEL = new JLabel("Пользователь : ");       // This is the label for username field.
    private final JLabel USER_PASSWORD_LABEL = new JLabel("Пароль : ");        // This is the label for user password field.
    
    private final JTextField SERVER_IP_FIELD = new JTextField(this.MAX_CHARS);             // This is the server ipv4 field.
    private final JTextField USERNAME_FIELD = new JTextField(this.MAX_CHARS);              // This is username field.
    private final JPasswordField USER_PASSWORD_FIELD = new JPasswordField(this.MAX_CHARS); // This is user password field.
        
    private String defaultIp = "127.0.0.1";
    
    /**
     * This is the main constructor of LoginFrame. All initialization, 
     * setting frame settings, will be done here.
     */
    public AuthenticationFrame() {
        try {
            setAllSettings();
        } catch (FontFormatException | IOException ex) {
            this.LOGGER.error(ExceptionUtils.getFullStackTrace(ex));
        }

        initializeFrame();    
    }
    
    private void setIcon() {
        final BufferedImage appIcon = getIconImage();
        if (appIcon != null) {
            this.MAIN_FRAME.setIconImage(appIcon);
        }
    }

    private BufferedImage getIconImage() {
        BufferedImage iconImage = null;
        final File iconFile = new File("bckg/icon.png");
        try {
            iconImage = ImageIO.read(iconFile);
        } catch (IOException ioe) {
            this.LOGGER.error(ExceptionUtils.getFullStackTrace(ioe));
        }
        return iconImage;
    }
    
    private String checkLastIp() {
        final File config = new File("data/config/config.ini");
        if (config.exists()) {
            try {
                final FileInputStream instream = new FileInputStream(config);
                final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(instream));
                String stroke;
                
                while ((stroke = bufferedReader.readLine()) != null) {
                    if (stroke.contains("server_ip:")) {
                        String[] tokenizerBuffer = stroke.split(" ");
                        
                        return tokenizerBuffer[1].trim();
                    }
                }
            } catch (IOException ioe) {
                this.LOGGER.error(ExceptionUtils.getFullStackTrace(ioe));
            }
        }
        
        return null;
    }
    
    /**
     * This function will place main frame to the center of screen.
     */
    private void frameToScreenCenter() {
        // Get the size of the screen
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        // Determine the new location of the window
        int x = (dimension.width - this.FRAME_WIDTH)/2;
        int y = (dimension.height - FRAME_HEIGHT)/2;

        // Move the window
        this.MAIN_FRAME.setLocation(x, y);
    }
    
    /**
     * 
     */
    private void addAllActionListeners() {
        final LoginListener loginListener = new LoginListener(
                this.USERNAME_FIELD, this.USER_PASSWORD_FIELD, this.SERVER_IP_FIELD, this
        );
        
        this.LOGIN_BUTTON.addActionListener(loginListener);
    }
    
    /**
     * 
     */
    private void setAllSettings() 
            throws FontFormatException, IOException {
        addAllActionListeners();
        
        this.SERVER_IP_LABEL.setForeground(this.FONT_COLOR);
        this.SERVER_IP_LABEL.setFont(this.FRAME_FONT);
        
        this.USERNAME_LABEL.setForeground(this.FONT_COLOR);
        this.USERNAME_LABEL.setFont(this.FRAME_FONT);
        
        this.USER_PASSWORD_LABEL.setForeground(this.FONT_COLOR);
        this.USER_PASSWORD_LABEL.setFont(this.FRAME_FONT);
        
        this.LOGIN_BUTTON.setForeground(this.FONT_COLOR);
        this.LOGIN_BUTTON.setFont(this.FRAME_FONT);
                
        this.SERVER_IP_FIELD.setFont(this.FRAME_FONT);
        this.USERNAME_FIELD.setFont(this.FRAME_FONT);
        this.USER_PASSWORD_FIELD.setFont(this.FRAME_FONT);
    }
    
    public void dispose() {
        this.MAIN_FRAME.dispose();
    }
    
    /**
     * This function will initialize JFrame - MAIN_FRAME instance. Here
     * window size, window layout, resizability mode parameters will be
     * set.
     */
    private void initializeFrame() {
        initializePanel();
        frameToScreenCenter();
        setIcon();
        
        this.MAIN_FRAME.setUndecorated(this.NO_BORDER);
        this.MAIN_FRAME.setLayout(new BorderLayout());
        this.MAIN_FRAME.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        this.MAIN_FRAME.add(this.MAIN_PANEL, BorderLayout.CENTER);
        
        this.MAIN_FRAME.setSize(this.FRAME_WIDTH, this.FRAME_HEIGHT);
        this.MAIN_FRAME.setVisible(this.VISIBLE);
        this.MAIN_FRAME.setResizable(this.NOT_RESIZABLE);
    }
    
    /**
     * This function will initialize main panel and will configure all
     * elements of the panel, like password field, username field,
     * server ipv4 field and login button. This function will be called
     * inside initializeFrame() function. The reason is, main panel
     * have to be configured and initialized before main frame, because
     * main panel will be hold by main frame and it is better to configre
     * all elements inside main panel before adding the last one to the
     * main frame.
     */
    private void initializePanel() {        
        if ((defaultIp = checkLastIp()) != null) {
            this.SERVER_IP_FIELD.setText(this.defaultIp);
        } else {
            this.SERVER_IP_FIELD.setText("127.0.0.1");
        }
        
        this.MAIN_PANEL.setBackground(this.LOGIN_BCKG_COLOR);
        this.MAIN_PANEL.setLayout(new GridBagLayout());
        
        this.GBC.fill = GridBagConstraints.HORIZONTAL;
        this.GBC.gridx = 0;
        this.GBC.gridy = 0;
        this.MAIN_PANEL.add(this.SERVER_IP_LABEL, this.GBC);

        this.GBC.fill = GridBagConstraints.HORIZONTAL;
        this.GBC.gridx = 1;
        this.GBC.gridy = 0;
        this.GBC.insets = new Insets(0, 0, 0, 0);
        this.MAIN_PANEL.add(this.SERVER_IP_FIELD, this.GBC);

        this.GBC.fill = GridBagConstraints.HORIZONTAL;
        this.GBC.gridx = 0;
        this.GBC.gridy = 1;
        this.MAIN_PANEL.add(this.USERNAME_LABEL, this.GBC);

        this.GBC.fill = GridBagConstraints.HORIZONTAL;
        this.GBC.gridx = 1;
        this.GBC.gridy = 1;
        this.GBC.insets = new Insets(0, 0, 0, 0);
        this.MAIN_PANEL.add(this.USERNAME_FIELD, this.GBC);

        this.GBC.fill = GridBagConstraints.HORIZONTAL;
        this.GBC.gridx = 0;
        this.GBC.gridy = 2;
        this.MAIN_PANEL.add(this.USER_PASSWORD_LABEL, this.GBC);

        this.GBC.fill = GridBagConstraints.HORIZONTAL;
        this.GBC.gridx = 1;
        this.GBC.gridy = 2;
        this.GBC.insets = new Insets(0, 0, 0, 0);
        this.MAIN_PANEL.add(this.USER_PASSWORD_FIELD, this.GBC);

        this.GBC.fill = GridBagConstraints.HORIZONTAL;
        this.GBC.gridx = 1;
        this.GBC.gridy = 3;
        this.GBC.insets = new Insets(0, 0, 0, 0);
        this.MAIN_PANEL.add(this.LOGIN_BUTTON, this.GBC);
    }
}