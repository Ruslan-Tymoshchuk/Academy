package ua.com.rtim.academy.dao;

import static java.time.LocalDate.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ua.com.rtim.academy.config.TestConfig;
import ua.com.rtim.academy.domain.Holiday;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class HolidayDaoTest {

    @Autowired
    private HolidayDao holidayDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void finds_all_holidays() {
        assertEquals(3, holidayDao.findAll().size());
    }

    @Test
    void adds_new_holiday() {
        Holiday holiday = new Holiday();
        holiday.setName("New Year");
        holiday.setDate(of(2021, 12, 31));
        int expected = countRowsInTable(jdbcTemplate, "Holidays") + 1;

        holidayDao.create(holiday);

        assertEquals(expected, countRowsInTable(jdbcTemplate, "Holidays"));
    }

    @Test
    void gets_holiday_by_id() {
        Holiday expected = new Holiday();
        expected.setId(3);
        expected.setName("Holiday3");
        expected.setDate(of(2022, 3, 3));

        Holiday actual = holidayDao.getById(3);

        assertEquals(expected, actual);
    }

    @Test
    void updates_the_holiday() {
        Holiday expected = new Holiday();
        expected.setId(3);
        expected.setName("Test");
        expected.setDate(of(2022, 4, 4));

        holidayDao.update(expected);

        assertEquals(expected, holidayDao.getById(3));
    }

    @Test
    void deletes_the_holiday() {
        int expected = countRowsInTable(jdbcTemplate, "Holidays") - 1;

        holidayDao.delete(3);

        assertEquals(expected, countRowsInTable(jdbcTemplate, "Holidays"));
    }
}