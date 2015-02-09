package com.pulse.desktop.controller;

import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.table.PatientRoomTableService;
import com.pulse.desktop.controller.table.TableService;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.JTextField;
import com.pulse.model.PatientRoom;
import com.pulse.model.constant.Privelegy;
import com.pulse.rest.client.PatientRoomClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author befast
 */
public class CreatePatientRoomListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private PatientRoomClient patientRoomClient = new PatientRoomClient();
    private PatientRoomTableService tableService;

    private JTextField nameField;

    public CreatePatientRoomListener(Privelegy privelegy, TableService.TableHolder tableHolder, JTextField nameField) {
        super(privelegy, tableHolder);
        this.tableService = new PatientRoomTableService(tableHolder);
        this.nameField = nameField;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        ThreadPoolService.INSTANCE.execute(() -> {
            try {
                String name = this.nameField.getText().trim();

                if (name.contains(" ")) {
                    ResultToolbarService.INSTANCE.showFailedStatus("Номер палаты не должен содержать пробелов");
                    return;
                }

                if (name.isEmpty()) {
                    ResultToolbarService.INSTANCE.showFailedStatus("Введите номер палаты");
                    return;
                }

                final PatientRoom patientRoom = new PatientRoom();
                patientRoom.setName(name);

                this.patientRoomClient.update(patientRoom);
                this.tableService.add(patientRoom);
                ResultToolbarService.INSTANCE.showSuccessStatus();
            } catch (IOException ioe) {
                this.LOGGER.error(ioe.getMessage());
                ResultToolbarService.INSTANCE.showFailedStatus("Ошибка сети");
            }
        });
    }
}
