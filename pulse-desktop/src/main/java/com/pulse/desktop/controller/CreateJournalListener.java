package com.pulse.desktop.controller;

import com.google.common.io.Files;
import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.service.UserFacade;
import com.pulse.desktop.controller.table.JournalTableService;
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
import com.pulse.model.Journal;
import com.pulse.model.User;
import com.pulse.model.constant.Privelegy;
import com.pulse.model.constant.PrivelegyDir;
import com.pulse.rest.client.JournalClient;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Vladimir Shin
 */
public class CreateJournalListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private Privelegy privelegy;
    private TableHolder tableHolder;

    private final JournalClient JOURNAL_CLIENT = new JournalClient();
    private final FileManager FILE_MANAGER = new FileManager();

    private JournalTableService journalTableService;

    public CreateJournalListener(Privelegy privelegy, TableService.TableHolder tableHolder) {
        super(privelegy, tableHolder);
        this.privelegy = privelegy;
        this.tableHolder = tableHolder;
        this.journalTableService = new JournalTableService(tableHolder);
    }

    private String getSelectedForm() {
        final File recordsFolder = new File(PrivelegyDir.JOURNALS_PATH.getTemplatePath());

        this.LOGGER.info("Review folder: " + PrivelegyDir.JOURNALS_PATH.getTemplatePath());

        if (recordsFolder.exists()) {
            final String[] recordsNames = recordsFolder.list();

            final String selectedTemplate = (String) JOptionPane.showInputDialog(
                    null,
                    "Выберите шаблон",
                    "Создание",
                    JOptionPane.NO_OPTION,
                    null,
                    recordsNames,
                    null);

            return selectedTemplate;
        }

        return null;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        ThreadPoolService.INSTANCE.execute(() -> {
            final String templateName = getSelectedForm();

            if (templateName == null || templateName.isEmpty()) {
                return;
            }
            if (UserFacade.INSTANCE.getApplicationUser() == null) {
                return;
            }

            final String applicationUsername = UserFacade.INSTANCE.getApplicationUser().getNfp();
            final User applicationUser = UserFacade.INSTANCE.findBy(applicationUsername);

            FILE_MANAGER.copyFile(PrivelegyDir.JOURNALS_PATH.getTemplatePath() + templateName,
                    PrivelegyDir.JOURNALS_PATH.getTemporaryPath() + templateName);

            try {
                File file = new File(PrivelegyDir.JOURNALS_PATH.getTemporaryPath() + templateName);

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

                    final Journal journal = new Journal();
                    journal.setCreatedBy(applicationUser.getId());
                    journal.setCreatedDate(new Date());
                    journal.setPath("./journal/".concat(hash.concat(NameValidator.INSTANCE.getExtension(templateName))));
                    journal.setUpdatedBy(applicationUser.getId());
                    journal.setBinary(new String(Base64.encodeBase64(buffer), "UTF-8"));
                    journal.setName(templateName);

                    this.JOURNAL_CLIENT.update(journal);
                    this.journalTableService.add(journal);

                    ResultToolbarService.INSTANCE.showSuccessStatus();
                } else {
                    ResultToolbarService.INSTANCE.showFailedStatus("Файд не найден");
                }
            } catch (IOException ioe) {
                LOGGER.error(getClass().getName() + ": " + ioe.getMessage());
                ResultToolbarService.INSTANCE.showFailedStatus("Ошибка сети");
            } catch (InterruptedException ie) {
                LOGGER.error(getClass().getName() + ": " + ie.getMessage());
                ResultToolbarService.INSTANCE.showFailedStatus("Ошибка");
            }
        });
    }

}
