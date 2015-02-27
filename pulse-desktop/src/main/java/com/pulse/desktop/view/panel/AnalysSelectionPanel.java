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
public class AnalysSelectionPanel {
    
    private final JPanel ROOT_PANEL = new JPanel();
    
    private final DefaultListModel<CheckListItem> ANALYS_GROUP_DLM = new DefaultListModel<>();
    private final JList ANALYS_GROUP = new JList<CheckListItem>(this.ANALYS_GROUP_DLM);
    private final JScrollPane ANALYS_GROUP_SCROLL_PANE = new JScrollPane(this.ANALYS_GROUP);
    
    private final DefaultListModel ANALYS_DLM = new DefaultListModel();
    private final JList ANALYS = new JList<CheckListItem>(this.ANALYS_DLM);
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
//                if (file.endsWith(Settings.DOC_TRAILOR)) {
//                    file = file.substring(0, file.indexOf(Settings.DOC_TRAILOR));
//                } 
//
//                if (file.endsWith(Settings.OLD_DOC_TRAILOR)) {
//                    file = file.substring(0, file.indexOf(Settings.OLD_DOC_TRAILOR));
//                }
//
//                if (file.endsWith(Settings.XSL_TRAILOR)) {
//                    file = file.substring(0, file.indexOf(Settings.XSL_TRAILOR));
//                }
                
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
        
        for (int x=0; x<this.analysesGroupNames.length; x++) {
            this.analysesGroupNames[x].setSelected(false);
        }   
        
        this.ANALYS_DLM.removeAllElements();
    }
    
    public void fillList(DefaultListModel<CheckListItem> dlm, String groupName) {
        String[] files = getDirectoriesName(this.service.getRoot().concat(groupName).concat("/"));
                
        TreeSet<String> filesList = new TreeSet<String>();
        
        for (String file : files) {
//           if (file.endsWith(Settings.DOC_TRAILOR)) {
//               file = file.substring(0, file.indexOf(Settings.DOC_TRAILOR));
//           } 
//           
//           if (file.endsWith(Settings.OLD_DOC_TRAILOR)) {
//               file = file.substring(0, file.indexOf(Settings.OLD_DOC_TRAILOR));
//           }
//           
//           if (file.endsWith(Settings.XSL_TRAILOR)) {
//               file = file.substring(0, file.indexOf(Settings.XSL_TRAILOR));
//           }
           
           filesList.add(file);
        }
        
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
    
    private String[] getDirectoriesName(String fileName) {
        File file = new File(fileName);
        File[] files = null;
        String[] groupsList = null;
        
        if (file.exists()) {
            files = file.listFiles();
            
            TreeSet<File> filesList = new TreeSet<File>();
            boolean addAll = filesList.addAll(Arrays.asList(files));

            groupsList = new String[filesList.size()];
            for (int x=0;x<groupsList.length;x++) {
                groupsList[x] = filesList.pollFirst().getName().toString();
            }

            filesList.clear();
        }
                        
        return groupsList;
    }
    
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
                JList list = (JList) event.getSource();
                int index = list.locationToIndex(event.getPoint());
                
                if (index >= 0) {
                    CheckListItem item = (CheckListItem) list.getModel().getElementAt(index);
                    item.setSelected(!item.isSelected());
                    list.repaint(list.getCellBounds(index, index));
                    
                    String analysesGroupName = item.toString();
                    clearList(ANALYS_DLM);       
                    
                    for (int x=0;x<list.getModel().getSize();x++) {
                        CheckListItem tmpItem = (CheckListItem) list.getModel().getElementAt(x);
                        analysesGroupName = tmpItem.toString();
                        
                        if (tmpItem.isSelected()) {                        
                            if (!groupToAnalysMap.containsKey(analysesGroupName)) {                            
                                groupToAnalysMap.put(analysesGroupName, new ArrayList<String>());
                            } else {
                                tmpItem.setSelected(true);
                            }

                            fillList(ANALYS_DLM, analysesGroupName);
                        } else {

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
                JList list = (JList) event.getSource();
                int index = list.locationToIndex(event.getPoint());
                
                if (index >= 0) {
                    CheckListItem item = (CheckListItem) list.getModel().getElementAt(index);
                    item.setSelected(!item.isSelected());
                    list.repaint(list.getCellBounds(index, index));

                    String analysesGroupName = item.toString();

                    analysesGroupName = item.toString();
                    Set<String> selectedGroupsSet = groupToAnalysMap.keySet();                        
                    String belongsTo = getGroupNameForAnalys(selectedGroupsSet, analysesGroupName);
                    ArrayList<String> analysesList = groupToAnalysMap.get(belongsTo);
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
        
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());
        panel1.add(this.ANALYS_GROUP_SCROLL_PANE);
        JPanel panel2 = new JPanel();
        panel1.setLayout(new BorderLayout());
        panel2.add(this.ANALYS_SRCOLL_PANE);
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, ANALYS_GROUP_SCROLL_PANE, ANALYS_SRCOLL_PANE);
        
        this.ROOT_PANEL.add(splitPane);
    }
    
    public JPanel getRootPanel() {
        return this.ROOT_PANEL;
    }
}