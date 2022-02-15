package ua.com.rtim.academy.dao;

import static java.time.LocalTime.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ua.com.rtim.academy.domain.Address;
import ua.com.rtim.academy.domain.Audience;
import ua.com.rtim.academy.domain.Course;
import ua.com.rtim.academy.domain.Group;
import ua.com.rtim.academy.domain.Lesson;
import ua.com.rtim.academy.domain.LessonTime;
import ua.com.rtim.academy.domain.Teacher;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
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
        lesson.setDate(LocalDate.of(2022, 11, 11));
        LessonTime lessonTime = new LessonTime();
        lessonTime.setId(1);
        lesson.setTime(lessonTime);
        Group group1 = new Group();
        group1.setId(1);
        Group group2 = new Group();
        group2.setId(2);
        List<Group> groups = Arrays.asList(group1, group2);
        lesson.setGroups(groups);
        int expected = countRowsInTable(jdbcTemplate, "Lessons") + 1;
        lessonDao.create(lesson);
        assertEquals(expected, countRowsInTable(jdbcTemplate, "Lessons"));
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
        lessonTime.setStartTime(of(8, 40));
        lessonTime.setEndTime(of(9, 20));
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
        lessonTime.setStartTime(of(10, 40));
        lessonTime.setEndTime(of(11, 20));
        expected.setTime(lessonTime);
        Group group1 = new Group();
        group1.setId(1);
        Group group2 = new Group();
        group2.setId(2);
        List<Group> groups = Arrays.asList(group1, group2);
        expected.setGroups(groups);
        lessonDao.update(expected);
        assertEquals(expected, lessonDao.getById(3));
    }

    @Test
    void delete_shouldBeRemoveEntity_fromTheDataBase() {
        int rows = countRowsInTable(jdbcTemplate, "Lessons");
        lessonDao.delete(3);
        assertEquals(rows - 1, countRowsInTable(jdbcTemplate, "Lessons"));
    }

    @Test
    void shouldBeGet_AllGroupLessonsByTime_fromTheDataBase() {
        LocalTime startTime = of(8, 40);
        LocalTime endTime = of(10, 20);
        assertEquals(2, lessonDao.getGroupLessons(1, startTime, endTime).size());
    }
}