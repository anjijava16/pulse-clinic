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
package com.pulse.desktop.view.frame.childframes;


import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import com.pulse.desktop.controller.table.TableService.TableHolder;
import com.pulse.desktop.controller.builder.ToolbarBuilder;
import com.pulse.model.constant.Privilege;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class AbstractTabledChildFrame {
    
    private final boolean RESIZABLE   = true;
    private final boolean MAXIMIZABLE = true;
    private final boolean CLOSABLE    = false;    
    private final boolean ICONFIABLE  = false;

    private List<JComponent> toolbarBtnList;    
    private TableHolder tableHolder;
    private Privilege privilege;
    
    protected final JInternalFrame CHILD_FRAME = new JInternalFrame();
    protected final GridBagConstraints gbc = new GridBagConstraints();
    protected final JPanel ROOT_PANEL = new JPanel();    
    protected final int maxChars = 13;
    protected int width;
    protected int height;  
    
    protected final JToolBar TOOLBAR = ToolbarBuilder.build();
            
    // =================================================================================== Private functions
    private void initializeToolbar() {        
        if (this.toolbarBtnList != null && !this.toolbarBtnList.isEmpty()) {
            for (JComponent component : this.toolbarBtnList) {
                addToolbarComponent(component, true);
            }
        }        
    }
    
    private void addToolbarComponent(JComponent toolbarButton, boolean addSeparator) {
        this.TOOLBAR.add(toolbarButton);
        
        if (addSeparator) {
            this.TOOLBAR.addSeparator();
        }
    }
        
    private void initializeRootPanel() {
        this.ROOT_PANEL.setVisible(true);
        this.ROOT_PANEL.setLayout(new BorderLayout());
        this.ROOT_PANEL.add(this.TOOLBAR, BorderLayout.NORTH);
        
        if (this.tableHolder != null) {
            this.ROOT_PANEL.add(this.tableHolder.getScrollPane(), 
                    BorderLayout.CENTER);
        }
    } 
    // =================================================================================== Private functions
    
    public AbstractTabledChildFrame() {        
    }
    
    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }
    
    public void setTableHolder(TableHolder tableHolder) {
        this.tableHolder = tableHolder;
    }
            
    public void setToolbarBtnList(List<JComponent> toolbarBtnList) {
        this.toolbarBtnList = toolbarBtnList;
    }
    
    public boolean isVisible() {
        return this.CHILD_FRAME.isVisible();
    }
    
    public void setVisible(boolean visibility) {
        this.CHILD_FRAME.setVisible(visibility);
    }
        
    public void initializeFrame() {
        initializeToolbar();  
        initializeRootPanel();
        
        this.width = 969;
        this.height = 400;
                
        this.CHILD_FRAME.setResizable(this.RESIZABLE);
        this.CHILD_FRAME.setClosable(this.CLOSABLE);
        this.CHILD_FRAME.setMaximizable(this.MAXIMIZABLE);
        this.CHILD_FRAME.setIconifiable(this.ICONFIABLE);
        
        this.CHILD_FRAME.setSize(this.width, this.height);
        this.CHILD_FRAME.add(this.ROOT_PANEL); 
        
        this.CHILD_FRAME.setTitle(this.privilege.getName());
    }

    public Privilege getPrivilege() {
        return this.privilege;
    }
    
    public TableHolder getTableHolder() {
        return this.tableHolder;
    }
    
    public JInternalFrame getInternalFrame() {        
        return this.CHILD_FRAME;    
    }   
       
}