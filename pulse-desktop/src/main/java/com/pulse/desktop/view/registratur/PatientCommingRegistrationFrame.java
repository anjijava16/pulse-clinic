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
package com.pulse.desktop.view.registratur;


import com.pulse.desktop.controller.service.UserFacade;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import com.pulse.desktop.view.frame.childframes.assignment.AssignmentService;

import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.view.panel.SearchAndShowPatientInfoPanel;
import com.pulse.desktop.view.panel.VisitCoursePanel;

import com.pulse.desktop.view.util.HashBuilder;
import com.pulse.desktop.view.util.NameValidator;
import com.pulse.desktop.view.util.Settings;
import com.pulse.desktop.view.util.Values;
import com.pulse.model.Patient;
import com.pulse.model.User;
import com.pulse.model.Visit;
import com.pulse.model.constant.PaymentStatus;
import com.pulse.model.constant.Privilege;
import com.pulse.rest.client.VisitClient;
import java.io.UnsupportedEncodingException;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public final class PatientCommingRegistrationFrame {

    private final boolean RESIZABLE = false;
    private final boolean CLOSABLE = true;
    private final boolean MAXIMIZABLE = false;
    private final boolean ICONFIABLE = false;

    protected final String FRAME_DESCRIPTION = "Оформить визит";

    protected JInternalFrame frame = new JInternalFrame(this.FRAME_DESCRIPTION,
            this.RESIZABLE,
            this.CLOSABLE,
            this.MAXIMIZABLE,
            this.ICONFIABLE);

    protected final GridBagConstraints gbc = new GridBagConstraints();
    protected final JPanel ROOT_PANEL = new JPanel();

    protected int width = 340;
    protected int height = 460;

    protected int maxChars = 13;

    protected final JToolBar TOOLBAR = new JToolBar();

    public JInternalFrame getInternalFrame() {
        return this.frame;
    }

    protected void setToolbarSettings() {
        this.TOOLBAR.setFloatable(false);
        this.TOOLBAR.setVisible(true);
    }

    protected void addToolbarButton(JComponent toolbarButton, boolean addSeparator) {
        this.TOOLBAR.add(toolbarButton);

        if (addSeparator) {
            this.TOOLBAR.addSeparator();
        }
    }

    public boolean frameIsVisible() {
        return this.frame.isVisible();
    }

    public void setFrameVisible(boolean visibility) {
        this.frame.setVisible(visibility);
    }

    private final CardLayout CARD_LAYOUT = new CardLayout();
    private final JPanel CARD_PANEL = new JPanel();
    private final JPanel PATIENT_SEARCH_PANEL = new JPanel();
    private final JPanel VISIT_COURSE_PANEL = new JPanel();

    private final SearchAndShowPatientInfoPanel SHOW_PATIENT_INFO_PANEL
            = new SearchAndShowPatientInfoPanel();

    private final VisitCoursePanel VISIT_COURSE_SELECTION_PANEL;
    private final AtomicInteger step = new AtomicInteger(0);
    private final int MAX_STEP = 1;
    private final int MIN_STEP = 0;

    private final JPanel BUTTONS_PANEL = new JPanel(new FlowLayout());
    
    private final JButton NEXT_BUTTON = new JButton(">");
    private final JButton SAVE_BUTTON = new JButton("Сохранить");
    private final JButton PREVIOUS_BUTTON = new JButton("<");
   
    private final VisitClient visitClient = new VisitClient();

    public PatientCommingRegistrationFrame() {
        this.VISIT_COURSE_SELECTION_PANEL = new VisitCoursePanel();

        setAllSettings();
        addAllActionListeners();
        intializeFrame();
    }

    public VisitCoursePanel getVisitCoursePanel() {
        return this.VISIT_COURSE_SELECTION_PANEL;
    }

    public void intializeFrame() {
        this.SAVE_BUTTON.setVisible(false);

        initializeRootPanel();

        this.width = 480;
        this.height = 610;

        this.frame.setLayout(new BorderLayout());
        this.frame.setSize(this.width, this.height);

        this.frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.frame.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                setFrameVisible(false);
            }
        });
        
        this.BUTTONS_PANEL.add(this.PREVIOUS_BUTTON);
        this.BUTTONS_PANEL.add(this.SAVE_BUTTON);
        this.BUTTONS_PANEL.add(this.NEXT_BUTTON);
        
        this.frame.add(this.ROOT_PANEL, BorderLayout.CENTER);
        this.frame.add(this.BUTTONS_PANEL, BorderLayout.SOUTH);
    }

    public void initializeRootPanel() {
        initializeCardPanel();

        this.ROOT_PANEL.setLayout(new BorderLayout());
        this.ROOT_PANEL.setVisible(true);
        this.ROOT_PANEL.add(this.CARD_PANEL);
    }

    public void setAllSettings() {
        this.SHOW_PATIENT_INFO_PANEL.getPatientInfoPanel().denyEdit();
        this.frame.setVisible(false);
    }

    private void initializeCardPanel() {
        initializePatientSearchPanel();
        initializeVisitCoursePanel();

        JPanel searchWrapperPanel = new JPanel();
        searchWrapperPanel.setLayout(new FlowLayout());
        searchWrapperPanel.add(this.PATIENT_SEARCH_PANEL);
        
        this.CARD_PANEL.setLayout(this.CARD_LAYOUT);
        this.CARD_PANEL.add(searchWrapperPanel, "0");
        this.CARD_PANEL.add(VISIT_COURSE_SELECTION_PANEL.getRootPanel(), "1");
    }

    private void initializePatientSearchPanel() {
        this.PATIENT_SEARCH_PANEL.setLayout(new BorderLayout());
        this.PATIENT_SEARCH_PANEL.setVisible(true);

        this.PATIENT_SEARCH_PANEL.add(this.SHOW_PATIENT_INFO_PANEL.getRootPanel());
    }

    private void initializeVisitCoursePanel() {
        this.VISIT_COURSE_PANEL.setLayout(new BoxLayout(this.VISIT_COURSE_PANEL, BoxLayout.Y_AXIS));
        this.VISIT_COURSE_PANEL.setVisible(true);

        this.VISIT_COURSE_PANEL.add(this.VISIT_COURSE_SELECTION_PANEL.getRootPanel());
    }

    private void hidePanel() {
        setFrameVisible(false);
        this.SAVE_BUTTON.setVisible(false);

        this.VISIT_COURSE_SELECTION_PANEL.disableVisitTypeButtons();

        // Disable doctorx boxes and unlick check boxes
        this.VISIT_COURSE_SELECTION_PANEL.reset();

        // Hide internal frames with assignments
        AssignmentService.INSTANCE.hideAllFrames();
    }

    public void addAllActionListeners() {
        this.NEXT_BUTTON.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {        
                final Patient selectedPatient = SHOW_PATIENT_INFO_PANEL.getPatientInfoPanel().getCurrentPatient();

                if (selectedPatient == null) {
                    ResultToolbarService.INSTANCE.showFailedStatus("Пациент не выбран");
                    return;
                }

                if (step.incrementAndGet() >= MAX_STEP) {
                    step.set(MAX_STEP);

                    SAVE_BUTTON.setVisible(true);
                }

                CARD_LAYOUT.show(CARD_PANEL, String.valueOf(step));
            }
        });

        this.PREVIOUS_BUTTON.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {                
                SAVE_BUTTON.setVisible(false);

                if (step.decrementAndGet() <= MIN_STEP) {
                    step.set(MIN_STEP);
                }

                CARD_LAYOUT.show(CARD_PANEL, String.valueOf(step));
            }
        });

        this.SAVE_BUTTON.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (UserFacade.INSTANCE.getApplicationUser() == null) return;
                
                CARD_LAYOUT.show(CARD_PANEL, String.valueOf(0));

                final String fromOrganisation = VISIT_COURSE_SELECTION_PANEL.getFromOrganisationList().getSelectedItem().toString();
                final String fromDoctor = VISIT_COURSE_SELECTION_PANEL.getFromDoctorArea().getText();

                final Patient selectedPatient = SHOW_PATIENT_INFO_PANEL.getPatientInfoPanel().getCurrentPatient();

                if (selectedPatient == null) {
                    ResultToolbarService.INSTANCE.showFailedStatus("Пациент не выбран");
                    return;
                }
                
                if (!VISIT_COURSE_SELECTION_PANEL.getLaboratoryCheckBox().isSelected()
                        && !VISIT_COURSE_SELECTION_PANEL.getUltrasoundCheckBox().isSelected()
                        && !VISIT_COURSE_SELECTION_PANEL.getGinecologyCheckBox().isSelected()
                        && !VISIT_COURSE_SELECTION_PANEL.getUrologyCheckBox().isSelected()
                        && !VISIT_COURSE_SELECTION_PANEL.getStationaryCheckBox().isSelected()
                        && !VISIT_COURSE_SELECTION_PANEL.getHirurgiyaCheckBox().isSelected()
                        && !VISIT_COURSE_SELECTION_PANEL.getOkulistCheckBox().isSelected()
                        && !VISIT_COURSE_SELECTION_PANEL.getFiziCheckBox().isSelected()
                        && !VISIT_COURSE_SELECTION_PANEL.getTeraCheckBox().isSelected()
                        && !VISIT_COURSE_SELECTION_PANEL.getEndoCheckBox().isSelected()
                        && !VISIT_COURSE_SELECTION_PANEL.getNevroCheckBox().isSelected()
                        && !VISIT_COURSE_SELECTION_PANEL.getVerteCheckBox().isSelected()
                        && !VISIT_COURSE_SELECTION_PANEL.getMriCheckBox().isSelected()) {

                    SAVE_BUTTON.setVisible(false);

                    return;
                }
                                
                // Laboratory
                if (VISIT_COURSE_SELECTION_PANEL.getLaboratoryCheckBox().isSelected()) {
                    HashMap<String, ArrayList<String>> selectedCourses
                            = AssignmentService.INSTANCE.getLaboratoryFrame().getAnalysSelectionPanel().getAllSelectionData();

                    Set<String> selectedGroups = selectedCourses.keySet();
                    for (String group : selectedGroups) {
                        ArrayList<String> selectedAnalyses = selectedCourses.get(group);
                        for (String analys : selectedAnalyses) {
                            String hash = HashBuilder.INSTANCE.calculate();
                            String extension = NameValidator.INSTANCE.getExtension(analys);
                            
                            if (hash == null) {
                                ResultToolbarService.INSTANCE.showFailedStatus("Кодировка не поддерживается");
                                return;
                            }
                
                            try {
                                hash = HashBuilder.INSTANCE.token(hash.concat(analys));
                            } catch (UnsupportedEncodingException uee) {
                                ResultToolbarService.INSTANCE.showFailedStatus("Кодировка не поддерживается");
                                return;
                            }
                            
                            try {
                                Visit visit = new Visit();
                                visit.setDepartmentId(Privilege.Laboratory.getId());
                                visit.setAnalysGroup(group);
                                visit.setAnalysName(analys);
                                visit.setCreatedBy(UserFacade.INSTANCE.getApplicationUser().getUsername());
                                visit.setPatientId(selectedPatient.getId());
                                visit.setPaymentStatus(PaymentStatus.NOT_PAYED.getId());
                                visit.setTillDate(Values.Empty.getValue());
                                visit.setFromDoctor(fromDoctor);
                                visit.setFromOrganisation(fromOrganisation);
                                visit.setFilename(hash.concat(extension));
                                visit.setFilepath(String.valueOf(Privilege.Laboratory.getId()).concat("/").concat(String.valueOf(selectedPatient.getId())).concat("/"));

                                visit.setVisitDate(new Date());
                                visit.setVisitStatus(Settings.NOT_VISITED);
                                visit.setVisitType(Settings.VISIT_TYPE_CAMED);

                                visitClient.update(visit);
                            } catch (IOException ioe) {
                                ioe.printStackTrace();
                            }
                        }
                    }

                    AssignmentService.INSTANCE.getLaboratoryFrame().getAnalysSelectionPanel().clearAllSelectionData();
                }

                // Urology
                if (VISIT_COURSE_SELECTION_PANEL.getUrologyCheckBox().isSelected()) {
                    String selectedDoctorNfp = VISIT_COURSE_SELECTION_PANEL.getUrologyDoctorsBox().getSelectedItem().toString();
                    User account = UserFacade.INSTANCE.findBy(selectedDoctorNfp);
                    try {
                        Visit visit = new Visit();
                        visit.setDoctorId(account.getId());
                        visit.setDepartmentId(Privilege.Urology.getId());
                        visit.setAnalysGroup(Values.Unknown.getValue());
                        visit.setAnalysName(Values.Unknown.getValue());
                        visit.setCreatedBy(UserFacade.INSTANCE.getApplicationUser().getUsername());
                        visit.setPatientId(selectedPatient.getId());
                        visit.setPaymentStatus(PaymentStatus.NOT_PAYED.getId());
                        visit.setTillDate(Values.Empty.getValue());
                        visit.setFilename(Values.Empty.getValue());
                        visit.setFilepath(Values.Empty.getValue());
                        visit.setVisitDate(new Date());
                        visit.setFromDoctor(fromDoctor);
                        visit.setFromOrganisation(fromOrganisation);
                        visit.setVisitStatus(Settings.NOT_VISITED);
                        visit.setVisitType(Settings.VISIT_TYPE_CAMED);

                        visitClient.update(visit);
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }

                // Ginecology
                if (VISIT_COURSE_SELECTION_PANEL.getGinecologyCheckBox().isSelected()) {
//                    HashMap<String, ArrayList<String>> selectedCourses = AssignmentService.INSTANCE.getGinecologyFrame().getAnalysSelectionPanel().getAllSelectionData();
                    String selectedDoctorNfp = VISIT_COURSE_SELECTION_PANEL.getGinecologyDoctorsBox().getSelectedItem().toString();
                    User account = UserFacade.INSTANCE.findBy(selectedDoctorNfp);

//                    Set<String> selectedGroups = selectedCourses.keySet();
//                    for (String group : selectedGroups) {
//                        ArrayList<String> selectedAnalyses = selectedCourses.get(group);
//                        for (String analys : selectedAnalyses) {
//                            try {
//                                Visit visit = new Visit();
//                                visit.setDoctorId(account.getId());
//                                visit.setDepartmentId(Privilege.Ginecology.getId());
//                                visit.setAnalysGroup(group);
//                                visit.setAnalysName(analys);
//                                visit.setCreatedBy(UserFacade.INSTANCE.getApplicationUser().getUsername());
//                                visit.setPatientId(selectedPatient.getId());
//                                visit.setPaymentStatus(PaymentStatus.NOT_PAYED.getId());
//                                visit.setTillDate(Values.Empty.getValue());
//                                visit.setFromDoctor(fromDoctor);
//                                visit.setFromOrganisation(fromOrganisation);
//                                visit.setFilename(hash.concat(PrivelegyDir.GINECOLOGY_PATH.getDocsTrailor()));
//                                visit.setFilepath(String.valueOf(Privilege.Ginecology.getId()).concat("/").concat(String.valueOf(selectedPatient.getId())).concat("/"));
//
//                                visit.setVisitDate(new Date());
//                                visit.setVisitStatus(Settings.NOT_VISITED);
//                                visit.setVisitType(Settings.VISIT_TYPE_CAMED);
//
//                                visitClient.update(visit);
//                            } catch (IOException ioe) {
//                                ioe.printStackTrace();
//                            }
//                        }
//                    }

//                    if (selectedGroups.size() == 0) {
                        try {
                            Visit visit = new Visit();
                            visit.setDoctorId(account.getId());
                            visit.setDepartmentId(Privilege.Ginecology.getId());
                            visit.setAnalysGroup(Values.Unknown.getValue());
                            visit.setAnalysName(Values.Unknown.getValue());
                            visit.setCreatedBy(UserFacade.INSTANCE.getApplicationUser().getUsername());
                            visit.setPatientId(selectedPatient.getId());
                            visit.setPaymentStatus(PaymentStatus.NOT_PAYED.getId());
                            visit.setTillDate(Values.Empty.getValue());
                            visit.setFilename(Values.Empty.getValue());
                            visit.setFilepath(Values.Empty.getValue());
                            visit.setVisitDate(new Date());
                            visit.setFromDoctor(fromDoctor);
                            visit.setFromOrganisation(fromOrganisation);
                            visit.setVisitStatus(Settings.NOT_VISITED);
                            visit.setVisitType(Settings.VISIT_TYPE_CAMED);

                            visitClient.update(visit);
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
//                    }

                    AssignmentService.INSTANCE.getGinecologyFrame().getAnalysSelectionPanel().clearAllSelectionData();
                }

                // Ultrasound
                if (VISIT_COURSE_SELECTION_PANEL.getUltrasoundCheckBox().isSelected()) {
//                    HashMap<String, ArrayList<String>> selectedCourses = AssignmentService.INSTANCE.getUltrasoundFrame().getAnalysSelectionPanel().getAllSelectionData();
                    String selectedDoctorNfp = VISIT_COURSE_SELECTION_PANEL.getUltraSoundDoctorsBox().getSelectedItem().toString();
                    User account = UserFacade.INSTANCE.findBy(selectedDoctorNfp);
//                    Set<String> selectedGroups = selectedCourses.keySet();
//                    for (String group : selectedGroups) {
//                        ArrayList<String> selectedAnalyses = selectedCourses.get(group);
//                        for (String analys : selectedAnalyses) {
//                            try {
//                                Visit visit = new Visit();
//                                visit.setDoctorId(account.getId());
//                                visit.setDepartmentId(Privilege.Ultrasound.getId());
//                                visit.setAnalysGroup(group);
//                                visit.setAnalysName(analys);
//                                visit.setCreatedBy(UserFacade.INSTANCE.getApplicationUser().getUsername());
//                                visit.setPatientId(selectedPatient.getId());
//                                visit.setPaymentStatus(PaymentStatus.NOT_PAYED.getId());
//                                visit.setTillDate(Values.Empty.getValue());
//                                visit.setFromDoctor(fromDoctor);
//                                visit.setFromOrganisation(fromOrganisation);
//                                visit.setFilename(hash.concat(PrivelegyDir.ULTRASOUND_PATH.getDocsTrailor()));
//                                visit.setFilepath(String.valueOf(Privilege.Ultrasound.getId()).concat("/")
//                                        .concat(String.valueOf(selectedPatient.getId())).concat("/"));
//
//                                visit.setVisitDate(new Date());
//                                visit.setVisitStatus(Settings.NOT_VISITED);
//                                visit.setVisitType(Settings.VISIT_TYPE_CAMED);
//
//                                visitClient.update(visit);
//                            } catch (IOException ioe) {
//                                ioe.printStackTrace();
//                            }
//                        }
//                    }
//
//                    if (selectedGroups.size() == 0) {
                        try {
                            Visit visit = new Visit();
                            visit.setDoctorId(account.getId());
                            visit.setDepartmentId(Privilege.Ultrasound.getId());
                            visit.setAnalysGroup(Values.Unknown.getValue());
                            visit.setAnalysName(Values.Unknown.getValue());
                            visit.setCreatedBy(UserFacade.INSTANCE.getApplicationUser().getUsername());
                            visit.setPatientId(selectedPatient.getId());
                            visit.setPaymentStatus(PaymentStatus.NOT_PAYED.getId());
                            visit.setTillDate(Values.Empty.getValue());
                            visit.setFilename(Values.Empty.getValue());
                            visit.setFilepath(Values.Empty.getValue());
                            visit.setVisitDate(new Date());
                            visit.setFromDoctor(fromDoctor);
                            visit.setFromOrganisation(fromOrganisation);
                            visit.setVisitStatus(Settings.NOT_VISITED);
                            visit.setVisitType(Settings.VISIT_TYPE_CAMED);

                            visitClient.update(visit);
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
//                    }

                    AssignmentService.INSTANCE.getUltrasoundFrame().getAnalysSelectionPanel().clearAllSelectionData();
                }

                // Surgery
                if (VISIT_COURSE_SELECTION_PANEL.getHirurgiyaCheckBox().isSelected()) {
                    String selectedDoctorNfp = VISIT_COURSE_SELECTION_PANEL.getHirurgiyaDoctorsBox().getSelectedItem().toString();
                    User account = UserFacade.INSTANCE.findBy(selectedDoctorNfp);
                    try {
                        Visit visit = new Visit();
                        visit.setDoctorId(account.getId());
                        visit.setDepartmentId(Privilege.Hirurgiya.getId());
                        visit.setAnalysGroup(Values.Unknown.getValue());
                        visit.setAnalysName(Values.Unknown.getValue());
                        visit.setCreatedBy(UserFacade.INSTANCE.getApplicationUser().getUsername());
                        visit.setPatientId(selectedPatient.getId());
                        visit.setPaymentStatus(PaymentStatus.NOT_PAYED.getId());
                        visit.setTillDate(Values.Empty.getValue());
                        visit.setFilename(Values.Empty.getValue());
                        visit.setFilepath(Values.Empty.getValue());
                        visit.setVisitDate(new Date());
                        visit.setFromDoctor(fromDoctor);
                        visit.setFromOrganisation(fromOrganisation);
                        visit.setVisitStatus(Settings.NOT_VISITED);
                        visit.setVisitType(Settings.VISIT_TYPE_CAMED);

                        visitClient.update(visit);
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }

                // Oculist
                if (VISIT_COURSE_SELECTION_PANEL.getOkulistCheckBox().isSelected()) {
                    String selectedDoctorNfp = VISIT_COURSE_SELECTION_PANEL.getOkulistDoctorsBox().getSelectedItem().toString();
                    User account = UserFacade.INSTANCE.findBy(selectedDoctorNfp);
                    try {
                        Visit visit = new Visit();
                        visit.setDoctorId(account.getId());
                        visit.setDepartmentId(Privilege.Okulist.getId());
                        visit.setAnalysGroup(Values.Unknown.getValue());
                        visit.setAnalysName(Values.Unknown.getValue());
                        visit.setCreatedBy(UserFacade.INSTANCE.getApplicationUser().getUsername());
                        visit.setPatientId(selectedPatient.getId());
                        visit.setPaymentStatus(PaymentStatus.NOT_PAYED.getId());
                        visit.setTillDate(Values.Empty.getValue());
                        visit.setFilename(Values.Empty.getValue());
                        visit.setFilepath(Values.Empty.getValue());
                        visit.setVisitDate(new Date());
                        visit.setFromDoctor(fromDoctor);
                        visit.setFromOrganisation(fromOrganisation);
                        visit.setVisitStatus(Settings.NOT_VISITED);
                        visit.setVisitType(Settings.VISIT_TYPE_CAMED);

                        visitClient.update(visit);
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
                
                // Fizio
                if (VISIT_COURSE_SELECTION_PANEL.getFiziCheckBox().isSelected()) {
//                    HashMap<String, ArrayList<String>> selectedCourses = 
//                            AssignmentService.INSTANCE.getPhysiotherapyFrame().getAnalysSelectionPanel().getAllSelectionData();
                    
                    String selectedDoctorNfp = VISIT_COURSE_SELECTION_PANEL.getFizioDoctorsBox().getSelectedItem().toString();
                    User account = UserFacade.INSTANCE.findBy(selectedDoctorNfp);
//                    Set<String> selectedGroups = selectedCourses.keySet();
//                    for (String group : selectedGroups) {
//                        ArrayList<String> selectedAnalyses = selectedCourses.get(group);
//                        for (String analys : selectedAnalyses) {
//                            try {
//                                Visit visit = new Visit();
//                                visit.setDoctorId(account.getId());
//                                visit.setDepartmentId(Privilege.Fizio.getId());
//                                visit.setAnalysGroup(group);
//                                visit.setAnalysName(analys);
//                                visit.setCreatedBy(UserFacade.INSTANCE.getApplicationUser().getUsername());
//                                visit.setPatientId(selectedPatient.getId());
//                                visit.setPaymentStatus(PaymentStatus.NOT_PAYED.getId());
//                                visit.setTillDate(Values.Empty.getValue());
//                                visit.setFromDoctor(fromDoctor);
//                                visit.setFromOrganisation(fromOrganisation);
//                                visit.setFilename(hash.concat(PrivelegyDir.FIZIO_PATH.getDocsTrailor()));
//                                visit.setFilepath(String.valueOf(Privilege.Fizio.getId()).concat("/")
//                                        .concat(String.valueOf(selectedPatient.getId())).concat("/"));
//
//                                visit.setVisitDate(new Date());
//                                visit.setVisitStatus(Settings.NOT_VISITED);
//                                visit.setVisitType(Settings.VISIT_TYPE_CAMED);
//
//                                visitClient.update(visit);
//                            } catch (IOException ioe) {
//                                ioe.printStackTrace();
//                            }
//                        }
//                    }
//
//                    if (selectedGroups.size() == 0) {
                        try {
                            Visit visit = new Visit();
                            visit.setDoctorId(account.getId());
                            visit.setDepartmentId(Privilege.Fizio.getId());
                            visit.setAnalysGroup(Values.Unknown.getValue());
                            visit.setAnalysName(Values.Unknown.getValue());
                            visit.setCreatedBy(UserFacade.INSTANCE.getApplicationUser().getUsername());
                            visit.setPatientId(selectedPatient.getId());
                            visit.setPaymentStatus(PaymentStatus.NOT_PAYED.getId());
                            visit.setTillDate(Values.Empty.getValue());
                            visit.setFilename(Values.Empty.getValue());
                            visit.setFilepath(Values.Empty.getValue());
                            visit.setVisitDate(new Date());
                            visit.setFromDoctor(fromDoctor);
                            visit.setFromOrganisation(fromOrganisation);
                            visit.setVisitStatus(Settings.NOT_VISITED);
                            visit.setVisitType(Settings.VISIT_TYPE_CAMED);

                            visitClient.update(visit);
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
//                    }

                    AssignmentService.INSTANCE.getPhysiotherapyFrame().getAnalysSelectionPanel().clearAllSelectionData();
                }
                                                
                // Terapeutic
                if (VISIT_COURSE_SELECTION_PANEL.getTeraCheckBox().isSelected()) {
                    String selectedDoctorNfp = VISIT_COURSE_SELECTION_PANEL.getTerapevtDoctorsBox().getSelectedItem().toString();
                    User account = UserFacade.INSTANCE.findBy(selectedDoctorNfp);
                    try {
                        Visit visit = new Visit();
                        visit.setDoctorId(account.getId());
                        visit.setDepartmentId(Privilege.Terapevt.getId());
                        visit.setAnalysGroup(Values.Unknown.getValue());
                        visit.setAnalysName(Values.Unknown.getValue());
                        visit.setCreatedBy(UserFacade.INSTANCE.getApplicationUser().getUsername());
                        visit.setPatientId(selectedPatient.getId());
                        visit.setPaymentStatus(PaymentStatus.NOT_PAYED.getId());
                        visit.setTillDate(Values.Empty.getValue());
                        visit.setFilename(Values.Empty.getValue());
                        visit.setFilepath(Values.Empty.getValue());
                        visit.setVisitDate(new Date());
                        visit.setFromDoctor(fromDoctor);
                        visit.setFromOrganisation(fromOrganisation);
                        visit.setVisitStatus(Settings.NOT_VISITED);
                        visit.setVisitType(Settings.VISIT_TYPE_CAMED);

                        visitClient.update(visit);
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }

                // Endokrinolog
                if (VISIT_COURSE_SELECTION_PANEL.getEndoCheckBox().isSelected()) {
                    String selectedDoctorNfp = VISIT_COURSE_SELECTION_PANEL.getEndokriDoctorsBox().getSelectedItem().toString();
                    User account = UserFacade.INSTANCE.findBy(selectedDoctorNfp);
                    try {
                        Visit visit = new Visit();
                        visit.setDoctorId(account.getId());
                        visit.setDepartmentId(Privilege.Endokrinolog.getId());
                        visit.setAnalysGroup(Values.Unknown.getValue());
                        visit.setAnalysName(Values.Unknown.getValue());
                        visit.setCreatedBy(UserFacade.INSTANCE.getApplicationUser().getUsername());
                        visit.setPatientId(selectedPatient.getId());
                        visit.setPaymentStatus(PaymentStatus.NOT_PAYED.getId());
                        visit.setTillDate(Values.Empty.getValue());
                        visit.setFilename(Values.Empty.getValue());
                        visit.setFilepath(Values.Empty.getValue());
                        visit.setVisitDate(new Date());
                        visit.setFromDoctor(fromDoctor);
                        visit.setFromOrganisation(fromOrganisation);
                        visit.setVisitStatus(Settings.NOT_VISITED);
                        visit.setVisitType(Settings.VISIT_TYPE_CAMED);

                        visitClient.update(visit);
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }

                // Nevropatolog
                if (VISIT_COURSE_SELECTION_PANEL.getNevroCheckBox().isSelected()) {
                    String selectedDoctorNfp = VISIT_COURSE_SELECTION_PANEL.getNevroDoctorsBox().getSelectedItem().toString();
                    User account = UserFacade.INSTANCE.findBy(selectedDoctorNfp);
                    try {
                        Visit visit = new Visit();
                        visit.setDoctorId(account.getId());
                        visit.setDepartmentId(Privilege.Nevropatolog.getId());
                        visit.setAnalysGroup(Values.Unknown.getValue());
                        visit.setAnalysName(Values.Unknown.getValue());
                        visit.setCreatedBy(UserFacade.INSTANCE.getApplicationUser().getUsername());
                        visit.setPatientId(selectedPatient.getId());
                        visit.setPaymentStatus(PaymentStatus.NOT_PAYED.getId());
                        visit.setTillDate(Values.Empty.getValue());
                        visit.setFilename(Values.Empty.getValue());
                        visit.setFilepath(Values.Empty.getValue());
                        visit.setVisitDate(new Date());
                        visit.setFromDoctor(fromDoctor);
                        visit.setFromOrganisation(fromOrganisation);
                        visit.setVisitStatus(Settings.NOT_VISITED);
                        visit.setVisitType(Settings.VISIT_TYPE_CAMED);

                        visitClient.update(visit);
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }

                // Verte
                if (VISIT_COURSE_SELECTION_PANEL.getVerteCheckBox().isSelected()) {
                    String selectedDoctorNfp = VISIT_COURSE_SELECTION_PANEL.getVerteDoctorsBox().getSelectedItem().toString();
                    User account = UserFacade.INSTANCE.findBy(selectedDoctorNfp);
                    try {
                        Visit visit = new Visit();
                        visit.setDoctorId(account.getId());
                        visit.setDepartmentId(Privilege.Vertebrolog.getId());
                        visit.setAnalysGroup(Values.Unknown.getValue());
                        visit.setAnalysName(Values.Unknown.getValue());
                        visit.setCreatedBy(UserFacade.INSTANCE.getApplicationUser().getUsername());
                        visit.setPatientId(selectedPatient.getId());
                        visit.setPaymentStatus(PaymentStatus.NOT_PAYED.getId());
                        visit.setTillDate(Values.Empty.getValue());
                        visit.setFilename(Values.Empty.getValue());
                        visit.setFilepath(Values.Empty.getValue());
                        visit.setVisitDate(new Date());
                        visit.setFromDoctor(fromDoctor);
                        visit.setFromOrganisation(fromOrganisation);
                        visit.setVisitStatus(Settings.NOT_VISITED);
                        visit.setVisitType(Settings.VISIT_TYPE_CAMED);

                        visitClient.update(visit);
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }

                // Mri
                if (VISIT_COURSE_SELECTION_PANEL.getMriCheckBox().isSelected()) {
//                    HashMap<String, ArrayList<String>> selectedCourses = 
//                            AssignmentService.INSTANCE.getMriFrame().getAnalysSelectionPanel().getAllSelectionData();
                    
                    String selectedDoctorNfp = VISIT_COURSE_SELECTION_PANEL.getMriSoundDoctorBox().getSelectedItem().toString();
                    User account = UserFacade.INSTANCE.findBy(selectedDoctorNfp);
//                    Set<String> selectedGroups = selectedCourses.keySet();
//                    for (String group : selectedGroups) {
//                        ArrayList<String> selectedAnalyses = selectedCourses.get(group);
//                        for (String analys : selectedAnalyses) {
//                            try {
//                                Visit visit = new Visit();
//                                visit.setDoctorId(account.getId());
//                                visit.setDepartmentId(Privilege.MagneticResonanceImaging.getId());
//                                visit.setAnalysGroup(group);
//                                visit.setAnalysName(analys);
//                                visit.setCreatedBy(UserFacade.INSTANCE.getApplicationUser().getUsername());
//                                visit.setPatientId(selectedPatient.getId());
//                                visit.setPaymentStatus(PaymentStatus.NOT_PAYED.getId());
//                                visit.setTillDate(Values.Empty.getValue());
//                                visit.setFromDoctor(fromDoctor);
//                                visit.setFromOrganisation(fromOrganisation);
//                                visit.setFilename(hash.concat(PrivelegyDir.FIZIO_PATH.getDocsTrailor()));
//                                visit.setFilepath(String.valueOf(Privilege.Fizio.getId()).concat("/")
//                                        .concat(String.valueOf(selectedPatient.getId())).concat("/"));
//
//                                visit.setVisitDate(new Date());
//                                visit.setVisitStatus(Settings.NOT_VISITED);
//                                visit.setVisitType(Settings.VISIT_TYPE_CAMED);
//
//                                visitClient.update(visit);
//                            } catch (IOException ioe) {
//                                ioe.printStackTrace();
//                            }
//                        }
//                    }
//
//                    if (selectedGroups.size() == 0) {
                        try {
                            Visit visit = new Visit();
                            visit.setDoctorId(account.getId());
                            visit.setDepartmentId(Privilege.MagneticResonanceImaging.getId());
                            visit.setAnalysGroup(Values.Unknown.getValue());
                            visit.setAnalysName(Values.Unknown.getValue());
                            visit.setCreatedBy(UserFacade.INSTANCE.getApplicationUser().getUsername());
                            visit.setPatientId(selectedPatient.getId());
                            visit.setPaymentStatus(PaymentStatus.NOT_PAYED.getId());
                            visit.setTillDate(Values.Empty.getValue());
                            visit.setFilename(Values.Empty.getValue());
                            visit.setFilepath(Values.Empty.getValue());
                            visit.setVisitDate(new Date());
                            visit.setFromDoctor(fromDoctor);
                            visit.setFromOrganisation(fromOrganisation);
                            visit.setVisitStatus(Settings.NOT_VISITED);
                            visit.setVisitType(Settings.VISIT_TYPE_CAMED);

                            visitClient.update(visit);
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
//                    }

                    AssignmentService.INSTANCE.getMriFrame().getAnalysSelectionPanel().clearAllSelectionData();
                }
                
                hidePanel();
            }
        });
    }
}