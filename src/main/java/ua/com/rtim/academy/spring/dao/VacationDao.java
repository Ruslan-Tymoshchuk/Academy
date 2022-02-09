package ua.com.rtim.academy.spring.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ua.com.rtim.academy.domain.Vacation;

@Component
public class VacationDao implements CrudRepository<Vacation> {

    public static final String GET_ALL_VACATIONS_QUERY = "SELECT id, start_date, end_date FROM vacations";
    public static final String ADD_NEW_VACATION_QUERY = "INSERT INTO vacations(teacher_id, start_date, end_date) VALUES (?, ?, ?)";
    public static final String GET_VACATION_BY_ID_QUERY = "SELECT id, start_date, end_date FROM vacations WHERE id = ?";
    public static final String UPDATE_VACATION_QUERY = "UPDATE vacations SET teacher_id = ?, start_date = ?, end_date = ? WHERE id = ?";
    public static final String DELETE_VACATION_BY_ID_QUERY = "DELETE FROM vacations WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final VacationMapper vacationMapper;

    public VacationDao(JdbcTemplate jdbcTemplate, VacationMapper vacationMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.vacationMapper = vacationMapper;
    }

    @Override
    public List<Vacation> findAll() {
        return jdbcTemplate.query(GET_ALL_VACATIONS_QUERY, vacationMapper);
    }

    @Override
    public void create(Vacation vacation) {
        jdbcTemplate.update(ADD_NEW_VACATION_QUERY, vacation.getTeacher().getId(), vacation.getStartDate(),
                vacation.getEndDate());
    }

    @Override
    public Vacation getById(int id) {
        return jdbcTemplate.queryForObject(GET_VACATION_BY_ID_QUERY, vacationMapper, id);
    }

    @Override
    public void update(Vacation vacation) {
        jdbcTemplate.update(UPDATE_VACATION_QUERY, vacation.getTeacher().getId(), vacation.getStartDate(),
                vacation.getEndDate(), vacation.getId());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_VACATION_BY_ID_QUERY, id);
    }
}