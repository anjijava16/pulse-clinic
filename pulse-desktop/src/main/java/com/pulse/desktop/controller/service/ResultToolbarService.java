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
package com.pulse.desktop.controller.service;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public enum ResultToolbarService {
    
    INSTANCE;
    
    private final int TIMEOUT_VALUE = 1; // in seconds
    
    private final Color SUCCESS_COLOR = new Color(55, 174, 54);
    private final Color ERROR_COLOR = new Color(226, 13, 31);
    
    private final ExecutorService WORKER = Executors.newCachedThreadPool();
    
    private JPanel toolbarPanel = new JPanel();
    private JLabel resultLabel = new JLabel();
    private JPanel innerPanel = new JPanel();
        
    private ResultToolbarService() {
        build();
    }
    
    private void build() {
        
        this.innerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.innerPanel.add(this.resultLabel);
        
        this.toolbarPanel.setLayout(new BorderLayout());
        this.toolbarPanel.add(innerPanel, BorderLayout.CENTER);
        this.toolbarPanel.setVisible(false);
    }
    
    public JPanel getResultPanel() {
        return this.toolbarPanel;
    }
    
    public void showSuccessStatus() {
        this.WORKER.submit(() -> {
            this.innerPanel.setBackground(SUCCESS_COLOR);
            this.resultLabel.setText("Успешно");
            
            this.toolbarPanel.setVisible(true);
            
            try {
                TimeUnit.SECONDS.sleep(TIMEOUT_VALUE);
            } catch (InterruptedException ie) {}
            
            this.resultLabel.setText("");
            this.toolbarPanel.setVisible(false);
        });       
    }
    
    public void showFailedStatus(String text) {
        this.WORKER.submit(() -> {
            this.innerPanel.setBackground(ERROR_COLOR);
            this.resultLabel.setText(text);
            
            this.toolbarPanel.setVisible(true);
            
            try {
                TimeUnit.SECONDS.sleep(TIMEOUT_VALUE);
            } catch (InterruptedException ie) {}
            
            this.resultLabel.setText("");
            this.toolbarPanel.setVisible(false);
        });        
    }
}