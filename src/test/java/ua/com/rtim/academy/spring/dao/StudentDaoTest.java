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
    private GroupDao groupDao;
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
        studentDao.create(student);
        assertEquals(4, JdbcTestUtils.countRowsInTable(jdbcTemplate, "Students"));
    }

    @Test
    void update_shouldBeUpdateEntity_inTheDataBase() {
        Student student = studentDao.getById(3).get();
        student.setFirstName("TestName");
        student.setLastName("TestLastName");
        student.setPhone("+380504442277");
        student.setEmail("test@gamil.com");
        studentDao.update(student);
        assertEquals(student, studentDao.getById(3).get());
    }

    @Test
    void delete_shouldBeRemoveEntity_fromTheDataBase() {
        studentDao.delete(3);
        assertEquals(2, JdbcTestUtils.countRowsInTable(jdbcTemplate, "Students"));
    }

    @Test
    void shouldBeAdd_studentToGroup_inTheDataBase() {
        studentDao.addToGroup(groupDao.getById(3).get(), studentDao.getById(3).get());
        Student student = studentDao.getById(3).get();
        assertEquals(3, student.getGroup().getId());
    }
}