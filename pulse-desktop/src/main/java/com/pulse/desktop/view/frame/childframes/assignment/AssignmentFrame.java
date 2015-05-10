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
package com.pulse.desktop.view.frame.childframes.assignment;


import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import javax.swing.BoxLayout;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import com.pulse.desktop.view.frame.childframes.template.TemplateService;
import com.pulse.desktop.view.panel.AnalysSelectionPanel;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public final class AssignmentFrame {

    private final boolean RESIZABLE   = false;
    private final boolean CLOSABLE    = true;
    private final boolean MAXIMIZABLE = false;
    private final boolean ICONFIABLE  = false;

    private AnalysSelectionPanel analysSelectionPanel;

    protected final JInternalFrame frame = new JInternalFrame(
            "", this.RESIZABLE, this.CLOSABLE, this.MAXIMIZABLE, this.ICONFIABLE
    );

    protected final GridBagConstraints gbc = new GridBagConstraints();
    protected final JPanel ROOT_PANEL = new JPanel();
    
    protected int width  = 340;
    protected int height = 450;
    protected int maxChars = 13;

    public JInternalFrame getInternalFrame() {
        return this.frame;
    }

    public AssignmentFrame(TemplateService service, String title) {
        this.analysSelectionPanel = new AnalysSelectionPanel(service);
        
        this.frame.setMaximizable(true);
        this.frame.setTitle(title);
        setAllSettings();       
        addAllActionListeners();
        initializeFrame();
    }
    
    public AnalysSelectionPanel getAnalysSelectionPanel() {
        return this.analysSelectionPanel;
    }
    
    public void initializeFrame() {
        initializeRootPanel();
        
        this.width = 1100;
        this.height = 550;
        
        this.frame.setLayout(new BorderLayout());

        this.frame.add(this.ROOT_PANEL);

        this.frame.setSize(this.width, this.height);
        this.frame.setResizable(true);
    }
    
    public void initializeRootPanel() {
        this.ROOT_PANEL.setLayout(new BoxLayout(this.ROOT_PANEL, BoxLayout.Y_AXIS)); 
        this.ROOT_PANEL.add(this.analysSelectionPanel.getRootPanel());
    }
    
    public void setAllSettings() {        
        this.frame.setVisible(false);
        this.frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.frame.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                getInternalFrame().setVisible(false);
            }
        });
    }

    public void addAllActionListeners() {}
}
