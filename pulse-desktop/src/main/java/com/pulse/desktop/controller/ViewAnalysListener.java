/*
 * Copyright 2015 Vladimir Shin
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.pulse.desktop.controller;


import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.table.TableService;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import com.pulse.desktop.view.util.ConstantValues;
import com.pulse.desktop.view.util.FileManager;
import com.pulse.desktop.view.util.Settings;
import com.pulse.model.Visit;
import com.pulse.model.constant.Privilege;
import com.pulse.model.constant.PrivelegyDir;
import com.pulse.model.constant.Status;
import com.pulse.rest.client.VisitClient;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class ViewAnalysListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final FileManager FILEMANAGER = new FileManager();

    private SimpleDateFormat dateFormat;

    private PrivelegyDir privelegyDir;

    private VisitClient visitClient = new VisitClient();

    public ViewAnalysListener(Privilege privilege, TableService.TableHolder holder, SimpleDateFormat dateFormat) {
        super(privilege, holder);
        this.dateFormat = dateFormat;

        this.privelegyDir = PrivelegyDir.getPathBy(privilege);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        ThreadPoolService.INSTANCE.execute(() -> {
            int row = getTableHolder().getTable().getSelectedRow();

            if (row == -1) {
                ResultToolbarService.INSTANCE.showFailedStatus("Анализ не выбран");

                return;
            }

            String visitIdBuffer = getTableHolder().getTable().getValueAt(row, TableService.VISIT_ID_FIELD).toString();
            String patientIdBuffer = getTableHolder().getTable().getValueAt(row, TableService.PATIENT_ID_FIELD).toString();
            String visitStatusBuffer = getTableHolder().getTable().getValueAt(row, TableService.VISIT_TYPE_FIELD).toString();
            String visitDateBuffer = getTableHolder().getTable().getValueAt(row, TableService.COMMING_DATE_FIELD).toString();

            LOGGER.info(getClass() + "~> visitIdBuffer: " + visitIdBuffer);
            LOGGER.info(getClass() + "~> patientIdBuffer: " + patientIdBuffer);
            LOGGER.info(getClass() + "~> visitStatusBuffer: " + visitStatusBuffer);
            LOGGER.info(getClass() + "~> visitDateBuffer: " + visitDateBuffer);

            long visitId = Long.valueOf(visitIdBuffer);
            long patientId = Long.valueOf(patientIdBuffer);

            if (visitId < 0) {
                ResultToolbarService.INSTANCE.showFailedStatus("Неверный ID. Обновите список и попробуйте снова");
                return;
            }

            if (patientId < 0) {
                ResultToolbarService.INSTANCE.showFailedStatus("Неверный ID. Обновите список и попробуйте снова");
                return;
            }

            String groupName = TableService.getValueAt(getTableHolder(), row, TableService.GROUP_FIELD).toString();
            String analysName = TableService.getValueAt(getTableHolder(), row, TableService.ANALYS_FIELD).toString();
            String patientNfp = TableService.getValueAt(getTableHolder(), row, TableService.PATIENT_NFP_FIELD).toString();
            String patientBirthday = TableService.getValueAt(getTableHolder(), row, TableService.BIRTHDAY_FIELD).toString();

            if (groupName == null || groupName.equals(ConstantValues.UNKNOWN)) {
                return;
            }
            if (analysName == null || analysName.equals(ConstantValues.UNKNOWN)) {
                return;
            }

            LOGGER.info(getClass() + "~> groupName: " + groupName);
            LOGGER.info(getClass() + "~> analysName: " + analysName);
            LOGGER.info(getClass() + "~> patientNfp: " + patientNfp);
            LOGGER.info(getClass() + "~> patientBirthday: " + patientBirthday);

            LOGGER.info(getClass() + "~> " + visitStatusBuffer.equals(Status.NOT_HANDLED.getName()));

            if (visitStatusBuffer.equals(Status.NOT_HANDLED.getName())) {
                LOGGER.info(getClass() + "~> groupName: " + groupName);

                FILEMANAGER.copyToTemp(privelegyDir.getAnalysPath() + groupName + File.separator, privelegyDir.getTemporaryPath(), analysName);

                try {
                    String appPath;
                    if (analysName.endsWith("doc") || analysName.endsWith("docx")) {
                        appPath = Settings.M_OFFICE_PATH;
                    } else {
                        appPath = Settings.E_OFFICE_PATH;
                    }

                    Process process = Runtime.getRuntime().exec(appPath + " " + privelegyDir.getTemporaryPath() + analysName);
                    process.waitFor();

                    byte[] buffer = FILEMANAGER.readFile(privelegyDir.getTemporaryPath() + analysName);

                    Visit visit = visitClient.getBy(visitId);
                    if (visit != null) {
                        visit.setVisitStatus(Settings.STATUS_TYPE_VIEW);
                        visit.setBinary(new String(Base64.encodeBase64(buffer), "UTF-8"));

                        visitClient.update(visit);
                    }
                } catch (IOException ioe) {
                    LOGGER.error(getClass().getName() + ": " + ioe.getMessage());
                    ResultToolbarService.INSTANCE.showFailedStatus("Ошибка сети");
                } catch (InterruptedException ie) {
                    LOGGER.error(getClass().getName() + ": " + ie.getMessage());
                    ResultToolbarService.INSTANCE.showFailedStatus("Ошибка");
                }
            } else {
                try {
                    final Visit visit = visitClient.getWithBinaryBy(visitId);

                    if (visit == null) {
                        throw new NullPointerException("visit is null");
                    }

                    byte[] decodedBuffer = Base64.decodeBase64(visit.getBinary());

                    LOGGER.info("visit.getFilename(): " + visit.getFilename());

                    FileOutputStream outstream = new FileOutputStream(privelegyDir.getTemporaryPath() + visit.getFilename());
                    outstream.write(decodedBuffer);
                    outstream.flush();
                    outstream.close();

                    String appPath;
                    if (visit.getFilename().endsWith("doc") || visit.getFilename().endsWith("docx")) {
                        appPath = Settings.M_OFFICE_PATH;
                    } else {
                        appPath = Settings.E_OFFICE_PATH;
                    }

                    Process process = Runtime.getRuntime().exec(appPath + " "
                            + privelegyDir.getTemporaryPath() + visit.getFilename());
                    process.waitFor();

                    byte[] buffer = FILEMANAGER.readFile(privelegyDir.getTemporaryPath() + visit.getFilename());

                    visit.setVisitStatus(Settings.STATUS_TYPE_VIEW);
                    visit.setBinary(new String(Base64.encodeBase64(buffer), "UTF-8"));
                    visitClient.update(visit);
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
