package com.pulse.model;

/**
 *
 * @author befast
 */
public class FilteredVisit extends Pojo {
    
    private boolean searchByDepartment;
    private long doctorId;
    private int departmentId;
    private String fromDate;
    private String untilDate;

    public boolean isSearchByDepartment() {
        return searchByDepartment;
    }

    public void setSearchByDepartment(boolean searchByDepartment) {
        this.searchByDepartment = searchByDepartment;
    }   
    
    public long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }    

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getUntilDate() {
        return untilDate;
    }

    public void setUntilDate(String untilDate) {
        this.untilDate = untilDate;
    }
        
}
