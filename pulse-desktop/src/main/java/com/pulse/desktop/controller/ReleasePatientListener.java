package com.pulse.desktop.controller;

import com.pulse.desktop.controller.builder.MessageBuilder;
import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.table.TableService;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.JOptionPane;
import com.pulse.desktop.view.manager.WindowManager;
import com.pulse.model.Visit;
import com.pulse.model.constant.Privelegy;
import com.pulse.rest.client.VisitClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author befast
 */
public class ReleasePatientListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private VisitClient client = new VisitClient();

    public ReleasePatientListener(Privelegy privelegy, TableService.TableHolder tableHolder) {
        super(privelegy, tableHolder);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        ThreadPoolService.INSTANCE.execute(() -> {
            WindowManager.getInstance().getAppointmentFrame().getTableHolder().clear();

            int row = getTableHolder().getTable().getSelectedRow();

            if (row == -1) {
                ResultToolbarService.INSTANCE.showFailedStatus("Запись не выбрана");
                return;
            }

            final int answer = MessageBuilder.getAnswerCode(null, "Вы действительно хотите выписать пациента?",
                    "Выписка");

            // If answer = YES
            if (answer == JOptionPane.YES_OPTION) {
                final String visitIdBuffer = getTableHolder().getTable().getValueAt(row, TableService.VISIT_ID_FIELD).toString();

                if (visitIdBuffer == null) {
                    return;
                }

                try {
                    final Visit visit = this.client.getBy(Long.valueOf(visitIdBuffer));
                    if (visit == null) {
                        return;
                    }

                    visit.setReleased(true);

                    this.client.update(visit);

                    ResultToolbarService.INSTANCE.showSuccessStatus();
                } catch (IOException ioe) {
                    this.LOGGER.error(getClass() + ": " + ioe.getMessage());
                    ResultToolbarService.INSTANCE.showFailedStatus("Ошибка сети");
                }
            }
        });
    }

}
