package com.pulse.desktop.controller;

import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.table.NextVisitTableService;
import com.pulse.desktop.controller.table.TableService;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import com.pulse.desktop.view.manager.UIHandlerFacade;
import com.pulse.model.NextVisit;
import com.pulse.model.constant.Privelegy;
import com.pulse.rest.client.NextVisitClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author befast
 */
public class SearchNextVisitByDateListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private JDatePickerImpl calendar;
    private SimpleDateFormat originFormat;

    private NextVisitClient client = new NextVisitClient();

    private SimpleDateFormat requestFormat = new SimpleDateFormat("yyyy.MM.dd");

    private NextVisitTableService tableService;

    public SearchNextVisitByDateListener(Privelegy privelegy, JDatePickerImpl calendar, TableService.TableHolder holder, SimpleDateFormat originFormat) {
        super(privelegy, holder);
        this.calendar = calendar;
        this.originFormat = originFormat;
        this.tableService = new NextVisitTableService(holder);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        ThreadPoolService.INSTANCE.execute(() -> {
            UIHandlerFacade.getInstance().updateLaboratoryFrameIterator();

            getTableHolder().clear();

            try {
                Date originDate = this.originFormat.parse(this.calendar.getJFormattedTextField().getText());
                String dateBuffer = this.requestFormat.format(originDate);

                final List<NextVisit> list = this.client.findByDate(dateBuffer);
                this.tableService.add(list);
                ResultToolbarService.INSTANCE.showSuccessStatus();
            } catch (IOException ioe) {
                this.LOGGER.error("SearchByDateListener: " + ioe.getMessage());
                ResultToolbarService.INSTANCE.showFailedStatus("Ошибка сети");
            } catch (ParseException pe) {
                this.LOGGER.error("SearchByDateListener: " + pe.getMessage());
                ResultToolbarService.INSTANCE.showFailedStatus("Неверный формат даты");
            }
        });
    }
}
