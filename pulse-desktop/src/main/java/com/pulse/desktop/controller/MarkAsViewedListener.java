package com.pulse.desktop.controller;

import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.JOptionPane;
import com.pulse.desktop.controller.builder.MessageBuilder;
import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.table.TableService;
import com.pulse.model.Visit;
import com.pulse.model.constant.Privelegy;
import com.pulse.model.constant.Status;
import com.pulse.rest.client.VisitClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author befast
 */
public class MarkAsViewedListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private Privelegy privelegy;
    private TableService.TableHolder tableHolder;

    private VisitClient visitClient = new VisitClient();

    public MarkAsViewedListener(Privelegy privelegy, TableService.TableHolder tableHolder) {
        super(privelegy, tableHolder);
        this.privelegy = privelegy;
        this.tableHolder = tableHolder;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        ThreadPoolService.INSTANCE.execute(() -> {
            this.LOGGER.info(getClass() + ": actionPerformed()");

            if (getTableHolder() != null && getTableHolder().getTable() != null) {
                int row = getTableHolder().getTable().getSelectedRow();

                if (row == -1) {
                    ResultToolbarService.INSTANCE.showFailedStatus("Запись не выбрана");
                    return;
                }

                final int answer = MessageBuilder.getAnswerCode(null, "Изменить статус прихода?",
                        "Визит");

                // If answer = YES
                if (answer == JOptionPane.YES_OPTION) {
                    final long visitId = Long.valueOf(getTableHolder().getModel()
                            .getValueAt(row, TableService.VISIT_ID_FIELD).toString());

                    try {
                        final Visit visit = this.visitClient.getBy(visitId);
                        visit.setVisitStatus(Status.HANDLED.getCode());

                        this.visitClient.update(visit);
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
