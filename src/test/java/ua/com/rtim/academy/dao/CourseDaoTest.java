package ua.com.rtim.academy.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ua.com.rtim.academy.config.TestConfig;
import ua.com.rtim.academy.domain.Course;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class CourseDaoTest {

    @Autowired
    private CourseDao courseDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void finds_all_courses() {
        assertEquals(4, courseDao.findAll().size());
    }

    @Test
    void adds_new_course() {
        Course course = new Course();
        course.setName("English");
        course.setDescription(" ");
        int expected = countRowsInTable(jdbcTemplate, "Courses") + 1;

        courseDao.create(course);

        assertEquals(expected, countRowsInTable(jdbcTemplate, "Courses"));
    }

    @Test
    void gets_course_by_id() {
        Course expected = new Course();
        expected.setId(3);
        expected.setName("Physics");
        expected.setDescription(" ");

        Course actual = courseDao.getById(3);

        assertEquals(expected, actual);
    }

    @Test
    void updates_the_course() {
        Course expected = new Course();
        expected.setId(3);
        expected.setName("Test");
        expected.setDescription("Test");

        courseDao.update(expected);

        assertEquals(expected, courseDao.getById(3));
    }

    @Test
    void deletes_the_course() {
        int expected = countRowsInTable(jdbcTemplate, "Courses") - 1;

        courseDao.delete(4);

        assertEquals(expected, countRowsInTable(jdbcTemplate, "Courses"));
    }

    @Test
    void finds_all_courses_by_teacher() {
        assertEquals(3, courseDao.findByTeacher(1).size());
    }
}