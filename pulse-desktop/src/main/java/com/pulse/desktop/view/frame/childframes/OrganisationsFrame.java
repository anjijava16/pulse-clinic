package com.pulse.desktop.view.frame.childframes;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;
import com.pulse.desktop.controller.table.TableService;
import com.pulse.desktop.controller.AddOrganisationListener;
import com.pulse.desktop.controller.DeleteOrganisationListener;
import com.pulse.desktop.controller.UpdateOrganisationsListener;
import com.pulse.model.constant.Privelegy;


/**
 *
 * @author befast
 */
public class OrganisationsFrame extends AbstractTabledChildFrame {
    
    private final TableService.TableHolder TABLE_HOLDER = TableService.INSTANCE.buildTable(TableService.ORGANISATION_TABLE);
    private final List<JComponent> TOOLBAR_BTN_LIST = new ArrayList<>(3);
    
    private final JTextField ORGANISATION_NAME_FIELD = new JTextField(this.maxChars);  
    
    private final JButton ADD_ORGANISATION_BUTTON = new JButton(new ImageIcon("./pic/form_creation.png"));
    private final JButton DELETE_ORGANISATION_BUTTON = new JButton("", new ImageIcon("./pic/form_removing.png"));
    private final JButton UPDATE_BUTTON = new JButton("", new ImageIcon("./pic/update.png"));    
    
    private final Privelegy privelegy = Privelegy.Organisation;
    
    private void buildActionListeners() {
        AddOrganisationListener aol = new AddOrganisationListener(this.privelegy, this.TABLE_HOLDER, this.ORGANISATION_NAME_FIELD);
        DeleteOrganisationListener dol = new DeleteOrganisationListener(this.privelegy, this.TABLE_HOLDER);
        UpdateOrganisationsListener uol = new UpdateOrganisationsListener(this.privelegy, this.TABLE_HOLDER);
        
        this.ADD_ORGANISATION_BUTTON.addActionListener(aol);
        this.DELETE_ORGANISATION_BUTTON.addActionListener(dol);
        this.UPDATE_BUTTON.addActionListener(uol);
    }
    
    public OrganisationsFrame() {
        super.setPrivelegy(privelegy);
        super.setTableHolder(this.TABLE_HOLDER);
        
        buildActionListeners();
        
        this.ADD_ORGANISATION_BUTTON.setToolTipText("Добавить");
        this.DELETE_ORGANISATION_BUTTON.setToolTipText("Удалить");
        this.UPDATE_BUTTON.setToolTipText("Обновить");
        
        this.TOOLBAR_BTN_LIST.add(this.UPDATE_BUTTON); 
        this.TOOLBAR_BTN_LIST.add(this.ORGANISATION_NAME_FIELD);     
        this.TOOLBAR_BTN_LIST.add(this.ADD_ORGANISATION_BUTTON);
        this.TOOLBAR_BTN_LIST.add(this.DELETE_ORGANISATION_BUTTON);        
        
        super.setToolbarBtnList(TOOLBAR_BTN_LIST);        
        super.initializeFrame();
    }
}
