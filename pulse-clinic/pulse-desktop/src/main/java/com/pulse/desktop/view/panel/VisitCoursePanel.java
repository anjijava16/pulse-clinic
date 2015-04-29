/*
 * Copyright 2015 Vladimir Shin
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.pulse.desktop.view.panel;


import com.pulse.desktop.controller.service.ResultToolbarService;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import com.pulse.desktop.view.frame.childframes.assignment.AssignmentService;
import com.pulse.model.Organisation;
import com.pulse.model.User;
import com.pulse.model.constant.Privilege;
import com.pulse.rest.client.OrganisationClient;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class VisitCoursePanel {
    
    private final JPanel ROOT_PANEL = new JPanel();
    private GridBagConstraints gbc = new GridBagConstraints();
    
    private final JCheckBox LABORATORY_CHECKBOX = new JCheckBox(" Лаборатория ");
    private final JCheckBox ULTRASOUND_CHECKBOX = new JCheckBox(" УЗИ ");
    private final JCheckBox UROLOGY_CHECKBOX = new JCheckBox(" Урология ");
    private final JCheckBox GYNECOLOGY_CHECKBOX = new JCheckBox(" Гинекология ");
    private final JCheckBox STATIONARY_CHECKBOX = new JCheckBox(" Стационар ");
    
    private final JCheckBox SURGERY_CHECKBOX = new JCheckBox(" Хирургия ");
    private final JCheckBox OCULIST_CHECKBOX = new JCheckBox(" Окулист ");
    private final JCheckBox PHYSIOTHERAPY_CHECKBOX = new JCheckBox(" Физиотерапия ");
    private final JCheckBox THERAPY_CHECKBOX = new JCheckBox(" Терапевт ");
    private final JCheckBox ENDO_CHECKBOX = new JCheckBox(" Эндокринолог ");
    private final JCheckBox NEUROLOGIST_CHECKBOX = new JCheckBox(" Невропатолог ");
    private final JCheckBox VERTEBROLOGIST_CHECKBOX = new JCheckBox(" Вертебролог ");
    private final JCheckBox MRI_CHECKBOX = new JCheckBox(" МРТ ");  
    
    private final DefaultComboBoxModel<String> ULTRASOUND_DCM = new DefaultComboBoxModel<>();
    private final DefaultComboBoxModel<String> GYNECOLOGY_DCM = new DefaultComboBoxModel<>();
    private final DefaultComboBoxModel<String> UROLOGY_DCM = new DefaultComboBoxModel<>();
    private final DefaultComboBoxModel<String> STATIONARY_DCM = new DefaultComboBoxModel<>();

    private final DefaultComboBoxModel<String> SURGERY_DCM = new DefaultComboBoxModel<>();
    private final DefaultComboBoxModel<String> OCULIST_DCM = new DefaultComboBoxModel<>();
    private final DefaultComboBoxModel<String> PHYSIOTHERAPY_DCM = new DefaultComboBoxModel<>();
    private final DefaultComboBoxModel<String> THERAPY_DCM = new DefaultComboBoxModel<>();
    private final DefaultComboBoxModel<String> ENDO_DCM = new DefaultComboBoxModel<>();
    private final DefaultComboBoxModel<String> NEUROLOGIST_DCM = new DefaultComboBoxModel<>();
    private final DefaultComboBoxModel<String> VERTEBROLOGIST_DCM = new DefaultComboBoxModel<>();
    private final DefaultComboBoxModel<String> MRI_DCM = new DefaultComboBoxModel<>();
    
    private final JComboBox<String> ULTRASOUND_DOCTOR_NAME_BOX = new JComboBox<>(this.ULTRASOUND_DCM);
    private final JComboBox<String> GYNECOLOGY_DOCTOR_NAME_BOX = new JComboBox<>(this.GYNECOLOGY_DCM);
    private final JComboBox<String> UROLOGY_DOCTOR_NAME_BOX = new JComboBox<>(this.UROLOGY_DCM);
    private final JComboBox<String> STATIONARY_DOCTOR_NAME_BOX = new JComboBox<>(this.STATIONARY_DCM);
    
    private final JComboBox<String> SURGERY_DOCTOR_NAME_BOX = new JComboBox<>(this.SURGERY_DCM);
    private final JComboBox<String> OCULIST_DOCTOR_NAME_BOX = new JComboBox<>(this.OCULIST_DCM);
    private final JComboBox<String> PHYSIOTHERAPY_DOCTOR_NAME_BOX = new JComboBox<>(this.PHYSIOTHERAPY_DCM);
    private final JComboBox<String> THERAPY_DOCTOR_NAME_BOX = new JComboBox<>(this.THERAPY_DCM);
    private final JComboBox<String> ENDO_DOCTOR_NAME_BOX = new JComboBox<>(this.ENDO_DCM);
    private final JComboBox<String> NEUROLOGIST_DOCTOR_NAME_BOX = new JComboBox<>(this.NEUROLOGIST_DCM);
    private final JComboBox<String> VERTEBROLOGIST_DOCTOR_NAME_BOX = new JComboBox<>(this.VERTEBROLOGIST_DCM);
    private final JComboBox<String> MRI_DOCTOR_NAME_BOX = new JComboBox<>(this.MRI_DCM);
    
    private final JPanel consultationPanel = new JPanel();
    private final JRadioButton FIRST_VISIT_RADIO_BUTTON = new JRadioButton("Первичный", true);
    private final JRadioButton SECOND_VISIT_RADIO_BUTTON = new JRadioButton("Вторичный", false);
    private final ButtonGroup VISIT_RADIO_BUTTON_GROUP = new ButtonGroup();
    
    private final JPanel visitInfoPanel = new JPanel();
    private final JComboBox<String> FROM_ORG_AREA = new JComboBox<>();
    private final JTextField FROM_DOCTOR_AREA = new JTextField();
    
    private final OrganisationClient organisationClient = new OrganisationClient();
    
    public void fillDoctorsList(User user) {
        if (user.getPrivelegy() == Privilege.Ultrasound.getId()) this.ULTRASOUND_DCM.addElement(user.getNfp());
        if (user.getPrivelegy() == Privilege.Ginecology.getId()) this.GYNECOLOGY_DCM.addElement(user.getNfp());
        if (user.getPrivelegy() == Privilege.Urology.getId()) this.UROLOGY_DCM.addElement(user.getNfp());
        if (user.getPrivelegy() == Privilege.Stationary.getId()) this.STATIONARY_DCM.addElement(user.getNfp());
        if (user.getPrivelegy() == Privilege.Hirurgiya.getId()) this.SURGERY_DCM.addElement(user.getNfp());
        if (user.getPrivelegy() == Privilege.Okulist.getId()) this.OCULIST_DCM.addElement(user.getNfp());
        if (user.getPrivelegy() == Privilege.Fizio.getId()) this.PHYSIOTHERAPY_DCM.addElement(user.getNfp());
        if (user.getPrivelegy() == Privilege.Terapevt.getId()) this.THERAPY_DCM.addElement(user.getNfp());
        if (user.getPrivelegy() == Privilege.Endokrinolog.getId()) this.ENDO_DCM.addElement(user.getNfp());
        if (user.getPrivelegy() == Privilege.Nevropatolog.getId()) this.NEUROLOGIST_DCM.addElement(user.getNfp());
        if (user.getPrivelegy() == Privilege.Vertebrolog.getId()) this.VERTEBROLOGIST_DCM.addElement(user.getNfp());
        if (user.getPrivelegy() == Privilege.MagneticResonanceImaging.getId()) this.MRI_DCM.addElement(user.getNfp());
    }
    
    public VisitCoursePanel() {
        this.LABORATORY_CHECKBOX.setSelected(false);
        this.UROLOGY_CHECKBOX.setSelected(false);
        this.GYNECOLOGY_CHECKBOX.setSelected(false);
        this.ULTRASOUND_CHECKBOX.setSelected(false);
        this.STATIONARY_CHECKBOX.setSelected(false);
        
        this.UROLOGY_DOCTOR_NAME_BOX.setEnabled(false);
        this.GYNECOLOGY_DOCTOR_NAME_BOX.setEnabled(false);
        this.ULTRASOUND_DOCTOR_NAME_BOX.setEnabled(false);
        this.STATIONARY_DOCTOR_NAME_BOX.setEnabled(false);
        
        this.SURGERY_DOCTOR_NAME_BOX.setEnabled(false);
        this.OCULIST_DOCTOR_NAME_BOX.setEnabled(false);
        this.PHYSIOTHERAPY_DOCTOR_NAME_BOX.setEnabled(false);
        this.THERAPY_DOCTOR_NAME_BOX.setEnabled(false);
        this.ENDO_DOCTOR_NAME_BOX.setEnabled(false);
        this.NEUROLOGIST_DOCTOR_NAME_BOX.setEnabled(false);
        this.VERTEBROLOGIST_DOCTOR_NAME_BOX.setEnabled(false);
        this.MRI_DOCTOR_NAME_BOX.setEnabled(false);
        
        this.VISIT_RADIO_BUTTON_GROUP.add(FIRST_VISIT_RADIO_BUTTON);
        this.VISIT_RADIO_BUTTON_GROUP.add(SECOND_VISIT_RADIO_BUTTON);
        this.FIRST_VISIT_RADIO_BUTTON.setEnabled(false);
        this.SECOND_VISIT_RADIO_BUTTON.setEnabled(false);
        
        try {
            List<Organisation> organisationsList = this.organisationClient.listAll();
            organisationsList.stream().forEach((organisation) -> {
                this.FROM_ORG_AREA.addItem(organisation.getName());
            });
        } catch (IOException ioe) {  
        }
        
        configurePanel();
        addAllActionListeners();
    }

    public void reset() {
        getGinecologyDoctorsBox().setEnabled(false);
        getUrologyDoctorsBox().setEnabled(false);
        getUltraSoundDoctorsBox().setEnabled(false);
        getStationaryDoctorsBox().setEnabled(false);        
        getHirurgiyaDoctorsBox().setEnabled(false);
        getOkulistDoctorsBox().setEnabled(false);
        getFizioDoctorsBox().setEnabled(false);
        getTerapevtDoctorsBox().setEnabled(false);
        getEndokriDoctorsBox().setEnabled(false);
        getNevroDoctorsBox().setEnabled(false);
        getVerteDoctorsBox().setEnabled(false);
        getMriSoundDoctorBox().setEnabled(false);
        
        getLaboratoryCheckBox().setSelected(false);
        getGinecologyCheckBox().setSelected(false);
        getUrologyCheckBox().setSelected(false);
        getUltrasoundCheckBox().setSelected(false);
        getStationaryCheckBox().setSelected(false);        
        getHirurgiyaCheckBox().setSelected(false);
        getOkulistCheckBox().setSelected(false);
        getFiziCheckBox().setSelected(false);
        getTeraCheckBox().setSelected(false);
        getEndoCheckBox().setSelected(false);
        getNevroCheckBox().setSelected(false);
        getVerteCheckBox().setSelected(false);
        getMriCheckBox().setSelected(false);
        
        if (this.FROM_ORG_AREA.getItemCount() > 0) {
            this.FROM_ORG_AREA.setSelectedIndex(0);
        }
        
        this.FROM_DOCTOR_AREA.setText("");
    }    
    public JComboBox<String> getFromOrganisationList() {
        return FROM_ORG_AREA;
    }
    public JTextField getFromDoctorArea() {
        return FROM_DOCTOR_AREA;
    }    
    public JCheckBox getLaboratoryCheckBox() {
        return this.LABORATORY_CHECKBOX;
    }    
    public JCheckBox getUltrasoundCheckBox() {
        return this.ULTRASOUND_CHECKBOX;
    }    
    public JCheckBox getStationaryCheckBox() {
        return this.STATIONARY_CHECKBOX;
    }    
    public JCheckBox getUrologyCheckBox() {
        return this.UROLOGY_CHECKBOX;
    }    
    public JCheckBox getGinecologyCheckBox() {
        return this.GYNECOLOGY_CHECKBOX;
    }    
    public JCheckBox getMriCheckBox() {
        return this.MRI_CHECKBOX;
    }
    public JCheckBox getHirurgiyaCheckBox() {
        return this.SURGERY_CHECKBOX;
    }
    public JCheckBox getOkulistCheckBox() {
        return this.OCULIST_CHECKBOX;
    }
    public JCheckBox getFiziCheckBox() {
        return this.PHYSIOTHERAPY_CHECKBOX;
    }
    public JCheckBox getTeraCheckBox() {
        return this.THERAPY_CHECKBOX;
    }
    public JCheckBox getEndoCheckBox() {
        return this.ENDO_CHECKBOX;
    }
    public JCheckBox getNevroCheckBox() {
        return this.NEUROLOGIST_CHECKBOX;
    }
    public JCheckBox getVerteCheckBox() {
        return this.VERTEBROLOGIST_CHECKBOX;
    }    public JPanel getRootPanel() {
        return this.ROOT_PANEL;
    }    
    public JComboBox<String> getMriSoundDoctorBox() {
        return this.MRI_DOCTOR_NAME_BOX;
    }    
    public JComboBox<String> getUltraSoundDoctorsBox() {
        return this.ULTRASOUND_DOCTOR_NAME_BOX;
    }    
    public JComboBox<String> getUrologyDoctorsBox() {
        return this.UROLOGY_DOCTOR_NAME_BOX;
    }                
    public JComboBox<String> getHirurgiyaDoctorsBox() {
        return this.SURGERY_DOCTOR_NAME_BOX;
    }
    public JComboBox<String> getOkulistDoctorsBox() {
        return this.OCULIST_DOCTOR_NAME_BOX;
    }
    public JComboBox<String> getFizioDoctorsBox() {
        return this.PHYSIOTHERAPY_DOCTOR_NAME_BOX;
    }
    public JComboBox<String> getTerapevtDoctorsBox() {
        return this.THERAPY_DOCTOR_NAME_BOX;
    }
    public JComboBox<String> getEndokriDoctorsBox() {
        return this.ENDO_DOCTOR_NAME_BOX;
    }
    public JComboBox<String> getNevroDoctorsBox() {
        return this.NEUROLOGIST_DOCTOR_NAME_BOX;
    }
    public JComboBox<String> getVerteDoctorsBox() {
        return this.VERTEBROLOGIST_DOCTOR_NAME_BOX;
    }
    
    public JComboBox<String> getGinecologyDoctorsBox() {
        return this.GYNECOLOGY_DOCTOR_NAME_BOX;
    }
    
    public JComboBox<String> getStationaryDoctorsBox() {
        return this.STATIONARY_DOCTOR_NAME_BOX;
    }
    
    public void disableVisitTypeButtons() {
        this.FIRST_VISIT_RADIO_BUTTON.setEnabled(false);
        this.SECOND_VISIT_RADIO_BUTTON.setEnabled(false);
    }
    
    private void configurePanel() {
        this.ROOT_PANEL.setSize(480, 610);
        this.ROOT_PANEL.setLayout(new BoxLayout(this.ROOT_PANEL, BoxLayout.Y_AXIS));
        this.ROOT_PANEL.setVisible(true);
        
        JPanel visitCoursePanel = new JPanel();
        visitCoursePanel.setLayout(new GridBagLayout());
        visitCoursePanel.setBorder(new TitledBorder(new EtchedBorder(), "Направление"));
        
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 0;
        this.gbc.gridy = 0;
        this.gbc.gridwidth = 1;
        this.gbc.ipady = 1;
        this.gbc.weightx = 0.0;
        this.gbc.gridwidth = 1;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        visitCoursePanel.add(this.LABORATORY_CHECKBOX, this.gbc);
//        
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 0;
        this.gbc.gridy = 1;
        this.gbc.gridwidth = 1;
        this.gbc.ipady = 1;
        this.gbc.weightx = 0.0;
        this.gbc.gridwidth = 1;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        visitCoursePanel.add(this.ULTRASOUND_CHECKBOX, this.gbc);
        
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 1;
        this.gbc.gridy = 1;
        this.gbc.gridwidth = 1;
        this.gbc.ipady = 1;
        this.gbc.weightx = 0.0;
        this.gbc.gridwidth = 1;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        visitCoursePanel.add(new JLabel(" Врач "), this.gbc);
        
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 2;
        this.gbc.gridy = 1;
        this.gbc.gridwidth = 1;
        this.gbc.ipady = 1;
        this.gbc.weightx = 0.0;
        this.gbc.gridwidth = 1;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        visitCoursePanel.add(this.ULTRASOUND_DOCTOR_NAME_BOX, this.gbc);
//        
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 0;
        this.gbc.gridy = 2;
        this.gbc.gridwidth = 1;
        this.gbc.ipady = 1;
        this.gbc.weightx = 0.0;
        this.gbc.gridwidth = 1;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        visitCoursePanel.add(this.UROLOGY_CHECKBOX, this.gbc);
        
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 1;
        this.gbc.gridy = 2;
        this.gbc.gridwidth = 1;
        this.gbc.ipady = 1;
        this.gbc.weightx = 0.0;
        this.gbc.gridwidth = 1;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        visitCoursePanel.add(new JLabel(" Врач "), this.gbc);
        
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 2;
        this.gbc.gridy = 2;
        this.gbc.gridwidth = 1;
        this.gbc.ipady = 1;
        this.gbc.weightx = 0.0;
        this.gbc.gridwidth = 1;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        visitCoursePanel.add(this.UROLOGY_DOCTOR_NAME_BOX, this.gbc);
//        
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 0;
        this.gbc.gridy = 3;
        this.gbc.gridwidth = 1;
        this.gbc.ipady = 1;
        this.gbc.weightx = 0.0;
        this.gbc.gridwidth = 1;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        visitCoursePanel.add(this.GYNECOLOGY_CHECKBOX, this.gbc);
        
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 1;
        this.gbc.gridy = 3;
        this.gbc.gridwidth = 1;
        this.gbc.ipady = 1;
        this.gbc.weightx = 0.0;
        this.gbc.gridwidth = 1;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        visitCoursePanel.add(new JLabel(" Врач "), this.gbc);
        
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 2;
        this.gbc.gridy = 3;
        this.gbc.gridwidth = 1;
        this.gbc.ipady = 1;
        this.gbc.weightx = 0.0;
        this.gbc.gridwidth = 1;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        visitCoursePanel.add(this.GYNECOLOGY_DOCTOR_NAME_BOX, this.gbc);
        
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 0;
        this.gbc.gridy = 4;
        this.gbc.gridwidth = 1;
        this.gbc.ipady = 1;
        this.gbc.weightx = 0.0;
        this.gbc.gridwidth = 1;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        visitCoursePanel.add(this.STATIONARY_CHECKBOX, this.gbc);
        
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 1;
        this.gbc.gridy = 4;
        this.gbc.gridwidth = 1;
        this.gbc.ipady = 1;
        this.gbc.weightx = 0.0;
        this.gbc.gridwidth = 1;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        visitCoursePanel.add(new JLabel(" Врач "), this.gbc);
        
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 2;
        this.gbc.gridy = 4;
        this.gbc.gridwidth = 1;
        this.gbc.ipady = 1;
        this.gbc.weightx = 0.0;
        this.gbc.gridwidth = 1;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        visitCoursePanel.add(this.STATIONARY_DOCTOR_NAME_BOX, this.gbc);
                
        {
            this.gbc.fill = GridBagConstraints.HORIZONTAL;
            this.gbc.gridx = 0;
            this.gbc.gridy = 5;
            this.gbc.gridwidth = 1;
            this.gbc.ipady = 1;
            this.gbc.weightx = 0.0;
            this.gbc.gridwidth = 1;
            this.gbc.insets = new Insets(0, 0, 0, 0);
            visitCoursePanel.add(this.SURGERY_CHECKBOX, this.gbc);

            this.gbc.fill = GridBagConstraints.HORIZONTAL;
            this.gbc.gridx = 1;
            this.gbc.gridy = 5;
            this.gbc.gridwidth = 1;
            this.gbc.ipady = 1;
            this.gbc.weightx = 0.0;
            this.gbc.gridwidth = 1;
            this.gbc.insets = new Insets(0, 0, 0, 0);
            visitCoursePanel.add(new JLabel(" Врач "), this.gbc);

            this.gbc.fill = GridBagConstraints.HORIZONTAL;
            this.gbc.gridx = 2;
            this.gbc.gridy = 5;
            this.gbc.gridwidth = 1;
            this.gbc.ipady = 1;
            this.gbc.weightx = 0.0;
            this.gbc.gridwidth = 1;
            this.gbc.insets = new Insets(0, 0, 0, 0);
            visitCoursePanel.add(this.SURGERY_DOCTOR_NAME_BOX, this.gbc);
        }
        
        {
            this.gbc.fill = GridBagConstraints.HORIZONTAL;
            this.gbc.gridx = 0;
            this.gbc.gridy = 6;
            this.gbc.gridwidth = 1;
            this.gbc.ipady = 1;
            this.gbc.weightx = 0.0;
            this.gbc.gridwidth = 1;
            this.gbc.insets = new Insets(0, 0, 0, 0);
            visitCoursePanel.add(this.OCULIST_CHECKBOX, this.gbc);

            this.gbc.fill = GridBagConstraints.HORIZONTAL;
            this.gbc.gridx = 1;
            this.gbc.gridy = 6;
            this.gbc.gridwidth = 1;
            this.gbc.ipady = 1;
            this.gbc.weightx = 0.0;
            this.gbc.gridwidth = 1;
            this.gbc.insets = new Insets(0, 0, 0, 0);
            visitCoursePanel.add(new JLabel(" Врач "), this.gbc);

            this.gbc.fill = GridBagConstraints.HORIZONTAL;
            this.gbc.gridx = 2;
            this.gbc.gridy = 6;
            this.gbc.gridwidth = 1;
            this.gbc.ipady = 1;
            this.gbc.weightx = 0.0;
            this.gbc.gridwidth = 1;
            this.gbc.insets = new Insets(0, 0, 0, 0);
            visitCoursePanel.add(this.OCULIST_DOCTOR_NAME_BOX, this.gbc);
        }
        
        {
            this.gbc.fill = GridBagConstraints.HORIZONTAL;
            this.gbc.gridx = 0;
            this.gbc.gridy = 7;
            this.gbc.gridwidth = 1;
            this.gbc.ipady = 1;
            this.gbc.weightx = 0.0;
            this.gbc.gridwidth = 1;
            this.gbc.insets = new Insets(0, 0, 0, 0);
            visitCoursePanel.add(this.PHYSIOTHERAPY_CHECKBOX, this.gbc);

            this.gbc.fill = GridBagConstraints.HORIZONTAL;
            this.gbc.gridx = 1;
            this.gbc.gridy = 7;
            this.gbc.gridwidth = 1;
            this.gbc.ipady = 1;
            this.gbc.weightx = 0.0;
            this.gbc.gridwidth = 1;
            this.gbc.insets = new Insets(0, 0, 0, 0);
            visitCoursePanel.add(new JLabel(" Врач "), this.gbc);

            this.gbc.fill = GridBagConstraints.HORIZONTAL;
            this.gbc.gridx = 2;
            this.gbc.gridy = 7;
            this.gbc.gridwidth = 1;
            this.gbc.ipady = 1;
            this.gbc.weightx = 0.0;
            this.gbc.gridwidth = 1;
            this.gbc.insets = new Insets(0, 0, 0, 0);
            visitCoursePanel.add(this.PHYSIOTHERAPY_DOCTOR_NAME_BOX, this.gbc);
        }
        
        {
            this.gbc.fill = GridBagConstraints.HORIZONTAL;
            this.gbc.gridx = 0;
            this.gbc.gridy = 8;
            this.gbc.gridwidth = 1;
            this.gbc.ipady = 1;
            this.gbc.weightx = 0.0;
            this.gbc.gridwidth = 1;
            this.gbc.insets = new Insets(0, 0, 0, 0);
            visitCoursePanel.add(this.THERAPY_CHECKBOX, this.gbc);

            this.gbc.fill = GridBagConstraints.HORIZONTAL;
            this.gbc.gridx = 1;
            this.gbc.gridy = 8;
            this.gbc.gridwidth = 1;
            this.gbc.ipady = 1;
            this.gbc.weightx = 0.0;
            this.gbc.gridwidth = 1;
            this.gbc.insets = new Insets(0, 0, 0, 0);
            visitCoursePanel.add(new JLabel(" Врач "), this.gbc);

            this.gbc.fill = GridBagConstraints.HORIZONTAL;
            this.gbc.gridx = 2;
            this.gbc.gridy = 8;
            this.gbc.gridwidth = 1;
            this.gbc.ipady = 1;
            this.gbc.weightx = 0.0;
            this.gbc.gridwidth = 1;
            this.gbc.insets = new Insets(0, 0, 0, 0);
            visitCoursePanel.add(this.THERAPY_DOCTOR_NAME_BOX, this.gbc);
        }
        
        {
            this.gbc.fill = GridBagConstraints.HORIZONTAL;
            this.gbc.gridx = 0;
            this.gbc.gridy = 9;
            this.gbc.gridwidth = 1;
            this.gbc.ipady = 1;
            this.gbc.weightx = 0.0;
            this.gbc.gridwidth = 1;
            this.gbc.insets = new Insets(0, 0, 0, 0);
            visitCoursePanel.add(this.ENDO_CHECKBOX, this.gbc);

            this.gbc.fill = GridBagConstraints.HORIZONTAL;
            this.gbc.gridx = 1;
            this.gbc.gridy = 9;
            this.gbc.gridwidth = 1;
            this.gbc.ipady = 1;
            this.gbc.weightx = 0.0;
            this.gbc.gridwidth = 1;
            this.gbc.insets = new Insets(0, 0, 0, 0);
            visitCoursePanel.add(new JLabel(" Врач "), this.gbc);

            this.gbc.fill = GridBagConstraints.HORIZONTAL;
            this.gbc.gridx = 2;
            this.gbc.gridy = 9;
            this.gbc.gridwidth = 1;
            this.gbc.ipady = 1;
            this.gbc.weightx = 0.0;
            this.gbc.gridwidth = 1;
            this.gbc.insets = new Insets(0, 0, 0, 0);
            visitCoursePanel.add(this.ENDO_DOCTOR_NAME_BOX, this.gbc);
        }
        
        {
            this.gbc.fill = GridBagConstraints.HORIZONTAL;
            this.gbc.gridx = 0;
            this.gbc.gridy = 10;
            this.gbc.gridwidth = 1;
            this.gbc.ipady = 1;
            this.gbc.weightx = 0.0;
            this.gbc.gridwidth = 1;
            this.gbc.insets = new Insets(0, 0, 0, 0);
            visitCoursePanel.add(this.NEUROLOGIST_CHECKBOX, this.gbc);

            this.gbc.fill = GridBagConstraints.HORIZONTAL;
            this.gbc.gridx = 1;
            this.gbc.gridy = 10;
            this.gbc.gridwidth = 1;
            this.gbc.ipady = 1;
            this.gbc.weightx = 0.0;
            this.gbc.gridwidth = 1;
            this.gbc.insets = new Insets(0, 0, 0, 0);
            visitCoursePanel.add(new JLabel(" Врач "), this.gbc);

            this.gbc.fill = GridBagConstraints.HORIZONTAL;
            this.gbc.gridx = 2;
            this.gbc.gridy = 10;
            this.gbc.gridwidth = 1;
            this.gbc.ipady = 1;
            this.gbc.weightx = 0.0;
            this.gbc.gridwidth = 1;
            this.gbc.insets = new Insets(0, 0, 0, 0);
            visitCoursePanel.add(this.NEUROLOGIST_DOCTOR_NAME_BOX, this.gbc);
        }
        
        {
            this.gbc.fill = GridBagConstraints.HORIZONTAL;
            this.gbc.gridx = 0;
            this.gbc.gridy = 11;
            this.gbc.gridwidth = 1;
            this.gbc.ipady = 1;
            this.gbc.weightx = 0.0;
            this.gbc.gridwidth = 1;
            this.gbc.insets = new Insets(0, 0, 0, 0);
            visitCoursePanel.add(this.VERTEBROLOGIST_CHECKBOX, this.gbc);

            this.gbc.fill = GridBagConstraints.HORIZONTAL;
            this.gbc.gridx = 1;
            this.gbc.gridy = 11;
            this.gbc.gridwidth = 1;
            this.gbc.ipady = 1;
            this.gbc.weightx = 0.0;
            this.gbc.gridwidth = 1;
            this.gbc.insets = new Insets(0, 0, 0, 0);
            visitCoursePanel.add(new JLabel(" Врач "), this.gbc);

            this.gbc.fill = GridBagConstraints.HORIZONTAL;
            this.gbc.gridx = 2;
            this.gbc.gridy = 11;
            this.gbc.gridwidth = 1;
            this.gbc.ipady = 1;
            this.gbc.weightx = 0.0;
            this.gbc.gridwidth = 1;
            this.gbc.insets = new Insets(0, 0, 0, 0);
            visitCoursePanel.add(this.VERTEBROLOGIST_DOCTOR_NAME_BOX, this.gbc);
        }
        
        {
            this.gbc.fill = GridBagConstraints.HORIZONTAL;
            this.gbc.gridx = 0;
            this.gbc.gridy = 12;
            this.gbc.gridwidth = 1;
            this.gbc.ipady = 1;
            this.gbc.weightx = 0.0;
            this.gbc.gridwidth = 1;
            this.gbc.insets = new Insets(0, 0, 0, 0);
            visitCoursePanel.add(this.MRI_CHECKBOX, this.gbc);

            this.gbc.fill = GridBagConstraints.HORIZONTAL;
            this.gbc.gridx = 1;
            this.gbc.gridy = 12;
            this.gbc.gridwidth = 1;
            this.gbc.ipady = 1;
            this.gbc.weightx = 0.0;
            this.gbc.gridwidth = 1;
            this.gbc.insets = new Insets(0, 0, 0, 0);
            visitCoursePanel.add(new JLabel(" Врач "), this.gbc);

            this.gbc.fill = GridBagConstraints.HORIZONTAL;
            this.gbc.gridx = 2;
            this.gbc.gridy = 12;
            this.gbc.gridwidth = 1;
            this.gbc.ipady = 1;
            this.gbc.weightx = 0.0;
            this.gbc.gridwidth = 1;
            this.gbc.insets = new Insets(0, 0, 0, 0);
            visitCoursePanel.add(this.MRI_DOCTOR_NAME_BOX, this.gbc);
        }
        
        consultationPanel.setLayout(new GridBagLayout());
        consultationPanel.setBorder(new TitledBorder(new EtchedBorder(), "Консультация"));
        
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 0;
        this.gbc.gridy = 0;
        this.gbc.gridwidth = 1;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        consultationPanel.add(this.FIRST_VISIT_RADIO_BUTTON, this.gbc);
        
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 1;
        this.gbc.gridy = 0;
        this.gbc.gridwidth = 1;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        consultationPanel.add(this.SECOND_VISIT_RADIO_BUTTON, this.gbc);
        
        this.visitInfoPanel.setLayout(new GridBagLayout());
        this.visitInfoPanel.setBorder(new TitledBorder(new EtchedBorder(), "Приход"));
        
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 0;
        this.gbc.gridy = 0;
        this.gbc.gridwidth = 1;
        this.gbc.ipadx=220;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        this.visitInfoPanel.add(new JLabel("От организации: "), this.gbc);
        
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 0;
        this.gbc.gridy = 1;
        this.gbc.gridwidth = 2;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        this.visitInfoPanel.add(this.FROM_ORG_AREA, this.gbc);
        
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 0;
        this.gbc.gridy = 2;
        this.gbc.gridwidth = 2;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        this.visitInfoPanel.add(new JLabel("От врача: "), this.gbc);
        
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 0;
        this.gbc.gridy = 3;
        this.gbc.gridwidth = 2;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        this.visitInfoPanel.add(this.FROM_DOCTOR_AREA, this.gbc);
        
        this.ROOT_PANEL.add(visitCoursePanel);
        this.ROOT_PANEL.add(visitInfoPanel);
        this.ROOT_PANEL.add(consultationPanel);
    }
    
    private void addAllActionListeners() {
        this.MRI_CHECKBOX.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (MRI_CHECKBOX.isSelected()) {
                    FIRST_VISIT_RADIO_BUTTON.setEnabled(true);
                    SECOND_VISIT_RADIO_BUTTON.setEnabled(true);
                    MRI_DOCTOR_NAME_BOX.setEnabled(true);
                                        
//                    AssignmentService.INSTANCE.getMriFrame().getInternalFrame().setVisible(true);
                } else {
                    if (!STATIONARY_CHECKBOX.isSelected()) {
                        FIRST_VISIT_RADIO_BUTTON.setEnabled(false);
                        SECOND_VISIT_RADIO_BUTTON.setEnabled(false);
                    }
                    
                    MRI_DOCTOR_NAME_BOX.setEnabled(false);
                                        
//                    AssignmentService.INSTANCE.getMriFrame().getInternalFrame().setVisible(false);
                }
            }
        });
        
        this.LABORATORY_CHECKBOX.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (LABORATORY_CHECKBOX.isSelected()) {
                    AssignmentService.INSTANCE.getLaboratoryFrame().getInternalFrame().setVisible(true);
                } else {
                    AssignmentService.INSTANCE.getLaboratoryFrame().getInternalFrame().setVisible(false);
                }
            }
        });
                
        this.ULTRASOUND_CHECKBOX.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (ULTRASOUND_CHECKBOX.isSelected()) {     
                    FIRST_VISIT_RADIO_BUTTON.setEnabled(true);
                    SECOND_VISIT_RADIO_BUTTON.setEnabled(true);
                    ULTRASOUND_DOCTOR_NAME_BOX.setEnabled(true);
//                    AssignmentService.INSTANCE.getUltrasoundFrame().getInternalFrame().setVisible(true);
                    
                } else {                            
                    FIRST_VISIT_RADIO_BUTTON.setEnabled(false);
                    SECOND_VISIT_RADIO_BUTTON.setEnabled(false);
                    ULTRASOUND_DOCTOR_NAME_BOX.setEnabled(false);
//                    AssignmentService.INSTANCE.getUltrasoundFrame().getInternalFrame().setVisible(false);
                }
            }
        });
        
        this.UROLOGY_CHECKBOX.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (GYNECOLOGY_CHECKBOX.isSelected()
                        && UROLOGY_CHECKBOX.isSelected()) {
                    ResultToolbarService.INSTANCE.showFailedStatus("Пациент не может быть направлен в гинекологию и урологию одновременно");
                    
                    UROLOGY_CHECKBOX.setSelected(false);
                    UROLOGY_DOCTOR_NAME_BOX.setEnabled(false);
                    return;
                }
                
                if (UROLOGY_CHECKBOX.isSelected()) {
                    FIRST_VISIT_RADIO_BUTTON.setEnabled(true);
                    SECOND_VISIT_RADIO_BUTTON.setEnabled(true);
                    UROLOGY_DOCTOR_NAME_BOX.setEnabled(true);
                    
                } else {
                    if (!GYNECOLOGY_CHECKBOX.isSelected()) {
                        FIRST_VISIT_RADIO_BUTTON.setEnabled(false);
                        SECOND_VISIT_RADIO_BUTTON.setEnabled(false);
                    }
                    
                    UROLOGY_DOCTOR_NAME_BOX.setEnabled(false);
                }
            }
        });
        
        this.GYNECOLOGY_CHECKBOX.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (GYNECOLOGY_CHECKBOX.isSelected()
                        && UROLOGY_CHECKBOX.isSelected()) {
                    ResultToolbarService.INSTANCE.showFailedStatus("Пациент не может быть направлен в гинекологию и урологию одновременно");

                    GYNECOLOGY_CHECKBOX.setSelected(false);
                    GYNECOLOGY_DOCTOR_NAME_BOX.setEnabled(false);
                    return;
                }

                if (GYNECOLOGY_CHECKBOX.isSelected()) {
                    FIRST_VISIT_RADIO_BUTTON.setEnabled(true);
                    SECOND_VISIT_RADIO_BUTTON.setEnabled(true);
                    GYNECOLOGY_DOCTOR_NAME_BOX.setEnabled(true);
//                    AssignmentService.INSTANCE.getGinecologyFrame().getInternalFrame().setVisible(true);

                } else {
                    if (!UROLOGY_CHECKBOX.isSelected()) {
                        FIRST_VISIT_RADIO_BUTTON.setEnabled(false);
                        SECOND_VISIT_RADIO_BUTTON.setEnabled(false);
                    }

                    GYNECOLOGY_DOCTOR_NAME_BOX.setEnabled(false);
//                    AssignmentService.INSTANCE.getGinecologyFrame().getInternalFrame().setVisible(false);
                }
            }
        });    
                
        this.SURGERY_CHECKBOX.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (SURGERY_CHECKBOX.isSelected()) {
                    FIRST_VISIT_RADIO_BUTTON.setEnabled(true);
                    SECOND_VISIT_RADIO_BUTTON.setEnabled(true);
                    SURGERY_DOCTOR_NAME_BOX.setEnabled(true);
                } else {
                    FIRST_VISIT_RADIO_BUTTON.setEnabled(false);
                    SECOND_VISIT_RADIO_BUTTON.setEnabled(false);

                    SURGERY_DOCTOR_NAME_BOX.setEnabled(false);
                }
            }
        });
        
        this.OCULIST_CHECKBOX.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (OCULIST_CHECKBOX.isSelected()) {
                    FIRST_VISIT_RADIO_BUTTON.setEnabled(true);
                    SECOND_VISIT_RADIO_BUTTON.setEnabled(true);
                    OCULIST_DOCTOR_NAME_BOX.setEnabled(true);
                } else {
                    FIRST_VISIT_RADIO_BUTTON.setEnabled(false);
                    SECOND_VISIT_RADIO_BUTTON.setEnabled(false);

                    OCULIST_DOCTOR_NAME_BOX.setEnabled(false);
                }
            }
        });
        
        this.PHYSIOTHERAPY_CHECKBOX.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (PHYSIOTHERAPY_CHECKBOX.isSelected()) {
                    FIRST_VISIT_RADIO_BUTTON.setEnabled(true);
                    SECOND_VISIT_RADIO_BUTTON.setEnabled(true);
                    PHYSIOTHERAPY_DOCTOR_NAME_BOX.setEnabled(true);

//                    AssignmentService.INSTANCE.getPhysiotherapyFrame().getInternalFrame().setVisible(true);
                } else {
                    FIRST_VISIT_RADIO_BUTTON.setEnabled(false);
                    SECOND_VISIT_RADIO_BUTTON.setEnabled(false);

                    PHYSIOTHERAPY_DOCTOR_NAME_BOX.setEnabled(false);

//                    AssignmentService.INSTANCE.getPhysiotherapyFrame().getInternalFrame().setVisible(false);
                }
            }
        });
        
        this.THERAPY_CHECKBOX.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (THERAPY_CHECKBOX.isSelected()) {
                    FIRST_VISIT_RADIO_BUTTON.setEnabled(true);
                    SECOND_VISIT_RADIO_BUTTON.setEnabled(true);
                    THERAPY_DOCTOR_NAME_BOX.setEnabled(true);
                } else {
                    FIRST_VISIT_RADIO_BUTTON.setEnabled(false);
                    SECOND_VISIT_RADIO_BUTTON.setEnabled(false);

                    THERAPY_DOCTOR_NAME_BOX.setEnabled(false);
                }
            }
        });
        
        this.ENDO_CHECKBOX.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (ENDO_CHECKBOX.isSelected()) {
                    FIRST_VISIT_RADIO_BUTTON.setEnabled(true);
                    SECOND_VISIT_RADIO_BUTTON.setEnabled(true);
                    ENDO_DOCTOR_NAME_BOX.setEnabled(true);
                } else {
                    FIRST_VISIT_RADIO_BUTTON.setEnabled(false);
                    SECOND_VISIT_RADIO_BUTTON.setEnabled(false);

                    ENDO_DOCTOR_NAME_BOX.setEnabled(false);
                }
            }
        });
        
        this.NEUROLOGIST_CHECKBOX.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (NEUROLOGIST_CHECKBOX.isSelected()) {
                    FIRST_VISIT_RADIO_BUTTON.setEnabled(true);
                    SECOND_VISIT_RADIO_BUTTON.setEnabled(true);
                    NEUROLOGIST_DOCTOR_NAME_BOX.setEnabled(true);
                } else {
                    FIRST_VISIT_RADIO_BUTTON.setEnabled(false);
                    SECOND_VISIT_RADIO_BUTTON.setEnabled(false);

                    NEUROLOGIST_DOCTOR_NAME_BOX.setEnabled(false);
                }
            }
        });
        
        this.VERTEBROLOGIST_CHECKBOX.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (VERTEBROLOGIST_CHECKBOX.isSelected()) {
                    FIRST_VISIT_RADIO_BUTTON.setEnabled(true);
                    SECOND_VISIT_RADIO_BUTTON.setEnabled(true);
                    VERTEBROLOGIST_DOCTOR_NAME_BOX.setEnabled(true);
                } else {
                    FIRST_VISIT_RADIO_BUTTON.setEnabled(false);
                    SECOND_VISIT_RADIO_BUTTON.setEnabled(false);

                    VERTEBROLOGIST_DOCTOR_NAME_BOX.setEnabled(false);
                }
            }
        });
        
        this.STATIONARY_CHECKBOX.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {    
                if (STATIONARY_CHECKBOX.isSelected()) {
                    FIRST_VISIT_RADIO_BUTTON.setEnabled(true);
                    SECOND_VISIT_RADIO_BUTTON.setEnabled(true);
                    STATIONARY_DOCTOR_NAME_BOX.setEnabled(true);
                } else {
                    FIRST_VISIT_RADIO_BUTTON.setEnabled(false);
                    SECOND_VISIT_RADIO_BUTTON.setEnabled(false);
                    
                    STATIONARY_DOCTOR_NAME_BOX.setEnabled(false);
                }                
            }
        });
    }    
}