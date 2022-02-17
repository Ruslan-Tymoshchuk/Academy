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
import ua.com.rtim.academy.domain.Address;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class AddressDaoTest {

    @Autowired
    private AddressDao addressDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void finds_all_addresses() {
        assertEquals(4, addressDao.findAll().size());
    }

    @Test
    void adds_new_address() {
        Address address = new Address();
        address.setCountry("Ukraine");
        address.setRegion("Kyivska");
        address.setCity("Kyiv");
        address.setStreet("Velika Vasilkivska");
        address.setHouseNumber("114");
        address.setPostalCode("020590");
        int expected = countRowsInTable(jdbcTemplate, "Addresses") + 1;

        addressDao.create(address);

        assertEquals(expected, countRowsInTable(jdbcTemplate, "Addresses"));
    }

    @Test
    void gets_address_by_id() {
        Address expected = new Address();
        expected.setId(3);
        expected.setCountry("Ukraine");
        expected.setRegion("Kyivska");
        expected.setCity("Kyiv");
        expected.setStreet("Velika Vasilkivska");
        expected.setHouseNumber("116");
        expected.setPostalCode("020590");

        Address actual = addressDao.getById(3);

        assertEquals(expected, actual);
    }

    @Test
    void updates_the_address() {
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
    void deletes_the_address() {
        int expected = countRowsInTable(jdbcTemplate, "Addresses") - 1;

        addressDao.delete(4);

        assertEquals(expected, countRowsInTable(jdbcTemplate, "Addresses"));
    }
}