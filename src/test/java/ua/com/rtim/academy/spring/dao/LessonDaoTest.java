package ua.com.rtim.academy.spring.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.rtim.academy.domain.Address;
import ua.com.rtim.academy.domain.Audience;
import ua.com.rtim.academy.domain.Course;
import ua.com.rtim.academy.domain.Lesson;
import ua.com.rtim.academy.domain.LessonTime;
import ua.com.rtim.academy.domain.Teacher;
import ua.com.rtim.academy.spring.config.AppConfig;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = { "classpath:schema.sql",
        "classpath:testdata.sql" })
class LessonDaoTest {

    @Autowired
    private LessonDao lessonDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void findAll_shouldBeGetAllEntities_fromTheDataBase() {
        assertEquals(3, lessonDao.findAll().size());
    }

    @Test
    void create_shouldBeAddNewEntity_intoTheDataBase() {
        Lesson lesson = new Lesson();
        Teacher teacher = new Teacher();
        teacher.setId(1);
        Course course = new Course();
        course.setId(1);
        Audience audience = new Audience();
        audience.setId(1);
        lesson.setTeacher(teacher);
        lesson.setCourse(course);
        lesson.setAudience(audience);
        int rows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "Lessons");
        lessonDao.create(lesson);
        assertEquals(rows + 1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "Lessons"));
    }

    @Test
    void getById_shouldBeGetEntity_fromTheDataBase() {
        Lesson expected = new Lesson();
        expected.setId(1);
        Teacher teacher = new Teacher();
        teacher.setId(1);
        expected.setTeacher(teacher);
        Course course = new Course();
        course.setId(1);
        expected.setCourse(course);
        Audience audience = new Audience();
        audience.setId(1);
        expected.setAudience(audience);
        expected.setDate(LocalDate.of(2022, 11, 11));
        LessonTime lessonTime = new LessonTime();
        lessonTime.setId(1);
        lessonTime.setStartTime(LocalTime.of(8, 40));
        lessonTime.setEndTime(LocalTime.of(9, 20));
        expected.setTime(lessonTime);
        assertEquals(expected, lessonDao.getById(1));
    }

    @Test
    void update_shouldBeUpdateEntity_inTheDataBase() {
        Lesson expected = new Lesson();
        expected.setId(3);
        Teacher teacher = new Teacher();
        teacher.setId(1);
        Address address = new Address();
        address.setId(3);
        expected.setTeacher(teacher);
        Course course = new Course();
        course.setId(3);
        expected.setCourse(course);
        Audience audience = new Audience();
        audience.setId(3);
        expected.setAudience(audience);
        expected.setDate(LocalDate.of(2021, 12, 31));
        LessonTime lessonTime = new LessonTime();
        lessonTime.setId(3);
        lessonTime.setStartTime(LocalTime.of(10, 40));
        lessonTime.setEndTime(LocalTime.of(11, 20));
        expected.setTime(lessonTime);
        lessonDao.update(expected);
        assertEquals(expected, lessonDao.getById(3));
    }

    @Test
    void delete_shouldBeRemoveEntity_fromTheDataBase() {
        int rows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "Lessons");
        lessonDao.delete(3);
        assertEquals(rows - 1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "Lessons"));
    }
}