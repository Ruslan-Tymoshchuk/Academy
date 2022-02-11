package ua.com.rtim.academy.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.com.rtim.academy.domain.Holiday;

@Component
public class HolidayMapper implements RowMapper<Holiday> {

    @Override
    public Holiday mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Holiday holiday = new Holiday();
        holiday.setId(resultSet.getInt("id"));
        holiday.setName(resultSet.getString("name"));
        holiday.setDate(resultSet.getObject(3, LocalDate.class));
        return holiday;
    }
}