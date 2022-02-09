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

import ua.com.rtim.academy.domain.AcademicDegree;
import ua.com.rtim.academy.domain.Address;
import ua.com.rtim.academy.domain.Gender;
import ua.com.rtim.academy.domain.Teacher;
import ua.com.rtim.academy.spring.config.AppConfig;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = { "classpath:schema.sql",
        "classpath:testdata.sql" })
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
        teacher.setAddress(address);
        teacher.setAcademicDegree(AcademicDegree.valueOf("MASTER"));
        teacherDao.create(teacher);
        assertEquals(4, JdbcTestUtils.countRowsInTable(jdbcTemplate, "Teachers"));
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
        teacherDao.delete(3);
        assertEquals(2, JdbcTestUtils.countRowsInTable(jdbcTemplate, "Teachers"));
    }
}