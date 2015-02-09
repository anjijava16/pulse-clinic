package com.pulse.desktop.controller;

import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.table.TableService;
import java.awt.event.ActionEvent;
import com.pulse.desktop.view.manager.WindowManager;
import com.pulse.model.constant.Privelegy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Vladimir Shin
 */
public class CreateVisitListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    public CreateVisitListener(Privelegy privelegy, TableService.TableHolder holder) {
        super(privelegy, holder);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        ThreadPoolService.INSTANCE.execute(() -> {
            if (!WindowManager.getInstance().getPatientCommingRegistrationFrame().frameIsVisible()) {
                WindowManager.getInstance().getPatientCommingRegistrationFrame().setFrameVisible(true);
            } else {
                WindowManager.getInstance().getPatientCommingRegistrationFrame().setFrameVisible(false);
            }
        });
    }
}
