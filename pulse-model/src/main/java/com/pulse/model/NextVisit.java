package com.pulse.model;

import java.util.Date;

/**
 *
 * @author Vladimir Shin
 */
public class NextVisit extends Model {
    
    private Date visitDate;
    private long patientId;
    private long doctorId;
    private boolean handled;
    
    public NextVisit() {
    }

    public boolean isHandled() {
        return handled;
    }

    public void setHandled(boolean handled) {
        this.handled = handled;
    }    

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }
        
}
