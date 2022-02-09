package ua.com.rtim.academy.spring.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.rtim.academy.domain.Course;
import ua.com.rtim.academy.spring.config.AppConfig;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = { "classpath:schema.sql",
        "classpath:testdata.sql" })
class CourseDaoTest {

    @Autowired
    private CourseDao courseDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void findAll_shouldBeGetAllEntities_fromTheDataBase() {
        assertEquals(4, courseDao.findAll().size());
    }

    @Test
    void create_shouldBeAddNewEntity_intoTheDataBase() {
        Course course = new Course();
        course.setName("English");
        course.setDescription(" ");
        int rows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "Courses");
        courseDao.create(course);
        assertEquals(rows + 1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "Courses"));
    }

    @Test
    void getById_shouldBeGetEntity_fromTheDataBase() {
        Course expected = new Course();
        expected.setId(3);
        expected.setName("Physics");
        expected.setDescription(" ");
        assertEquals(expected, courseDao.getById(3));
    }

    @Test
    void update_shouldBeUpdateEntity_inTheDataBase() {
        Course expected = courseDao.getById(3);
        expected.setName("Test");
        expected.setDescription("Test");
        courseDao.update(expected);
        assertEquals(expected, courseDao.getById(3));
    }

    @Test
    void delete_shouldBeRemoveEntity_fromTheDataBase() {
        int rows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "Courses");
        courseDao.delete(4);
        assertEquals(rows - 1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "Courses"));
    }
}