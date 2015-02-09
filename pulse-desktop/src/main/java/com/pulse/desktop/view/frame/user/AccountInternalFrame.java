package com.pulse.desktop.view.frame.user;

import com.pulse.desktop.controller.AddUserListener;
import com.pulse.desktop.controller.DeleteUserListener;
import com.pulse.desktop.controller.RefreshUsersListener;
import com.pulse.desktop.controller.OpenUpdateUserFrameListener;
import com.pulse.desktop.controller.table.TableService;
import com.pulse.desktop.controller.table.TableService.TableHolder;
import com.pulse.model.constant.Privelegy;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author befast
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
        intializeFrame();
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
    public void intializeFrame() {
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
        final RefreshUsersListener rul = new RefreshUsersListener(Privelegy.None, this.TABLE_HOLDER);
        final AddUserListener aul = new AddUserListener(Privelegy.None, this.TABLE_HOLDER);
        final OpenUpdateUserFrameListener suufl = new OpenUpdateUserFrameListener(Privelegy.None, this.TABLE_HOLDER);
        final DeleteUserListener dul = new DeleteUserListener(Privelegy.None, this.TABLE_HOLDER);
        
        this.UPDATE_USERS_BUTTON.addActionListener(rul);
        this.ADD_USER_BUTTON.addActionListener(aul);        
        this.EDIT_USER_BUTTON.addActionListener(suufl);        
        this.DELETE_USER_BUTTON.addActionListener(dul);
    }
}