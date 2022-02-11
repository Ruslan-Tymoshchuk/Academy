package ua.com.rtim.academy.dao;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import ua.com.rtim.academy.domain.Holiday;
import ua.com.rtim.academy.mapper.HolidayMapper;

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
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_HOLIDAY_QUERY, new String[] { "id" });
            statement.setString(1, holiday.getName());
            statement.setObject(2, holiday.getDate());
            return statement;
        }, keyHolder);
        holiday.setId(keyHolder.getKeyAs(Integer.class));
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