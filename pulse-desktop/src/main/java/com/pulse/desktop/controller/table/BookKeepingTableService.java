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
package com.pulse.desktop.controller.table;


import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.pulse.desktop.controller.service.PatientService;
import com.pulse.desktop.controller.service.UserFacade;
import com.pulse.desktop.controller.table.TableService.TableHolder;
import com.pulse.desktop.view.util.ConstantValues;
import com.pulse.model.Patient;
import com.pulse.model.User;
import com.pulse.model.Visit;
import com.pulse.model.constant.BonusStatus;
import com.pulse.model.constant.PaymentStatus;
import com.pulse.model.constant.Privilege;
import com.pulse.model.constant.Status;
import com.pulse.model.constant.VisitType;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class BookKeepingTableService {
    
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
    private TableHolder holder;

    public BookKeepingTableService(TableHolder holder) {
        this.holder = holder;
    }
    
    public void proxyFrom(final List<Visit> list) {
        final AtomicInteger number = new AtomicInteger(0);
        list.stream().forEach((visit) -> {
            final String[] data = new String[TableService.INSTANCE.BOOKKEEPING_TABLE_HEADER.length];
            int ptr = 0;        

            final Patient patient = PatientService.INSTANCE.getById(visit.getPatientId());
            final User account = UserFacade.INSTANCE.findBy(visit.getDoctorId());

            if (patient == null) {
                throw new NullPointerException("Can't find patient by id: " + visit.getPatientId());
            }

            String department;
            if (Privilege.findById(visit.getDepartmentId()) != null) {
                department = Privilege.findById(visit.getDepartmentId()).getName();
            } else {
                department = ConstantValues.UNKNOWN;
            }

            String doctorNfp;
            if (account == null) {
                doctorNfp = ConstantValues.UNKNOWN;
            } else {
                doctorNfp = account.getNfp();
            }

            final String visitDate = dateFormat.format(visit.getVisitDate());
            data[ptr++] = String.valueOf(number.incrementAndGet());
            data[ptr++] = visitDate;
            data[ptr++] = String.valueOf(visit.getId());
            data[ptr++] = String.valueOf(visit.getPatientId());
            data[ptr++] = patient.getNfp();
            data[ptr++] = String.valueOf(patient.getBirthday());
            data[ptr++] = department;
            data[ptr++] = visit.getAnalysGroup();
            data[ptr++] = visit.getAnalysName();
            data[ptr++] = doctorNfp;
            data[ptr++] = PaymentStatus.findBy(visit.getPaymentStatus()).getName();
            data[ptr++] = VisitType.findBy(visit.getPatientType()).getLabel();
            data[ptr++] = BonusStatus.findBy(visit.getDiscount()).getName();
            data[ptr++] = Status.findBy(visit.getVisitStatus()).getName();
            data[ptr++] = visit.getFromOrganisation();
            data[ptr++] = visit.getFromDoctor();
            data[ptr++] = visit.getCreatedBy();
            
            holder.getModel().addRow(data);
        });
    }
}

