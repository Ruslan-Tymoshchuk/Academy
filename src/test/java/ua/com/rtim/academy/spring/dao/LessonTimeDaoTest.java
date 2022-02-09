package ua.com.rtim.academy.spring.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.rtim.academy.domain.LessonTime;
import ua.com.rtim.academy.spring.config.AppConfig;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = { "classpath:schema.sql",
        "classpath:testdata.sql" })
class LessonTimeDaoTest {

    @Autowired
    private LessonTimeDao lessonTimeDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void findAll_shouldBeGetAllEntities_fromTheDataBase() {
        assertEquals(4, lessonTimeDao.findAll().size());
    }

    @Test
    void create_shouldBeAddNewEntity_intoTheDataBase() {
        LessonTime lessonTime = new LessonTime();
        lessonTime.setStartTime(LocalTime.of(8, 20));
        lessonTime.setEndTime(LocalTime.of(9, 40));
        int rows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "lessons_times");
        lessonTimeDao.create(lessonTime);
        assertEquals(rows + 1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "lessons_times"));
    }

    @Test
    void getById_shouldBeGetEntity_fromTheDataBase() {
        LessonTime expected = new LessonTime();
        expected.setId(3);
        expected.setStartTime(LocalTime.of(10, 40));
        expected.setEndTime(LocalTime.of(11, 20));
        assertEquals(expected, lessonTimeDao.getById(3));
    }

    @Test
    void update_shouldBeUpdateEntity_inTheDataBase() {
        LessonTime expected = lessonTimeDao.getById(3);
        expected.setStartTime(LocalTime.of(11, 40));
        expected.setEndTime(LocalTime.of(12, 20));
        lessonTimeDao.update(expected);
        assertEquals(expected, lessonTimeDao.getById(3));
    }

    @Test
    void delete_shouldBeRemoveEntity_fromTheDataBase() {
        int rows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "lessons_times");
        lessonTimeDao.delete(4);
        assertEquals(rows - 1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "lessons_times"));
    }
}