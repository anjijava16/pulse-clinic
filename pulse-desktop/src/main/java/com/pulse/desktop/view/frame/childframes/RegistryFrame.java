package com.pulse.desktop.view.frame.childframes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import com.pulse.desktop.controller.table.TableService;
import com.pulse.desktop.controller.builder.FilterBoxBuilder;
import com.pulse.desktop.controller.CommonSearchListener;
import com.pulse.desktop.controller.ShowCreatePatientListener;
import com.pulse.desktop.controller.CreateVisitListener;
import com.pulse.desktop.controller.ShowDeletePatientListener;
import com.pulse.desktop.controller.DeleteVisitListener;
import com.pulse.desktop.controller.EditPatientListener;
import com.pulse.desktop.controller.PatientTypeFilterListener;
import com.pulse.desktop.controller.SearchByDateListener;
import com.pulse.desktop.controller.SearchFieldFocusListener;
import com.pulse.model.constant.Privelegy;


/**
 * 
 * @author befast
 */
public class RegistryFrame extends AbstractTabledChildFrame {
    
    private final TableService.TableHolder TABLE_HOLDER = TableService.INSTANCE.buildTable(TableService.SIMPLE_TABLE);
    private final List<JComponent> TOOLBAR_BTN_LIST = new ArrayList<>(10);    
    private final JTextField SEARCH_PATTERN_FIELD = new JTextField(this.maxChars);    
    private final DefaultComboBoxModel<String> FILTER_BOX_MODEL = FilterBoxBuilder.build();
    private final JComboBox<String> PATIENT_LIST_BOX = new JComboBox<>(FILTER_BOX_MODEL);

    private final JButton SEARCH_PATTERN_BUTTON = new JButton("", new ImageIcon("./pic/update.png"));
    private final JButton DATE_FILTER_BUTTON = new JButton("", new ImageIcon("./pic/update.png"));    
    private final JDatePickerImpl SRCH_DATE_PICKER = new JDatePickerImpl(new JDatePanelImpl(null));  
    
    private final SimpleDateFormat VISIT_DATE_FORMATTER = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd.MM.yyyy");
    
    private final Privelegy privelegy = Privelegy.Registratur;
    
    private final JButton CREATE_PATIENT_BUTTON = new JButton("", new ImageIcon("./pic/add_user.png"));
    private final JButton UPDATE_PATIENT_BUTTON = new JButton("", new ImageIcon("./pic/edit_user.png"));
    private final JButton DELETE_PATIENT_BUTTON = new JButton("", new ImageIcon("./pic/delete_user.png"));
    private final JButton CREATE_VISIT_BUTTON = new JButton("", new ImageIcon("./pic/add_visit.png"));
    private final JButton DELETE_VISIT_BUTTON = new JButton("", new ImageIcon("./pic/remove_visit.png"));
    
    public void disableAccess() {
        this.DELETE_PATIENT_BUTTON.setEnabled(false);
        this.DELETE_VISIT_BUTTON.setEnabled(false);
    }
        
    private void buildActionListeners() {
        PatientTypeFilterListener ptfl = new PatientTypeFilterListener(
                privelegy, this.PATIENT_LIST_BOX, this.TABLE_HOLDER, this.FORMATTER, this.SRCH_DATE_PICKER
        );
                        
        SearchByDateListener sbdl = new SearchByDateListener(
                privelegy, this.PATIENT_LIST_BOX, this.SRCH_DATE_PICKER, this.TABLE_HOLDER, this.FORMATTER
        );
        
        SearchFieldFocusListener sfl = new SearchFieldFocusListener();
        
        ShowCreatePatientListener cpl = new ShowCreatePatientListener(privelegy, this.TABLE_HOLDER);
        EditPatientListener epl = new EditPatientListener(privelegy, this.TABLE_HOLDER);        
        ShowDeletePatientListener dpl = new ShowDeletePatientListener(privelegy, this.TABLE_HOLDER);        
        
        CreateVisitListener cvl = new CreateVisitListener(privelegy, this.TABLE_HOLDER);
        DeleteVisitListener dvl = new DeleteVisitListener(privelegy, this.TABLE_HOLDER);
        
        this.SEARCH_PATTERN_FIELD.addFocusListener(sfl);
        
        this.DELETE_PATIENT_BUTTON.addActionListener(dpl);
        this.CREATE_PATIENT_BUTTON.addActionListener(cpl);
        this.UPDATE_PATIENT_BUTTON.addActionListener(epl);
        
        this.CREATE_VISIT_BUTTON.addActionListener(cvl);
        this.DELETE_VISIT_BUTTON.addActionListener(dvl);
        
        this.PATIENT_LIST_BOX.addActionListener(ptfl);
        
        CommonSearchListener csl = new CommonSearchListener(
                privelegy, this.TABLE_HOLDER, this.SEARCH_PATTERN_FIELD, this.FORMATTER
        );
        this.SEARCH_PATTERN_BUTTON.addActionListener(csl);
        this.DATE_FILTER_BUTTON.addActionListener(sbdl);
    }
    
    public void hideRemovePatientButton() {
        this.DELETE_PATIENT_BUTTON.setEnabled(false);
    }
    
    public RegistryFrame() {
        super.setPrivelegy(privelegy);
        super.setTableHolder(this.TABLE_HOLDER);
        
        buildActionListeners();
        
        this.SEARCH_PATTERN_FIELD.setText("Поиск");
        
        this.CREATE_VISIT_BUTTON.setToolTipText("Создать приход");
        
        this.CREATE_PATIENT_BUTTON.setToolTipText("Создать пациента");
        this.UPDATE_PATIENT_BUTTON.setToolTipText("Изменить пациента");
        this.DELETE_PATIENT_BUTTON.setToolTipText("Удалить пациента");
        this.DELETE_VISIT_BUTTON.setToolTipText("Удалить приход");
        
        this.SEARCH_PATTERN_BUTTON.setToolTipText("Поиск");
        this.DATE_FILTER_BUTTON.setToolTipText("Поиск");
        
        this.SRCH_DATE_PICKER.getJFormattedTextField().setText(this.FORMATTER.format(new Date()));
        
        this.TOOLBAR_BTN_LIST.add(this.CREATE_PATIENT_BUTTON);
        this.TOOLBAR_BTN_LIST.add(this.UPDATE_PATIENT_BUTTON);
        this.TOOLBAR_BTN_LIST.add(this.DELETE_PATIENT_BUTTON);
        this.TOOLBAR_BTN_LIST.add(this.CREATE_VISIT_BUTTON);
        this.TOOLBAR_BTN_LIST.add(this.DELETE_VISIT_BUTTON);
        this.TOOLBAR_BTN_LIST.add(this.PATIENT_LIST_BOX);
        this.TOOLBAR_BTN_LIST.add(this.SEARCH_PATTERN_FIELD);     
        this.TOOLBAR_BTN_LIST.add(this.SEARCH_PATTERN_BUTTON);
        this.TOOLBAR_BTN_LIST.add(this.SRCH_DATE_PICKER);
        this.TOOLBAR_BTN_LIST.add(this.DATE_FILTER_BUTTON);
        
        super.setToolbarBtnList(TOOLBAR_BTN_LIST);        
        super.initializeFrame();
    }
}