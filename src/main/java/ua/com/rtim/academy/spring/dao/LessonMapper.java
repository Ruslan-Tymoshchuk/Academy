package ua.com.rtim.academy.spring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.com.rtim.academy.domain.Lesson;

@Component
public class LessonMapper implements RowMapper<Lesson> {

    private final TeacherDao teacherDao;
    private final CourseDao courseDao;
    private final AudienceDao audienceDao;
    private final LessonTimeDao lessonTimeDao;

    public LessonMapper(TeacherDao teacherDao, CourseDao courseDao, AudienceDao audienceDao,
            LessonTimeDao lessonTimeDao) {
        this.teacherDao = teacherDao;
        this.courseDao = courseDao;
        this.audienceDao = audienceDao;
        this.lessonTimeDao = lessonTimeDao;
    }

    @Override
    public Lesson mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Lesson lesson = new Lesson();
        lesson.setId(resultSet.getInt("id"));
        lesson.setTeacher(teacherDao.getById(resultSet.getInt("teacher_id")));
        lesson.setCourse(courseDao.getById(resultSet.getInt("course_id")));
        lesson.setAudience(audienceDao.getById(resultSet.getInt("audience_id")));
        lesson.setDate(resultSet.getObject(5, LocalDate.class));
        lesson.setTime(lessonTimeDao.getById(resultSet.getInt("lesson_time_id")));
        return lesson;
    }
}