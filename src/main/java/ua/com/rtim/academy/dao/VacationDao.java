package ua.com.rtim.academy.dao;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import ua.com.rtim.academy.domain.Vacation;
import ua.com.rtim.academy.mapper.VacationMapper;

@Component
public class VacationDao implements CrudRepository<Vacation> {

    public static final String GET_ALL_VACATIONS_QUERY = "SELECT id, start_date, end_date FROM vacations";
    public static final String ADD_NEW_VACATION_QUERY = "INSERT INTO vacations(teacher_id, start_date, end_date) VALUES (?, ?, ?)";
    public static final String GET_VACATION_BY_ID_QUERY = "SELECT id, start_date, end_date FROM vacations WHERE id = ?";
    public static final String UPDATE_VACATION_QUERY = "UPDATE vacations SET teacher_id = ?, start_date = ?, end_date = ? WHERE id = ?";
    public static final String DELETE_VACATION_BY_ID_QUERY = "DELETE FROM vacations WHERE id = ?";
    public static final String GET_VACATIONS_BY_DATE_INTERVAL_QUERY = "SELECT id, start_date, end_date FROM vacations WHERE start_date >= ? AND end_date <= ?";

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
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_VACATION_QUERY, new String[] { "id" });
            statement.setInt(1, vacation.getTeacher().getId());
            statement.setObject(2, vacation.getStartDate());
            statement.setObject(3, vacation.getEndDate());
            return statement;
        }, keyHolder);
        vacation.setId(keyHolder.getKeyAs(Integer.class));
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

    public List<Vacation> getVacationsByDateInterval(LocalDate startDate, LocalDate endDate) {
        return jdbcTemplate.query(GET_VACATIONS_BY_DATE_INTERVAL_QUERY, vacationMapper, startDate, endDate);
    }
}