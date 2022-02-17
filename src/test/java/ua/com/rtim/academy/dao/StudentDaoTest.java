package ua.com.rtim.academy.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ua.com.rtim.academy.config.TestConfig;
import ua.com.rtim.academy.domain.Address;
import ua.com.rtim.academy.domain.Gender;
import ua.com.rtim.academy.domain.Group;
import ua.com.rtim.academy.domain.Student;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class StudentDaoTest {

    @Autowired
    private StudentDao studentDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void finds_all_students() {
        assertEquals(3, studentDao.findAll().size());
    }

    @Test
    void adds_new_student() {
        Student student = new Student();
        student.setFirstName("FName");
        student.setLastName("LName");
        student.setGender(Gender.valueOf("MALE"));
        student.setBirthDate(LocalDate.of(1985, 12, 31));
        student.setPhone("+380504442233");
        student.setEmail("name@gamil.com");
        Address address = new Address();
        address.setId(1);
        address.setCountry("Ukraine");
        address.setRegion("Kyivska");
        address.setCity("Kyiv");
        address.setStreet("Velika Vasilkivska");
        address.setHouseNumber("114");
        address.setPostalCode("020590");
        student.setAddress(address);
        Group group = new Group();
        group.setId(3);
        student.setGroup(group);
        int expected = countRowsInTable(jdbcTemplate, "Students") + 1;

        studentDao.create(student);

        assertEquals(expected, countRowsInTable(jdbcTemplate, "Students"));
    }

    @Test
    void gets_student_by_id() {
        Student expected = new Student();
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
        Group group = new Group();
        group.setId(1);
        group.setName("MM-13");
        expected.setGroup(group);

        Student actual = studentDao.getById(1);

        assertEquals(expected, actual);
    }

    @Test
    void updates_the_student() {
        Student expected = new Student();
        expected.setId(3);
        expected.setFirstName("FName");
        expected.setLastName("LName");
        expected.setGender(Gender.valueOf("MALE"));
        expected.setBirthDate(LocalDate.of(1990, 07, 07));
        expected.setPhone("+380504442233");
        expected.setEmail("name@gamil.com");
        Address address = new Address();
        address.setId(3);
        address.setCountry("Ukraine");
        address.setRegion("Kyivska");
        address.setCity("Kyiv");
        address.setStreet("Velika Vasilkivska");
        address.setHouseNumber("116");
        address.setPostalCode("020590");
        expected.setAddress(address);
        Group group = new Group();
        group.setId(3);
        group.setName("MM-15");
        expected.setGroup(group);

        studentDao.update(expected);

        assertEquals(expected, studentDao.getById(3));
    }

    @Test
    void deletes_the_student() {
        int expected = countRowsInTable(jdbcTemplate, "Students") - 1;

        studentDao.delete(3);

        assertEquals(expected, countRowsInTable(jdbcTemplate, "Students"));
    }
}