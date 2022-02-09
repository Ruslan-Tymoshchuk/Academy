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

import ua.com.rtim.academy.domain.Teacher;
import ua.com.rtim.academy.domain.Vacation;
import ua.com.rtim.academy.spring.config.AppConfig;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = { "classpath:schema.sql",
        "classpath:testdata.sql" })
class VacationDaoTest {

    @Autowired
    private VacationDao vacationDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void findAll_shouldBeGetAllEntities_fromTheDataBase() {
        assertEquals(3, vacationDao.findAll().size());
    }

    @Test
    void create_shouldBeAddNewEntity_intoTheDataBase() {
        Vacation vacation = new Vacation();
        vacation.setStartDate(LocalDate.of(2022, 5, 2));
        vacation.setEndDate(LocalDate.of(2022, 5, 22));
        Teacher teacher = new Teacher();
        teacher.setId(3);
        vacation.setTeacher(teacher);
        int rows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "Vacations");
        vacationDao.create(vacation);
        assertEquals(rows + 1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "Vacations"));
    }

    @Test
    void getById_shouldBeGetEntity_fromTheDataBase() {
        Vacation expected = new Vacation();
        expected.setId(3);
        expected.setStartDate(LocalDate.of(2022, 3, 3));
        expected.setEndDate(LocalDate.of(2022, 3, 22));
        assertEquals(expected, vacationDao.getById(3));
    }

    @Test
    void update_shouldBeUpdateEntity_inTheDataBase() {
        Vacation expected = vacationDao.getById(3);
        expected.setStartDate(LocalDate.of(2022, 4, 4));
        expected.setEndDate(LocalDate.of(2022, 4, 24));
        Teacher teacher = new Teacher();
        teacher.setId(3);
        expected.setTeacher(teacher);
        vacationDao.update(expected);
        assertEquals(expected, vacationDao.getById(3));
    }

    @Test
    void delete_shouldBeRemoveEntity_fromTheDataBase() {
        int rows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "Vacations");
        vacationDao.delete(3);
        assertEquals(rows - 1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "Vacations"));
    }
}