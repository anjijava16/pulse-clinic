package com.pulse.desktop.view.panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import com.pulse.desktop.view.util.HashBuilder;
import com.pulse.model.Patient;
import com.pulse.rest.client.PatientClient;
import java.io.IOException;
import java.text.SimpleDateFormat;


/**
 *
 * @author Vladimir Shin <vladimir.shin@gmail.com>
 */
public class PatientInfoPanel {
    
    private final PatientClient patientService = new PatientClient();
    private final JPanel ROOT_PANEL = new JPanel();
    
    private final JLabel FIRST_NAME_LABEL = new JLabel(" Имя: ");
    private final JLabel FAMILY_NAME_LABEL = new JLabel(" Фамилия: ");
    
    private final JLabel SEX_LABEL = new JLabel(" Пол: ");
    private final JLabel BIRTHDAY_LABEL = new JLabel(" Дата рождения: ");   
    private final JLabel EMAIL_LABEL = new JLabel(" Email: ");
    private final JLabel MOBILE_PHONE_LABEL = new JLabel(" Телефон: ");
    private final JLabel CLIENT_TYPE_LABEL = new JLabel(" Тип: ");
    
    private final String[] SEX_VALUES = {"Женский", "Мужской"};
    
    private final JComboBox<String> SEX_BOX = new JComboBox<>(this.SEX_VALUES);

    private final String[] CLIENT_TYPE = {"Обычный", "VIP"};
    
    private final int maxChars = 10;
    private final GridBagConstraints gbc = new GridBagConstraints();
    
    private final JTextField FIRST_NAME_FIELD = new JTextField(this.maxChars);
    private final JTextField FAMILY_NAME_FIELD = new JTextField(this.maxChars);
    private final JTextField EMAIL_FIELD = new JTextField(this.maxChars);
    private final JTextField MOBILE_PHONE_FIELD = new JTextField(this.maxChars);
    private final JComboBox<String> CLIENT_TYPE_BOX = new JComboBox<>(this.CLIENT_TYPE);
    private final JTextField BIRTHDAY_FIELD = new JTextField(this.maxChars);    
                
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    private Patient currentPatient;
    
    public PatientInfoPanel() {
        configurePanel();
    }
        
    public void allowAll() {
        this.FAMILY_NAME_FIELD.setEnabled(true);
        this.FIRST_NAME_FIELD.setEnabled(true);
        
        this.EMAIL_FIELD.setEnabled(true);
        this.MOBILE_PHONE_FIELD.setEnabled(true);
        this.CLIENT_TYPE_BOX.setEnabled(true);
        this.BIRTHDAY_FIELD.setEnabled(true);
        this.SEX_BOX.setEnabled(true);
        
        denyEdit();
    }
    
    public void denyAll() {
        this.FAMILY_NAME_FIELD.setEnabled(false);
        this.FIRST_NAME_FIELD.setEnabled(false);
        
        this.EMAIL_FIELD.setEnabled(false);
        this.MOBILE_PHONE_FIELD.setEnabled(false);
        this.CLIENT_TYPE_BOX.setEnabled(false);
        this.BIRTHDAY_FIELD.setEnabled(false);
        this.SEX_BOX.setEnabled(false);
    }
    
    public void allowEdit() {
        this.FAMILY_NAME_FIELD.setEditable(true);
        this.FIRST_NAME_FIELD.setEditable(true);
        
        this.EMAIL_FIELD.setEditable(true);
        this.MOBILE_PHONE_FIELD.setEditable(true);
        this.CLIENT_TYPE_BOX.setEditable(true);
        this.BIRTHDAY_FIELD.setEditable(true);
        this.SEX_BOX.setEditable(true);
    }
    
    public void denyEdit() {
        this.FAMILY_NAME_FIELD.setEditable(false);
        this.FIRST_NAME_FIELD.setEditable(false);
        
        this.EMAIL_FIELD.setEditable(false);
        this.MOBILE_PHONE_FIELD.setEditable(false);
        this.CLIENT_TYPE_BOX.setEditable(false);
        this.BIRTHDAY_FIELD.setEditable(false);
        this.SEX_BOX.setEditable(false);
    }
    
    public void clearAllData() {
        this.FAMILY_NAME_FIELD.setText("");
        this.FIRST_NAME_FIELD.setText("");
        
        this.EMAIL_FIELD.setText("");
        this.MOBILE_PHONE_FIELD.setText("");
        this.CLIENT_TYPE_BOX.setSelectedIndex(0);
        this.BIRTHDAY_FIELD.setText("1900-01-01");
        this.SEX_BOX.setSelectedIndex(0);
    }
    
    public Patient findAndShowBy(String fullname) throws IOException {
        final String token = HashBuilder.INSTANCE.token(fullname);
        final Patient patient = this.patientService.get(token);
        
        if (patient == null) return null;
        
        String[] nfpBuffer = patient.getNfp().split(" ");
        
        if (nfpBuffer.length < 2) return null;
        
        this.FAMILY_NAME_FIELD.setText(nfpBuffer[0]);
        this.FIRST_NAME_FIELD.setText(nfpBuffer[1]);
        
        this.EMAIL_FIELD.setText(patient.getEmail());
        this.MOBILE_PHONE_FIELD.setText(patient.getMobile());
        this.CLIENT_TYPE_BOX.setSelectedIndex(patient.getType());
        this.BIRTHDAY_FIELD.setText(sdf.format(patient.getBirthday()));
        this.SEX_BOX.setSelectedIndex(patient.getSex());
        
        return patient;
    }
    
    public JTextField getNameField() {
        return this.FIRST_NAME_FIELD;
    }
    
    public JTextField getFamilyNameField() {
        return this.FAMILY_NAME_FIELD;
    }
        
    public JTextField getEmailField() {
        return this.EMAIL_FIELD;
    }
    
    public JTextField getMobileField() {
        return this.MOBILE_PHONE_FIELD;
    }        
    
    public JComboBox<String> getClientTypeBox() {
        return this.CLIENT_TYPE_BOX;
    }
    
    public JTextField getBirthdayField() {
        return this.BIRTHDAY_FIELD;
    }
    
    public JComboBox<String> getSexBox() {
        return this.SEX_BOX;
    }
        
    private void configurePanel() {
        this.BIRTHDAY_FIELD.setText("1900-01-01");
        
        this.ROOT_PANEL.setLayout(new GridBagLayout());
        this.ROOT_PANEL.setVisible(true);
        this.ROOT_PANEL.setBorder(new TitledBorder(new EtchedBorder(), "Информация"));                               
                
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 0;
        this.gbc.gridy = 0;
        this.gbc.gridwidth = 1;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        this.ROOT_PANEL.add(this.FAMILY_NAME_LABEL, this.gbc);

        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 1;
        this.gbc.gridy = 0;
        this.gbc.gridwidth = 1;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        this.ROOT_PANEL.add(this.FAMILY_NAME_FIELD, this.gbc);
        
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 2;
        this.gbc.gridy = 0;
        this.gbc.gridwidth = 1;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        this.ROOT_PANEL.add(this.FIRST_NAME_LABEL, this.gbc);
        
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 3;
        this.gbc.gridy = 0;
        this.gbc.gridwidth = 1;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        this.ROOT_PANEL.add(this.FIRST_NAME_FIELD, this.gbc);
        
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 0;
        this.gbc.gridy = 1;
        this.gbc.gridwidth = 2;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        this.ROOT_PANEL.add(this.SEX_LABEL, this.gbc);

        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 1;
        this.gbc.gridy = 1;
        this.gbc.gridwidth = 1;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        this.ROOT_PANEL.add(this.SEX_BOX, this.gbc);
    
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 2;
        this.gbc.gridy = 1;
        this.gbc.gridwidth = 1;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        this.ROOT_PANEL.add(this.CLIENT_TYPE_LABEL, this.gbc);

        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 3;
        this.gbc.gridy = 1;
        this.gbc.gridwidth = 1;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        this.ROOT_PANEL.add(this.CLIENT_TYPE_BOX, this.gbc);
        
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 0;
        this.gbc.gridy = 2;
        this.gbc.gridwidth = 1;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        this.ROOT_PANEL.add(this.BIRTHDAY_LABEL, this.gbc);

        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 1;
        this.gbc.gridy = 2;
        this.gbc.gridwidth = 1;
        this.gbc.insets = new Insets(0, 0, 0, 0);        
        this.ROOT_PANEL.add(this.BIRTHDAY_FIELD, this.gbc);
                        
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 0;
        this.gbc.gridy = 3;
        this.gbc.gridwidth = 1;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        this.ROOT_PANEL.add(this.EMAIL_LABEL, this.gbc);

        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 1;
        this.gbc.gridy = 3;
        this.gbc.gridwidth = 1;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        this.ROOT_PANEL.add(this.EMAIL_FIELD, this.gbc);
        
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 2;
        this.gbc.gridy = 3;
        this.gbc.gridwidth = 1;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        this.ROOT_PANEL.add(this.MOBILE_PHONE_LABEL, this.gbc);

        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 3;
        this.gbc.gridy = 3;
        this.gbc.gridwidth = 1;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        this.ROOT_PANEL.add(this.MOBILE_PHONE_FIELD, this.gbc);
    }
    
    public JPanel getRootPanel() {
        return this.ROOT_PANEL;
    }
    
    public Patient getCurrentPatient() {
        return currentPatient;
    }

    public void setCurrentPatient(Patient currentPatient) {
        this.currentPatient = currentPatient;
    }    
}

