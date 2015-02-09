/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pulse.desktop.view.model;

public class CheckListItem {
    
    private String label;
    private boolean isSelected = false;

    public CheckListItem(String label) {
        this.label = label;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public String toString() {
        return label;
    }
}