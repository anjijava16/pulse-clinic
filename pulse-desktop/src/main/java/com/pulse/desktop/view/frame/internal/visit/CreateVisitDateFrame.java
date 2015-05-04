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
package com.pulse.desktop.view.frame.internal.visit;


import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.model.NextVisit;
import com.pulse.rest.client.NextVisitClient;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
final public class CreateVisitDateFrame {
    
    private final boolean RESIZABLE   = false;
    private final boolean CLOSABLE    = true;
    private final boolean MAXIMIZABLE = false;
    private final boolean ICONFIABLE  = false;

    protected final String FRAME_DESCRIPTION = "Дата";
    
    protected JInternalFrame frame = new JInternalFrame(this.FRAME_DESCRIPTION,
                                                        this.RESIZABLE,
                                                        this.CLOSABLE,
                                                        this.MAXIMIZABLE,
                                                        this.ICONFIABLE);

    protected final GridBagConstraints gbc = new GridBagConstraints();
    protected final JPanel ROOT_PANEL = new JPanel();
    
    protected int width  = 340;
    protected int height = 320;
    
    protected int maxChars = 13;
    
    protected final JToolBar TOOLBAR = new JToolBar(); 
    
    private volatile long doctorId;
    
    private final NextVisitClient visitClient = new NextVisitClient();
    
    public JInternalFrame getInternalFrame() {
        return this.frame;    
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }    
    
    protected void setToolbarSettings() {
        this.TOOLBAR.setFloatable(false);
        this.TOOLBAR.setVisible(true);
    }

    public boolean frameIsVisible() {
        return this.frame.isVisible();
    }
    
    public void setFrameVisible(boolean visibility) {
        this.frame.setVisible(visibility);
    }
    
    private final JButton CREATE_BUTTON = new JButton("Создать");
    
    private final JDatePickerImpl calendar = new JDatePickerImpl(new JDatePanelImpl(null));
    private final SimpleDateFormat SDF = new SimpleDateFormat("dd.MM.yyyy");
        
    private long patientId;
    
    public CreateVisitDateFrame(String title) {
        this.frame.setTitle(title);
        
        setAllSettings();
        initializeTabel();
        intializeFrame();
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }
            
    private void initializeTabel() {
    }

    private void initializeToolbar() {
        setToolbarSettings();
    }
    
    public void intializeFrame() {
        initializeRootPanel();
        
        this.frame.add(this.ROOT_PANEL);
    }

    public void initializeRootPanel() {
        this.ROOT_PANEL.setLayout(new GridBagLayout());
        this.ROOT_PANEL.setVisible(true);
        this.ROOT_PANEL.setBorder(new TitledBorder(new EtchedBorder(), "Дата"));
        
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 0;
        this.gbc.gridy = 0;
        this.gbc.gridwidth = 1;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        this.ROOT_PANEL.add(this.calendar, this.gbc);
        
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 0;
        this.gbc.gridy = 1;
        this.gbc.gridwidth = 1;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        this.ROOT_PANEL.add(this.CREATE_BUTTON, this.gbc);
    }

    public void setAllSettings() {
        addAllActionListeners();
        initializeToolbar();
        
        this.calendar.getJFormattedTextField().setText(this.SDF.format(new Date()));
        this.frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.frame.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                setFrameVisible(false);
            }
        });
        
        this.calendar.setToolTipText("Дата");
                
        this.width = 240;
        this.height = 130;
        
        this.frame.setMaximizable(false);
        this.frame.setResizable(false);
        this.frame.setSize(this.width, this.height);
        this.ROOT_PANEL.setVisible(true);
    }

    public void addAllActionListeners() {
        this.CREATE_BUTTON.addActionListener(ae -> {
            try {
                final NextVisit visit = new NextVisit();
                visit.setDoctorId(doctorId);
                visit.setPatientId(patientId);
                visit.setVisitDate(SDF.parse(calendar.getJFormattedTextField().getText()));

                visitClient.update(visit);

                setFrameVisible(false);
                ResultToolbarService.INSTANCE.showSuccessStatus();
            } catch (ParseException ex) {
                ResultToolbarService.INSTANCE.showFailedStatus("Ошибка");
            } catch (IOException ioe) {
                ResultToolbarService.INSTANCE.showFailedStatus("Ошибка сети");
            }
        });
    }
    
}

