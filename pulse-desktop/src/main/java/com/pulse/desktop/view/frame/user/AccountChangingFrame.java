package com.pulse.desktop.view.frame.user;

import com.pulse.desktop.controller.UpdateUserListener;
import com.pulse.desktop.controller.table.TableService.TableHolder;
import com.pulse.model.constant.Privelegy;
import java.awt.BorderLayout;
import javax.swing.JButton;

/**
 *
 * @author befast
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
        intializeFrame();
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
        final UpdateUserListener uul = new UpdateUserListener(Privelegy.None, this.holder);
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
        this.USER_INFO_PANEL.getUsernameField().setEditable(false);
    }
}
