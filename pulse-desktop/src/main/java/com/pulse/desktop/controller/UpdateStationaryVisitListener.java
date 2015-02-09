package com.pulse.desktop.controller;

import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.service.UserFacade;
import com.pulse.desktop.controller.table.StationaryTableService;
import com.pulse.desktop.controller.table.TableService;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import com.pulse.model.User;
import com.pulse.model.Visit;
import com.pulse.model.constant.Privelegy;
import com.pulse.rest.client.VisitClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author befast
 */
public class UpdateStationaryVisitListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final VisitClient visitClient = new VisitClient();
    private StationaryTableService tableService;

    public UpdateStationaryVisitListener(Privelegy privelegy, TableService.TableHolder tableHolder) {
        super(privelegy, tableHolder);
        this.tableService = new StationaryTableService(tableHolder);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        ThreadPoolService.INSTANCE.execute(() -> {
            try {
                getTableHolder().clear();

                long doctor;

                User appUser = UserFacade.INSTANCE.getApplicationUser();

                if (appUser == null) {
                    return;
                }

                if (appUser.getPrivelegy() == Privelegy.Admin.getId()) {
                    doctor = 0;
                } else {
                    doctor = appUser.getId();
                }

                List<Visit> list = this.visitClient.filterBy(Privelegy.Stationary.getId(), doctor);
                this.tableService.add(list);
                
                ResultToolbarService.INSTANCE.showSuccessStatus();
            } catch (IOException ioe) {
                this.LOGGER.error(ioe.getMessage());
                ResultToolbarService.INSTANCE.showFailedStatus("Ошибка сети");
            }
        });
    }
}
