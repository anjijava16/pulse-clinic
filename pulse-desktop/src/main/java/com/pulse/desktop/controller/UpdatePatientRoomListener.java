package com.pulse.desktop.controller;

import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.table.PatientRoomTableService;
import com.pulse.desktop.controller.table.TableService;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import com.pulse.model.PatientRoom;
import com.pulse.model.constant.Privelegy;
import com.pulse.rest.client.PatientRoomClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author befast
 */
public class UpdatePatientRoomListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final PatientRoomClient ORGANISATION_SERVICE = new PatientRoomClient();
    private PatientRoomTableService tableService;

    public UpdatePatientRoomListener(Privelegy privelegy, TableService.TableHolder tableHolder) {
        super(privelegy, tableHolder);
        this.tableService = new PatientRoomTableService(tableHolder);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        ThreadPoolService.INSTANCE.execute(() -> {
            try {
                getTableHolder().clear();

                List<PatientRoom> list = this.ORGANISATION_SERVICE.listAll();

                list.stream().forEach((organisation) -> {
                    this.tableService.add(organisation);
                });
                ResultToolbarService.INSTANCE.showSuccessStatus();
            } catch (IOException ioe) {
                this.LOGGER.error(ioe.getMessage());
                ResultToolbarService.INSTANCE.showFailedStatus("Ошибка сети");
            }
        });
    }
}
