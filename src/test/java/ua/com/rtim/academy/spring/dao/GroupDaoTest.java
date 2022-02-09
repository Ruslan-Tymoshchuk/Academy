package ua.com.rtim.academy.spring.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.rtim.academy.domain.Group;
import ua.com.rtim.academy.domain.Lesson;
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
        List<Lesson> lessons = new ArrayList<>();
        group.setLessons(lessons);
        int rows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "Groups");
        groupDao.create(group);
        assertEquals(rows + 1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "Groups"));
    }

    @Test
    void getById_shouldBeGetEntity_fromTheDataBase() {
        Group expected = new Group();
        expected.setId(3);
        expected.setName("MM-15");
        assertEquals(expected, groupDao.getById(3));
    }

    @Test
    void update_shouldBeUpdateEntity_inTheDataBase() {
        Group expected = groupDao.getById(3);
        expected.setName("Test");
        List<Lesson> lessons = new ArrayList<>();
        expected.setLessons(lessons);
        groupDao.update(expected);
        assertEquals(expected, groupDao.getById(3));
    }

    @Test
    void delete_shouldBeRemoveEntity_fromTheDataBase() {
        int rows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "Groups");
        groupDao.delete(4);
        assertEquals(rows - 1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "Groups"));
    }
}