package com.pulse.model;

/**
 *
 * @author befast
 */
public class PatientRoom extends Model {
    
    private String name;
    private int capacity = 0;
    private int limitation = 1;

    public String getName() {
        return name;
    }

    public int getLimitation() {
        return limitation;
    }

    public void setLimitation(int limitation) {
        this.limitation = limitation;
    }    

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
        
    public void setName(String name) {
        this.name = name;
    }
        
}
