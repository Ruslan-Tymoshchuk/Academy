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

import ua.com.rtim.academy.domain.Group;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
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
        int expected = countRowsInTable(jdbcTemplate, "Groups") + 1;
        groupDao.create(group);
        assertEquals(expected, countRowsInTable(jdbcTemplate, "Groups"));
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
        groupDao.update(expected);
        assertEquals(expected, groupDao.getById(3));
    }

    @Test
    void delete_shouldBeRemoveEntity_fromTheDataBase() {
        int expected = countRowsInTable(jdbcTemplate, "Groups") - 1;
        groupDao.delete(4);
        assertEquals(expected, countRowsInTable(jdbcTemplate, "Groups"));
    }
}