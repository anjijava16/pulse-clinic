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


import com.pulse.desktop.controller.service.PatientService;
import com.pulse.desktop.controller.service.UserFacade;
import com.pulse.desktop.view.util.Values;
import com.pulse.model.Visit;
import java.text.SimpleDateFormat;
import com.pulse.model.Patient;
import com.pulse.model.User;
import com.pulse.model.constant.BonusStatus;
import com.pulse.model.constant.VisitType;
import com.pulse.model.constant.PaymentStatus;
import com.pulse.model.constant.Privelegy;
import com.pulse.model.constant.Status;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class LaboratoryTableService {
    
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    public LaboratoryTableService() {
    }
    
    public String[] proxyFrom(Visit visit, SimpleDateFormat dateFormat) {
        final String[] data = new String[TableService.INSTANCE.GENERAL_TABLE_HEADER.length];
        int ptr = 0;        
        
        final Patient patient = PatientService.INSTANCE.getById(visit.getPatientId());
        final User account = UserFacade.INSTANCE.findBy(visit.getDoctorId());
        
        String department;
        if (Privelegy.findById(visit.getDepartmentId()) != null) {
            department = Privelegy.findById(visit.getDepartmentId()).getName();
        } else {
            department = Values.Unknown.getValue();
        }
        
        String doctorNfp;
        if (account == null) {
            doctorNfp = Values.Unknown.getValue();
        } else {
            doctorNfp = account.getNfp();
        }
        
        final String visitDate = dateFormat.format(visit.getVisitDate());
        
        data[ptr++] = visitDate;
        data[ptr++] = String.valueOf(visit.getId());
        data[ptr++] = String.valueOf(visit.getPatientId());
        data[ptr++] = patient.getNfp();
        data[ptr++] = this.sdf.format(patient.getBirthday());
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
        //data[ptr++] = visit.getCreatedBy();
        
        return data;
    }
}
