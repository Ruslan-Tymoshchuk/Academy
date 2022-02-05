package ua.com.rtim.academy.spring.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.rtim.academy.domain.Audience;
import ua.com.rtim.academy.domain.Course;
import ua.com.rtim.academy.domain.Group;
import ua.com.rtim.academy.domain.Lesson;
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
    private GroupDao groupDao;
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
        lessonDao.create(lesson);
        assertEquals(4, JdbcTestUtils.countRowsInTable(jdbcTemplate, "Lessons"));
    }

    @Test
    void update_shouldBeUpdateEntity_inTheDataBase() {
        Lesson expected = lessonDao.getById(3).get();
        expected.setDate(LocalDate.of(2021, 12, 31));
        lessonDao.update(expected);
        assertEquals(expected, lessonDao.getById(3).get());
    }

    @Test
    void shouldBeAdd_lessonToGroup_inTheDataBase() throws Exception {
        Lesson lesson = lessonDao.getById(3).get();
        Group group = groupDao.getById(3).get();
        lessonDao.addLessonToGroup(lesson, group);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "lessons_groups"));
    }

    @Test
    void delete_shouldBeRemoveEntity_fromTheDataBase() {
        lessonDao.delete(3);
        assertEquals(2, JdbcTestUtils.countRowsInTable(jdbcTemplate, "Lessons"));
    }
}