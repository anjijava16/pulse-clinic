package com.pulse.desktop.view.frame.childframes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import com.pulse.desktop.controller.table.TableService;
import com.pulse.desktop.controller.SearchByDepartmentCheckBoxListener;
import com.pulse.desktop.controller.SearchDepartmentsStatisticListener;
import com.pulse.model.User;
import com.pulse.model.constant.Privelegy;


/**
 * 
 * @author befast
 */
public class BookKeepingFrame extends AbstractTabledChildFrame {
    
    private final TableService.TableHolder TABLE_HOLDER = TableService.INSTANCE.buildTable(TableService.BOOK_KEEPING_TABLE);
    private final List<JComponent> TOOLBAR_BTN_LIST = new ArrayList<>(10);    
    
    private final Privelegy privelegy = Privelegy.BookKeeping;
    
    private final JCheckBox SEARCH_BY_DEPARTMENT = new JCheckBox("");
    private final DefaultComboBoxModel<String> DOCTORS_LIST_MODEL = new DefaultComboBoxModel<>();
    private final JComboBox<String> DOCTORS_LIST = new JComboBox<>(this.DOCTORS_LIST_MODEL);
    
    private final DefaultComboBoxModel<String> DEPARTMENTS_LIST_MODEL = new DefaultComboBoxModel<>();
    private final JComboBox<String> DEPARTMENTS_LIST = new JComboBox<>(this.DEPARTMENTS_LIST_MODEL);
    
    private final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd.MM.yyyy");
    private final JDatePickerImpl SEARCH_FROM_DATE_PICKER = new JDatePickerImpl(new JDatePanelImpl(null));
    private final JDatePickerImpl SEARCH_UNTIL_DATE_PICKER = new JDatePickerImpl(new JDatePanelImpl(null));
    
    private final JButton SEARCH_BUTTON = new JButton("", new ImageIcon("./pic/update.png"));
        
    private void initialize() {        
        this.SEARCH_FROM_DATE_PICKER.getJFormattedTextField().setText(this.FORMATTER.format(new Date()));
        this.SEARCH_UNTIL_DATE_PICKER.getJFormattedTextField().setText(this.FORMATTER.format(new Date()));
        
        this.DEPARTMENTS_LIST.setEnabled(false);
        
        Privelegy privelegies[] = Privelegy.values();
        for (Privelegy privelegy : privelegies) {
            if (privelegy.isDepartment()) {
                this.DEPARTMENTS_LIST_MODEL.addElement(privelegy.getName());
            }
        }
        
        SearchByDepartmentCheckBoxListener sbdcbl = new SearchByDepartmentCheckBoxListener(
                this.privelegy, this.SEARCH_BY_DEPARTMENT, this.DOCTORS_LIST, this.DEPARTMENTS_LIST
        );
        this.SEARCH_BY_DEPARTMENT.addActionListener(sbdcbl);
        
        SearchDepartmentsStatisticListener sdsl = new SearchDepartmentsStatisticListener(
                this.FORMATTER, 
                this.privelegy, 
                this.TABLE_HOLDER, 
                this.SEARCH_BY_DEPARTMENT, 
                this.DOCTORS_LIST, 
                this.DEPARTMENTS_LIST, 
                this.SEARCH_FROM_DATE_PICKER, 
                this.SEARCH_UNTIL_DATE_PICKER
        );
        this.SEARCH_BUTTON.addActionListener(sdsl);
    }
            
    public BookKeepingFrame() {
        super.setPrivelegy(privelegy);
        super.setTableHolder(this.TABLE_HOLDER);
        
        initialize();
                
        this.SEARCH_BUTTON.setToolTipText("Поиск");
        
        this.TOOLBAR_BTN_LIST.add(this.DOCTORS_LIST);
        this.TOOLBAR_BTN_LIST.add(this.SEARCH_BY_DEPARTMENT);
        this.TOOLBAR_BTN_LIST.add(this.DEPARTMENTS_LIST);
        this.TOOLBAR_BTN_LIST.add(this.SEARCH_FROM_DATE_PICKER);
        this.TOOLBAR_BTN_LIST.add(this.SEARCH_UNTIL_DATE_PICKER);
        this.TOOLBAR_BTN_LIST.add(this.SEARCH_BUTTON);
                
        super.setToolbarBtnList(TOOLBAR_BTN_LIST);        
        super.initializeFrame();
    }       
        
    public void addDoctor(User user) {
        if (user == null) return;
        if (user.getPrivelegy() <= 0) return;
        
        if (Privelegy.findById(user.getPrivelegy()).isDepartment()) {
            this.DOCTORS_LIST_MODEL.addElement(user.getNfp());
        }
    }
}