package com.pulse.desktop.controller;

import com.pulse.desktop.controller.builder.MessageBuilder;
import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.table.TableService.TableHolder;
import com.pulse.desktop.controller.table.TableService;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JOptionPane;
import com.pulse.model.Visit;
import com.pulse.model.constant.PaymentStatus;
import com.pulse.rest.client.VisitClient;

/**
 * This is event listener handler for "Pay JButton"
 *
 * @author befast
 */
public class MarkAsPayListener implements ActionListener {

    private TableHolder tableHolder;

    public MarkAsPayListener() {
    }

    private VisitClient client = new VisitClient();

    public void setTableHolder(TableHolder tableHolder) {
        this.tableHolder = tableHolder;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ThreadPoolService.INSTANCE.execute(() -> {
            if (this.tableHolder != null && this.tableHolder.getTable() != null) {
                int row = this.tableHolder.getTable().getSelectedRow();

                if (row != -1) {
                    final int answer = MessageBuilder.getAnswerCode(null, "Изменить статус?",
                            "Изменение статуса");

                    // If answer = YES
                    if (answer == JOptionPane.YES_OPTION) {
                        final long visitId = Long.valueOf(this.tableHolder.getModel()
                                .getValueAt(row, TableService.VISIT_ID_FIELD).toString());

                        try {
                            final Visit visit = this.client.getBy(visitId);
                            visit.setPaymentStatus(PaymentStatus.PAYED.getId());
                            this.client.update(visit);

                            ResultToolbarService.INSTANCE.showSuccessStatus();
                        } catch (IOException ioe) {
                            ResultToolbarService.INSTANCE.showFailedStatus("Ошибка сети");
                        }
                    }
                } else {
                    ResultToolbarService.INSTANCE.showFailedStatus("Запись не выбрана");
                }
            }
        });
    }

}
