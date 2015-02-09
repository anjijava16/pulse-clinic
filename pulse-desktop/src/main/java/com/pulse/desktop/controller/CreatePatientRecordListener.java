package com.pulse.desktop.controller;

import com.google.common.io.Files;
import com.pulse.desktop.controller.service.PatientFacade;
import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.service.UserFacade;
import com.pulse.desktop.controller.table.PatientRecordTableService;
import com.pulse.desktop.controller.table.TableService;
import com.pulse.desktop.controller.table.TableService.TableHolder;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import javax.swing.JOptionPane;

import com.pulse.desktop.view.util.FileManager;
import com.pulse.desktop.view.util.HashBuilder;
import com.pulse.desktop.view.util.NameValidator;
import com.pulse.desktop.view.util.Settings;
import com.pulse.model.Patient;
import com.pulse.model.Record;
import com.pulse.model.User;
import com.pulse.model.constant.Privelegy;
import com.pulse.model.constant.PrivelegyDir;
import com.pulse.rest.client.RecordClient;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Vladimir Shin
 */
public class CreatePatientRecordListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private Privelegy privelegy;
    private TableHolder tableHolder;

    private final RecordClient SERVICE_CLIENT = new RecordClient();
    private final FileManager FILE_MANAGER = new FileManager();

    private PatientRecordTableService tableService;

    public CreatePatientRecordListener(Privelegy privelegy, TableService.TableHolder tableHolder) {
        super(privelegy, tableHolder);
        this.privelegy = privelegy;
        this.tableHolder = tableHolder;
        this.tableService = new PatientRecordTableService(tableHolder);
    }

    private String getSelectedForm() {
        final File recordsFolder = new File(PrivelegyDir.RECORDS_PATH.getTemplatePath());

        this.LOGGER.info("Review folder: " + PrivelegyDir.RECORDS_PATH.getTemplatePath());

        if (recordsFolder.exists()) {
            final String[] recordsNames = recordsFolder.list();

            final String selectedTemplate = (String) JOptionPane.showInputDialog(
                    null,
                    "Выберите шаблон из списка",
                    "Анкета",
                    JOptionPane.NO_OPTION,
                    null,
                    recordsNames,
                    null);

            return selectedTemplate;
        } else {
            ResultToolbarService.INSTANCE.showFailedStatus("Папка с шаблонами не найдена");
        }

        return null;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        ThreadPoolService.INSTANCE.execute(() -> {
            if (UserFacade.INSTANCE.getApplicationUser() == null) {
                return;
            }

            final Patient patient = PatientFacade.INSTANCE.getLastSelectedPatient();

            this.LOGGER.info(getClass() + ": actionPerformed(), patientNfp: " + ((patient != null) ? patient.getNfp() : null));

            if (patient != null) {
                final String templateName = getSelectedForm();

                if (templateName == null) return;
                
                final String applicationUsername = UserFacade.INSTANCE.getApplicationUser().getNfp();
                final User applicationUser = UserFacade.INSTANCE.findBy(applicationUsername);

                FILE_MANAGER.copyFile(PrivelegyDir.RECORDS_PATH.getTemplatePath() + templateName, PrivelegyDir.RECORDS_PATH.getTemporaryPath() + templateName);

                try {
                    File file = new File(PrivelegyDir.RECORDS_PATH.getTemporaryPath() + templateName);

                    if (file.exists()) {
                        String appPath;
                        if (templateName.endsWith("doc") || templateName.endsWith("docx")) {
                            appPath = Settings.M_OFFICE_PATH;
                        } else {
                            appPath = Settings.E_OFFICE_PATH;
                        }

                        Process process = Runtime.getRuntime().exec(appPath + " " + file.getAbsolutePath());
                        int result = process.waitFor();

                        byte[] buffer = Files.toByteArray(file);

                        String hash = HashBuilder.INSTANCE.calculate();

                        final Record record = new Record();
                        record.setCreatedBy(applicationUser.getId());
                        record.setCreatedDate(new Date());
                        record.setPath("./record/".concat(String.valueOf(patient.getId())).concat("/"));
                        record.setPatientId(patient.getId());
                        record.setUpdatedBy(applicationUser.getId());
                        record.setBinary(new String(Base64.encodeBase64(buffer), "UTF-8"));
                        record.setName(hash.concat(NameValidator.INSTANCE.getExtension(templateName)));

                        this.SERVICE_CLIENT.updateRecord(record);
                        this.tableService.add(record, patient);
                        ResultToolbarService.INSTANCE.showSuccessStatus();
                    } else {
                        ResultToolbarService.INSTANCE.showFailedStatus("Файл не найден");
                    }
                } catch (IOException ioe) {
                    LOGGER.error(getClass().getName() + ": " + ioe.getMessage());
                    ResultToolbarService.INSTANCE.showFailedStatus("Ошибка сети");
                } catch (InterruptedException ie) {
                    LOGGER.error(getClass().getName() + ": " + ie.getMessage());
                    ResultToolbarService.INSTANCE.showFailedStatus("Ошибка сети");
                }
            }
        });
    }

}
