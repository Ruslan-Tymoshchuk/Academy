package ua.com.rtim.academy.spring.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import ua.com.rtim.academy.domain.Address;

@Component
public class AddressDao implements CrudRepository<Address> {

    public static final String GET_ALL_ADDRESSES_QUERY = "SELECT * FROM addresses";
    public static final String ADD_NEW_ADDRESS_QUERY = "INSERT INTO addresses(country, region, city, street, houseNumber, postalCode) "
            + "VALUES (?, ?, ?, ?, ?, ?)";
    public static final String GET_ADDRESS_BY_ID_QUERY = "SELECT * FROM addresses WHERE address_id = ?";
    public static final String UPDATE_ADDRESS_QUERY = "UPDATE addresses SET "
            + "country = ?, region = ?, city = ?, street = ?, houseNumber = ?, postalCode = ? WHERE address_id = ?";
    public static final String DELETE_ADDRESS_BY_ID_QUERY = "DELETE FROM addresses WHERE address_id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AddressDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Address> findAll() {
        return jdbcTemplate.query(GET_ALL_ADDRESSES_QUERY, (resultSet, rows) -> mapToAddress(resultSet));
    }

    @Override
    public void create(Address address) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_ADDRESS_QUERY,
                    new String[] { "address_id" });
            statement.setString(1, address.getCountry());
            statement.setString(2, address.getRegion());
            statement.setString(3, address.getCity());
            statement.setString(4, address.getStreet());
            statement.setString(5, address.getHouseNumber());
            statement.setString(6, address.postalCode());
            return statement;
        }, keyHolder);
        address.setId(keyHolder.getKeyAs(Integer.class));
    }

    @Override
    public Optional<Address> getById(int id) {
        return Optional.of(
                jdbcTemplate.queryForObject(GET_ADDRESS_BY_ID_QUERY, (resultSet, rows) -> mapToAddress(resultSet), id));
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

    public Address mapToAddress(ResultSet resultSet) throws SQLException {
        Address address = new Address();
        address.setId(resultSet.getInt("address_id"));
        address.setCountry(resultSet.getString("country"));
        address.setRegion(resultSet.getString("region"));
        address.setCity(resultSet.getString("city"));
        address.setStreet(resultSet.getString("street"));
        address.setHouseNumber(resultSet.getString("houseNumber"));
        address.setPostalCode(resultSet.getString("postalCode"));
        return address;
    }
}