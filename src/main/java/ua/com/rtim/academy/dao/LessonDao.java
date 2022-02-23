package ua.com.rtim.academy.dao;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ua.com.rtim.academy.dao.mapper.LessonMapper;
import ua.com.rtim.academy.domain.Lesson;

@Component
public class LessonDao implements CrudRepository<Lesson> {

    public static final String GET_ALL_LESSONS_QUERY = "SELECT * FROM lessons";
    public static final String ADD_NEW_LESSON_QUERY = "INSERT INTO lessons(teacher_id, course_id, audience_id, date, lesson_time_id) VALUES (?, ?, ?, ?, ?)";
    public static final String ADD_GROUP_BY_LESSON_QUERY = "INSERT INTO lessons_groups (lesson_id, group_id) VALUES (?, ?)";
    public static final String GET_LESSON_BY_ID_QUERY = "SELECT * FROM lessons WHERE id = ?";
    public static final String UPDATE_LESSON_QUERY = "UPDATE lessons SET teacher_id = ?, course_id = ?, "
            + "audience_id = ?, date = ? WHERE id = ?";
    public static final String DELETE_IRRELEVANT_GROUPS_BY_LESSON_QUERY = "DELETE FROM lessons_groups WHERE lesson_id = ? AND group_id != ?";
    public static final String DELETE_LESSON_BY_ID_QUERY = "DELETE FROM lessons WHERE id = ?";
    public static final String GET_GROUP_LESSONS_QUERY = "SELECT l.* FROM lessons l LEFT JOIN lessons_groups lg ON lg.lesson_id = l.id "
            + "WHERE group_id = ? AND date between ? and ?";

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
    @Transactional
    public Lesson create(Lesson lesson) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_LESSON_QUERY, new String[] { "id" });
            statement.setInt(1, lesson.getTeacher().getId());
            statement.setInt(2, lesson.getCourse().getId());
            statement.setInt(3, lesson.getAudience().getId());
            statement.setObject(4, lesson.getDate());
            statement.setInt(5, lesson.getTime().getId());
            return statement;
        }, keyHolder);
        lesson.setId(keyHolder.getKeyAs(Integer.class));
        lesson.getGroups()
                .forEach(group -> jdbcTemplate.update(ADD_GROUP_BY_LESSON_QUERY, lesson.getId(), group.getId()));
        return lesson;
    }

    @Override
    public Lesson getById(int id) {
        return jdbcTemplate.queryForObject(GET_LESSON_BY_ID_QUERY, lessonMapper, id);
    }

    @Override
    @Transactional
    public void update(Lesson lesson) {
        jdbcTemplate.update(UPDATE_LESSON_QUERY, lesson.getTeacher().getId(), lesson.getCourse().getId(),
                lesson.getAudience().getId(), lesson.getDate(), lesson.getId());
        lesson.getGroups().forEach(
                group -> jdbcTemplate.update(DELETE_IRRELEVANT_GROUPS_BY_LESSON_QUERY, lesson.getId(), group.getId()));
        lesson.getGroups()
                .forEach(group -> jdbcTemplate.update(ADD_GROUP_BY_LESSON_QUERY, lesson.getId(), group.getId()));
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_LESSON_BY_ID_QUERY, id);
    }

    public List<Lesson> findByGroupIdAndDateInterval(int groupId, LocalDate startDate, LocalDate endDate) {
        return jdbcTemplate.query(GET_GROUP_LESSONS_QUERY, lessonMapper, groupId, startDate, endDate);
    }
}