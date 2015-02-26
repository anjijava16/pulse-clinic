package com.pulse.desktop.controller.table;


import com.pulse.desktop.controller.service.PatientService;
import com.pulse.desktop.controller.service.UserFacade;
import com.pulse.desktop.controller.table.TableService.TableHolder;
import com.pulse.desktop.view.util.Values;
import com.pulse.model.Visit;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import com.pulse.model.Patient;
import com.pulse.model.User;
import com.pulse.model.constant.BonusStatus;
import com.pulse.model.constant.VisitType;
import com.pulse.model.constant.PaymentStatus;
import com.pulse.model.constant.Privelegy;
import com.pulse.model.constant.Status;

/**
 * 
 * @author befast
 */
public class BookKeepingTableService {
    
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
    private TableHolder holder;
    
    /*
     "Р”Р°С‚Р° РїСЂРёС…РѕРґР°", "РџСЂ.id", "РџР°.id", "Р¤Р?Рћ", "Р”Р°С‚Р° СЂРѕР¶РґРµРЅРёСЏ",
     "РќР°РїСЂР°РІР»РµРЅРёРµ", "Р“СЂСѓРїРїР°", "РђРЅР°-Р·/РћСЃРј-СЂ", "Р›РµС‡Р°С‰РёР№ РІСЂР°С‡", "РћРїР»Р°С‚РёР»",                                                       "РџР°С†РёРµРЅС‚",
     "РўРёРї", "РЎС‚Р°С‚СѓСЃ", "РћС‚ РѕСЂРі-РёРё", "РћС‚ РІСЂР°С‡Р°", "РЎРѕР·РґР°Р»"};
    */
                
    public BookKeepingTableService(TableHolder holder) {
        this.holder = holder;
    }
    
    public void proxyFrom(List<Visit> list) {
        final AtomicInteger number = new AtomicInteger(0);
        list.stream().forEach((visit) -> {
            final String[] data = new String[TableService.INSTANCE.BOOKKEEPING_TABLE_HEADER.length];
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
