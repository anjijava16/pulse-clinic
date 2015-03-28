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
package com.pulse.desktop.view.manager;


import com.pulse.desktop.view.frame.user.AccountInternalFrame;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.table.DefaultTableModel;
import com.pulse.desktop.controller.service.PatientService;
import com.pulse.desktop.view.util.FileManager;
import com.pulse.model.Patient;
import com.pulse.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public final class UIHandlerFacade {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    
    private ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(1);
    
    private static WindowManager WINDOW_MANAGER = WindowManager.getInstance();
    private static UIHandlerFacade UI_HANDLER_FACADE_IMPL = new UIHandlerFacade();
    
    private volatile int WORKING_HOURS_ITERATOR = 1;
    
    private AccountInternalFrame administrationFrame;
    
    private User temporaryAccount;
        
    private String applicationUserName;
    private User applicationUserAccount;
    
    private final int REGISTRY_NUMBER_FIELD        = 0;
    private final int REGISTRY_COMMING_DATE_FIELD  = 1;
    private final int REGISTRY_VIEW_DATE_FIELD     = 2;
    private final int REGISTRY_VISIT_ID_FIELD      = 3;
    private final int REGISTRY_PATIENT_ID_FIELD    = 4;
    private final int REGISTRY_PATIENT_NFP_FIELD   = 5;
    private final int REGISTRY_BIRTHDAY_FIELD      = 6;
    private final int REGISTRY_MOBILE_PHONE_FIELD  = 7;
    private final int REGISTRY_VISIT_COURSE_FIELD  = 8;
    private final int REGISTRY_GROUP_FIELD         = 9;
    private final int REGISTRY_ANALYS_FIELD        = 10;
    private final int REGISTRY_DOCTOR_NAME_FIELD   = 11;
    private final int REGISTRY_PAYING_STATUS_FIELD = 12;
    private final int REGISTRY_PATIENT_TYPE        = 13;
    private final int REGISTRY_VISIT_TYPE_FIELD    = 14;
    private final int REGISTRY_STATUS_FIELD        = 15;
    
    private final int BUSY_NUMBER_FIELD        = 0;
    private final int BUSY_COMMING_DATE_FIELD  = 1;
    private final int BUSY_VIEW_DATE_FIELD     = 2;
    private final int BUSY_VISIT_ID_FIELD      = 3;
    private final int BUSY_PATIENT_ID_FIELD    = 4;
    private final int BUSY_PATIENT_NFP_FIELD   = 5;
    private final int BUSY_BIRTHDAY_FIELD      = 6;
    private final int BUSY_MOBILE_PHONE_FIELD  = 7;
    private final int BUSY_VISIT_COURSE_FIELD  = 8;
    private final int BUSY_GROUP_FIELD         = 9;
    private final int BUSY_ANALYS_FIELD        = 10;
    private final int BUSY_DOCTOR_NAME_FIELD   = 11;
    private final int BUSY_PAYING_STATUS_FIELD = 12;
    private final int BUSY_PATIENT_TYPE        = 13;
    private final int BUSY_VISIT_TYPE_FIELD    = 14;
    private final int BUSY_STATUS_FIELD        = 15;
    
    private final FileManager FILEMANAGER = new FileManager();
    
    private volatile int registraturPatientNumber = 1;
    private volatile int accountingFrameIterator  = 1;
    private volatile int visitFrameIterator       = 1;
    private volatile int journalFrameIterator     = 1;
    private volatile int laboratoryFrameIterator  = 1;
    private volatile int ultrasoundFrameIterator  = 1;
    private volatile int urologyFrameIterator     = 1;
    private volatile int ginecologyFrameIterator  = 1;
    private volatile int stationaryFrameIterator  = 1;
    private volatile int busyHoursIterator        = 1;
    private volatile int patientsFormIterator     = 1;
    private volatile int appointmentIterator      = 1;
        
    private UIHandlerFacade() {
    }
    
    public void updateAppointmentIterator() {
        this.appointmentIterator = 1;
    }
    
    public void decreaseStationaryDepartmentIterator() {
        this.stationaryFrameIterator--;
    }
    
    public void updateStationaryFrameIterator() {
        this.stationaryFrameIterator = 1;
    }
    
    public void decreaseGinecologyDepartmentIterator() {
        this.ginecologyFrameIterator--;
    }
    
    public void updateGinecologyFrameIterator() {
        this.ginecologyFrameIterator = 1;
    }
    
    public void decreaseUrologyDepartmentIterator() {
        this.urologyFrameIterator--;
    }
    
    public void updateUrologyFrameIterator() {
        this.urologyFrameIterator = 1;
    }
    
    public void updateBusyHoursIterator() {
        this.busyHoursIterator = 1;
    }
    
    public void decreaseBusyHoursIterator() {
        this.busyHoursIterator--;
        
        if (this.busyHoursIterator == 0) {
            this.busyHoursIterator = 1;
        }
    }
    
    public void decreaseUltrasoundDepartmentIterator() {
        this.ultrasoundFrameIterator--;
    }
    
    public void updateUltrasoundFrameIterator() {
        this.ultrasoundFrameIterator = 1;
    }
    
    public void decreaseLaboratoryDepartmentIterator() {
        this.laboratoryFrameIterator--;
    }
    
    public void updateLaboratoryFrameIterator() {
        this.laboratoryFrameIterator = 1;
    }
    
    public void updateVisitFrameIterator() {
        this.visitFrameIterator = 1;
    }
    
    public void updatePatientsFormIterator() {
        this.patientsFormIterator = 1;
    }
    
    public void updateAccountingFrameIterator() {
        this.accountingFrameIterator = 1;
    }

    public void setApplicationUserAccount(User applicationUserAccount) {
        this.applicationUserAccount = applicationUserAccount;
    }
    
    public User getApplicationUserAccount() {
        return this.applicationUserAccount;
    }
            
    public void registrateApplicationUser(String applicationUserName) {
        this.applicationUserName = applicationUserName;
    }
    
    public int getNextWorkingHoursIterator() {
        return this.WORKING_HOURS_ITERATOR++;
    }
       
    public static UIHandlerFacade getInstance() {
        return UI_HANDLER_FACADE_IMPL;
    }
    
//    public void updateAccount(User account) {
//        final User accountLink = account;
//        
//        this.EXECUTOR_SERVICE.submit(new Runnable() {
//            @Override
//            public void run() {
//                ACCOUNTS_MANAGEMENT_FACADE.editAccount(accountLink);
//        
//                DefaultTableModel dtm = WINDOW_MANAGER.getAccountInternalFrame().getTableModel();
//                int rowCount = dtm.getRowCount();
//                int rowNumber = 0;
//                for (int x=0; x<rowCount; x++) {
//                    if (Long.valueOf(dtm.getValueAt(x, ACCOUNTS_MANAGEMENT_FACADE.ID_COLUMN).toString()) == accountLink.getId()) {
//                        rowNumber = x;
//                        break;
//                    }
//                }
//
//                dtm.setValueAt(accountLink.getNfp(), rowNumber, 2);
//                dtm.setValueAt(accountLink.getBirthday(), rowNumber, 3);
//                dtm.setValueAt(accountLink.getUsername(), rowNumber, 4);
//                dtm.setValueAt(Privilege.findById(accountLink.getPrivilege()).getName(), rowNumber, 5);
//            }
//        });        
//    }
//    
//    public void updatePatient(Patient patient, final String oldNfp) {
//        final Patient patientLink = patient;
//        
//        this.EXECUTOR_SERVICE.submit(new Runnable() {
//            @Override
//            public void run() {
//                boolean wasUpdated = PatientService.INSTANCE.update(patientLink);
//                
//                WINDOW_MANAGER.getPatientEditFrame().removeLastSelectedPatientFromList(oldNfp);
//                WINDOW_MANAGER.getPatientEditFrame().addPatientToList(patientLink);
//                
//                WINDOW_MANAGER.getPatientRemovingFrame().removeLastSelectedPatientFromList(oldNfp);
//                WINDOW_MANAGER.getPatientRemovingFrame().addPatientToList(patientLink);
//                
//                WINDOW_MANAGER.getPatientCommingRegistrationFrame().removeLastSelectedPatientFromList(oldNfp);
//                WINDOW_MANAGER.getPatientCommingRegistrationFrame().addPatientToList(patientLink);                
//            }
//        });        
//    }
    
    public void removePatientById(long patientId) {
        final long patientIdLink = patientId;
        
        this.EXECUTOR_SERVICE.submit(new Runnable() {
            @Override
            public void run() {
                PatientService.INSTANCE.deleteById(patientIdLink);
            }
        });
    }
    
    public void removeAppointmentById(long id) {
        final long idLink = id;
        
        this.EXECUTOR_SERVICE.submit(new Runnable() {
            @Override
            public void run() {
                DefaultTableModel model = WINDOW_MANAGER.getAppointmentFrame().getTableHolder().getModel();
                int rowSize = model.getRowCount();
                for (int x=0; x<rowSize; x++) {
                    if (model.getValueAt(x, 2).equals(String.valueOf(idLink))) {
                        model.removeRow(x);
                        return;
                    }
                }

                appointmentIterator--;
            }
        });
    }
    
    public void removePatientFormById(long id) {
        final long idLink = id;
        
        this.EXECUTOR_SERVICE.submit(new Runnable() {
            @Override
            public void run() {
                DefaultTableModel model = WINDOW_MANAGER.getPatientRecordForm().getTableHolder().getModel();
                int rowSize = model.getRowCount();
                for (int x=0; x<rowSize; x++) {
                    if (model.getValueAt(x, 2).equals(String.valueOf(idLink))) {
                        model.removeRow(x);
                        return;
                    }
                }

                patientsFormIterator--;
            }
        });        
    }
    
    public void remoreJournalById(long id) {
        final long idLink = id;
        
        this.EXECUTOR_SERVICE.submit(new Runnable() {
            @Override
            public void run() {
                DefaultTableModel model = WINDOW_MANAGER.getJournalFrame().getTableHolder().getModel();
                int rowSize = model.getRowCount();
                for (int x=0; x<rowSize; x++) {
                    if (model.getValueAt(x, 2).equals(String.valueOf(idLink))) {
                        model.removeRow(x);
                        return;
                    }
                }

                journalFrameIterator--;
            }
        });        
    }
    
    public void removePatient(Patient patient) {
        final Patient patientLink = patient;
        
        this.EXECUTOR_SERVICE.submit(new Runnable() {
            @Override
            public void run() {
                PatientService.INSTANCE.deleteById(patientLink.getId());
            }
        });        
    }
    
    public void addPatient(Patient patient) {   
        final Patient patientLink = patient;
                
        this.EXECUTOR_SERVICE.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    PatientService.INSTANCE.getPatientList().put(patientLink);
                } catch (InterruptedException ex) {
                    LOGGER.info("Error while adding new patient. Error message: '" + ex.getMessage() 
                            + "'. Localized message: '" + ex.getLocalizedMessage() + "'.");
                }
            }
        });                
    }
    
//    public boolean deleteAccount(long accoundId) {
//        ACCOUNTS_MANAGEMENT_FACADE.removeAccountById(accoundId);
//        
//        WINDOW_MANAGER.getAccountInternalFrame().clearTable();
//                        
//        LinkedBlockingQueue<Account> accountsList = ACCOUNTS_MANAGEMENT_FACADE.getAccountsList();
//        int step = 1;
//        for (Account account : accountsList) {
//            int ptr = 0;
//            String[] accountData = new String[6];
//            accountData[ptr++] = String.valueOf(step++);
//            accountData[ptr++] = String.valueOf(account.getId());
//            accountData[ptr++] = account.getNfp();
//            accountData[ptr++] = account.getBirthday();
//            accountData[ptr++] = account.getAccountName();
//            accountData[ptr++] = Privilege.findById(account.getPrivilege()).getName();
//            
//            if (this.administrationFrame == null) {
//                this.administrationFrame = WINDOW_MANAGER.getMainFrame().getAdministrationFrame();
//            }
//            
//            this.administrationFrame.getTableModel().addRow(accountData);
//        }
//        
//        return false;
//    }
        
    private void changeVisitTableRowData(long visitId, 
                                         DefaultTableModel dtm, 
                                         int columnNumber, 
                                         int changingColumnNumber, 
                                         String value) {
        final long visitIdConst = visitId;
        final DefaultTableModel dtmConst = dtm;
        final int columnNumberConst = columnNumber;
        final int changingColumnNumberConst = changingColumnNumber;
        final String valueConst = value;
        
        this.EXECUTOR_SERVICE.submit(new Runnable() {
            @Override
            public void run() {
                int rowCount = dtmConst.getRowCount();
                for (int x=0; x<rowCount; x++) {
                    if (dtmConst.getValueAt(x, columnNumberConst).equals(String.valueOf(visitIdConst))) {
                        dtmConst.setValueAt(valueConst, x, changingColumnNumberConst);
                        break;
                    }
                }
            }
        });        
    }
        
    public void updateRegsitryPatientCounter() {
        this.registraturPatientNumber = 1;
    }
    
    public void deletePatientVisitById(long id) {
        final long idLink = id;
        
        this.EXECUTOR_SERVICE.submit(new Runnable() {
            @Override
            public void run() {
                registraturPatientNumber = 1;
        
                int rowCount = WINDOW_MANAGER.getRegistryFrame().getTableHolder().getModel().getRowCount();
                for (int x=0;x<rowCount;x++) {
                    if (WINDOW_MANAGER.getRegistryFrame().getTableHolder().getModel().getValueAt(x, REGISTRY_VISIT_ID_FIELD).equals(String.valueOf(idLink))) {
                        WINDOW_MANAGER.getRegistryFrame().getTableHolder().getModel().removeRow(x);
                        break;
                    }
                }

                rowCount = WINDOW_MANAGER.getLaboratoryFrame().getTableHolder().getModel().getRowCount();
                for (int x=0;x<rowCount;x++) {
                    if (WINDOW_MANAGER.getLaboratoryFrame().getTableHolder().getModel().getValueAt(x, BUSY_VISIT_ID_FIELD).equals(String.valueOf(idLink))) {
                        WINDOW_MANAGER.getLaboratoryFrame().getTableHolder();                        
                        break;
                    }
                }

                rowCount = WINDOW_MANAGER.getUltrasoundFrame().getTableHolder().getModel().getRowCount();
                for (int x=0;x<rowCount;x++) {
                    if (WINDOW_MANAGER.getUltrasoundFrame().getTableHolder().getModel().getValueAt(x, BUSY_VISIT_ID_FIELD).equals(String.valueOf(idLink))) {
                        WINDOW_MANAGER.getUltrasoundFrame().getTableHolder().getModel().removeRow(x);
                        break;
                    }
                }

                rowCount = WINDOW_MANAGER.getUrologyFrame().getTableHolder().getModel().getRowCount();
                for (int x=0;x<rowCount;x++) {
                    if (WINDOW_MANAGER.getUrologyFrame().getTableHolder().getModel().getValueAt(x, BUSY_VISIT_ID_FIELD).equals(String.valueOf(idLink))) {
                        WINDOW_MANAGER.getUrologyFrame().getTableHolder().getModel().removeRow(x);
                        break;
                    }
                }

                rowCount = WINDOW_MANAGER.getGinecologyFrame().getTableHolder().getModel().getRowCount();
                for (int x=0;x<rowCount;x++) {
                    if (WINDOW_MANAGER.getGinecologyFrame().getTableHolder().getModel().getValueAt(x, BUSY_VISIT_ID_FIELD).equals(String.valueOf(idLink))) {
                        WINDOW_MANAGER.getGinecologyFrame().getTableHolder().getModel().removeRow(x);
                        break;
                    }
                }

                rowCount = WINDOW_MANAGER.getStationaryFrame().getTableHolder().getModel().getRowCount();
                for (int x=0;x<rowCount;x++) {
                    if (WINDOW_MANAGER.getStationaryFrame().getTableHolder().getModel().getValueAt(x, BUSY_VISIT_ID_FIELD).equals(String.valueOf(idLink))) {
                        WINDOW_MANAGER.getStationaryFrame().getTableHolder().getModel().removeRow(x);
                        break;
                    }
                }
                
                rowCount = WINDOW_MANAGER.getSurgeryFrame().getTableHolder().getModel().getRowCount();
                for (int x=0;x<rowCount;x++) {
                    if (WINDOW_MANAGER.getSurgeryFrame().getTableHolder().getModel().getValueAt(x, BUSY_VISIT_ID_FIELD).equals(String.valueOf(idLink))) {
                        WINDOW_MANAGER.getSurgeryFrame().getTableHolder().getModel().removeRow(x);
                        break;
                    }
                }
                
                rowCount = WINDOW_MANAGER.getOculistFrame().getTableHolder().getModel().getRowCount();
                for (int x=0;x<rowCount;x++) {
                    if (WINDOW_MANAGER.getOculistFrame().getTableHolder().getModel().getValueAt(x, BUSY_VISIT_ID_FIELD).equals(String.valueOf(idLink))) {
                        WINDOW_MANAGER.getOculistFrame().getTableHolder().getModel().removeRow(x);
                        break;
                    }
                }
                
                rowCount = WINDOW_MANAGER.getPhysiotherapyFrame().getTableHolder().getModel().getRowCount();
                for (int x=0;x<rowCount;x++) {
                    if (WINDOW_MANAGER.getPhysiotherapyFrame().getTableHolder().getModel().getValueAt(x, BUSY_VISIT_ID_FIELD).equals(String.valueOf(idLink))) {
                        WINDOW_MANAGER.getPhysiotherapyFrame().getTableHolder().getModel().removeRow(x);
                        break;
                    }
                }
                
                rowCount = WINDOW_MANAGER.getTherapeuticFrame().getTableHolder().getModel().getRowCount();
                for (int x=0;x<rowCount;x++) {
                    if (WINDOW_MANAGER.getTherapeuticFrame().getTableHolder().getModel().getValueAt(x, BUSY_VISIT_ID_FIELD).equals(String.valueOf(idLink))) {
                        WINDOW_MANAGER.getTherapeuticFrame().getTableHolder().getModel().removeRow(x);
                        break;
                    }
                }
                
                rowCount = WINDOW_MANAGER.getEndoFrame().getTableHolder().getModel().getRowCount();
                for (int x=0;x<rowCount;x++) {
                    if (WINDOW_MANAGER.getEndoFrame().getTableHolder().getModel().getValueAt(x, BUSY_VISIT_ID_FIELD).equals(String.valueOf(idLink))) {
                        WINDOW_MANAGER.getEndoFrame().getTableHolder().getModel().removeRow(x);
                        break;
                    }
                }
                
                rowCount = WINDOW_MANAGER.getNeurologistFrame().getTableHolder().getModel().getRowCount();
                for (int x=0;x<rowCount;x++) {
                    if (WINDOW_MANAGER.getNeurologistFrame().getTableHolder().getModel().getValueAt(x, BUSY_VISIT_ID_FIELD).equals(String.valueOf(idLink))) {
                        WINDOW_MANAGER.getNeurologistFrame().getTableHolder().getModel().removeRow(x);
                        break;
                    }
                }
                
                rowCount = WINDOW_MANAGER.getVertebrologistFrame().getTableHolder().getModel().getRowCount();
                for (int x=0;x<rowCount;x++) {
                    if (WINDOW_MANAGER.getVertebrologistFrame().getTableHolder().getModel().getValueAt(x, BUSY_VISIT_ID_FIELD).equals(String.valueOf(idLink))) {
                        WINDOW_MANAGER.getVertebrologistFrame().getTableHolder().getModel().removeRow(x);
                        break;
                    }
                }
            }
        });
    }
        
//    public void addAccount(Account account) {
//        final Account accountLink = account;
//        this.EXECUTOR_SERVICE.submit(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    ACCOUNTS_MANAGEMENT_FACADE.addAccount(accountLink);            
//
//                    int ptr = 0;
//                    String[] accountData = new String[6];
//                    accountData[ptr++] = String.valueOf(ACCOUNTS_MANAGEMENT_FACADE.getAccountNumber());
//                    accountData[ptr++] = String.valueOf(accountLink.getId());
//                    accountData[ptr++] = accountLink.getNfp();
//                    accountData[ptr++] = accountLink.getBirthday();
//                    accountData[ptr++] = accountLink.getAccountName();
//                    accountData[ptr++] = Privilege.findById(accountLink.getPrivilege()).getName();
//
//                    if (administrationFrame == null) {
//                        administrationFrame = WINDOW_MANAGER.getMainFrame().getAdministrationFrame();
//                    }
//
//                    administrationFrame.getTableModel().addRow(accountData);
//
//                    if (accountLink.getAccountName().equals(ACCOUNTS_MANAGEMENT_FACADE.getApplicationUserLogin())) {
//                        ACCOUNTS_MANAGEMENT_FACADE.setApplicationUser(accountLink);
//                    }
//
//                    if (accountLink.getNfp().equals(applicationUserName)) {
//                        applicationUserAccount = accountLink;
//                    }
//
//                    if (accountLink.getPrivilege() == Privilege.Urology.getId()
//                            || accountLink.getPrivilege() == Privilege.Ultrasound.getId()
//                            || accountLink.getPrivilege() == Privilege.Ginecology.getId()
//                            || accountLink.getPrivilege() == Privilege.Stationary.getId()
//                            || accountLink.getPrivilege() == Privilege.Hirurgiya.getId()
//                            || accountLink.getPrivilege() == Privilege.Okulist.getId()
//                            || accountLink.getPrivilege() == Privilege.Fizio.getId()
//                            || accountLink.getPrivilege() == Privilege.Terapevt.getId()
//                            || accountLink.getPrivilege() == Privilege.Endokrinolog.getId()
//                            || accountLink.getPrivilege() == Privilege.Nevropatolog.getId()
//                            || accountLink.getPrivilege() == Privilege.Vertebrolog.getId()
//                            || accountLink.getPrivilege() == Privilege.MagneticResonanceImaging.getId()) {
//                        WindowManager.getInstance().getBookKeepingFrame().addDoctor(accountLink.getNfp());
//                    }
//
//                    switch ((int) accountLink.getPrivilege()) {
//                        case Settings.UROLOGY:
//                            WINDOW_MANAGER.getPatientCommingRegistrationFrame().getVisitCoursePanel().getUrologyDoctorsBox().addItem(accountLink.getNfp());
//                            break;
//
//                        case Settings.GINECOLOG:
//                            WINDOW_MANAGER.getPatientCommingRegistrationFrame().getVisitCoursePanel().getGinecologyDoctorsBox().addItem(accountLink.getNfp());
//                            break;
//
//                        case Settings.ULTRASOUND:
//                            WINDOW_MANAGER.getPatientCommingRegistrationFrame().getVisitCoursePanel().getUltraSoundDoctorsBox().addItem(accountLink.getNfp());
//                            break;
//
//                        case Settings.STATIONARY:
//                            WINDOW_MANAGER.getPatientCommingRegistrationFrame().getVisitCoursePanel().getStationaryDoctorsBox().addItem(accountLink.getNfp());
//                            break;
//
//                        case Settings.HIRURGIYA:
//                            WINDOW_MANAGER.getPatientCommingRegistrationFrame().getVisitCoursePanel().getHirurgiyaDoctorsBox().addItem(accountLink.getNfp());
//                            break;
//                            
//                        case Settings.OKULIST:
//                            WINDOW_MANAGER.getPatientCommingRegistrationFrame().getVisitCoursePanel().getOkulistDoctorsBox().addItem(accountLink.getNfp());
//                            break;
//                            
//                        case Settings.FIZIO:
//                            WINDOW_MANAGER.getPatientCommingRegistrationFrame().getVisitCoursePanel().getFizioDoctorsBox().addItem(accountLink.getNfp());
//                            break;
//                                                        
//                        case Settings.TERAPEVT:
//                            WINDOW_MANAGER.getPatientCommingRegistrationFrame().getVisitCoursePanel().getTerapevtDoctorsBox().addItem(accountLink.getNfp());
//                            break;
//                            
//                        case Settings.ENDOKRINOLOG:
//                            WINDOW_MANAGER.getPatientCommingRegistrationFrame().getVisitCoursePanel().getEndokriDoctorsBox().addItem(accountLink.getNfp());
//                            break;
//                            
//                        case Settings.NEVRO:
//                            WINDOW_MANAGER.getPatientCommingRegistrationFrame().getVisitCoursePanel().getNevroDoctorsBox().addItem(accountLink.getNfp());
//                            break;
//                            
//                        case Settings.VERTE:
//                            WINDOW_MANAGER.getPatientCommingRegistrationFrame().getVisitCoursePanel().getVerteDoctorsBox().addItem(accountLink.getNfp());
//                            break;
//                            
//                        case Settings.MRI:
//                            WINDOW_MANAGER.getPatientCommingRegistrationFrame().getVisitCoursePanel().getMriSoundDoctorBox().addItem(accountLink.getNfp());
//                            break;
//                            
//                        default:
//                            break;
//                    }
//                } catch (NullPointerException npe) {
//                    LOGGER.error("Invalid login and password. Error message body: '" + npe.getMessage() + "'. Localized message: '" + npe.getLocalizedMessage() + "'.");
//                    JOptionPane.showMessageDialog(null, "Неверная комбинация логина и пароля.", "Ошибка аутентификации", JOptionPane.ERROR_MESSAGE);
//                    System.exit(100);
//                }   
//            }
//        });
//    }
    
}