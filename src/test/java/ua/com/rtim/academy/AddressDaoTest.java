package ua.com.rtim.academy;

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

import ua.com.rtim.academy.dao.AddressDao;
import ua.com.rtim.academy.domain.Address;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
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
        int expected = countRowsInTable(jdbcTemplate, "Addresses") + 1;
        addressDao.create(address);
        assertEquals(expected, countRowsInTable(jdbcTemplate, "Addresses"));
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
        int expected = countRowsInTable(jdbcTemplate, "Addresses") - 1;
        addressDao.delete(4);
        assertEquals(expected, countRowsInTable(jdbcTemplate, "Addresses"));
    }
}