package com.pulse.desktop.controller;

import com.pulse.desktop.controller.service.UserFacade;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;
import com.pulse.desktop.controller.builder.MessageBuilder;
import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.table.TableService;
import com.pulse.desktop.view.manager.WindowManager;
import com.pulse.desktop.view.util.Values;
import com.pulse.model.PatientRoom;
import com.pulse.model.User;
import com.pulse.model.Visit;
import com.pulse.model.constant.Privelegy;
import com.pulse.model.constant.Status;
import com.pulse.rest.client.PatientRoomClient;
import com.pulse.rest.client.VisitClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author befast
 */
public class MoveToStationaryListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private VisitClient client = new VisitClient();

    private PatientRoomClient roomClient = new PatientRoomClient();

    public MoveToStationaryListener(Privelegy privelegy, TableService.TableHolder tableHolder) {
        super(privelegy, tableHolder);
    }

    private String getRoom() throws IOException {
        List<PatientRoom> roomList = roomClient.listAll();
        String[] roomArray = new String[roomList.size()];
        int ptr = 0;
        for (PatientRoom room : roomList) {
            roomArray[ptr++] = room.getName();
        }

        final String selectedTemplate = (String) JOptionPane.showInputDialog(
                null,
                "Выберите палату",
                "Палата",
                JOptionPane.NO_OPTION,
                null,
                roomArray,
                null);

        return selectedTemplate;
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

            final String visitIdBuffer = getTableHolder().getTable().getValueAt(row, TableService.VISIT_ID_FIELD).toString();
            final String patientIdBuffer = getTableHolder().getTable().getValueAt(row, TableService.PATIENT_ID_FIELD).toString();
            final User account = UserFacade.INSTANCE.getApplicationUser();

            if (patientIdBuffer == null) {
                return;
            }
            if (account == null) {
                return;
            }
            if (visitIdBuffer == null) {
                return;
            }

            try {
                final String room = getRoom();

                if (room == null || room.trim().isEmpty()) {
                    return;
                }

                final Visit visit = new Visit();
                visit.setVisitStatus(Status.HANDLED.getCode());
                visit.setPatientId(Long.valueOf(patientIdBuffer));
                visit.setDoctorId(account.getId());
                visit.setFilename(Values.Empty.getValue());
                visit.setFilepath(Values.Empty.getValue());
                visit.setDepartmentId(Privelegy.Stationary.getId());
                visit.setAnalysName(Values.Unknown.getValue());
                visit.setAnalysGroup(Values.Unknown.getValue());
                visit.setTillDate(Values.Empty.getValue());
                visit.setFromOrganisation(Values.Empty.getValue());
                visit.setFromDoctor(Values.Empty.getValue());
                visit.setCreatedBy(account.getNfp());
                visit.setRoom(room);

                this.client.update(visit);
            } catch (IOException ioe) {
                this.LOGGER.error(getClass() + ": " + ioe.getMessage());
            }
        });
    }

}