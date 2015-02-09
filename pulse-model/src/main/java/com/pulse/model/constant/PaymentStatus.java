package com.pulse.model.constant;

/**
 *
 * @author befast
 */
public enum PaymentStatus {

    NOT_PAYED("Не оплачено", 0),
    PAYED("Оплачено", 1),
    DEBT("Долг", 2),
    BACK("Возврат", 3);
    
    private PaymentStatus(String name, int id) {
        this.name = name;
        this.id = id;
    }
    
    private final String name;
    private final int id;

    public static PaymentStatus findBy(String name) {
        for (PaymentStatus status : values()) {
            if (status.getName().equals(name)) return status;
        }
        
        return null;
    }
    
    public static PaymentStatus findBy(int id) {
        for (PaymentStatus status : values()) {
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
