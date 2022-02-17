package ua.com.rtim.academy.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.com.rtim.academy.dao.CourseDao;
import ua.com.rtim.academy.domain.AcademicDegree;
import ua.com.rtim.academy.domain.Gender;
import ua.com.rtim.academy.domain.Teacher;

@Component
public class TeacherMapper implements RowMapper<Teacher> {

    private final AddressMapper addressMapper;
    private final CourseDao courseDao;

    public TeacherMapper(AddressMapper addressMapper, CourseDao courseDao) {
        this.addressMapper = addressMapper;
        this.courseDao = courseDao;
    }

    @Override
    public Teacher mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Teacher teacher = new Teacher();
        teacher.setId(resultSet.getInt("id"));
        teacher.setFirstName(resultSet.getString("first_name"));
        teacher.setLastName(resultSet.getString("last_name"));
        teacher.setGender(Gender.valueOf(resultSet.getString("gender")));
        teacher.setBirthDate(resultSet.getObject(5, LocalDate.class));
        teacher.setPhone(resultSet.getString("phone"));
        teacher.setEmail(resultSet.getString("email"));
        teacher.setAddress(addressMapper.mapRow(resultSet, rowNum));
        teacher.setAcademicDegree(AcademicDegree.valueOf(resultSet.getString("academic_degree")));
        teacher.setCourses(courseDao.findByTeacher(teacher.getId()));
        return teacher;
    }
}