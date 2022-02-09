package ua.com.rtim.academy.spring.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ua.com.rtim.academy.domain.Lesson;

@Component
public class LessonDao implements CrudRepository<Lesson> {

    public static final String GET_ALL_LESSONS_QUERY = "SELECT * FROM lessons";
    public static final String ADD_NEW_LESSON_QUERY = "INSERT INTO lessons(teacher_id, course_id, audience_id) VALUES (?, ?, ?)";
    public static final String GET_LESSON_BY_ID_QUERY = "SELECT * FROM lessons WHERE id = ?";
    public static final String UPDATE_LESSON_QUERY = "UPDATE lessons SET teacher_id = ?, course_id = ?, "
            + "audience_id = ?, date = ? WHERE id = ?";
    public static final String DELETE_LESSON_BY_ID_QUERY = "DELETE FROM lessons WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final LessonMapper lessonMapper;

    public LessonDao(JdbcTemplate jdbcTemplate, LessonMapper lessonMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.lessonMapper = lessonMapper;
    }

    @Override
    public List<Lesson> findAll() {
        return jdbcTemplate.query(GET_ALL_LESSONS_QUERY, lessonMapper);
    }

    @Override
    public void create(Lesson lesson) {
        jdbcTemplate.update(ADD_NEW_LESSON_QUERY, lesson.getTeacher().getId(), lesson.getCourse().getId(),
                lesson.getAudience().getId());
    }

    @Override
    public Lesson getById(int id) {
        return jdbcTemplate.queryForObject(GET_LESSON_BY_ID_QUERY, lessonMapper, id);
    }

    @Override
    public void update(Lesson lesson) {
        jdbcTemplate.update(UPDATE_LESSON_QUERY, lesson.getTeacher().getId(), lesson.getCourse().getId(),
                lesson.getAudience().getId(), lesson.getDate(), lesson.getId());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_LESSON_BY_ID_QUERY, id);
    }
}