package ua.com.rtim.academy.dao;

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
import ua.com.rtim.academy.domain.Group;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class GroupDaoTest {

    @Autowired
    private GroupDao groupDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void finds_all_groups() {
        assertEquals(4, groupDao.findAll().size());
    }

    @Test
    void adds_new_group() {
        Group group = new Group();
        group.setName("HM-12");
        int expected = countRowsInTable(jdbcTemplate, "Groups") + 1;

        groupDao.create(group);

        assertEquals(expected, countRowsInTable(jdbcTemplate, "Groups"));
    }

    @Test
    void gets_group_by_id() {
        Group expected = new Group();
        expected.setId(3);
        expected.setName("MM-15");

        Group actual = groupDao.getById(3);

        assertEquals(expected, actual);
    }

    @Test
    void updates_the_group() {
        Group expected = new Group();
        expected.setId(3);
        expected.setName("Test");

        groupDao.update(expected);

        assertEquals(expected, groupDao.getById(3));
    }

    @Test
    void deletes_the_group() {
        int expected = countRowsInTable(jdbcTemplate, "Groups") - 1;

        groupDao.delete(4);

        assertEquals(expected, countRowsInTable(jdbcTemplate, "Groups"));
    }
}