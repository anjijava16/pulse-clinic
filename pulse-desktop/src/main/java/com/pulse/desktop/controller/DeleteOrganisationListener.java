package com.pulse.desktop.controller;

import com.pulse.desktop.controller.builder.MessageBuilder;
import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.table.OrganisationsTableService;
import com.pulse.desktop.controller.table.TableService;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.JOptionPane;
import com.pulse.model.constant.Privelegy;
import com.pulse.rest.client.OrganisationClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author befast
 */
public class DeleteOrganisationListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final OrganisationClient ORGANISATION_SERVICE = new OrganisationClient();

    private OrganisationsTableService tableService;

    public DeleteOrganisationListener(Privelegy privelegy, TableService.TableHolder holder) {
        super(privelegy, holder);
        this.tableService = new OrganisationsTableService(holder);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ThreadPoolService.INSTANCE.execute(() -> {
            if (getTableHolder() != null && getTableHolder().getTable() != null) {
                int row = getTableHolder().getTable().getSelectedRow();

                if (row == -1) {
                    ResultToolbarService.INSTANCE.showFailedStatus("Выберете запись для удаления");
                    return;
                }

                final int answer = MessageBuilder.getAnswerCode(
                        null, "Вы действительно хотите удалить запись?", "Удаление"
                );

                // If answer = YES
                if (answer == JOptionPane.YES_OPTION) {
                    try {
                        final String name = getTableHolder().getModel()
                                .getValueAt(row, TableService.ORGANISATION_NAME_FIELD).toString();

                        if (name != null) {
                            try {
                                this.ORGANISATION_SERVICE.deleteBy(name);
                                this.tableService.deleteById(name);
                                ResultToolbarService.INSTANCE.showSuccessStatus();
                            } catch (IOException ioe) {
                                this.LOGGER.error(getClass() + ": " + ioe.getMessage());
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException aioobe) {
                        this.LOGGER.error(getClass() + ": " + aioobe.getMessage());
                        ResultToolbarService.INSTANCE.showFailedStatus("Ошибка сети");
                    }
                }
            }
        });
    }
}