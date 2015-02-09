package com.pulse.desktop.controller;

import com.google.common.io.Files;
import com.pulse.desktop.controller.table.JournalTableService;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import com.pulse.desktop.controller.builder.MessageBuilder;
import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.table.TableService;
import com.pulse.desktop.view.util.Settings;
import com.pulse.model.Journal;
import com.pulse.model.constant.Privelegy;
import com.pulse.model.constant.PrivelegyDir;
import com.pulse.rest.client.JournalClient;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author befast
 */
public class OpenJournalListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private Privelegy privelegy;

    private TableService.TableHolder tableHolder;

    private JournalClient journalClient = new JournalClient();

    private JournalTableService journalService;

    public OpenJournalListener(Privelegy privelegy, TableService.TableHolder tableHolder) {
        super(privelegy, tableHolder);
        this.privelegy = privelegy;
        this.tableHolder = tableHolder;
        this.journalService = new JournalTableService(tableHolder);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        this.LOGGER.info(getClass() + ": actionPerformed()");

        ThreadPoolService.INSTANCE.execute(() -> {
            if (getTableHolder() != null && getTableHolder().getTable() != null) {
                int row = getTableHolder().getTable().getSelectedRow();

                if (row == -1) {
                    ResultToolbarService.INSTANCE.showFailedStatus("Запись не выбрана");
                    return;
                }

                final long recordId = Long.valueOf(getTableHolder().getModel()
                        .getValueAt(row, TableService.RECORD_ID_FIELD).toString());

                if (recordId < 0) {
                    ResultToolbarService.INSTANCE.showFailedStatus("Неверный ID. Обновите список и попробуйте снова");
                    return;
                }

                try {
                    final Journal journal = this.journalClient.getWithBinaryBy(recordId);

                    if (journal == null) {
                        return;
                    }
                    if (journal.getName() == null) {
                        return;
                    }
                    if (journal.getPath() == null) {
                        return;
                    }
                    if (journal.getBinary() == null) {
                        return;
                    }

                    byte[] decodedBuffer = Base64.decodeBase64(journal.getBinary());

                    PrivelegyDir privelegyDir = PrivelegyDir.getPathBy(privelegy);

                    if (privelegyDir == null) {
                        return;
                    }

                    FileOutputStream outstream = new FileOutputStream(privelegyDir.getTemporaryPath() + journal.getName());
                    outstream.write(decodedBuffer);
                    outstream.flush();
                    outstream.close();

                    File file = new File(privelegyDir.getTemporaryPath() + journal.getName());

                    if (file.exists()) {
                        String appPath;
                        if (journal.getName().endsWith("doc") || journal.getName().endsWith("docx")) {
                            appPath = Settings.M_OFFICE_PATH;
                        } else {
                            appPath = Settings.E_OFFICE_PATH;
                        }

                        Process process = Runtime.getRuntime().exec(appPath + " " + file.getAbsolutePath());
                        int result = process.waitFor();

                        byte[] buffer = Files.toByteArray(file);
                        journal.setBinary(new String(Base64.encodeBase64(buffer), "UTF-8"));

                        this.journalClient.update(journal);
                        ResultToolbarService.INSTANCE.showSuccessStatus();
                    } else {
                        ResultToolbarService.INSTANCE.showFailedStatus("Файл не найден");
                    }
                } catch (IOException ioe) {
                    LOGGER.error(getClass().getName() + ": " + ioe.getMessage());
                    ResultToolbarService.INSTANCE.showFailedStatus("Ошибка сети");
                } catch (InterruptedException ie) {
                    LOGGER.error(getClass().getName() + ": " + ie.getMessage());
                    ResultToolbarService.INSTANCE.showFailedStatus("Ошибка");
                }
            }
        });
    }

}
