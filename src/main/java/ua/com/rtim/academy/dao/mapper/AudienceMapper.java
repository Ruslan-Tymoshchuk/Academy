package ua.com.rtim.academy.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.com.rtim.academy.domain.Audience;

@Component
public class AudienceMapper implements RowMapper<Audience> {

    @Override
    public Audience mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Audience audience = new Audience();
        audience.setId(resultSet.getInt("id"));
        audience.setNumber(resultSet.getInt("number"));
        audience.setCapacity(resultSet.getInt("capacity"));
        return audience;
    }
}