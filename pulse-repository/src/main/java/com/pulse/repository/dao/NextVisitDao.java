package com.pulse.repository.dao;

import com.pulse.model.NextVisit;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;

/**
 *
 * @author befast
 */
public interface NextVisitDao {
    
    public NextVisit update(NextVisit visit) throws SQLException, EmptyResultDataAccessException;
    
    public List<NextVisit> findBy(String dateBuffer) throws SQLException, EmptyResultDataAccessException;
    
    public NextVisit delete(long id) throws SQLException, EmptyResultDataAccessException;
}
