package com.pulse.desktop.view.frame.childframes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import com.pulse.desktop.controller.table.TableService;
import com.pulse.desktop.controller.SearchNextVisitByDateListener;
import com.pulse.model.constant.Privelegy;

/**
 * 
 * @author Vladimir Shin
 */
public class NextVisitFrame extends AbstractTabledChildFrame {
    
    private final TableService.TableHolder TABLE_HOLDER = TableService.INSTANCE.buildTable(TableService.NEXT_VISIT_TABLE);
    private final List<JComponent> TOOLBAR_BTN_LIST = new ArrayList<>(10);    
     
    private final JButton DATE_FILTER_BUTTON = new JButton("", new ImageIcon("./pic/update.png"));    
    private final JDatePickerImpl SRCH_DATE_PICKER = new JDatePickerImpl(new JDatePanelImpl(null));  
    
    private final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd.MM.yyyy");
    
    private final Privelegy privelegy = Privelegy.NextVisit;
    
    private void buildActionListeners() {
        SearchNextVisitByDateListener sbdl = new SearchNextVisitByDateListener(
                privelegy, this.SRCH_DATE_PICKER, this.TABLE_HOLDER, this.FORMATTER
        );
        
        this.DATE_FILTER_BUTTON.addActionListener(sbdl);
    }
    
    public NextVisitFrame() {
        super.setPrivelegy(privelegy);
        super.setTableHolder(this.TABLE_HOLDER);
        
        buildActionListeners();
        
        this.DATE_FILTER_BUTTON.setToolTipText("Поиск");
        
        this.SRCH_DATE_PICKER.getJFormattedTextField().setText(this.FORMATTER.format(new Date()));
        
        this.TOOLBAR_BTN_LIST.add(this.SRCH_DATE_PICKER);
        this.TOOLBAR_BTN_LIST.add(this.DATE_FILTER_BUTTON);
                
        super.setToolbarBtnList(TOOLBAR_BTN_LIST);        
        super.initializeFrame();
    }        
}
