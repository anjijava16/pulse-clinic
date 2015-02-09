package com.pulse.desktop.controller;

import com.pulse.desktop.controller.builder.MessageBuilder;
import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.service.UserFacade;
import com.pulse.desktop.controller.table.TableService;
import java.awt.event.ActionEvent;
import com.pulse.desktop.view.manager.WindowManager;
import com.pulse.model.User;
import com.pulse.model.constant.Privelegy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author befast
 */
public class SaveSecondVisitListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    public SaveSecondVisitListener(Privelegy privelegy, TableService.TableHolder tableHolder) {
        super(privelegy, tableHolder);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        ThreadPoolService.INSTANCE.execute(() -> {
            if (UserFacade.INSTANCE.getApplicationUser() == null) {
                return;
            }

            int row = getTableHolder().getTable().getSelectedRow();

            if (row == -1) {
                ResultToolbarService.INSTANCE.showFailedStatus("Запись не выбрана");
                return;
            }

            String doctorNfp = UserFacade.INSTANCE.getApplicationUser().getNfp();
            long patientId = Long.valueOf(getTableHolder().getTable().getValueAt(row, TableService.PATIENT_ID_FIELD).toString());

            User account = UserFacade.INSTANCE.findBy(doctorNfp);

            WindowManager.getInstance().getCreateVisitDateFrame().setPatientId(patientId);

            try {
                WindowManager.getInstance().getCreateVisitDateFrame().setDoctorId(account.getId());
            } catch (NullPointerException npe) {
                LOGGER.error(npe.getMessage());
                ResultToolbarService.INSTANCE.showFailedStatus("Ошибка");
                return;
            }

            if (WindowManager.getInstance().getCreateVisitDateFrame().frameIsVisible()) {
                WindowManager.getInstance().getCreateVisitDateFrame().setFrameVisible(false);
            } else {
                WindowManager.getInstance().getCreateVisitDateFrame().setFrameVisible(true);
            }
        });
    }
}
