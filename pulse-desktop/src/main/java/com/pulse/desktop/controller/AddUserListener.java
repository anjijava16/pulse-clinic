package com.pulse.desktop.controller;

import java.awt.event.ActionEvent;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.table.TableService.TableHolder;
import com.pulse.desktop.view.manager.WindowManager;
import com.pulse.model.constant.Privelegy;


/**
 *
 * @author Vladimir Shin
 */
public class AddUserListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
        
    public AddUserListener(Privelegy privelegy, TableHolder tableHolder) {
        super(privelegy, tableHolder);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        ThreadPoolService.INSTANCE.execute(() -> {
            if (WindowManager.getInstance().getAccountCreationFrame().isVisible()) {
                WindowManager.getInstance().getAccountCreationFrame().setVisible(false);
            } else {
                WindowManager.getInstance().getAccountCreationFrame().setVisible(true);
            }
        });
    }
}
