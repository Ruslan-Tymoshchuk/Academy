package ua.com.rtim.academy.dao;

import static java.time.LocalDate.of;
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

import ua.com.rtim.academy.domain.Teacher;
import ua.com.rtim.academy.domain.Vacation;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class VacationDaoTest {

    @Autowired
    private VacationDao vacationDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void findAll_shouldBeGetAllEntities_fromTheDataBase() {
        assertEquals(4, vacationDao.findAll().size());
    }

    @Test
    void create_shouldBeAddNewEntity_intoTheDataBase() {
        Vacation vacation = new Vacation();
        vacation.setStartDate(of(2022, 5, 2));
        vacation.setEndDate(of(2022, 5, 22));
        Teacher teacher = new Teacher();
        teacher.setId(3);
        vacation.setTeacher(teacher);
        int expected = countRowsInTable(jdbcTemplate, "Vacations") + 1;
        vacationDao.create(vacation);
        assertEquals(expected, countRowsInTable(jdbcTemplate, "Vacations"));
    }

    @Test
    void getById_shouldBeGetEntity_fromTheDataBase() {
        Vacation expected = new Vacation();
        expected.setId(3);
        expected.setStartDate(of(2022, 3, 3));
        expected.setEndDate(of(2022, 3, 22));
        assertEquals(expected, vacationDao.getById(3));
    }

    @Test
    void update_shouldBeUpdateEntity_inTheDataBase() {
        Vacation expected = vacationDao.getById(3);
        expected.setStartDate(of(2022, 4, 4));
        expected.setEndDate(of(2022, 4, 24));
        Teacher teacher = new Teacher();
        teacher.setId(3);
        expected.setTeacher(teacher);
        vacationDao.update(expected);
        assertEquals(expected, vacationDao.getById(3));
    }

    @Test
    void delete_shouldBeRemoveEntity_fromTheDataBase() {
        int expected = countRowsInTable(jdbcTemplate, "Vacations") - 1;
        vacationDao.delete(3);
        assertEquals(expected, countRowsInTable(jdbcTemplate, "Vacations"));
    }

    @Test
    void shouldBeGet_AllVacationsByDate_fromTheDataBase() {
        LocalDate startDate = of(2022, 3, 3);
        LocalDate endDate = of(2022, 3, 22);
        assertEquals(3, vacationDao.getVacationsByDateInterval(startDate, endDate).size());
    }
}