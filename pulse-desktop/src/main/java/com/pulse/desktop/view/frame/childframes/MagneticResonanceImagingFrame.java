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
import com.pulse.desktop.controller.MarkAsViewedListener;
import com.pulse.desktop.controller.MoveToStationaryListener;
import com.pulse.desktop.controller.PatientTypeFilterListener;
import com.pulse.desktop.controller.SaveSecondVisitListener;
import com.pulse.desktop.controller.SearchByDateListener;
import com.pulse.desktop.controller.SearchFieldFocusListener;
import com.pulse.desktop.controller.ViewAnalysListener;
import com.pulse.desktop.controller.ViewPatientAppointmentListener;
import com.pulse.desktop.controller.ViewPatientRecordListener;
import com.pulse.model.constant.Privelegy;


public class MagneticResonanceImagingFrame extends AbstractTabledChildFrame {
    
    private final TableService.TableHolder TABLE_HOLDER = TableService.INSTANCE.buildTable(TableService.SIMPLE_TABLE);
    private final List<JComponent> TOOLBAR_BTN_LIST = new ArrayList<>(10);    
    private final JTextField SEARCH_PATTERN_FIELD = new JTextField(this.maxChars);    
    private final DefaultComboBoxModel<String> FILTER_BOX_MODEL = FilterBoxBuilder.build();
    private final JComboBox<String> PATIENT_LIST_BOX = new JComboBox<>(FILTER_BOX_MODEL);
    private final JButton VIEW_ANALYS_BUTTON = new JButton(new ImageIcon("./pic/view.png"));
    private final JButton SEARCH_PATTERN_BUTTON = new JButton("", new ImageIcon("./pic/update.png"));
    private final JButton DATE_FILTER_BUTTON = new JButton("", new ImageIcon("./pic/update.png"));    
    private final JDatePickerImpl SRCH_DATE_PICKER = new JDatePickerImpl(new JDatePanelImpl(null));  
    
    private final SimpleDateFormat VISIT_DATE_FORMATTER = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd.MM.yyyy");
    
    private final Privelegy privelegy = Privelegy.MagneticResonanceImaging;
    
    private final JButton VIEW_PATIENT_FORM_BUTTON = new JButton("", new ImageIcon("./pic/patient_form.png"));
    private final JButton VIEW_PATIENT_APPOINTMENT_BUTTON = new JButton("", new ImageIcon("./pic/pills.png"));
    private final JButton SAVE_SECOND_VISIT_BUTTON = new JButton("", new ImageIcon("./pic/second_visit.png"));
    private final JButton MARK_AS_HANDLED_BUTTON = new JButton("", new ImageIcon("./pic/handled.png"));
    
    private final JButton MOVE_TO_HOSPITAL_BUTTON = new JButton("", new ImageIcon("./pic/hospital.png"));
    
    public void disableAccess() {
        this.MARK_AS_HANDLED_BUTTON.setEnabled(false);
        this.MOVE_TO_HOSPITAL_BUTTON.setEnabled(false);
    }
    
    private void buildActionListeners() {
        MoveToStationaryListener mtsl = new MoveToStationaryListener(privelegy, this.TABLE_HOLDER);        
        this.MOVE_TO_HOSPITAL_BUTTON.addActionListener(mtsl);
        
        PatientTypeFilterListener ptfl = new PatientTypeFilterListener(
                privelegy, this.PATIENT_LIST_BOX, this.TABLE_HOLDER, this.FORMATTER, this.SRCH_DATE_PICKER
        );
        
        CommonSearchListener csl = new CommonSearchListener(
                privelegy, this.TABLE_HOLDER, this.SEARCH_PATTERN_FIELD, this.FORMATTER
        );
        
        SearchByDateListener sbdl = new SearchByDateListener(
                privelegy, this.PATIENT_LIST_BOX, this.SRCH_DATE_PICKER, this.TABLE_HOLDER, this.FORMATTER
        );
        
        ViewAnalysListener val = new ViewAnalysListener(
                privelegy, this.TABLE_HOLDER, this.VISIT_DATE_FORMATTER
        );
        
        ViewPatientRecordListener vpfl = new ViewPatientRecordListener(privelegy, this.TABLE_HOLDER); 
        
        SearchFieldFocusListener sfl = new SearchFieldFocusListener();
        
        ViewPatientAppointmentListener vpal = new ViewPatientAppointmentListener(privelegy, this.TABLE_HOLDER);
        
        SaveSecondVisitListener ssvl = new SaveSecondVisitListener(privelegy, this.TABLE_HOLDER);
        MarkAsViewedListener mavl = new MarkAsViewedListener(privelegy, this.TABLE_HOLDER);
        
        this.MARK_AS_HANDLED_BUTTON.addActionListener(mavl);
        this.SEARCH_PATTERN_FIELD.addFocusListener(sfl);
        this.VIEW_PATIENT_FORM_BUTTON.addActionListener(vpfl);
        this.PATIENT_LIST_BOX.addActionListener(ptfl);
        this.SEARCH_PATTERN_BUTTON.addActionListener(csl);
        this.DATE_FILTER_BUTTON.addActionListener(sbdl);
        this.VIEW_ANALYS_BUTTON.addActionListener(val);
        this.VIEW_PATIENT_APPOINTMENT_BUTTON.addActionListener(vpal);
        this.SAVE_SECOND_VISIT_BUTTON.addActionListener(ssvl);
    }
        
    public MagneticResonanceImagingFrame() {
        super.setPrivelegy(privelegy);
        super.setTableHolder(this.TABLE_HOLDER);
        
        buildActionListeners();
        
        this.SEARCH_PATTERN_FIELD.setText("Поиск");
                
        this.VIEW_PATIENT_FORM_BUTTON.setToolTipText("Анкета");
        this.VIEW_PATIENT_APPOINTMENT_BUTTON.setToolTipText("Назначения");
        this.SAVE_SECOND_VISIT_BUTTON.setToolTipText("Приди проверься");
        
        this.SEARCH_PATTERN_BUTTON.setToolTipText("Поиск");
        this.DATE_FILTER_BUTTON.setToolTipText("Поиск");
        this.SRCH_DATE_PICKER.getJFormattedTextField().setText(this.FORMATTER.format(new Date()));
        
        this.MARK_AS_HANDLED_BUTTON.setToolTipText("Осмотрен");
        this.MOVE_TO_HOSPITAL_BUTTON.setToolTipText("Направить в стационар");
        
        this.TOOLBAR_BTN_LIST.add(this.MOVE_TO_HOSPITAL_BUTTON);
        this.TOOLBAR_BTN_LIST.add(this.MARK_AS_HANDLED_BUTTON);
//        this.TOOLBAR_BTN_LIST.add(this.VIEW_ANALYS_BUTTON);
        this.TOOLBAR_BTN_LIST.add(this.VIEW_PATIENT_FORM_BUTTON);
        this.TOOLBAR_BTN_LIST.add(this.VIEW_PATIENT_APPOINTMENT_BUTTON);
        this.TOOLBAR_BTN_LIST.add(this.SAVE_SECOND_VISIT_BUTTON);
        this.TOOLBAR_BTN_LIST.add(this.PATIENT_LIST_BOX);
        this.TOOLBAR_BTN_LIST.add(this.SEARCH_PATTERN_FIELD);     
        this.TOOLBAR_BTN_LIST.add(this.SEARCH_PATTERN_BUTTON);
        this.TOOLBAR_BTN_LIST.add(this.SRCH_DATE_PICKER);
        this.TOOLBAR_BTN_LIST.add(this.DATE_FILTER_BUTTON);
                
        super.setToolbarBtnList(TOOLBAR_BTN_LIST);        
        super.initializeFrame();
    }        
}
