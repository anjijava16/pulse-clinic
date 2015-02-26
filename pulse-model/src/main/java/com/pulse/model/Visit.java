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
package com.pulse.model;


import java.util.Date;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class Visit extends Model {

    private Date visitDate;
    private long patientId;
    private int departmentId;
    private String analysGroup;
    private String analysName;
    private int paymentStatus;
    private int patientType;
    private int visitType;
    private int visitStatus;
    private long doctorId;
    
    private String tillDate;
    
    private String fromOrganisation;
    private String fromDoctor;
    
    private int discount;
    
    private boolean payback;
    
    private String createdBy;
    
    private String binary;
    
    private String filename;
    private String filepath;
    
    private String room;
    private boolean released;
    
    public Visit() {
    }

    public boolean isReleased() {
        return released;
    }   

    public void setReleased(boolean released) {
        this.released = released;
    }
        
    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }    

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }    

    public String getBinary() {
        return binary;
    }

    public void setBinary(String binary) {
        this.binary = binary;
    }
        
    public boolean isPayback() {
        return payback;
    }

    public void setPayback(boolean payback) {
        this.payback = payback;
    }
        
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getAnalysGroup() {
        return analysGroup;
    }

    public void setAnalysGroup(String analysGroup) {
        this.analysGroup = analysGroup;
    }

    public String getAnalysName() {
        return analysName;
    }

    public void setAnalysName(String analysName) {
        this.analysName = analysName;
    }

    public int getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(int paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public int getPatientType() {
        return patientType;
    }

    public void setPatientType(int patientType) {
        this.patientType = patientType;
    }

    public int getVisitType() {
        return visitType;
    }

    public void setVisitType(int visitType) {
        this.visitType = visitType;
    }

    public int getVisitStatus() {
        return visitStatus;
    }

    public void setVisitStatus(int visitStatus) {
        this.visitStatus = visitStatus;
    }

    public long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }

    public String getTillDate() {
        return tillDate;
    }

    public void setTillDate(String tillDate) {
        this.tillDate = tillDate;
    }

    public String getFromOrganisation() {
        return fromOrganisation;
    }

    public void setFromOrganisation(String fromOrganisation) {
        this.fromOrganisation = fromOrganisation;
    }

    public String getFromDoctor() {
        return fromDoctor;
    }

    public void setFromDoctor(String fromDoctor) {
        this.fromDoctor = fromDoctor;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
        
}
