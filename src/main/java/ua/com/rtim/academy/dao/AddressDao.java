package ua.com.rtim.academy.dao;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import ua.com.rtim.academy.dao.mapper.AddressMapper;
import ua.com.rtim.academy.domain.Address;

@Component
public class AddressDao implements CrudRepository<Address> {

    public static final String GET_ALL_ADDRESSES_QUERY = "SELECT * FROM addresses";
    public static final String ADD_NEW_ADDRESS_QUERY = "INSERT INTO addresses(country, region, city, street, house_number, postal_code) "
            + "VALUES (?, ?, ?, ?, ?, ?)";
    public static final String GET_ADDRESS_BY_ID_QUERY = "SELECT * FROM addresses WHERE id = ?";
    public static final String UPDATE_ADDRESS_QUERY = "UPDATE addresses SET "
            + "country = ?, region = ?, city = ?, street = ?, house_number = ?, postal_code = ? WHERE id = ?";
    public static final String DELETE_ADDRESS_BY_ID_QUERY = "DELETE FROM addresses WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final AddressMapper addressMapper;

    public AddressDao(JdbcTemplate jdbcTemplate, AddressMapper addressMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.addressMapper = addressMapper;
    }

    @Override
    public List<Address> findAll() {
        return jdbcTemplate.query(GET_ALL_ADDRESSES_QUERY, addressMapper);
    }

    @Override
    public Address create(Address address) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_ADDRESS_QUERY,
                    new String[] { "id" });
            statement.setString(1, address.getCountry());
            statement.setString(2, address.getRegion());
            statement.setString(3, address.getCity());
            statement.setString(4, address.getStreet());
            statement.setString(5, address.getHouseNumber());
            statement.setString(6, address.postalCode());
            return statement;
        }, keyHolder);
        address.setId(keyHolder.getKeyAs(Integer.class));
        return address;
    }

    @Override
    public Address getById(int id) {
        return jdbcTemplate.queryForObject(GET_ADDRESS_BY_ID_QUERY, addressMapper, id);
    }

    @Override
    public void update(Address address) {
        jdbcTemplate.update(UPDATE_ADDRESS_QUERY, address.getCountry(), address.getRegion(), address.getCity(),
                address.getStreet(), address.getHouseNumber(), address.postalCode(), address.getId());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_ADDRESS_BY_ID_QUERY, id);
    }
}