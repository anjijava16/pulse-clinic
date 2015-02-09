package com.pulse.desktop.view.frame.user;

import com.pulse.desktop.controller.CreateUserListener;
import com.pulse.desktop.controller.table.TableService;
import com.pulse.desktop.controller.table.TableService.TableHolder;
import com.pulse.model.constant.Privelegy;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JButton;

/**
 *
 * @author befast
 */
public final class AccountCreationFrame 
    extends AbstractInternalFrame {
                   
    private final UserInfoPanel USER_INFO_PANEL = new UserInfoPanel();
    
    private final JButton SAVE_BUTTON = new JButton("Сохранить");
        
    private TableHolder holder;
    
    public AccountCreationFrame(String title, TableHolder holder) {
        this.frame.setTitle(title);
        this.holder = holder;  
                
        setAllSettings();
        intializeFrame();
        addAllActionListeners();
    }    
    
    public void clear() {
        this.USER_INFO_PANEL.clearAllFields();
        this.frame.setVisible(false);
    }
    
    @Override
    public void addAllActionListeners() {
        final CreateUserListener cul = new CreateUserListener(Privelegy.None, this.holder);
        cul.setBirthdayField(USER_INFO_PANEL.getBirthdayField());
        cul.setFamilyNameField(USER_INFO_PANEL.getFamilyNameField());
        cul.setFatherNameField(USER_INFO_PANEL.getFatherNameField());
        cul.setNameField(USER_INFO_PANEL.getNameField());
        cul.setPasswordField(USER_INFO_PANEL.getPasswordField());
        cul.setPrivelegyField(USER_INFO_PANEL.getPrivelegyField());
        cul.setUsernameField(USER_INFO_PANEL.getUsernameField());

        this.SAVE_BUTTON.addActionListener(cul);
    }
        
    @Override
    public void initializeRootPanel() {
        this.ROOT_PANEL.setVisible(true);
        this.ROOT_PANEL.setLayout(new BorderLayout());
        
        this.ROOT_PANEL.add(this.USER_INFO_PANEL.getRootPanel(), BorderLayout.CENTER);
        this.ROOT_PANEL.add(this.SAVE_BUTTON, BorderLayout.SOUTH);
    }
    
    @Override
    public void intializeFrame() {
        initializeRootPanel();

        this.width = 310;
        this.height = 305;
        
        this.frame.setLayout(new BorderLayout());
        this.frame.setSize(this.width, this.height);

        this.frame.add(this.ROOT_PANEL);
    }

    @Override
    public void setAllSettings() {
    }
}