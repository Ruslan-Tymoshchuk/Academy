package ua.com.rtim.academy.spring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ua.com.rtim.academy.domain.Holiday;

@Component
public class HolidayDao implements CrudRepository<Holiday> {

    public static final String GET_ALL_HOLIDAYS_QUERY = "SELECT * FROM holidays";
    public static final String ADD_NEW_HOLIDAY_QUERY = "INSERT INTO holidays(name, date) VALUES (?, ?)";
    public static final String GET_HOLIDAY_BY_ID_QUERY = "SELECT * FROM holidays WHERE holiday_id = ?";
    public static final String UPDATE_HOLIDAY_QUERY = "UPDATE holidays SET name = ?, date = ? WHERE holiday_id = ?";
    public static final String DELETE_HOLIDAY_BY_ID_QUERY = "DELETE FROM holidays WHERE holiday_id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HolidayDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Holiday> findAll() {
        return jdbcTemplate.query(GET_ALL_HOLIDAYS_QUERY, (resultSet, rows) -> mapToHoliday(resultSet));
    }

    @Override
    public void create(Holiday holiday) {
        jdbcTemplate.update(ADD_NEW_HOLIDAY_QUERY, holiday.getName(), holiday.getDate());
    }

    @Override
    public Optional<Holiday> getById(int id) {
        return Optional.of(
                jdbcTemplate.queryForObject(GET_HOLIDAY_BY_ID_QUERY, (resultSet, rows) -> mapToHoliday(resultSet), id));
    }

    @Override
    public void update(Holiday holiday) {
        jdbcTemplate.update(UPDATE_HOLIDAY_QUERY, holiday.getName(), holiday.getDate(), holiday.getId());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_HOLIDAY_BY_ID_QUERY, id);
    }

    private Holiday mapToHoliday(ResultSet resultSet) throws SQLException {
        Holiday holiday = new Holiday();
        holiday.setId(resultSet.getInt("holiday_id"));
        holiday.setName(resultSet.getString("name"));
        holiday.setDate(resultSet.getObject(3, LocalDate.class));
        return holiday;
    }
}