package com.pulse.model.constant;

/**
 *
 * @author Vladimir Shin
 */
public enum BonusStatus {
    
    NO("Нет", 0),
    YES("Есть", 1),;
    
    private BonusStatus(String name, int id) {
        this.name = name;
        this.id = id;
    }
    
    private final String name;
    private final int id;

    public static BonusStatus findBy(String name) {
        for (BonusStatus status : values()) {
            if (status.getName().equals(name)) return status;
        }
        
        return null;
    }
    
    public static BonusStatus findBy(int id) {
        for (BonusStatus status : values()) {
            if (status.getId() == id) return status;
        }
        
        return null;
    }
    
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
