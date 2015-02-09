package com.pulse.desktop.controller;

import com.pulse.desktop.controller.table.OrganisationsTableService;
import com.pulse.model.Organisation;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.JTextField;

import com.pulse.desktop.controller.builder.MessageBuilder;
import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.table.TableService;
import com.pulse.model.constant.Privelegy;
import com.pulse.rest.client.OrganisationClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author befast
 */
public class AddOrganisationListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(CommonSearchListener.class);

    private final OrganisationClient ORGANISATION_SERVICE = new OrganisationClient();
    private OrganisationsTableService tableService;

    private JTextField nameField;

    public AddOrganisationListener(Privelegy privelegy, TableService.TableHolder tableHolder, JTextField nameField) {
        super(privelegy, tableHolder);
        this.tableService = new OrganisationsTableService(tableHolder);
        this.nameField = nameField;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (this.nameField == null) {
            return;
        }

        ThreadPoolService.INSTANCE.execute(() -> {
            try {
                String name = this.nameField.getText().trim();

                if (name.contains(" ")) {
                    ResultToolbarService.INSTANCE.showFailedStatus("Название не должно содержать пробелов");
                    return;
                }

                if (name.isEmpty()) {
                    ResultToolbarService.INSTANCE.showFailedStatus("Введите название организации");
                    return;
                }

                final Organisation organisation = new Organisation();
                organisation.setName(name);

                this.ORGANISATION_SERVICE.update(organisation);
                this.tableService.add(organisation);
                ResultToolbarService.INSTANCE.showSuccessStatus();
            } catch (IOException ioe) {
                this.LOGGER.error(ioe.getMessage());
                ResultToolbarService.INSTANCE.showFailedStatus("Ошибка сети");
            }
        });
    }
}
