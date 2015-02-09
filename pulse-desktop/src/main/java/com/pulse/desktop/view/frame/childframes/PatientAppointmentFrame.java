package com.pulse.desktop.view.frame.childframes;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import com.pulse.desktop.controller.table.TableService;
import com.pulse.desktop.controller.CreatePatientAppointmentListener;
import com.pulse.desktop.controller.DeletePatientAppointmentListener;
import com.pulse.desktop.controller.OpenPatientAppointmentListener;
import com.pulse.model.Patient;
import com.pulse.model.constant.Privelegy;

/**
 *
 * @author Vladimir Shin <vladimir.shin@gmail.com>
 */
public class PatientAppointmentFrame extends AbstractTabledChildFrame {
    
    private final TableService.TableHolder TABLE_HOLDER = TableService.INSTANCE.buildTable(TableService.PATIENT_RECORD_TABLE);
    private final List<JComponent> TOOLBAR_BTN_LIST = new ArrayList<>(10);    
    private final JTextField SEARCH_PATTERN_FIELD = new JTextField(this.maxChars);
    private final JDatePickerImpl SRCH_DATE_PICKER = new JDatePickerImpl(new JDatePanelImpl(null));  
    
    private final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd.MM.yyyy");
    
    private final Privelegy privelegy = Privelegy.PatientAppointment;
    
    private final JButton CREATE_PATIENT_RECORD_BUTTON = new JButton("", new ImageIcon("./pic/form_creation.png"));
    private final JButton REMOVE_PATIENT_RECORD_BUTTON = new JButton("", new ImageIcon("./pic/form_removing.png"));
    private final JButton VIEW_PATIENT_RECORD_BUTTON = new JButton("", new ImageIcon("./pic/view.png"));
    
    private Patient patient;
    
    private void buildActionListeners() {
        CreatePatientAppointmentListener cerl = new CreatePatientAppointmentListener(this.privelegy, this.TABLE_HOLDER);
        DeletePatientAppointmentListener dprc = new DeletePatientAppointmentListener(this.privelegy, this.TABLE_HOLDER);
        OpenPatientAppointmentListener oprl = new OpenPatientAppointmentListener(this.privelegy, this.TABLE_HOLDER);
        
        this.CREATE_PATIENT_RECORD_BUTTON.addActionListener(cerl);
        this.REMOVE_PATIENT_RECORD_BUTTON.addActionListener(dprc);
        this.VIEW_PATIENT_RECORD_BUTTON.addActionListener(oprl);
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }    
    
    public PatientAppointmentFrame() {
        super.setPrivelegy(privelegy);
        super.setTableHolder(this.TABLE_HOLDER);
        
        buildActionListeners();
        
        this.SEARCH_PATTERN_FIELD.setText("Поиск");
        
        this.CREATE_PATIENT_RECORD_BUTTON.setToolTipText("Создать");        
        this.REMOVE_PATIENT_RECORD_BUTTON.setToolTipText("Удалить");
        this.VIEW_PATIENT_RECORD_BUTTON.setToolTipText("Просмотр");
        
        this.SRCH_DATE_PICKER.getJFormattedTextField().setText(this.FORMATTER.format(new Date()));
        
        this.TOOLBAR_BTN_LIST.add(this.CREATE_PATIENT_RECORD_BUTTON);
        this.TOOLBAR_BTN_LIST.add(this.REMOVE_PATIENT_RECORD_BUTTON);     
        this.TOOLBAR_BTN_LIST.add(this.VIEW_PATIENT_RECORD_BUTTON);
                
        super.setToolbarBtnList(TOOLBAR_BTN_LIST);        
        super.initializeFrame();
    }
}

