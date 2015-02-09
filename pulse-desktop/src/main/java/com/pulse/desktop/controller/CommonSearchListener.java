package com.pulse.desktop.controller;

import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.table.TableProxy;
import com.pulse.desktop.controller.table.TableService;
import com.pulse.model.SearchTemplate;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JTextField;
import com.pulse.model.Visit;
import com.pulse.model.constant.Privelegy;
import com.pulse.rest.client.VisitClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author befast
 */
public class CommonSearchListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(CommonSearchListener.class);

    private JTextField searchField;
    private SimpleDateFormat dateFormat;

    private VisitClient visitClient = new VisitClient();

    public CommonSearchListener(Privelegy privelegy, TableService.TableHolder tableHolder, JTextField searchField, SimpleDateFormat dateFormat) {
        super(privelegy, tableHolder);
        this.searchField = searchField;
        this.dateFormat = dateFormat;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        ThreadPoolService.INSTANCE.execute(() -> {
            try {
                String pattern = this.searchField.getText();

                if (pattern == null || pattern.isEmpty()) {
                    return;
                }

                getTableHolder().clear();

                final SearchTemplate template = new SearchTemplate();
                template.setTemplate(pattern);

                final List<Visit> visitsList = this.visitClient.findByTemplate(template);
                visitsList.stream().forEach((visit) -> {
                    this.LOGGER.info("visit: " + visit);

                    final String[] data = TableProxy.INSTANCE.getRightBufferFrom(visit, getPrivelegy(), this.dateFormat);
                    if (visit.getDepartmentId() == getPrivelegy().getId()
                            || getPrivelegy().getId() == Privelegy.Registratur.getId()
                            || getPrivelegy().getId() == Privelegy.TicketWindow.getId()) {
                        getTableHolder().getModel().addRow(data);
                    }
                });

                ResultToolbarService.INSTANCE.showSuccessStatus();
            } catch (IOException ioe) {
                this.LOGGER.error("CommonSearchListener: " + ioe.getMessage());
                ResultToolbarService.INSTANCE.showFailedStatus("Ошибка сети");
            }
        });
    }
}
