package com.pulse.desktop.controller.table;

import com.pulse.desktop.controller.service.PatientService;
import com.pulse.desktop.controller.service.UserFacade;
import com.pulse.model.NextVisit;
import java.text.SimpleDateFormat;
import java.util.List;
import com.pulse.model.Patient;
import com.pulse.model.User;

/**
 *
 * @author befast
 */
public class NextVisitTableService {
    
    private final TableService.TableHolder holder;
    
    private final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy.MM.dd");
    
    public NextVisitTableService(TableService.TableHolder holder) {
        this.holder = holder;        
    }
    
    public void add(List<NextVisit> list) {
        final String[] data = new String[TableService.INSTANCE.NEXT_VISIT_TABLE_HEADER.length];
        
        list.stream().forEach((visit) -> {
            int ptr = 0;
            
            final Patient patient = PatientService.INSTANCE.getById(visit.getPatientId());
            final User account = UserFacade.INSTANCE.findBy(visit.getDoctorId());
            
            data[ptr++] = FORMATTER.format(visit.getVisitDate());            
            data[ptr++] = String.valueOf(visit.getId());            
            data[ptr++] = patient.getNfp();
            data[ptr++] = account.getNfp();
            data[ptr++] = patient.getMobile();
            
            holder.getModel().addRow(data);
        });
    }
}
