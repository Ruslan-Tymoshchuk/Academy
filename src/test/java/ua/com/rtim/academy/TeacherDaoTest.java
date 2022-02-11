package ua.com.rtim.academy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.time.LocalDate;
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

import ua.com.rtim.academy.dao.TeacherDao;
import ua.com.rtim.academy.domain.AcademicDegree;
import ua.com.rtim.academy.domain.Address;
import ua.com.rtim.academy.domain.Course;
import ua.com.rtim.academy.domain.Gender;
import ua.com.rtim.academy.domain.Teacher;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class TeacherDaoTest {

    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void findAll_shouldBeGetAllEntities_fromTheDataBase() {
        assertEquals(3, teacherDao.findAll().size());
    }

    @Test
    void create_shouldBeAddNewEntity_intoTheDataBase() {
        Teacher teacher = new Teacher();
        teacher.setFirstName("FName");
        teacher.setLastName("LName");
        teacher.setGender(Gender.valueOf("MALE"));
        teacher.setBirthDate(LocalDate.of(1985, 12, 31));
        teacher.setPhone("+380504442233");
        teacher.setEmail("name@gamil.com");
        Address address = new Address();
        address.setId(1);
        address.setCountry("Ukraine");
        address.setRegion("Kyivska");
        address.setCity("Kyiv");
        address.setStreet("Velika Vasilkivska");
        address.setHouseNumber("114");
        address.setPostalCode("020590");
        teacher.setAddress(address);
        teacher.setAcademicDegree(AcademicDegree.valueOf("MASTER"));
        Course course1 = new Course();
        course1.setId(1);
        Course course2 = new Course();
        course2.setId(2);
        List<Course> courses = Arrays.asList(course1, course2);
        teacher.setCourses(courses);
        int expected = countRowsInTable(jdbcTemplate, "Teachers") + 1;
        teacherDao.create(teacher);
        assertEquals(expected, countRowsInTable(jdbcTemplate, "Teachers"));
    }

    @Test
    void getById_shouldBeGetEntity_fromTheDataBase() {
        Teacher expected = new Teacher();
        expected.setId(1);
        expected.setFirstName("FName1");
        expected.setLastName("LName1");
        expected.setGender(Gender.valueOf("MALE"));
        expected.setBirthDate(LocalDate.of(1980, 1, 1));
        expected.setPhone("12345678");
        expected.setEmail("mail");
        Address address = new Address();
        address.setId(1);
        address.setCountry("Ukraine");
        address.setRegion("Kyivska");
        address.setCity("Kyiv");
        address.setStreet("Velika Vasilkivska");
        address.setHouseNumber("114");
        address.setPostalCode("020590");
        expected.setAddress(address);
        expected.setAcademicDegree(AcademicDegree.valueOf("BACHELOR"));
        assertEquals(expected, teacherDao.getById(1));
    }

    @Test
    void update_shouldBeUpdateEntity_inTheDataBase() {
        Teacher expected = new Teacher();
        expected.setId(1);
        expected.setFirstName("TestName");
        expected.setLastName("TestLastName");
        expected.setGender(Gender.valueOf("MALE"));
        expected.setBirthDate(LocalDate.of(1980, 1, 1));
        expected.setPhone("+380504442277");
        expected.setEmail("test@gamil.com");
        Address address = new Address();
        address.setId(1);
        address.setCountry("Ukraine");
        address.setRegion("Kyivska");
        address.setCity("Kyiv");
        address.setStreet("Velika Vasilkivska");
        address.setHouseNumber("114");
        address.setPostalCode("020590");
        expected.setAddress(address);
        expected.setAcademicDegree(AcademicDegree.valueOf("MASTER"));
        teacherDao.update(expected);
        assertEquals(expected, teacherDao.getById(1));
    }

    @Test
    void delete_shouldBeRemoveEntity_fromTheDataBase() {
        int expected = countRowsInTable(jdbcTemplate, "Teachers") - 1;
        teacherDao.delete(3);
        assertEquals(expected, countRowsInTable(jdbcTemplate, "Teachers"));
    }
}