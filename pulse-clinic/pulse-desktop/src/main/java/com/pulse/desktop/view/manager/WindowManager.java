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


import com.pulse.desktop.view.frame.MainFrame;
import com.pulse.desktop.view.frame.childframes.BookKeepingFrame;
import com.pulse.desktop.view.frame.childframes.EndoFrame;
import com.pulse.desktop.view.frame.childframes.GinecologyFrame;
import com.pulse.desktop.view.frame.childframes.JournalFrame;
import com.pulse.desktop.view.frame.childframes.LaboratoryFrame;
import com.pulse.desktop.view.frame.childframes.MagneticResonanceImagingFrame;
import com.pulse.desktop.view.frame.childframes.NeurologistFrame;
import com.pulse.desktop.view.frame.childframes.NextVisitFrame;
import com.pulse.desktop.view.frame.childframes.OculistFrame;
import com.pulse.desktop.view.frame.childframes.OrganisationsFrame;
import com.pulse.desktop.view.frame.childframes.PatientAppointmentFrame;
import com.pulse.desktop.view.frame.childframes.PatientRecordFrame;
import com.pulse.desktop.view.frame.childframes.PatientRoomFrame;
import com.pulse.desktop.view.frame.childframes.PhysiotherapyFrame;
import com.pulse.desktop.view.frame.childframes.RegistryFrame;
import com.pulse.desktop.view.frame.childframes.StationaryFrame;
import com.pulse.desktop.view.frame.childframes.StatisticFrame;
import com.pulse.desktop.view.frame.childframes.SurgeryFrame;
import com.pulse.desktop.view.frame.childframes.TherapeuticFrame;
import com.pulse.desktop.view.frame.childframes.TicketWindowFrame;
import com.pulse.desktop.view.frame.childframes.UltrasoundFrame;
import com.pulse.desktop.view.frame.childframes.UrologyFrame;
import com.pulse.desktop.view.frame.childframes.VertebrologistFrame;

import com.pulse.desktop.view.registratur.PatientCommingRegistrationFrame;
import com.pulse.desktop.view.registratur.PatientUpdateFrame;
import com.pulse.desktop.view.registratur.PatienCreationFrame;
import com.pulse.desktop.view.registratur.PatientRemovingFrame;
import com.pulse.desktop.view.frame.internal.visit.CreateVisitDateFrame;
import com.pulse.desktop.view.frame.user.AccountChangingFrame;
import com.pulse.desktop.view.frame.user.AccountCreationFrame;
import com.pulse.desktop.view.frame.user.AccountInternalFrame;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public final class WindowManager {

    private final static WindowManager WINDOW_MANAGER = new WindowManager();
    
    private AccountInternalFrame administrationFrame = new AccountInternalFrame("Пользователи");
    private AccountCreationFrame accountCreationFrame = new AccountCreationFrame("Создание", administrationFrame.getTableHolder());
    private AccountChangingFrame accountChangingFrame = new AccountChangingFrame("Изменение", administrationFrame.getTableHolder());
            
    private final PatienCreationFrame PATIENT_CREATION_FRAME 
            = new PatienCreationFrame("Пациент");
    
    private final PatientUpdateFrame PATIENT_EDIT_FRAME
            = new PatientUpdateFrame("Изменение");
    
    private final PatientRemovingFrame PATIENT_REMOVING_FRAME
            = new PatientRemovingFrame("Удаление");
            
    private final PatientCommingRegistrationFrame PATIENT_COMMING_REGISTRATION_FRAME
            = new PatientCommingRegistrationFrame();
                
    //==========================================================================
    
    private final TicketWindowFrame PAYMONT_FRAME = new TicketWindowFrame();
                
    private final CreateVisitDateFrame CREATE_DATE_VISIT_FRAME
            = new CreateVisitDateFrame("Приди проверься");
        
    // ---------------------------------------------------------------- NEW
    private final LaboratoryFrame LABORATORY = new LaboratoryFrame();
    private final RegistryFrame REGISTRY = new RegistryFrame();
    private final UrologyFrame UROLOGY = new UrologyFrame();
    private final GinecologyFrame GINECOLOGY = new GinecologyFrame();
    private final UltrasoundFrame ULTRASOUND = new UltrasoundFrame();
    private final StationaryFrame STATIONARY = new StationaryFrame();
    private final SurgeryFrame SURGERY = new SurgeryFrame();
    private final OculistFrame OCULIST = new OculistFrame();
    private final PhysiotherapyFrame PHYSIO = new PhysiotherapyFrame();
    private final TherapeuticFrame THERAPEUTIC = new TherapeuticFrame();
    private final EndoFrame ENDO = new EndoFrame();
    private final NeurologistFrame NEURO = new NeurologistFrame();
    private final VertebrologistFrame VERTE = new VertebrologistFrame();
    
    private final JournalFrame JOURNAL_FRAME = new JournalFrame();
    
    private final BookKeepingFrame BOOK_KEEPING_FRAME = new BookKeepingFrame();
    
    private final MagneticResonanceImagingFrame MRI = new MagneticResonanceImagingFrame();
    
    private final PatientRecordFrame PATIENT_RECORD_FORM = new PatientRecordFrame();
    private final PatientAppointmentFrame PATIENT_APPOINTMENT_FRAME = new PatientAppointmentFrame();
    private final OrganisationsFrame ORGANISATION_FRAME = new OrganisationsFrame();
    private final StatisticFrame STATISTIC_FRAME = new StatisticFrame();
    
    private final NextVisitFrame NEXT_VISIT_FRAME = new NextVisitFrame();
    private final PatientRoomFrame PATIENT_ROOM_FRAME = new PatientRoomFrame();
    // ---------------------------------------------------------------- NEW
    
    private MainFrame mainFrame;
    
    private WindowManager() {
    }

    public AccountCreationFrame getAccountCreationFrame() {
        return accountCreationFrame;
    }

    public AccountChangingFrame getAccountChangingFrame() {
        return accountChangingFrame;
    }
            
    public PatientRoomFrame getPatientRoomFrame() {
        return this.PATIENT_ROOM_FRAME;
    }
    
    public TicketWindowFrame getTicketWindowFrame() {
        return this.PAYMONT_FRAME;
    }
    
    public PatientRecordFrame getPatientRecordForm() {
        return this.PATIENT_RECORD_FORM;
    }
    
    public OrganisationsFrame getOrganisationsFrame() {
        return this.ORGANISATION_FRAME;
    }
    
    public StatisticFrame getStatisticFrame() {
        return this.STATISTIC_FRAME;
    }
    
    public MagneticResonanceImagingFrame getMriFrame() {
        return this.MRI;
    }
            
    public PatientRemovingFrame getPatientRemovingFrame() {
        return this.PATIENT_REMOVING_FRAME;
    }
        
    public PatientUpdateFrame getPatientEditFrame() {
        return this.PATIENT_EDIT_FRAME;
    }
    
    public AccountInternalFrame getAccountInternalFrame() {
        return this.administrationFrame;
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
    
    public StationaryFrame getStationaryFrame() {
        return this.STATIONARY;
    }
    
    public CreateVisitDateFrame getCreateVisitDateFrame() {
        return this.CREATE_DATE_VISIT_FRAME;
    }
    
    public NextVisitFrame getVisitFrame() {
        return this.NEXT_VISIT_FRAME;
    }
    
    public BookKeepingFrame getBookKeepingFrame() {
        return this.BOOK_KEEPING_FRAME;
    }
    
    public JournalFrame getJournalFrame() {
        return this.JOURNAL_FRAME;
    }
    
    public PatientAppointmentFrame getAppointmentFrame() {
        return this.PATIENT_APPOINTMENT_FRAME;
    }
        
    public GinecologyFrame getGinecologyFrame() {
        return this.GINECOLOGY;
    }
    
    public UltrasoundFrame getUltrasoundFrame() {
        return this.ULTRASOUND;
    }

    public PatientCommingRegistrationFrame getPatientCommingRegistrationFrame() {
        return PATIENT_COMMING_REGISTRATION_FRAME;
    }    

    public OculistFrame getOculistFrame() {
        return this.OCULIST;
    }
    
    public SurgeryFrame getSurgeryFrame() {
        return this.SURGERY;
    }
    
    public PhysiotherapyFrame getPhysiotherapyFrame() {
        return this.PHYSIO;
    }
    
    public EndoFrame getEndoFrame() {
        return this.ENDO;
    }
    
    public NeurologistFrame getNeurologistFrame() {
        return this.NEURO;
    }
    
    public VertebrologistFrame getVertebrologistFrame() {
        return this.VERTE;
    }
    
    public TherapeuticFrame getTherapeuticFrame() {
        return this.THERAPEUTIC;
    }
                
    public static WindowManager getInstance() {        
        return WINDOW_MANAGER;
    }
    
    public PatienCreationFrame getPatientCreationFrame() {
        return this.PATIENT_CREATION_FRAME;
    }
    
    public LaboratoryFrame getLaboratoryFrame() {
        return this.LABORATORY;
    }
    
    public RegistryFrame getRegistryFrame() {
        return this.REGISTRY;
    }
    
    public UrologyFrame getUrologyFrame() {
        return this.UROLOGY;
    }
}
