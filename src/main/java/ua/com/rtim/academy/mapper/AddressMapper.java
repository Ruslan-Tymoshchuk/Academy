package ua.com.rtim.academy.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.com.rtim.academy.domain.Address;

@Component
public class AddressMapper implements RowMapper<Address> {

    @Override
    public Address mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Address address = new Address();
        address.setId(resultSet.getInt("id"));
        address.setCountry(resultSet.getString("country"));
        address.setRegion(resultSet.getString("region"));
        address.setCity(resultSet.getString("city"));
        address.setStreet(resultSet.getString("street"));
        address.setHouseNumber(resultSet.getString("house_number"));
        address.setPostalCode(resultSet.getString("postal_code"));
        return address;
    }
}