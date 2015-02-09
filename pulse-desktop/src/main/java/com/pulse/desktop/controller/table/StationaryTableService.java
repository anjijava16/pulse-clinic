package com.pulse.desktop.controller.table;

import com.pulse.desktop.controller.service.PatientService;
import com.pulse.desktop.controller.service.UserFacade;
import com.pulse.model.Visit;
import java.text.SimpleDateFormat;
import java.util.List;
import com.pulse.model.Patient;
import com.pulse.model.User;
import com.pulse.model.constant.BonusStatus;
import com.pulse.model.constant.PaymentStatus;

/**
 *
 * @author befast
 */
public class StationaryTableService {
    private TableService.TableHolder holder;
    
    private final SimpleDateFormat SDF = new SimpleDateFormat("yyyy.MM.dd");
    
    public StationaryTableService(TableService.TableHolder holder) {
        this.holder = holder;        
    }
    
    public void add(List<Visit> list) {
        final String[] data = new String[TableService.INSTANCE.STATIONARY_TABLE_HEADER.length];
                                
        for (Visit visit : list) {        
            int ptr = 0;
            
            final Patient patient = PatientService.INSTANCE.getById(visit.getPatientId());
            final User account = UserFacade.INSTANCE.findBy(visit.getDoctorId());
            
            data[ptr++] = SDF.format(visit.getVisitDate());            
            data[ptr++] = String.valueOf(visit.getId());   
            data[ptr++] = account.getNfp();
            data[ptr++] = patient.getNfp();
            data[ptr++] = visit.getRoom();
            data[ptr++] = PaymentStatus.findBy(visit.getPaymentStatus()).getName();
            data[ptr++] = BonusStatus.findBy(visit.getDiscount()).getName();
            
            holder.getModel().addRow(data);
        }        
    }
}
