package ua.com.rtim.academy.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ua.com.rtim.academy.TestConfig;
import ua.com.rtim.academy.domain.Audience;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class AudienceDaoTest {

    @Autowired
    private AudienceDao audienceDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void findAll_shouldGetAllAudiences() {
        assertEquals(4, audienceDao.findAll().size());
    }

    @Test
    void create_shouldAddNewAudience() {
        Audience audience = new Audience();
        audience.setNumber(234);
        audience.setCapacity(25);
        int expected = countRowsInTable(jdbcTemplate, "Audiences") + 1;

        audienceDao.create(audience);

        assertEquals(expected, countRowsInTable(jdbcTemplate, "Audiences"));
    }

    @Test
    void getById_shouldGetAudienceById() {
        Audience expected = new Audience();
        expected.setId(3);
        expected.setNumber(125);
        expected.setCapacity(20);

        Audience actual = audienceDao.getById(3);

        assertEquals(expected, actual);
    }

    @Test
    void update_shouldUpdateAudience() {
        Audience expected = new Audience();
        expected.setId(3);
        expected.setNumber(25);
        expected.setCapacity(30);

        audienceDao.update(expected);

        assertEquals(expected, audienceDao.getById(3));
    }

    @Test
    void delete_shouldDeleteAudience() {
        int expected = countRowsInTable(jdbcTemplate, "Audiences") - 1;

        audienceDao.delete(4);

        assertEquals(expected, countRowsInTable(jdbcTemplate, "Audiences"));
    }

    @Test
    void givenNull_whenCreateAudience_thenExeption() {
        assertThrows(NullPointerException.class, () -> audienceDao.create(null));
    }

    @Test
    void givenNonExistId_whenGetById_thenExeption() {
        assertThrows(EmptyResultDataAccessException.class, () -> audienceDao.getById(0));
    }

    @Test
    void givenNull_whenUpdateAudience_thenExeption() {
        assertThrows(NullPointerException.class, () -> audienceDao.update(null));
    }
}