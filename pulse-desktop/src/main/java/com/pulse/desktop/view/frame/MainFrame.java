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
package com.pulse.desktop.view.frame;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.view.frame.childframes.assignment.AssignmentService;
import com.pulse.desktop.view.manager.WindowManager;
import com.pulse.desktop.view.util.Settings;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class MainFrame {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final WindowManager WINDOW_MANAGER = WindowManager.getInstance();
        
    private JFrame mainFrame;
    private final JDesktopPane desktopPane = new JDesktopPane();
        
    private final JMenuBar MENU_BAR = new JMenuBar();
    private final JMenu VIEW_MENU = new JMenu("Вид");
    
    private final JMenu ADMIN_BRANCH = new JMenu("Управление");
    private final JMenu BRANCH_MENU = new JMenu("Отделы");
    private final JMenu STATISTIC_MENU = new JMenu("Отчеты");
    
    private final JCheckBoxMenuItem PATIENT_ROOM_M_ITEM = new JCheckBoxMenuItem("Палаты");
    private final JCheckBoxMenuItem ORGANISATIONS_M_ITEM = new JCheckBoxMenuItem("Организации");
    private final JCheckBoxMenuItem ADMIN_M_ITEM = new JCheckBoxMenuItem("Пользователи");
    private final JCheckBoxMenuItem REGISTRY_M_ITEM = new JCheckBoxMenuItem("Регистратура");
    private final JCheckBoxMenuItem LABORATORY_M_ITEM = new JCheckBoxMenuItem("Лаборатория");
    private final JCheckBoxMenuItem UROLOGY_M_ITEM = new JCheckBoxMenuItem("Урология");
    private final JCheckBoxMenuItem GINECOLOGY_M_ITEM = new JCheckBoxMenuItem("Гинекология");
    private final JCheckBoxMenuItem JOURNAL_M_ITEM = new JCheckBoxMenuItem("Журналы");
    private final JCheckBoxMenuItem ULTRASOUND_M_ITEM = new JCheckBoxMenuItem("УЗИ");
    private final JCheckBoxMenuItem CHECK_M_ITEM = new JCheckBoxMenuItem("Приди проверься");
    private final JCheckBoxMenuItem MONEY_M_ITEM = new JCheckBoxMenuItem("Бухгалтерия");
    private final JCheckBoxMenuItem HOSPITAL_M_ITEM = new JCheckBoxMenuItem("Стационар");
    private final JCheckBoxMenuItem PAYMONT_M_ITEM = new JCheckBoxMenuItem("Касса");
    private final JCheckBoxMenuItem HIRURGIYA_M_ITEM = new JCheckBoxMenuItem("Хирургия");
    private final JCheckBoxMenuItem OCULIST_M_ITEM = new JCheckBoxMenuItem("Окулист");
    private final JCheckBoxMenuItem FIZIO_M_ITEM = new JCheckBoxMenuItem("Физиотерапевт");
    private final JCheckBoxMenuItem TERAPEVT_M_ITEM = new JCheckBoxMenuItem("Терапевт");
    private final JCheckBoxMenuItem ENDOKRINOLOG_M_ITEM = new JCheckBoxMenuItem("Эндокринолог");
    private final JCheckBoxMenuItem NEVROPATOLOG_M_ITEM = new JCheckBoxMenuItem("Невропатолог");
    private final JCheckBoxMenuItem VERTEBROLOG_M_ITEM = new JCheckBoxMenuItem("Вертебролог");
    private final JCheckBoxMenuItem MRI_M_ITEM = new JCheckBoxMenuItem("МРТ");
    private final JCheckBoxMenuItem STATISTIC_M_ITEM = new JCheckBoxMenuItem("Статистика");

    private volatile int WIDTH = 985;
    private volatile int HEIGHT = 680;
    
    private final int FRAME_FONT_SIZE = 10;
    private final Font FRAME_FONT = new Font("Dialog", Font.BOLD, this.FRAME_FONT_SIZE);
            
    public MainFrame() {        
        initializeFrame();
        
        intializeMenu();
        intializeToolBar();
        
        addAllActionListeners();
    }
    
    public void setVisible(boolean visibility) {
        this.mainFrame.setVisible(visibility);
    }
    
    private void setIcon() {
        BufferedImage appIcon = getIconImage();
        if (appIcon != null) {
            this.mainFrame.setIconImage(appIcon);
        }
    }
    
    public void setApplicationUser(String appUserName) {
        this.mainFrame.setTitle(Settings.APPLICATION_VERSION.concat(appUserName));
    }

    private BufferedImage getIconImage() {
        BufferedImage iconImage = null;
        final File iconFile = new File("bckg/icon.png");

        try {
            iconImage = ImageIO.read(iconFile);
        } catch (IOException ioe) {
            this.LOGGER.error(ExceptionUtils.getFullStackTrace(ioe));
        }

        return iconImage;
    }
    
    public void enableMriFrame(boolean enabled) {
        this.MRI_M_ITEM.setEnabled(enabled);
    }
    
    public void enableStatisticFrame(boolean enabled) {
        this.STATISTIC_M_ITEM.setEnabled(enabled);
    }
    
    public void enableVerteFrame(boolean enabled) {
        this.VERTEBROLOG_M_ITEM.setEnabled(enabled);
    }
    
    public void enableNevroFrame(boolean enabled) {
        this.NEVROPATOLOG_M_ITEM.setEnabled(enabled);
    }
    
    public void enableEndoFrame(boolean enabled) {
        this.ENDOKRINOLOG_M_ITEM.setEnabled(enabled);
    }
    
    public void enableTeraFrame(boolean enabled) {
        this.TERAPEVT_M_ITEM.setEnabled(enabled);
    }
    
    public void enableFizioFrame(boolean enabled) {
        this.FIZIO_M_ITEM.setEnabled(enabled);
    }
    
    public void enableOcuFrame(boolean enabled) {
        this.OCULIST_M_ITEM.setEnabled(enabled);
    }
    
    public void enableHirurgiyaFrame(boolean enabled) {
        this.HIRURGIYA_M_ITEM.setEnabled(enabled);
    }
    
    public void enableTicketFrame(boolean enabled) {
        this.PAYMONT_M_ITEM.setEnabled(enabled);
    }
    
    public void enableHospitalFrame(boolean enabled) {
        this.HOSPITAL_M_ITEM.setEnabled(enabled);
    }
    
    public void enablePaymentFrame(boolean enabled) {
        this.PAYMONT_M_ITEM.setEnabled(enabled);
    }
        
    public void enableMoneyFrame(boolean enabled) {
        this.MONEY_M_ITEM.setEnabled(enabled);
    }
            
    public void enableCheckFrame(boolean enabled) {
        this.CHECK_M_ITEM.setEnabled(enabled);
    }
            
    public void enableUltraSoundFrame(boolean enabled) {
        this.ULTRASOUND_M_ITEM.setEnabled(enabled);
    }
            
    public void enableJournalFrame(boolean enabled) {
        this.JOURNAL_M_ITEM.setEnabled(enabled);
    }
    
    public void enableGinecologyFrame(boolean enabled) {
        this.GINECOLOGY_M_ITEM.setEnabled(enabled);
    } 
            
    public void enableUrologyFrame(boolean enabled) {
        this.UROLOGY_M_ITEM.setEnabled(enabled);
    }  
        
    public void enableLaboratoryFrame(boolean enabled) {
        this.LABORATORY_M_ITEM.setEnabled(enabled);
    }        
            
    public void enableAdministrationFrame(boolean enabled) {
        this.ADMIN_M_ITEM.setEnabled(enabled);
    }
    
    public void enableOrganisationFrame(boolean enabled) {
        this.ORGANISATIONS_M_ITEM.setEnabled(enabled);
    }
    
    public void enablePatientRoomFrame(boolean enabled) {
        this.PATIENT_ROOM_M_ITEM.setEnabled(enabled);
    }
    
    public void enableRegistrationFrame(boolean enabled) {
        this.REGISTRY_M_ITEM.setEnabled(enabled);
    }
    
    /**
     * This function will place main frame to the center of screen.
     */
    private void frameToScreenCenter() {
        // Get the size of the screen
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        // Determine the new location of the window
        int x = (dimension.width - this.WIDTH)/2;
        int y = (dimension.height - HEIGHT)/2;

        // Move the window
        this.mainFrame.setLocation(x, y);
    }
    
    private void addAllActionListeners() {
        this.PATIENT_ROOM_M_ITEM.addActionListener(ae -> {
            if (! WINDOW_MANAGER.getPatientRoomFrame().isVisible()) {
                WINDOW_MANAGER.getPatientRoomFrame().setVisible(true);
            }
            else {
                WINDOW_MANAGER.getPatientRoomFrame().setVisible(false);
            }
        });
        
        this.ORGANISATIONS_M_ITEM.addActionListener(ae -> {
            if (! WINDOW_MANAGER.getOrganisationsFrame().isVisible()) {
                WINDOW_MANAGER.getOrganisationsFrame().setVisible(true);
            }
            else {
                WINDOW_MANAGER.getOrganisationsFrame().setVisible(false);
            }
        });
        
        this.REGISTRY_M_ITEM.addActionListener(ae -> {
            if (! WINDOW_MANAGER.getRegistryFrame().isVisible()) {
                WINDOW_MANAGER.getRegistryFrame().setVisible(true);
            }
            else {
                WINDOW_MANAGER.getRegistryFrame().setVisible(false);
            }
        });

        this.ADMIN_M_ITEM.addActionListener(ae -> {
            if (! WINDOW_MANAGER.getAccountInternalFrame().isVisible()) {
                WINDOW_MANAGER.getAccountInternalFrame().setVisible(true);
            }
            else {
                WINDOW_MANAGER.getAccountInternalFrame().setVisible(false);
            }
        });
        
        this.UROLOGY_M_ITEM.addActionListener(ae -> {
            if (! WINDOW_MANAGER.getUrologyFrame().isVisible()) {
                WINDOW_MANAGER.getUrologyFrame().setVisible(true);
            }
            else {
                WINDOW_MANAGER.getUrologyFrame().setVisible(false);
            }
        });
        
        this.GINECOLOGY_M_ITEM.addActionListener(ae -> {
            if (! WINDOW_MANAGER.getGinecologyFrame().isVisible()) {
                WINDOW_MANAGER.getGinecologyFrame().setVisible(true);
            }
            else {
                WINDOW_MANAGER.getGinecologyFrame().setVisible(false);
            }
        });

        this.LABORATORY_M_ITEM.addActionListener(ae -> {
            if (!WINDOW_MANAGER.getLaboratoryFrame().isVisible()) {
                WINDOW_MANAGER.getLaboratoryFrame().setVisible(true);
            }
            else {
                WINDOW_MANAGER.getLaboratoryFrame().setVisible(false);
            }
        });
                
        this.ULTRASOUND_M_ITEM.addActionListener(ae -> {
            if (! WINDOW_MANAGER.getUltrasoundFrame().isVisible()) {
                WINDOW_MANAGER.getUltrasoundFrame().setVisible(true);
            }
            else {
                WINDOW_MANAGER.getUltrasoundFrame().setVisible(false);
            }
        });
        
        this.JOURNAL_M_ITEM.addActionListener(ae -> {
            if (! WINDOW_MANAGER.getJournalFrame().isVisible()) {
                WINDOW_MANAGER.getJournalFrame().setVisible(true);
            }
            else {
                WINDOW_MANAGER.getJournalFrame().setVisible(false);
            }
        });
        
        this.CHECK_M_ITEM.addActionListener(ae -> {
            if (! WINDOW_MANAGER.getVisitFrame().isVisible()) {
                WINDOW_MANAGER.getVisitFrame().setVisible(true);
            }
            else {
                WINDOW_MANAGER.getVisitFrame().setVisible(false);
            }
        });
        
        this.HOSPITAL_M_ITEM.addActionListener(ae -> {
            if (! WINDOW_MANAGER.getStationaryFrame().isVisible()) {
                WINDOW_MANAGER.getStationaryFrame().setVisible(true);
            }
            else {
                WINDOW_MANAGER.getStationaryFrame().setVisible(false);
            }
        });
        
        this.PAYMONT_M_ITEM.addActionListener(ae -> {
            if (!  WINDOW_MANAGER.getTicketWindowFrame().isVisible()) {
                WINDOW_MANAGER.getTicketWindowFrame().setVisible(true);
            }
            else {
                WINDOW_MANAGER.getTicketWindowFrame().setVisible(false);
            }
        });
        
        this.HIRURGIYA_M_ITEM.addActionListener(ae -> {
            if (! WINDOW_MANAGER.getSurgeryFrame().isVisible()) {
                WINDOW_MANAGER.getSurgeryFrame().setVisible(true);
            }
            else {
                WINDOW_MANAGER.getSurgeryFrame().setVisible(false);
            }
        });
        
        this.OCULIST_M_ITEM.addActionListener(ae -> {
            if (! WINDOW_MANAGER.getOculistFrame().isVisible()) {
                WINDOW_MANAGER.getOculistFrame().setVisible(true);
            }
            else {
                WINDOW_MANAGER.getOculistFrame().setVisible(false);
            }
        });
        
        this.FIZIO_M_ITEM.addActionListener(ae -> {
            if (! WINDOW_MANAGER.getPhysiotherapyFrame().isVisible()) {
                WINDOW_MANAGER.getPhysiotherapyFrame().setVisible(true);
            }
            else {
                WINDOW_MANAGER.getPhysiotherapyFrame().setVisible(false);
            }
        });
        
        this.TERAPEVT_M_ITEM.addActionListener(ae -> {
            if (! WINDOW_MANAGER.getTherapeuticFrame().isVisible()) {
                WINDOW_MANAGER.getTherapeuticFrame().setVisible(true);
            }
            else {
                WINDOW_MANAGER.getTherapeuticFrame().setVisible(false);
            }
        });
        
        this.ENDOKRINOLOG_M_ITEM.addActionListener(ae -> {
            if (! WINDOW_MANAGER.getEndoFrame().isVisible()) {
                WINDOW_MANAGER.getEndoFrame().setVisible(true);
            }
            else {
                WINDOW_MANAGER.getEndoFrame().setVisible(false);
            }
        });
                
        this.NEVROPATOLOG_M_ITEM.addActionListener(ae -> {
            if (! WINDOW_MANAGER.getNeurologistFrame().isVisible()) {
                WINDOW_MANAGER.getNeurologistFrame().setVisible(true);
            }
            else {
                WINDOW_MANAGER.getNeurologistFrame().setVisible(false);
            }
        });
        
        this.VERTEBROLOG_M_ITEM.addActionListener(ae -> {
            if (! WINDOW_MANAGER.getVertebrologistFrame().isVisible()) {
                WINDOW_MANAGER.getVertebrologistFrame().setVisible(true);
            }
            else {
                WINDOW_MANAGER.getVertebrologistFrame().setVisible(false);
            }
        });
        
        this.STATISTIC_M_ITEM.addActionListener(ae -> {
            if (! WINDOW_MANAGER.getStatisticFrame().isVisible()) {
                WINDOW_MANAGER.getStatisticFrame().setVisible(true);
            }
            else {
                WINDOW_MANAGER.getStatisticFrame().setVisible(false);
            }
        });
        
        this.MRI_M_ITEM.addActionListener(ae -> {
            if (! WINDOW_MANAGER.getMriFrame().isVisible()) {
                WINDOW_MANAGER.getMriFrame().setVisible(true);
            }
            else {
                WINDOW_MANAGER.getMriFrame().setVisible(false);
            }
        });
        
        this.MONEY_M_ITEM.addActionListener(ae -> {
            if (! WINDOW_MANAGER.getBookKeepingFrame().isVisible()) {
                WINDOW_MANAGER.getBookKeepingFrame().setVisible(true);
            }
            else {
                WINDOW_MANAGER.getBookKeepingFrame().setVisible(false);
            }
        });
    }

    private void intializeMenu() {
        this.MENU_BAR.setVisible(true);
        
        this.CHECK_M_ITEM.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
        this.ADMIN_M_ITEM.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
        this.REGISTRY_M_ITEM.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
        this.JOURNAL_M_ITEM.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, InputEvent.CTRL_MASK));
        this.PAYMONT_M_ITEM.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
        this.MONEY_M_ITEM.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_MASK));
        this.LABORATORY_M_ITEM.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));
        this.UROLOGY_M_ITEM.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_MASK));
        this.GINECOLOGY_M_ITEM.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK));
        this.ULTRASOUND_M_ITEM.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        this.HOSPITAL_M_ITEM.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        this.HIRURGIYA_M_ITEM.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));
        this.OCULIST_M_ITEM.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_MASK));
        this.FIZIO_M_ITEM.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
        this.TERAPEVT_M_ITEM.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
        this.ENDOKRINOLOG_M_ITEM.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
        this.NEVROPATOLOG_M_ITEM.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK));
        this.VERTEBROLOG_M_ITEM.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK));
        this.MRI_M_ITEM.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
        this.ORGANISATIONS_M_ITEM.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK));
        this.PATIENT_ROOM_M_ITEM.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
        this.STATISTIC_M_ITEM.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
        
        this.ADMIN_BRANCH.add(this.ADMIN_M_ITEM);
        this.ADMIN_BRANCH.add(this.ORGANISATIONS_M_ITEM);
        this.ADMIN_BRANCH.add(this.PATIENT_ROOM_M_ITEM);
        this.BRANCH_MENU.add(REGISTRY_M_ITEM);
        this.BRANCH_MENU.add(LABORATORY_M_ITEM);
        this.BRANCH_MENU.add(UROLOGY_M_ITEM);
        this.BRANCH_MENU.add(GINECOLOGY_M_ITEM);
        this.BRANCH_MENU.add(ULTRASOUND_M_ITEM);
        this.BRANCH_MENU.add(HOSPITAL_M_ITEM);
        this.BRANCH_MENU.add(HIRURGIYA_M_ITEM);
        this.BRANCH_MENU.add(OCULIST_M_ITEM);
        this.BRANCH_MENU.add(FIZIO_M_ITEM);
        this.BRANCH_MENU.add(TERAPEVT_M_ITEM);
        this.BRANCH_MENU.add(ENDOKRINOLOG_M_ITEM);
        this.BRANCH_MENU.add(NEVROPATOLOG_M_ITEM);
        this.BRANCH_MENU.add(VERTEBROLOG_M_ITEM);
        this.BRANCH_MENU.add(MRI_M_ITEM);
        this.STATISTIC_MENU.add(JOURNAL_M_ITEM);
        this.STATISTIC_MENU.add(CHECK_M_ITEM);
        this.STATISTIC_MENU.add(MONEY_M_ITEM);
        this.STATISTIC_MENU.add(PAYMONT_M_ITEM);    
        this.STATISTIC_MENU.add(STATISTIC_M_ITEM);
        
        this.VIEW_MENU.add(this.ADMIN_BRANCH);
        this.VIEW_MENU.add(this.BRANCH_MENU);
        this.VIEW_MENU.add(this.STATISTIC_MENU);
        
        this.MENU_BAR.add(this.VIEW_MENU);
        
        this.mainFrame.setJMenuBar(this.MENU_BAR);
    }

    private void intializeToolBar() {
        this.ADMIN_M_ITEM.setFont(this.FRAME_FONT);
        this.REGISTRY_M_ITEM.setFont(this.FRAME_FONT);
        this.LABORATORY_M_ITEM.setFont(this.FRAME_FONT);
        this.ULTRASOUND_M_ITEM.setFont(this.FRAME_FONT);
        this.UROLOGY_M_ITEM.setFont(this.FRAME_FONT);
        this.GINECOLOGY_M_ITEM.setFont(this.FRAME_FONT);
        this.HOSPITAL_M_ITEM.setFont(this.FRAME_FONT);
        this.JOURNAL_M_ITEM.setFont(this.FRAME_FONT);
        this.CHECK_M_ITEM.setFont(this.FRAME_FONT);
        this.MONEY_M_ITEM.setFont(this.FRAME_FONT);
        this.PAYMONT_M_ITEM.setFont(this.FRAME_FONT);   
        this.PATIENT_ROOM_M_ITEM.setFont(this.FRAME_FONT);
        this.ORGANISATIONS_M_ITEM.setFont(this.FRAME_FONT);
        this.STATISTIC_M_ITEM.setFont(this.FRAME_FONT);
        
        this.HIRURGIYA_M_ITEM.setFont(this.FRAME_FONT);
        this.OCULIST_M_ITEM.setFont(this.FRAME_FONT);
        this.FIZIO_M_ITEM.setFont(this.FRAME_FONT);
        this.TERAPEVT_M_ITEM.setFont(this.FRAME_FONT);
        this.ENDOKRINOLOG_M_ITEM.setFont(this.FRAME_FONT);
        this.NEVROPATOLOG_M_ITEM.setFont(this.FRAME_FONT);
        this.VERTEBROLOG_M_ITEM.setFont(this.FRAME_FONT);
        this.MRI_M_ITEM.setFont(this.FRAME_FONT);
    }

    private void initializeFrame() {
        this.mainFrame = new JFrame(Settings.APPLICATION_VERSION);
        setIcon();
        
        frameToScreenCenter();
        
        this.mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.mainFrame.setLayout(new BorderLayout());
                        
        addInternalFrame(this.WINDOW_MANAGER.getAccountChangingFrame().getInternalFrame());
        addInternalFrame(this.WINDOW_MANAGER.getAccountCreationFrame().getInternalFrame());
        addInternalFrame(this.WINDOW_MANAGER.getAccountInternalFrame().getInternalFrame());

        addInternalFrame(this.WINDOW_MANAGER.getPatientCreationFrame().getInternalFrame());
        addInternalFrame(this.WINDOW_MANAGER.getRegistryFrame().getInternalFrame());
        addInternalFrame(this.WINDOW_MANAGER.getPatientCommingRegistrationFrame().getInternalFrame());
        addInternalFrame(this.WINDOW_MANAGER.getLaboratoryFrame().getInternalFrame());
        addInternalFrame(this.WINDOW_MANAGER.getUltrasoundFrame().getInternalFrame());
        addInternalFrame(this.WINDOW_MANAGER.getUrologyFrame().getInternalFrame());
        addInternalFrame(this.WINDOW_MANAGER.getGinecologyFrame().getInternalFrame());
        addInternalFrame(this.WINDOW_MANAGER.getPatientRecordForm().getInternalFrame());
        addInternalFrame(this.WINDOW_MANAGER.getPatientRemovingFrame().getInternalFrame());
        addInternalFrame(this.WINDOW_MANAGER.getAppointmentFrame().getInternalFrame());
        addInternalFrame(this.WINDOW_MANAGER.getJournalFrame().getInternalFrame());
        addInternalFrame(this.WINDOW_MANAGER.getBookKeepingFrame().getInternalFrame());
        addInternalFrame(this.WINDOW_MANAGER.getVisitFrame().getInternalFrame());
        addInternalFrame(this.WINDOW_MANAGER.getCreateVisitDateFrame().getInternalFrame());
        addInternalFrame(this.WINDOW_MANAGER.getStationaryFrame().getInternalFrame());
        addInternalFrame(this.WINDOW_MANAGER.getPatientEditFrame().getInternalFrame());
        addInternalFrame(this.WINDOW_MANAGER.getOrganisationsFrame().getInternalFrame());
        addInternalFrame(this.WINDOW_MANAGER.getStatisticFrame().getInternalFrame());
        addInternalFrame(this.WINDOW_MANAGER.getPatientRoomFrame().getInternalFrame());
                
        addInternalFrame(this.WINDOW_MANAGER.getTicketWindowFrame().getInternalFrame());
        
        addInternalFrame(this.WINDOW_MANAGER.getSurgeryFrame().getInternalFrame());
        addInternalFrame(this.WINDOW_MANAGER.getOculistFrame().getInternalFrame());
        addInternalFrame(this.WINDOW_MANAGER.getVertebrologistFrame().getInternalFrame());
        addInternalFrame(this.WINDOW_MANAGER.getTherapeuticFrame().getInternalFrame());
        addInternalFrame(this.WINDOW_MANAGER.getNeurologistFrame().getInternalFrame());
        addInternalFrame(this.WINDOW_MANAGER.getPhysiotherapyFrame().getInternalFrame());
        addInternalFrame(this.WINDOW_MANAGER.getEndoFrame().getInternalFrame());
        addInternalFrame(this.WINDOW_MANAGER.getMriFrame().getInternalFrame());
        
        addInternalFrame(AssignmentService.INSTANCE.getGinecologyFrame().getInternalFrame());
        addInternalFrame(AssignmentService.INSTANCE.getLaboratoryFrame().getInternalFrame());
        addInternalFrame(AssignmentService.INSTANCE.getMriFrame().getInternalFrame());
        addInternalFrame(AssignmentService.INSTANCE.getPhysiotherapyFrame().getInternalFrame());
        addInternalFrame(AssignmentService.INSTANCE.getUltrasoundFrame().getInternalFrame());
        // ------------------------------------------------------------ NEW
        
        this.mainFrame.setJMenuBar(this.MENU_BAR);
        this.mainFrame.add(this.desktopPane);
        
        this.mainFrame.add(ResultToolbarService.INSTANCE.getResultPanel(), BorderLayout.SOUTH);
        
        this.mainFrame.setSize(this.WIDTH, this.HEIGHT);
        this.mainFrame.setVisible(false);
    }

    public void addInternalFrame(JInternalFrame internalFrame) {
        this.desktopPane.add(internalFrame);
    }
}