package com.pulse.model;

import java.util.Date;

/**
 *
 * @author Vladimir Shin
 */
public class Record extends Model {
    
    private long createdBy;
    private long patientId;
    private Date createdDate;
    private long updatedBy;
    private String name;
    private String path;
    private boolean busy;
    private String binary;
    
    public Record() {
    }

    public String getBinary() {
        return binary;
    }

    public void setBinary(String binary) {
        this.binary = binary;
    }
        
    public long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long accountId) {
        this.patientId = accountId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }
        
}
