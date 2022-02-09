package ua.com.rtim.academy.spring.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ua.com.rtim.academy.domain.Holiday;

@Component
public class HolidayDao implements CrudRepository<Holiday> {

    public static final String GET_ALL_HOLIDAYS_QUERY = "SELECT * FROM holidays";
    public static final String ADD_NEW_HOLIDAY_QUERY = "INSERT INTO holidays(name, date) VALUES (?, ?)";
    public static final String GET_HOLIDAY_BY_ID_QUERY = "SELECT * FROM holidays WHERE id = ?";
    public static final String UPDATE_HOLIDAY_QUERY = "UPDATE holidays SET name = ?, date = ? WHERE id = ?";
    public static final String DELETE_HOLIDAY_BY_ID_QUERY = "DELETE FROM holidays WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final HolidayMapper holidayMapper;

    public HolidayDao(JdbcTemplate jdbcTemplate, HolidayMapper holidayMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.holidayMapper = holidayMapper;
    }

    @Override
    public List<Holiday> findAll() {
        return jdbcTemplate.query(GET_ALL_HOLIDAYS_QUERY, holidayMapper);
    }

    @Override
    public void create(Holiday holiday) {
        jdbcTemplate.update(ADD_NEW_HOLIDAY_QUERY, holiday.getName(), holiday.getDate());
    }

    @Override
    public Holiday getById(int id) {
        return jdbcTemplate.queryForObject(GET_HOLIDAY_BY_ID_QUERY, holidayMapper, id);
    }

    @Override
    public void update(Holiday holiday) {
        jdbcTemplate.update(UPDATE_HOLIDAY_QUERY, holiday.getName(), holiday.getDate(), holiday.getId());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_HOLIDAY_BY_ID_QUERY, id);
    }
}