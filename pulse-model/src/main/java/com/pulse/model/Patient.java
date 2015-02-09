package com.pulse.model;

import java.util.Date;

/**
 *
 * @author Vladimir Shin
 */
public class Patient extends Model {
    
    private String nfp;
    private int sex;
    private Date birthday;
    private int type;
    private String email;
    private String mobile;
    private int discount;
    private String token;
    
    public Patient() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }    

    public String getNfp() {
        return nfp;
    }

    public void setNfp(String nfp) {
        this.nfp = nfp;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
        
}