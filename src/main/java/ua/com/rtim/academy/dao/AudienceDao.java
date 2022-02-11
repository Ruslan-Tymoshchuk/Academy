package ua.com.rtim.academy.dao;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import ua.com.rtim.academy.domain.Audience;
import ua.com.rtim.academy.mapper.AudienceMapper;

@Component
public class AudienceDao implements CrudRepository<Audience> {

    public static final String GET_ALL_AUDIENCES_QUERY = "SELECT * FROM audiences";
    public static final String ADD_NEW_AUDIENCE_QUERY = "INSERT INTO audiences(number, capacity) VALUES (?, ?)";
    public static final String GET_AUDIENCE_BY_ID_QUERY = "SELECT * FROM audiences WHERE id = ?";
    public static final String UPDATE_AUDIENCE_QUERY = "UPDATE audiences SET number = ?, capacity = ? WHERE id = ?";
    public static final String DELETE_AUDIENCE_BY_ID_QUERY = "DELETE FROM audiences WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final AudienceMapper audienceMapper;

    public AudienceDao(JdbcTemplate jdbcTemplate, AudienceMapper audienceMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.audienceMapper = audienceMapper;
    }

    @Override
    public List<Audience> findAll() {
        return jdbcTemplate.query(GET_ALL_AUDIENCES_QUERY, audienceMapper);
    }

    @Override
    public void create(Audience audience) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_AUDIENCE_QUERY, new String[] { "id" });
            statement.setInt(1, audience.getNumber());
            statement.setInt(2, audience.getCapacity());
            return statement;
        }, keyHolder);
        audience.setId(keyHolder.getKeyAs(Integer.class));
    }

    @Override
    public Audience getById(int id) {
        return jdbcTemplate.queryForObject(GET_AUDIENCE_BY_ID_QUERY, audienceMapper, id);
    }

    @Override
    public void update(Audience audience) {
        jdbcTemplate.update(UPDATE_AUDIENCE_QUERY, audience.getNumber(), audience.getCapacity(), audience.getId());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_AUDIENCE_BY_ID_QUERY, id);
    }
}