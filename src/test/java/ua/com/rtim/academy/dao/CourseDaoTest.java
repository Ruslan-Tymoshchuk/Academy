package ua.com.rtim.academy.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ua.com.rtim.academy.TestConfig;
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
    void findAll_shouldGetAllCourses() {
        assertEquals(4, courseDao.findAll().size());
    }

    @Test
    void create_shouldAddNewCourse() {
        Course course = new Course();
        course.setName("English");
        course.setDescription(" ");
        int expected = countRowsInTable(jdbcTemplate, "Courses") + 1;

        courseDao.create(course);

        assertEquals(expected, countRowsInTable(jdbcTemplate, "Courses"));
    }

    @Test
    void getById_shouldGetCourseById() {
        Course expected = new Course();
        expected.setId(3);
        expected.setName("Physics");
        expected.setDescription(" ");

        Course actual = courseDao.getById(3);

        assertEquals(expected, actual);
    }

    @Test
    void update_shouldUpdateCourse() {
        Course expected = new Course();
        expected.setId(3);
        expected.setName("Test");
        expected.setDescription("Test");

        courseDao.update(expected);

        assertEquals(expected, courseDao.getById(3));
    }

    @Test
    void delete_shouldDeleteCourse() {
        int expected = countRowsInTable(jdbcTemplate, "Courses") - 1;

        courseDao.delete(4);

        assertEquals(expected, countRowsInTable(jdbcTemplate, "Courses"));
    }

    @Test
    void shouldGet_allCoursesByTeacher() {
        assertEquals(3, courseDao.findByTeacher(1).size());
    }

    @Test
    void givenNull_whenCreateCourse_thenExeption() {
        assertThrows(NullPointerException.class, () -> courseDao.create(null));
    }

    @Test
    void givenNonExistId_whenGetById_thenExeption() {
        assertThrows(EmptyResultDataAccessException.class, () -> courseDao.getById(0));
    }

    @Test
    void givenNull_whenUpdateCourse_thenExeption() {
        assertThrows(NullPointerException.class, () -> courseDao.update(null));
    }
}