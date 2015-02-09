package com.pulse.desktop.controller;

import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.table.OrganisationsTableService;
import com.pulse.desktop.controller.table.TableService;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import com.pulse.model.Organisation;
import com.pulse.model.constant.Privelegy;
import com.pulse.rest.client.OrganisationClient;
import com.pulse.rest.client.PowerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author befast
 */
public class UpdateOrganisationsListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final OrganisationClient ORGANISATION_SERVICE = new OrganisationClient();
    private OrganisationsTableService tableService;

    private PowerClient powerClient = new PowerClient();

    public UpdateOrganisationsListener(Privelegy privelegy, TableService.TableHolder tableHolder) {
        super(privelegy, tableHolder);
        this.tableService = new OrganisationsTableService(tableHolder);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        ThreadPoolService.INSTANCE.execute(() -> {
            try {
                getTableHolder().clear();

                List<Organisation> list = this.ORGANISATION_SERVICE.listAll();

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
