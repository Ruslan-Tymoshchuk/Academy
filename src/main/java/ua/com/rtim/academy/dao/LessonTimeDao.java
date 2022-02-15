package ua.com.rtim.academy.dao;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import ua.com.rtim.academy.dao.mapper.LessonTimeMapper;
import ua.com.rtim.academy.domain.LessonTime;

@Component
public class LessonTimeDao implements CrudRepository<LessonTime> {

    public static final String GET_ALL_LESSONSTIMES_QUERY = "SELECT * FROM lessons_times";
    public static final String ADD_NEW_LESSONTIME_QUERY = "INSERT INTO lessons_times(start_time, end_time) VALUES (?, ?)";
    public static final String GET_LESSONTIME_BY_ID_QUERY = "SELECT * FROM lessons_times WHERE id = ?";
    public static final String UPDATE_LESSONTIME_QUERY = "UPDATE lessons_times SET start_time = ?, end_time = ? WHERE id = ?";
    public static final String DELETE_LESSONTIME_BY_ID_QUERY = "DELETE FROM lessons_times WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final LessonTimeMapper lessonTimeMapper;

    public LessonTimeDao(JdbcTemplate jdbcTemplate, LessonTimeMapper lessonTimeMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.lessonTimeMapper = lessonTimeMapper;
    }

    @Override
    public List<LessonTime> findAll() {
        return jdbcTemplate.query(GET_ALL_LESSONSTIMES_QUERY, lessonTimeMapper);
    }

    @Override
    public LessonTime create(LessonTime lessonTime) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_LESSONTIME_QUERY, new String[] { "id" });
            statement.setObject(1, lessonTime.getStartTime());
            statement.setObject(2, lessonTime.getEndTime());
            return statement;
        }, keyHolder);
        lessonTime.setId(keyHolder.getKeyAs(Integer.class));
        return lessonTime;
    }

    @Override
    public LessonTime getById(int id) {
        return jdbcTemplate.queryForObject(GET_LESSONTIME_BY_ID_QUERY, lessonTimeMapper,
                id);
    }

    @Override
    public void update(LessonTime lessonTime) {
        jdbcTemplate.update(UPDATE_LESSONTIME_QUERY, lessonTime.getStartTime(), lessonTime.getEndTime(),
                lessonTime.getId());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_LESSONTIME_BY_ID_QUERY, id);
    }
}