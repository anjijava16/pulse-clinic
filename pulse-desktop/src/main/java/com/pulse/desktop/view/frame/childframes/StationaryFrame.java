package com.pulse.desktop.view.frame.childframes;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import com.pulse.desktop.controller.table.TableService;
import com.pulse.desktop.controller.ReleasePatientListener;
import com.pulse.desktop.controller.UpdateStationaryVisitListener;
import com.pulse.model.constant.Privelegy;


public class StationaryFrame extends AbstractTabledChildFrame {
    
    private final TableService.TableHolder TABLE_HOLDER = TableService.INSTANCE.buildTable(TableService.STATIONARY_TABLE);
    private final List<JComponent> TOOLBAR_BTN_LIST = new ArrayList<>(10);    

    private Privelegy privelegy = Privelegy.Stationary;
    
    private final JButton SIGN_OUT_BUTTON = new JButton("", new ImageIcon("./pic/sign_out.png"));
    private final JButton UPDATE_BUTTON = new JButton("", new ImageIcon("./pic/update.png"));
    
    private void buildActionListeners() {
        UpdateStationaryVisitListener usvl = new UpdateStationaryVisitListener(this.privelegy, this.TABLE_HOLDER);
        ReleasePatientListener rpl = new ReleasePatientListener(this.privelegy, this.TABLE_HOLDER);
        
        this.SIGN_OUT_BUTTON.addActionListener(rpl);
        this.UPDATE_BUTTON.addActionListener(usvl);
    }
        
    public StationaryFrame() {
        super.setPrivelegy(privelegy);
        super.setTableHolder(this.TABLE_HOLDER);
        
        buildActionListeners();
        
        this.SIGN_OUT_BUTTON.setToolTipText("Выписать");                
        this.UPDATE_BUTTON.setToolTipText("Обновить");
        
        this.TOOLBAR_BTN_LIST.add(this.UPDATE_BUTTON);
        this.TOOLBAR_BTN_LIST.add(this.SIGN_OUT_BUTTON);
                
        super.setToolbarBtnList(TOOLBAR_BTN_LIST);        
        super.initializeFrame();
    }        
}