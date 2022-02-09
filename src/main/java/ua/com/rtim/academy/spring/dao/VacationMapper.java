package ua.com.rtim.academy.spring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.com.rtim.academy.domain.Vacation;

@Component
public class VacationMapper implements RowMapper<Vacation> {

    @Override
    public Vacation mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Vacation vacation = new Vacation();
        vacation.setId(resultSet.getInt("id"));
        vacation.setStartDate(resultSet.getObject(2, LocalDate.class));
        vacation.setEndDate(resultSet.getObject(3, LocalDate.class));
        return vacation;
    }
}