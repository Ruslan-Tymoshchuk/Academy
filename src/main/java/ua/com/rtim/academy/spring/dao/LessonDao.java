package ua.com.rtim.academy.spring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ua.com.rtim.academy.domain.Group;
import ua.com.rtim.academy.domain.Lesson;

@Component
public class LessonDao implements CrudRepository<Lesson> {

    public static final String GET_ALL_LESSONS_QUERY = "SELECT * FROM lessons";
    public static final String ADD_NEW_LESSON_QUERY = "INSERT INTO lessons(teacher_id, course_id, audience_id) VALUES (?, ?, ?)";
    public static final String GET_LESSON_BY_ID_QUERY = "SELECT * FROM lessons WHERE lesson_id = ?";
    public static final String UPDATE_LESSON_QUERY = "UPDATE lessons SET teacher_id = ?, course_id = ?, "
            + "audience_id = ?, date = ? WHERE lesson_id = ?";
    public static final String DELETE_LESSON_BY_ID_QUERY = "DELETE FROM lessons WHERE lesson_id = ?";
    public static final String ADD_LESSON_TO_GROUP_QUERY = "INSERT INTO lessons_groups(lesson_id, group_id) VALUES (?, ?)";

    private final JdbcTemplate jdbcTemplate;
    private final TeacherDao teacherDao;
    private final CourseDao courseDao;
    private final AudienceDao audienceDao;
    private final LessonTimeDao lessonTimeDao;

    @Autowired
    public LessonDao(JdbcTemplate jdbcTemplate, TeacherDao teacherDao, CourseDao courseDao, AudienceDao audienceDao,
            LessonTimeDao lessonTimeDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.teacherDao = teacherDao;
        this.courseDao = courseDao;
        this.audienceDao = audienceDao;
        this.lessonTimeDao = lessonTimeDao;
    }

    @Override
    public List<Lesson> findAll() {
        return jdbcTemplate.query(GET_ALL_LESSONS_QUERY, (resultSet, rows) -> mapToLesson(resultSet));
    }

    @Override
    public void create(Lesson lesson) {
        jdbcTemplate.update(ADD_NEW_LESSON_QUERY, lesson.getTeacher().getId(), lesson.getCourse().getId(),
                lesson.getAudience().getId());
    }

    @Override
    public Optional<Lesson> getById(int id) {
        return Optional.of(
                jdbcTemplate.queryForObject(GET_LESSON_BY_ID_QUERY, (resultSet, rows) -> mapToLesson(resultSet), id));
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

    public void addLessonToGroup(Lesson lesson, Group group) {
        jdbcTemplate.update(ADD_LESSON_TO_GROUP_QUERY, lesson.getId(), group.getId());
    }

    private Lesson mapToLesson(ResultSet resultSet) throws SQLException {
        Lesson lesson = new Lesson();
        lesson.setId(resultSet.getInt("lesson_id"));
        lesson.setTeacher(teacherDao.getById(resultSet.getInt("teacher_id")).orElseThrow(SQLException::new));
        lesson.setCourse(courseDao.getById(resultSet.getInt("course_id")).orElseThrow(SQLException::new));
        lesson.setAudience(audienceDao.getById(resultSet.getInt("audience_id")).orElseThrow(SQLException::new));
        lesson.setDate(resultSet.getObject(5, LocalDate.class));
        lesson.setTime(lessonTimeDao.getById(resultSet.getInt("lesson_time_id")).orElseThrow(SQLException::new));
        return lesson;
    }
}