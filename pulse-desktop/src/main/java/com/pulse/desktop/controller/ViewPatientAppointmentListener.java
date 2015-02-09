package com.pulse.desktop.controller;

import com.pulse.desktop.controller.builder.MessageBuilder;
import com.pulse.desktop.controller.service.PatientFacade;
import com.pulse.desktop.controller.service.PatientService;
import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.table.AppointmentTableService;
import com.pulse.desktop.controller.table.TableService;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import com.pulse.desktop.view.manager.WindowManager;
import com.pulse.model.Appointment;
import com.pulse.model.Patient;
import com.pulse.model.constant.Privelegy;
import com.pulse.rest.client.AppointmentClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author befast
 */
public class ViewPatientAppointmentListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private AppointmentClient appointmentClient = new AppointmentClient();

    private AppointmentTableService tableService;

    public ViewPatientAppointmentListener(Privelegy privelegy, TableService.TableHolder tableHolder) {
        super(privelegy, tableHolder);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        ThreadPoolService.INSTANCE.execute(() -> {

            if (this.tableService == null) {
                this.tableService = new AppointmentTableService(WindowManager.getInstance().getAppointmentFrame().getTableHolder());
            }

            WindowManager.getInstance().getAppointmentFrame().getTableHolder().clear();

            int row = getTableHolder().getTable().getSelectedRow();

            if (row == -1) {
                ResultToolbarService.INSTANCE.showFailedStatus("Запись не выбрана");
                return;
            }

            if (!WindowManager.getInstance().getAppointmentFrame().isVisible()) {
                WindowManager.getInstance().getAppointmentFrame().setVisible(true);

                String patientIdBuffer = getTableHolder().getTable().getValueAt(row, TableService.PATIENT_ID_FIELD).toString();
                String doctorNfp = getTableHolder().getTable().getValueAt(row, TableService.DOCTOR_NAME_FIELD).toString();
                Patient patient = PatientService.INSTANCE.getById(Long.valueOf(patientIdBuffer));

                if (patient == null) {
                    this.LOGGER.error(getClass() + ": Patient instance is null");
                    return;
                }

                PatientFacade.INSTANCE.setLastSelectedPatient(patient);
                
                try {
                    final List<Appointment> recordsList = this.appointmentClient.listBy(patient.getId());
                    if (recordsList != null && !recordsList.isEmpty()) {
                        recordsList.stream().forEach((appointment) -> {
                            this.tableService.add(appointment, patient);
                        });
                    }
                } catch (IOException ioe) {
                    this.LOGGER.error(getClass() + ": " + ioe.getMessage());
                    ResultToolbarService.INSTANCE.showFailedStatus("Ошибка сети");
                }
            } else {
                WindowManager.getInstance().getAppointmentFrame().setVisible(false);
            }
        });
    }

}
