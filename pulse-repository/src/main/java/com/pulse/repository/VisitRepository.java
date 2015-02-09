package com.pulse.repository;

import com.pulse.model.constant.FilterType;
import com.pulse.model.FilteredVisit;
import java.util.List;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.dao.EmptyResultDataAccessException;

import com.pulse.model.Visit;
import com.pulse.repository.dao.VisitDao;
import com.pulse.repository.mapper.VisitMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author Vladimir Shin <vladimir.shin@gmail.com>
 */
@Repository (value = "visitRepository")
public class VisitRepository implements VisitDao {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private VisitMapper visitMapper;
    
    @Value("${repository.patientvisit.table.name:patient_visit}")
    private String tableName;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
        
    public VisitRepository() {        
    }
    
    @Override
    public Visit get(long id) throws SQLException, EmptyResultDataAccessException {
        final String query = "SELECT * FROM " + this.tableName + " WHERE id=?";
        
        final Visit visit = this.jdbcTemplate.queryForObject(query, new Long[] {id}, this.visitMapper);

        return visit;
    }

    @Override
    public List<Visit> list(long patientId) throws SQLException, EmptyResultDataAccessException {
        final String query = "SELECT * FROM " + this.tableName + " WHERE patient_id=?";
        
        final List<Visit> recordList = this.jdbcTemplate.query(query, new Long[] {patientId}, this.visitMapper);

        return recordList;
    }

    @Override
    public Visit update(Visit visit) throws SQLException, EmptyResultDataAccessException {
        if (visit == null) return null;
                
        final String updateQuery = "UPDATE " + this.tableName + " SET visit_time=?,patient_id=?,department=?,course_group_name=?,"
                + "course_analys_name=?,paying_status=?,patient_type=?,visit_type=?,visit_status=?,doctor_id=?,till_date=?,"
                + "from_organisation=?,from_doctor=?,discount=?,created_by=?,payback=?,filename=?,filepath=?,room=?,released=? WHERE id=?";
        
        final String createQuery = "INSERT INTO " + this.tableName + " (visit_time, patient_id, department, course_group_name,"
                + " course_analys_name, paying_status, patient_type, visit_type, visit_status, doctor_id, till_date,"
                + " from_organisation, from_doctor,discount,created_by,payback,filename,filepath,room) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        // Create record logic
        if (visit.getId() == -1) {
            int updated = this.jdbcTemplate.update(createQuery, 
                    visit.getVisitDate(), 
                    visit.getPatientId(), 
                    visit.getDepartmentId(), 
                    visit.getAnalysGroup(), 
                    visit.getAnalysName(),
                    visit.getPaymentStatus(),
                    visit.getPatientType(),
                    visit.getVisitType(),
                    visit.getVisitStatus(),
                    visit.getDoctorId(),
                    visit.getTillDate(),
                    visit.getFromOrganisation(),
                    visit.getFromDoctor(),
                    visit.getDiscount(),
                    visit.getCreatedBy(),
                    visit.isPayback(),
                    visit.getFilename(),
                    visit.getFilepath(),
                    visit.getRoom());
        }
        
        // Update record logic
        else {
            int updated = this.jdbcTemplate.update(updateQuery, 
                    visit.getVisitDate(), 
                    visit.getPatientId(), 
                    visit.getDepartmentId(), 
                    visit.getAnalysGroup(), 
                    visit.getAnalysName(),
                    visit.getPaymentStatus(),
                    visit.getPatientType(),
                    visit.getVisitType(),
                    visit.getVisitStatus(),
                    visit.getDoctorId(),
                    visit.getTillDate(),
                    visit.getFromOrganisation(),
                    visit.getFromDoctor(),
                    visit.getDiscount(),
                    visit.getCreatedBy(),
                    visit.isPayback(),
                    visit.getFilename(),
                    visit.getFilepath(),
                    visit.getRoom(),
                    visit.isReleased(),
                    visit.getId());
        }
        
        return visit;
    }

    @Override
    public Visit delete(long id) throws SQLException, EmptyResultDataAccessException {
        if (id < 0) return null;
        
        final String getQuery = "SELECT * FROM " + this.tableName + " WHERE id=?";
        final String deleteQuery = "DELETE FROM " + this.tableName + " WHERE id=?";
        
        final Visit visit = this.jdbcTemplate.queryForObject(getQuery, this.visitMapper, id);
        
        int result;
        if (visit != null) {        
            result = this.jdbcTemplate.update(deleteQuery, id);
        }
        
        return visit;
    }

    @Override
    public List<Visit> listOrganisationsVisits(String from, String until) throws SQLException, EmptyResultDataAccessException {
        final String query = "SELECT * FROM " + this.tableName + " WHERE trim(coalesce(from_organisation, '')) <> '' AND visit_time >= DATE(?) AND visit_time <= DATE(?)";
        
        final List<Visit> recordList = this.jdbcTemplate.query(query, this.visitMapper, from, until);
        
        return recordList;
    }

    @Override
    public List<Visit> listOrganisationVisitsBy(String organisation, String from, String until) throws SQLException, EmptyResultDataAccessException {
        final String query = "SELECT * FROM " + this.tableName + " WHERE from_organisation like ? AND visit_time >= DATE(?) AND visit_time <= DATE(?)";
        
        final List<Visit> recordList = this.jdbcTemplate.query(query, new Object[]{ "%" + organisation + "%", from, until}, this.visitMapper);
        
        return recordList;
    }

    @Override
    public List<Visit> findByDate(String dateBuffer) throws SQLException, EmptyResultDataAccessException {
        final String query = "SELECT * FROM " + this.tableName + " WHERE DATE(visit_time)=? ORDER BY id";
        
        final List<Visit> recordList = this.jdbcTemplate.query(query, this.visitMapper, dateBuffer);
        
        return recordList;
    }
    
    @Override
    public List<Visit> findByTemplate(String template) throws SQLException, EmptyResultDataAccessException {
        final String query = "select a.*, b.nfp, c.nfp "
                      + "from " + this.tableName + " a "
                      + "left join "
                      + "patient c "
                      + " on c.id=a.patient_id "
                      + "left join "
                      + "user b "
                      + "on b.id=a.doctor_id "                    
                      + "where "
                      + "a.course_analys_name like ? "
                      + "or a.course_group_name like ? "                    
                      + "or b.nfp like ? "
                      + "or c.nfp like ? "                    
                      + "or DATE_FORMAT(b.birthday, \'%Y-%m-%d\') like ? "
                      + "or DATE_FORMAT(c.birthday, \'%Y-%m-%d\') like ?";
        
        String temp = "%"+template+"%";
        
        final List<Visit> recordList = this.jdbcTemplate.query(
                query, new Object[] {temp, temp, temp, temp, temp, temp}, this.visitMapper
        );
        
        return recordList;
    }

    @Override
    public List<Visit> filterBy(int filterType, int type, String date) throws SQLException, EmptyResultDataAccessException {
        String query = null;
        
        if (filterType == FilterType.ALL.getId()) {
           return findByDate(date);
        }
        else if (filterType == FilterType.VISIT_TYPE_FILTER.getId()) {
            query = "SELECT * FROM " + this.tableName + " WHERE visit_type=? AND DATE(visit_time)=?";
        } else if (filterType == FilterType.VISIT_STATUS_FILTER.getId()) {
            query = "SELECT * FROM " + this.tableName + " WHERE visit_status=? AND DATE(visit_time)=?";
        } 
        
        this.LOGGER.info("query {}, {}, {}", query, type, date);
        
        final List<Visit> recordList = this.jdbcTemplate.query(query, this.visitMapper, type, date);
        
        return recordList;
    }
    
    @Override
    public List<Visit> filterBy(FilteredVisit filter) throws SQLException, EmptyResultDataAccessException {        
        final String queryByDoctor = "SELECT * FROM " + this.tableName + " WHERE doctor_id=? AND visit_time >= DATE(?) AND visit_time <= DATE(?)";
        final String queryByDepartment = "SELECT * FROM " + this.tableName + " WHERE department=? AND visit_time >= DATE(?) AND visit_time <= DATE(?)";
        
        final List<Visit> recordList;
        
        if (filter.isSearchByDepartment()) {
            recordList = this.jdbcTemplate.query(queryByDepartment, this.visitMapper, filter.getDepartmentId(), filter.getFromDate(), filter.getUntilDate());            
        } else {
            recordList = this.jdbcTemplate.query(queryByDoctor, this.visitMapper, filter.getDoctorId(), filter.getFromDate(), filter.getUntilDate()); 
        }
        
        return recordList;
    }
    
    @Override
    public List<Visit> filterBy(int department, long doctor) throws SQLException, EmptyResultDataAccessException {        
        final String queryByDoctor = "SELECT * FROM " + this.tableName + " WHERE department=? AND doctor_id=? AND released=0";
        
        final String selectAll = "SELECT * FROM " + this.tableName + " WHERE department=? AND released=0";
        
        final List<Visit> recordList;
        
        if (doctor == 0) {
            recordList = this.jdbcTemplate.query(selectAll, this.visitMapper, department); 
        } else {
            recordList = this.jdbcTemplate.query(queryByDoctor, this.visitMapper, department, doctor); 
        }
        
        return recordList;
    }
}
