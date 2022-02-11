package ua.com.rtim.academy;

import static java.time.LocalTime.of;
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

import ua.com.rtim.academy.dao.LessonTimeDao;
import ua.com.rtim.academy.domain.LessonTime;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
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
        lessonTime.setStartTime(of(8, 20));
        lessonTime.setEndTime(of(9, 40));
        int expected = countRowsInTable(jdbcTemplate, "lessons_times") + 1;
        lessonTimeDao.create(lessonTime);
        assertEquals(expected, countRowsInTable(jdbcTemplate, "lessons_times"));
    }

    @Test
    void getById_shouldBeGetEntity_fromTheDataBase() {
        LessonTime expected = new LessonTime();
        expected.setId(3);
        expected.setStartTime(of(10, 40));
        expected.setEndTime(of(11, 20));
        assertEquals(expected, lessonTimeDao.getById(3));
    }

    @Test
    void update_shouldBeUpdateEntity_inTheDataBase() {
        LessonTime expected = lessonTimeDao.getById(3);
        expected.setStartTime(of(11, 40));
        expected.setEndTime(of(12, 20));
        lessonTimeDao.update(expected);
        assertEquals(expected, lessonTimeDao.getById(3));
    }

    @Test
    void delete_shouldBeRemoveEntity_fromTheDataBase() {
        int expected = countRowsInTable(jdbcTemplate, "lessons_times") - 1;
        lessonTimeDao.delete(4);
        assertEquals(expected, countRowsInTable(jdbcTemplate, "lessons_times"));
    }
}