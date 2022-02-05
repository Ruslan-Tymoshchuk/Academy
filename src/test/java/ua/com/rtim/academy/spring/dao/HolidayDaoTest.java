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

import ua.com.rtim.academy.domain.Holiday;
import ua.com.rtim.academy.spring.config.AppConfig;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = { "classpath:schema.sql",
        "classpath:testdata.sql" })
class HolidayDaoTest {

    @Autowired
    private HolidayDao holidayDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void findAll_shouldBeGetAllEntities_fromTheDataBase() {
        assertEquals(3, holidayDao.findAll().size());
    }

    @Test
    void create_shouldBeAddNewEntity_intoTheDataBase() {
        Holiday holiday = new Holiday();
        holiday.setName("New Year");
        holiday.setDate(LocalDate.of(2021, 12, 31));
        holidayDao.create(holiday);
        assertEquals(4, JdbcTestUtils.countRowsInTable(jdbcTemplate, "Holidays"));
    }

    @Test
    void getById_shouldBeGetEntity_fromTheDataBase() {
        Holiday expected = new Holiday();
        expected.setId(3);
        expected.setName("Holiday3");
        expected.setDate(LocalDate.of(2022, 3, 3));
        assertEquals(expected, holidayDao.getById(3).get());
    }

    @Test
    void update_shouldBeUpdateEntity_inTheDataBase() {
        Holiday expected = holidayDao.getById(3).get();
        expected.setName("Test");
        expected.setDate(LocalDate.of(2022, 4, 4));
        holidayDao.update(expected);
        assertEquals(expected, holidayDao.getById(3).get());
    }

    @Test
    void delete_shouldBeRemoveEntity_fromTheDataBase() {
        holidayDao.delete(3);
        assertEquals(2, JdbcTestUtils.countRowsInTable(jdbcTemplate, "Holidays"));
    }
}