package ua.com.rtim.academy.spring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ua.com.rtim.academy.domain.Audience;

@Component
public class AudienceDao implements CrudRepository<Audience> {

    public static final String GET_ALL_AUDIENCES_QUERY = "SELECT * FROM audiences";
    public static final String ADD_NEW_AUDIENCE_QUERY = "INSERT INTO audiences(number, capacity) VALUES (?, ?)";
    public static final String GET_AUDIENCE_BY_ID_QUERY = "SELECT * FROM audiences WHERE audience_id = ?";
    public static final String UPDATE_AUDIENCE_QUERY = "UPDATE audiences SET number = ?, capacity = ? WHERE audience_id = ?";
    public static final String DELETE_AUDIENCE_BY_ID_QUERY = "DELETE FROM audiences WHERE audience_id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AudienceDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Audience> findAll() {
        return jdbcTemplate.query(GET_ALL_AUDIENCES_QUERY, (resultSet, rows) -> mapToAudience(resultSet));
    }

    @Override
    public void create(Audience audience) {
        jdbcTemplate.update(ADD_NEW_AUDIENCE_QUERY, audience.getNumber(), audience.getCapacity());
    }

    @Override
    public Optional<Audience> getById(int id) {
        return Optional.of(jdbcTemplate.queryForObject(GET_AUDIENCE_BY_ID_QUERY,
                (resultSet, rows) -> mapToAudience(resultSet), id));
    }

    @Override
    public void update(Audience audience) {
        jdbcTemplate.update(UPDATE_AUDIENCE_QUERY, audience.getNumber(), audience.getCapacity(), audience.getId());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_AUDIENCE_BY_ID_QUERY, id);
    }

    public Audience mapToAudience(ResultSet resultSet) throws SQLException {
        Audience audience = new Audience();
        audience.setId(resultSet.getInt("audience_id"));
        audience.setNumber(resultSet.getInt("number"));
        audience.setCapacity(resultSet.getInt("capacity"));
        return audience;
    }
}