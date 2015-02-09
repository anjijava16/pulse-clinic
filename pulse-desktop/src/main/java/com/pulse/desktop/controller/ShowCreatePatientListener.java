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
public class ShowCreatePatientListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    public ShowCreatePatientListener(Privelegy privelegy, TableService.TableHolder holder) {
        super(privelegy, holder);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        ThreadPoolService.INSTANCE.execute(() -> {
            if (!WindowManager.getInstance().getPatientCreationFrame().frameIsVisible()) {
                WindowManager.getInstance().getPatientCreationFrame().setFrameVisible(true);
            } else {
                WindowManager.getInstance().getPatientCreationFrame().setFrameVisible(false);
            }
        });
    }

}
