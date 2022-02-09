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

import ua.com.rtim.academy.domain.Address;
import ua.com.rtim.academy.spring.config.AppConfig;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = { "classpath:schema.sql",
        "classpath:testdata.sql" })
class AddressDaoTest {

    @Autowired
    private AddressDao addressDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void findAll_shouldBeGetAllEntities_fromTheDataBase() {
        assertEquals(4, addressDao.findAll().size());
    }

    @Test
    void create_shouldBeAddNewEntity_intoTheDataBase() {
        Address address = new Address();
        address.setCountry("Ukraine");
        address.setRegion("Kyivska");
        address.setCity("Kyiv");
        address.setStreet("Velika Vasilkivska");
        address.setHouseNumber("114");
        address.setPostalCode("020590");
        int rows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "Addresses");
        addressDao.create(address);
        assertEquals(rows + 1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "Addresses"));
    }

    @Test
    void getById_shouldBeGetEntity_fromTheDataBase() {
        Address expected = new Address();
        expected.setId(3);
        expected.setCountry("Ukraine");
        expected.setRegion("Kyivska");
        expected.setCity("Kyiv");
        expected.setStreet("Velika Vasilkivska");
        expected.setHouseNumber("116");
        expected.setPostalCode("020590");
        assertEquals(expected, addressDao.getById(3));
    }

    @Test
    void update_shouldBeUpdateEntity_inTheDataBase() {
        Address expected = new Address();
        expected.setId(3);
        expected.setCountry("Ukraine");
        expected.setRegion("Kyivska");
        expected.setCity("Kyiv");
        expected.setStreet("Velika Vasilkivska");
        expected.setHouseNumber("116");
        expected.setPostalCode("020590");
        addressDao.update(expected);
        assertEquals(expected, addressDao.getById(3));
    }

    @Test
    void delete_shouldBeRemoveEntity_fromTheDataBase() {
        int rows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "Addresses");
        addressDao.delete(4);
        assertEquals(rows - 1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "Addresses"));
    }
}