package ua.com.rtim.academy.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.com.rtim.academy.dao.AudienceDao;
import ua.com.rtim.academy.dao.CourseDao;
import ua.com.rtim.academy.dao.GroupDao;
import ua.com.rtim.academy.dao.LessonTimeDao;
import ua.com.rtim.academy.dao.TeacherDao;
import ua.com.rtim.academy.domain.Lesson;

@Component
public class LessonMapper implements RowMapper<Lesson> {

    private final TeacherDao teacherDao;
    private final CourseDao courseDao;
    private final AudienceDao audienceDao;
    private final LessonTimeDao lessonTimeDao;
    private final GroupDao groupDao;

    public LessonMapper(TeacherDao teacherDao, CourseDao courseDao, AudienceDao audienceDao,
            LessonTimeDao lessonTimeDao, GroupDao groupDao) {
        this.teacherDao = teacherDao;
        this.courseDao = courseDao;
        this.audienceDao = audienceDao;
        this.lessonTimeDao = lessonTimeDao;
        this.groupDao = groupDao;
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
        lesson.setGroups(groupDao.findByLesson(lesson));
        return lesson;
    }
}