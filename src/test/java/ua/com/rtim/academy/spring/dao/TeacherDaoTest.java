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
    void update_shouldBeUpdateEntity_inTheDataBase() {
        Teacher teacher = teacherDao.getById(3).get();
        teacher.setFirstName("TestName");
        teacher.setLastName("TestLastName");
        teacher.setPhone("+380504442277");
        teacher.setEmail("test@gamil.com");
        teacher.setAcademicDegree(AcademicDegree.valueOf("MASTER"));
        teacherDao.update(teacher);
        assertEquals(teacher, teacherDao.getById(3).get());
    }

    @Test
    void delete_shouldBeRemoveEntity_fromTheDataBase() {
        teacherDao.delete(3);
        assertEquals(2, JdbcTestUtils.countRowsInTable(jdbcTemplate, "Teachers"));
    }
}