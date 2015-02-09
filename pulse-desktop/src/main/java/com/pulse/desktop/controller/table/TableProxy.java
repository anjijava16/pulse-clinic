package com.pulse.desktop.controller.table;

import com.pulse.model.Visit;
import com.pulse.model.constant.Privelegies;
import java.text.SimpleDateFormat;
import com.pulse.model.constant.Privelegy;

/**
 *
 * @author befast
 */
public enum TableProxy {
    INSTANCE;
    
    private TableProxy() {
    }
    
    private final RegistryTableService REGISTRY_SERVICE = new RegistryTableService();
    private final LaboratoryTableService LABORATORY_SERVICE = new LaboratoryTableService();
    
    public String[] getRightBufferFrom(Visit visit, Privelegy privelegy, SimpleDateFormat dateFormat) {
        final String[] buffer;
        
        switch (privelegy.getId()) {                
            case Privelegies.LABORATORY:
                buffer = LABORATORY_SERVICE.proxyFrom(visit, dateFormat);
                return buffer;
                
            default:
                buffer = REGISTRY_SERVICE.proxyFrom(visit, dateFormat);
                return buffer;
        }
    }
}
