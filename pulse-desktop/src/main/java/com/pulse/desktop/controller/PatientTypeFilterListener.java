package com.pulse.desktop.controller;

import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.table.TableProxy;
import com.pulse.desktop.controller.table.TableService;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JComboBox;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import com.pulse.model.Visit;
import com.pulse.model.constant.Privelegy;
import com.pulse.rest.client.VisitClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Vladimir Shin <vladimir.shin@gmail.com>
 */
public class PatientTypeFilterListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(PatientTypeFilterListener.class);

    private JComboBox<String> filterList;

    private SimpleDateFormat dateFormat;

    private VisitClient visitClient = new VisitClient();

    private JDatePickerImpl calendar;

    private SimpleDateFormat originFormat = new SimpleDateFormat("yyyy.MM.dd");

    public PatientTypeFilterListener(Privelegy privelegy, JComboBox<String> filterList, TableService.TableHolder holder, SimpleDateFormat dateFormat, JDatePickerImpl calendar) {
        super(privelegy, holder);
        this.filterList = filterList;
        this.dateFormat = dateFormat;
        this.calendar = calendar;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        ThreadPoolService.INSTANCE.execute(() -> {
            getTableHolder().clear();

            String filterString = this.filterList.getSelectedItem().toString();

            int filterType = 0;
            int value = 0;

            switch (filterString) {
                case "Все":
                    filterType = 0;
                    break;
            //case "РџРѕ РїСЂРёС…РѕРґСѓ": filterType = 1; break;
                //case "РџРѕ Р·Р°РїРёСЃРё": filterType = 1; break;
                case "Осмотренные":
                    filterType = 2;
                    break;
                case "Неосмотренные":
                    filterType = 2;
                    break;
                default:
                    filterType = 0;
                    break;
            }

            switch (filterString) {
                case "Все":
                    value = 0;
                    break; // Doesnt matter
                //case "РџРѕ РїСЂРёС…РѕРґСѓ": value = 0; break;
                //case "РџРѕ Р·Р°РїРёСЃРё": value = 1; break;
                case "Неосмотренные":
                    value = 0;
                    break;
                case "Осмотренные":
                    value = 1;
                    break;
                default:
                    value = 0;
                    break;
            }

            try {
                Date date = dateFormat.parse(calendar.getJFormattedTextField().getText());
                String dateBuffer = originFormat.format(date);

                final List<Visit> visitsList = this.visitClient.filterBy(filterType, value, dateBuffer);
                visitsList.stream().forEach((visit) -> {
                    this.LOGGER.info("visit: " + visit);

                    final String[] data = TableProxy.INSTANCE.getRightBufferFrom(visit, getPrivelegy(), this.dateFormat);

                    if (visit.getDepartmentId() == getPrivelegy().getId()
                            || getPrivelegy().getId() == Privelegy.Registratur.getId()
                            || getPrivelegy().getId() == Privelegy.TicketWindow.getId()) {
                        getTableHolder().getModel().addRow(data);
                    }
                });
                //ResultToolbarService.INSTANCE.showSuccessStatus();
            } catch (IOException ioe) {
                this.LOGGER.error("PatientTypeFilterListener: " + ioe.getMessage());
                ResultToolbarService.INSTANCE.showFailedStatus("Ошибка сети");
            } catch (ParseException pe) {
                this.LOGGER.error("PatientTypeFilterListener: " + pe.getMessage());
                ResultToolbarService.INSTANCE.showFailedStatus("Неверный формат даты");
            }
        });
    }

}
