package com.pulse.desktop.view.model;

import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class CheckListRenderer 
    extends JCheckBox
    implements ListCellRenderer {
    
    private static final long serialVersionUID = 233265411268587L;
    
    @Override
    public Component getListCellRendererComponent(JList list, 
                                                  Object value, 
                                                  int index, 
                                                  boolean isSelected, 
                                                  boolean hasFocus) {
        setEnabled(list.isEnabled());
        setSelected(((CheckListItem)value).isSelected());
        setFont(list.getFont());
        setBackground(list.getBackground());
        setForeground(list.getForeground());
        setText(((CheckListItem)value).toString());
        
        return this;
    }
}