package ua.com.rtim.academy.spring.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.rtim.academy.domain.Audience;
import ua.com.rtim.academy.spring.config.AppConfig;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = { "classpath:schema.sql",
        "classpath:testdata.sql" })
class AudienceDaoTest {

    @Autowired
    private AudienceDao audienceDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void findAll_shouldBeGetAllEntities_fromTheDataBase() {
        assertEquals(4, audienceDao.findAll().size());
    }

    @Test
    void create_shouldBeAddNewEntity_intoTheDataBase() {
        Audience audience = new Audience();
        audience.setNumber(234);
        audience.setCapacity(25);
        audienceDao.create(audience);
        assertEquals(5, JdbcTestUtils.countRowsInTable(jdbcTemplate, "Audiences"));
    }

    @Test
    void getById_shouldBeGetEntity_fromTheDataBase() {
        Audience expected = new Audience();
        expected.setId(3);
        expected.setNumber(125);
        expected.setCapacity(20);
        assertEquals(expected, audienceDao.getById(3).get());
    }

    @Test
    void update_shouldBeUpdateEntity_inTheDataBase() {
        Audience expected = audienceDao.getById(3).get();
        expected.setNumber(25);
        expected.setCapacity(30);
        audienceDao.update(expected);
        assertEquals(expected, audienceDao.getById(3).get());
    }

    @Test
    void delete_shouldBeRemoveEntity_fromTheDataBase() {
        audienceDao.delete(4);
        assertEquals(3, JdbcTestUtils.countRowsInTable(jdbcTemplate, "Audiences"));
    }
}