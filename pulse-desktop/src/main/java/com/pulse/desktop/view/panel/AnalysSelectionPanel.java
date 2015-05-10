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


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import com.pulse.desktop.view.frame.childframes.template.TemplateService;
import com.pulse.desktop.view.model.CheckListItem;
import com.pulse.desktop.view.model.CheckListRenderer;
import com.pulse.desktop.view.util.Settings;
import java.awt.Dimension;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
@SuppressWarnings("unchecked")
public class AnalysSelectionPanel {
    
    private final JPanel ROOT_PANEL = new JPanel();

    private final DefaultListModel<CheckListItem> ANALYS_GROUP_DLM = new DefaultListModel<>();
    private final JList<CheckListItem> ANALYS_GROUP = new JList(this.ANALYS_GROUP_DLM);
    private final JScrollPane ANALYS_GROUP_SCROLL_PANE = new JScrollPane(this.ANALYS_GROUP);
    private final DefaultListModel ANALYS_DLM = new DefaultListModel();
    private final JList<CheckListItem> ANALYS = new JList<>(this.ANALYS_DLM);
    private final JScrollPane ANALYS_SRCOLL_PANE = new JScrollPane(this.ANALYS);   
    
    private CheckListItem[] analysesGroupNames;
    private CheckListItem[] analysesNames;
        
    private HashMap<String, ArrayList<String>> groupToAnalysMap 
            = new HashMap<>();
    
    private TemplateService service;
    
    public AnalysSelectionPanel(TemplateService service) {
        this.service = service;
        
        String[] groupList = getDirectoriesName(this.service.getRoot());
        if (groupList != null) {
            analysesGroupNames = new CheckListItem[groupList.length];

            int ptr = 0;
            for (CheckListItem item : analysesGroupNames) {
                analysesGroupNames[ptr] = new CheckListItem(groupList[ptr++]);
            }
            
            configurePanel();
        }        
    }
    
    public void clearList(DefaultListModel<String> dlm) {
        dlm.removeAllElements();
    }
    
    public String getGroupNameForAnalys(Set<String> dirNames, String analysName) {
        for (String dir : dirNames) {
            String[] files = getDirectoriesName(this.service.getRoot() + dir + "/");
            
            for (String file : files) {
                if (file.equals(analysName)) {
                    return dir;
                }
            }
        }
        
        return null;
    }    
    
    public HashMap<String, ArrayList<String>> getAllSelectionData() {
        return this.groupToAnalysMap;
    }
    
    public void clearAllSelectionData() {
        this.groupToAnalysMap.clear();

        for (final CheckListItem item : this.analysesGroupNames) {
            item.setSelected(false);
        }

        this.ANALYS_DLM.removeAllElements();
    }
    
    public void fillList(DefaultListModel<CheckListItem> dlm, String groupName) {
        String[] files = getDirectoriesName(this.service.getRoot().concat(groupName).concat("/"));
                
        final TreeSet<String> filesList = new TreeSet<>();
        filesList.addAll(Arrays.asList(files));

        analysesNames = new CheckListItem[filesList.size()];
        int ptr = 0;
        
        while (!filesList.isEmpty()) {
            analysesNames[ptr] = new CheckListItem(filesList.pollFirst());
            
            if (groupToAnalysMap.containsKey(groupName)) {
                ArrayList<String> selectedAnalyses = groupToAnalysMap.get(groupName);
                for (String selectedAnalys : selectedAnalyses) {
                    if (analysesNames[ptr].toString().equals(selectedAnalys)) {
                        analysesNames[ptr].setSelected(true);
                    }
                }
            }
            
            dlm.addElement(analysesNames[ptr++]);
        }
        
        filesList.clear();
    }
    
    private String[] getDirectoriesName(final String fileName) {
        if (fileName == null) {
            throw new IllegalArgumentException("fileName can't be null");
        }

        final File file = new File(fileName);
        File[] files;
        String[] groupsList = null;
        
        if (file.exists()) {
            files = file.listFiles();

            if (files != null) {
                final TreeSet<File> filesList = new TreeSet<>();
                filesList.addAll(Arrays.asList(files));

                groupsList = new String[filesList.size()];
                for (int x = 0; x < groupsList.length; x++) {
                    groupsList[x] = filesList.pollFirst().getName();
                }

                filesList.clear();
            }
        }
                        
        return groupsList;
    }

    @SuppressWarnings("unchecked")
    private void configurePanel() {       
        for (CheckListItem item : analysesGroupNames) {
            this.ANALYS_GROUP_DLM.addElement(item);
        } 
        
        this.ANALYS_GROUP.setBackground(Color.LIGHT_GRAY);
        this.ANALYS_GROUP.setCellRenderer(new CheckListRenderer());
        this.ANALYS_GROUP.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.ANALYS_GROUP.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                final JList list = (JList) event.getSource();
                final int index = list.locationToIndex(event.getPoint());
                
                if (index >= 0) {
                    final CheckListItem item = (CheckListItem) list.getModel().getElementAt(index);
                    item.setSelected(!item.isSelected());
                    list.repaint(list.getCellBounds(index, index));

                    clearList(ANALYS_DLM);       
                    
                    for (int x=0;x<list.getModel().getSize();x++) {
                        final CheckListItem tmpItem = (CheckListItem) list.getModel().getElementAt(x);
                        final String analysesGroupName = tmpItem.toString();
                        
                        if (tmpItem.isSelected()) {                        
                            if (!groupToAnalysMap.containsKey(analysesGroupName)) {                            
                                groupToAnalysMap.put(analysesGroupName, new ArrayList<>());
                            } else {
                                tmpItem.setSelected(true);
                            }

                            fillList(ANALYS_DLM, analysesGroupName);
                        }
                    }
                }
            }
        });
                              
        this.ANALYS.setBackground(Color.LIGHT_GRAY);
        this.ANALYS.setCellRenderer(new CheckListRenderer());
        this.ANALYS.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.ANALYS.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {                
                final JList list = (JList) event.getSource();
                final int index = list.locationToIndex(event.getPoint());
                
                if (index >= 0) {
                    final CheckListItem item = (CheckListItem) list.getModel().getElementAt(index);
                    item.setSelected(!item.isSelected());
                    list.repaint(list.getCellBounds(index, index));

                    final String analysesGroupName = item.toString();
                    final Set<String> selectedGroupsSet = groupToAnalysMap.keySet();
                    final String belongsTo = getGroupNameForAnalys(selectedGroupsSet, analysesGroupName);
                    final ArrayList<String> analysesList = groupToAnalysMap.get(belongsTo);

                    if (item.isSelected()) {
                        if (!analysesList.contains(analysesGroupName)) {
                            analysesList.add(analysesGroupName);
                        }
                    } else {
                        if (analysesList.contains(analysesGroupName)) {
                            analysesList.remove(analysesGroupName);                            
                        }
                    }
                }
            }
        });
        
        this.ROOT_PANEL.setLayout(new BorderLayout());        
        this.ANALYS_GROUP_SCROLL_PANE.setMinimumSize(new Dimension(600, 100));
        
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());
        panel1.add(this.ANALYS_GROUP_SCROLL_PANE);

        final JPanel panel2 = new JPanel();
        panel1.setLayout(new BorderLayout());
        panel2.add(this.ANALYS_SRCOLL_PANE);
        
        final JSplitPane splitPane = new JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT, ANALYS_GROUP_SCROLL_PANE, ANALYS_SRCOLL_PANE
        );
        
        this.ROOT_PANEL.add(splitPane);
    }
    
    public JPanel getRootPanel() {
        return this.ROOT_PANEL;
    }
}