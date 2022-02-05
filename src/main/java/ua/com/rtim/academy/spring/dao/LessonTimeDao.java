package ua.com.rtim.academy.spring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ua.com.rtim.academy.domain.LessonTime;

@Component
public class LessonTimeDao implements CrudRepository<LessonTime> {

    public static final String GET_ALL_LESSONSTIMES_QUERY = "SELECT * FROM lessons_times";
    public static final String ADD_NEW_LESSONTIME_QUERY = "INSERT INTO lessons_times(starttime, endtime) VALUES (?, ?)";
    public static final String GET_LESSONTIME_BY_ID_QUERY = "SELECT * FROM lessons_times WHERE lesson_time_id = ?";
    public static final String UPDATE_LESSONTIME_QUERY = "UPDATE lessons_times SET starttime = ?, endtime = ? WHERE lesson_time_id = ?";
    public static final String DELETE_LESSONTIME_BY_ID_QUERY = "DELETE FROM lessons_times WHERE lesson_time_id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LessonTimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<LessonTime> findAll() {
        return jdbcTemplate.query(GET_ALL_LESSONSTIMES_QUERY, (resultSet, rows) -> mapToLessonsTime(resultSet));
    }

    @Override
    public void create(LessonTime lessonTime) {
        jdbcTemplate.update(ADD_NEW_LESSONTIME_QUERY, lessonTime.getStartTime(), lessonTime.getEndTime());
    }

    @Override
    public Optional<LessonTime> getById(int id) {
        return Optional.of(jdbcTemplate.queryForObject(GET_LESSONTIME_BY_ID_QUERY,
                (resultSet, rows) -> mapToLessonsTime(resultSet), id));
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

    public LessonTime mapToLessonsTime(ResultSet resultSet) throws SQLException {
        LessonTime lessonTime = new LessonTime();
        lessonTime.setId(resultSet.getInt("lesson_time_id"));
        lessonTime.setStartTime(resultSet.getObject(2, LocalTime.class));
        lessonTime.setEndTime(resultSet.getObject(3, LocalTime.class));
        return lessonTime;
    }
}