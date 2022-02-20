package ua.com.rtim.academy.dao;

import static java.time.LocalDate.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ua.com.rtim.academy.config.TestConfig;
import ua.com.rtim.academy.domain.Teacher;
import ua.com.rtim.academy.domain.Vacation;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class VacationDaoTest {

    @Autowired
    private VacationDao vacationDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void findAll_shouldGetAllVacations() {
        assertEquals(4, vacationDao.findAll().size());
    }

    @Test
    void create_shouldAddNewVacation() {
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
    void getById_shouldGetVacationById() {
        Vacation expected = new Vacation();
        expected.setId(3);
        expected.setStartDate(of(2022, 3, 3));
        expected.setEndDate(of(2022, 3, 22));

        Vacation actual = vacationDao.getById(3);

        assertEquals(expected, actual);
    }

    @Test
    void update_shouldUpdateVacation() {
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
    void delete_shouldDeleteVacation() {
        int expected = countRowsInTable(jdbcTemplate, "Vacations") - 1;

        vacationDao.delete(3);

        assertEquals(expected, countRowsInTable(jdbcTemplate, "Vacations"));
    }

    @Test
    void shouldGet_allVacationsByDate() {
        LocalDate startDate = of(2022, 3, 3);
        LocalDate endDate = of(2022, 3, 22);

        int actual = vacationDao.findByDateInterval(startDate, endDate).size();

        assertEquals(3, actual);
    }

    @Test
    void givenNull_whenCreateVacation_thenExeption() {
        assertThrows(NullPointerException.class, () -> vacationDao.create(null));
    }

    @Test
    void givenNonExistId_whenGetById_thenExeption() {
        assertThrows(EmptyResultDataAccessException.class, () -> vacationDao.getById(0));
    }

    @Test
    void givenNull_whenUpdateVacation_thenExeption() {
        assertThrows(NullPointerException.class, () -> vacationDao.update(null));
    }

    @Test
    void givenNonExistId_whenDelete_thenExeption() {
        assertThrows(EmptyResultDataAccessException.class, () -> vacationDao.getById(0));
    }
}