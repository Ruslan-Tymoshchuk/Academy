package ua.com.rtim.academy.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.com.rtim.academy.domain.Gender;
import ua.com.rtim.academy.domain.Student;

@Component
public class StudentMapper implements RowMapper<Student> {

    private final AddressMapper addressMapper;
    private final GroupMapper groupMapper;

    public StudentMapper(AddressMapper addressMapper, GroupMapper groupMapper) {
        this.addressMapper = addressMapper;
        this.groupMapper = groupMapper;
    }

    @Override
    public Student mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Student student = new Student();
        student.setId(resultSet.getInt("id"));
        student.setFirstName(resultSet.getString("first_name"));
        student.setLastName(resultSet.getString("last_name"));
        student.setGender(Gender.valueOf(resultSet.getString("gender")));
        student.setBirthDate(resultSet.getObject(5, LocalDate.class));
        student.setPhone(resultSet.getString("phone"));
        student.setEmail(resultSet.getString("email"));
        student.setAddress(addressMapper.mapRow(resultSet, rowNum));
        student.setGroup(groupMapper.mapRow(resultSet, rowNum));
        return student;
    }
}