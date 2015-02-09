package com.pulse.desktop.controller;

import com.pulse.desktop.controller.builder.MessageBuilder;
import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.table.JournalTableService;
import com.pulse.desktop.controller.table.TableService;
import com.pulse.desktop.controller.table.TableService.TableHolder;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.JOptionPane;

import com.pulse.model.constant.Privelegy;
import com.pulse.rest.client.JournalClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Vladimir Shin
 */
public class DeleteJournalListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private Privelegy privelegy;
    private TableHolder tableHolder;

    private JournalClient journalClient = new JournalClient();

    private JournalTableService tableService;

    public DeleteJournalListener(Privelegy privelegy, TableService.TableHolder tableHolder) {
        super(privelegy, tableHolder);
        this.privelegy = privelegy;
        this.tableHolder = tableHolder;
        this.tableService = new JournalTableService(tableHolder);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        this.LOGGER.info(getClass() + ": actionPerformed()");

        ThreadPoolService.INSTANCE.execute(() -> {
            if (getTableHolder() != null && getTableHolder().getTable() != null) {
                int row = getTableHolder().getTable().getSelectedRow();

                if (row == -1) {
                    ResultToolbarService.INSTANCE.showFailedStatus("Запись не выбрана");
                    return;
                }

                final int answer = MessageBuilder.getAnswerCode(null, "Вы действительно хотите удалить запись?",
                        "Удаление");

                // If answer = YES
                if (answer == JOptionPane.YES_OPTION) {
                    final long recordId = Long.valueOf(getTableHolder().getModel()
                            .getValueAt(row, TableService.RECORD_ID_FIELD).toString());

                    try {
                        this.journalClient.delete(recordId);
                        this.tableService.deleteById(recordId);
                        ResultToolbarService.INSTANCE.showSuccessStatus();
                    } catch (IOException ioe) {
                        this.LOGGER.error(getClass() + ": " + ioe.getMessage());
                        ResultToolbarService.INSTANCE.showFailedStatus("Ошибка сети");
                    }
                }
            }
        });
    }

}
