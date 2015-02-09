package com.pulse.model;

import java.util.Date;

/**
 *
 * @author befast
 */
public class User extends Model {
    
    private String nfp;
    private Date birthday;
    private String username;
    private String password;
    private int privelegy;

    public String getNfp() {
        return nfp;
    }

    public void setNfp(String nfp) {
        this.nfp = nfp;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPrivelegy() {
        return privelegy;
    }

    public void setPrivelegy(int privelegy) {
        this.privelegy = privelegy;
    }
        
}
