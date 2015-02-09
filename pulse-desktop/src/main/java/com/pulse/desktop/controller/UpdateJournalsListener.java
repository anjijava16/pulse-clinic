package com.pulse.desktop.controller;

import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.table.JournalTableService;
import com.pulse.desktop.controller.table.TableService;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import com.pulse.model.Journal;
import com.pulse.model.constant.Privelegy;
import com.pulse.rest.client.JournalClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author befast
 */
public class UpdateJournalsListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final JournalClient ORGANISATION_SERVICE = new JournalClient();
    private JournalTableService tableService;

    public UpdateJournalsListener(Privelegy privelegy, TableService.TableHolder tableHolder) {
        super(privelegy, tableHolder);
        this.tableService = new JournalTableService(tableHolder);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        ThreadPoolService.INSTANCE.execute(() -> {
            try {
                getTableHolder().clear();

                List<Journal> list = this.ORGANISATION_SERVICE.list();

                list.stream().forEach((journal) -> {
                    this.tableService.add(journal);
                });
            } catch (IOException ioe) {
                this.LOGGER.error(ioe.getMessage());
            }
        });
    }
}
