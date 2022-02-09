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

import ua.com.rtim.academy.domain.Address;
import ua.com.rtim.academy.domain.Gender;
import ua.com.rtim.academy.domain.Group;
import ua.com.rtim.academy.domain.Student;
import ua.com.rtim.academy.spring.config.AppConfig;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = { "classpath:schema.sql",
        "classpath:testdata.sql" })
class StudentDaoTest {

    @Autowired
    private StudentDao studentDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void findAll_shouldBeGetAllEntities_fromTheDataBase() {
        assertEquals(3, studentDao.findAll().size());
    }

    @Test
    void create_shouldBeAddNewEntity_intoTheDataBase() {
        Student student = new Student();
        student.setFirstName("FName");
        student.setLastName("LName");
        student.setGender(Gender.valueOf("MALE"));
        student.setBirthDate(LocalDate.of(1985, 12, 31));
        student.setPhone("+380504442233");
        student.setEmail("name@gamil.com");
        Address address = new Address();
        address.setId(1);
        student.setAddress(address);
        Group group = new Group();
        group.setId(3);
        student.setGroup(group);
        int rows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "Students");
        studentDao.create(student);
        assertEquals(rows + 1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "Students"));
    }

    @Test
    void getById_shouldBeGetEntity_fromTheDataBase() {
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
        assertEquals(expected, studentDao.getById(1));
    }

    @Test
    void update_shouldBeUpdateEntity_inTheDataBase() {
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
    void delete_shouldBeRemoveEntity_fromTheDataBase() {
        int rows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "Students");
        studentDao.delete(3);
        assertEquals(rows - 1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "Students"));
    }
}