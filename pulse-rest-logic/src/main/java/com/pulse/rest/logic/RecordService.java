package com.pulse.rest.logic;

import com.pulse.model.Record;
import java.util.List;

/**
 *
 * @author Vladimir Shin <vladimir.shin@gmail.com>
 */
public interface RecordService {
    
    public Record getById(long id);
    
    public List<Record> listBy(long patientId);
    
    public Record update(Record record);
    
    public Record delete(long id);
    
}
