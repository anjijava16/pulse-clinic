/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pulse.model;

import java.util.List;

/**
 *
 * @author befast
 */
public class Records extends Pojo {
    
    private List<Record> list;
    
    public Records() {
    }

    public List<Record> getList() {
        return list;
    }

    public void setList(List<Record> list) {
        this.list = list;
    }
        
}
