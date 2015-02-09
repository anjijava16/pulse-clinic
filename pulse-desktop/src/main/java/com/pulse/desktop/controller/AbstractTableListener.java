package com.pulse.desktop.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.pulse.desktop.controller.table.TableService;
import com.pulse.model.constant.Privelegy;

/**
 *
 * @author Vladimir Shin
 */
public abstract class AbstractTableListener implements ActionListener {

    private final Privelegy privelegy;    
    private final TableService.TableHolder tableHolder;
    
    public AbstractTableListener(Privelegy privelegy, TableService.TableHolder tableHolder) {
        this.tableHolder = tableHolder;
        this.privelegy = privelegy;
    }
    
    public Privelegy getPrivelegy() {
        return this.privelegy;
    }
    
    public TableService.TableHolder getTableHolder() {
        return this.tableHolder;
    }
    
    @Override
    public abstract void actionPerformed(ActionEvent e);
}
