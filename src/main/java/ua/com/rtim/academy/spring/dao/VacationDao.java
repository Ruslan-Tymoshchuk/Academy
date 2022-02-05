package ua.com.rtim.academy.spring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ua.com.rtim.academy.domain.Teacher;
import ua.com.rtim.academy.domain.Vacation;

@Component
public class VacationDao implements CrudRepository<Vacation> {

    public static final String GET_ALL_VACATIONS_QUERY = "SELECT vacation_id, startdate, enddate FROM vacations";
    public static final String ADD_NEW_VACATION_QUERY = "INSERT INTO vacations(startdate, enddate) VALUES (?, ?)";
    public static final String GET_VACATION_BY_ID_QUERY = "SELECT vacation_id, startdate, enddate FROM vacations WHERE vacation_id = ?";
    public static final String UPDATE_VACATION_QUERY = "UPDATE vacations SET startdate = ?, enddate = ? WHERE vacation_id = ?";
    public static final String DELETE_VACATION_BY_ID_QUERY = "DELETE FROM vacations WHERE vacation_id = ?";
    public static final String ADD_TEACHER_VACATION_QUERY = "INSERT INTO vacations(vacation_id, teacher_id, startdate, enddate) VALUES (?, ?, ?, ?)";
    public static final String GET_ALL_VACATIONS_BY_TEACHERID_QUERY = "SELECT vacation_id, startdate, enddate FROM vacations WHERE teacher_id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public VacationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Vacation> findAll() {
        return jdbcTemplate.query(GET_ALL_VACATIONS_QUERY, (resultSet, rows) -> mapToVacation(resultSet));
    }

    @Override
    public void create(Vacation vacation) {
        jdbcTemplate.update(ADD_NEW_VACATION_QUERY, vacation.getStartDate(), vacation.getEndDate());
    }

    @Override
    public Optional<Vacation> getById(int id) {
        return Optional.of(jdbcTemplate.queryForObject(GET_VACATION_BY_ID_QUERY,
                (resultSet, rows) -> mapToVacation(resultSet), id));
    }

    @Override
    public void update(Vacation vacation) {
        jdbcTemplate.update(UPDATE_VACATION_QUERY, vacation.getStartDate(), vacation.getEndDate(), vacation.getId());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_VACATION_BY_ID_QUERY, id);
    }

    public void addTeacherVacation(Vacation vacation, Teacher teacher) {
        jdbcTemplate.update(ADD_TEACHER_VACATION_QUERY, vacation.getId(), teacher.getId(), vacation.getStartDate(),
                vacation.getEndDate());
    }

    public List<Vacation> findVacationsByTeacherId(int id) {
        return jdbcTemplate.query(GET_ALL_VACATIONS_BY_TEACHERID_QUERY, (resultSet, rows) -> mapToVacation(resultSet),
                id);
    }

    private Vacation mapToVacation(ResultSet resultSet) throws SQLException {
        Vacation vacation = new Vacation();
        vacation.setId(resultSet.getInt("vacation_id"));
        vacation.setStartDate(resultSet.getObject(2, LocalDate.class));
        vacation.setEndDate(resultSet.getObject(3, LocalDate.class));
        return vacation;
    }
}