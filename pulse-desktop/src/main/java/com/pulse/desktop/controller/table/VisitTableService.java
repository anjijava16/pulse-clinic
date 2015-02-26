package com.pulse.desktop.controller.table;

import com.pulse.desktop.controller.service.PatientService;
import com.pulse.desktop.controller.service.UserFacade;
import com.pulse.desktop.view.util.Values;
import com.pulse.model.Visit;
import java.util.List;

import com.pulse.model.Patient;
import com.pulse.model.User;
import com.pulse.model.constant.Privelegy;

/**
 *
 * @author befast
 */
public class VisitTableService {
    private TableService.TableHolder holder;
    
    public VisitTableService(TableService.TableHolder holder) {
        this.holder = holder;        
    }
    
    public void add(List<Visit> list) {
        final String[] data = new String[TableService.INSTANCE.STATISTIC_TABLE_HEADER.length];
        
        int number = 1;
                        
        for (Visit visit : list) {        
            int ptr = 0;
            
            final Patient patient = PatientService.INSTANCE.getById(visit.getPatientId());
            final User account = UserFacade.INSTANCE.findBy(visit.getDoctorId());
            
            data[ptr++] = String.valueOf(number++);
            data[ptr++] = String.valueOf(visit.getId());
            
            if (patient != null)
                data[ptr] = String.valueOf(patient.getNfp());
            else
                data[ptr] = Values.Unknown.getValue();
            
            ptr++;
            
            data[ptr++] = Privelegy.findById(visit.getDepartmentId()).getName();
            
            data[ptr++] = visit.getAnalysGroup();
            data[ptr++] = visit.getAnalysName();
            
            if (account != null)
                data[ptr] = account.getNfp();
            else
                data[ptr] = Values.Unknown.getValue();
            ptr++;
                
            data[ptr++] = visit.getFromOrganisation();
            data[ptr++] = visit.getFromDoctor();
            data[ptr] = visit.getCreatedBy();
            
            holder.getModel().addRow(data);
        }        
    }
}