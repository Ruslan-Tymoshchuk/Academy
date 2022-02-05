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

import ua.com.rtim.academy.domain.Group;
import ua.com.rtim.academy.spring.config.AppConfig;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = { "classpath:schema.sql",
        "classpath:testdata.sql" })
class GroupDaoTest {

    @Autowired
    private GroupDao groupDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void findAll_shouldBeGetAllEntities_fromTheDataBase() {
        assertEquals(4, groupDao.findAll().size());
    }

    @Test
    void create_shouldBeAddNewEntity_intoTheDataBase() {
        Group group = new Group();
        group.setName("HM-12");
        groupDao.create(group);
        assertEquals(5, JdbcTestUtils.countRowsInTable(jdbcTemplate, "Groups"));
    }

    @Test
    void getById_shouldBeGetEntity_fromTheDataBase() {
        Group expected = new Group();
        expected.setId(3);
        expected.setName("MM-15");
        assertEquals(expected, groupDao.getById(3).get());
    }

    @Test
    void update_shouldBeUpdateEntity_inTheDataBase() {
        Group expected = groupDao.getById(3).get();
        expected.setName("Test");
        groupDao.update(expected);
        assertEquals(expected, groupDao.getById(3).get());
    }

    @Test
    void delete_shouldBeRemoveEntity_fromTheDataBase() {
        groupDao.delete(4);
        assertEquals(3, JdbcTestUtils.countRowsInTable(jdbcTemplate, "Groups"));
    }
}