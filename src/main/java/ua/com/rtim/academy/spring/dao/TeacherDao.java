package ua.com.rtim.academy.spring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ua.com.rtim.academy.domain.AcademicDegree;
import ua.com.rtim.academy.domain.Gender;
import ua.com.rtim.academy.domain.Teacher;

@Component
public class TeacherDao implements CrudRepository<Teacher> {

    public static final String GET_ALL_TEACHERS_QUERY = "SELECT teacher_id, first_name, last_name, gender, birthdate, phone, email, "
            + "ad.*, academicDegree FROM teachers t LEFT JOIN addresses ad ON ad.address_id = t.address_id";
    public static final String ADD_NEW_TEACHER_QUERY = "INSERT INTO teachers"
            + "(first_name, last_name, gender, birthDate, phone, email, address_id, academicDegree) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String GET_TEACHER_BY_ID_QUERY = "SELECT teacher_id, first_name, last_name, gender, birthdate, phone, email, "
            + "ad.*, academicDegree FROM teachers t LEFT JOIN addresses ad ON ad.address_id = t.address_id WHERE teacher_id = ?";
    public static final String UPDATE_TEACHER_QUERY = "UPDATE teachers SET first_name = ?, last_name = ?, phone = ?, email = ?, academicDegree = ? WHERE teacher_id = ?";
    public static final String DELETE_TEACHER_BY_ID_QUERY = "DELETE FROM teachers WHERE teacher_id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final AddressDao addressDao;

    @Autowired
    public TeacherDao(JdbcTemplate jdbcTemplate, AddressDao addressDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.addressDao = addressDao;
    }

    @Override
    public List<Teacher> findAll() {
        return jdbcTemplate.query(GET_ALL_TEACHERS_QUERY, (resultSet, rows) -> mapToTeacher(resultSet));
    }

    @Override
    public void create(Teacher teacher) {
        jdbcTemplate.update(ADD_NEW_TEACHER_QUERY, teacher.getFirstName(), teacher.getLastName(),
                String.valueOf(teacher.getGender()), teacher.getBirthDate(), teacher.getPhone(), teacher.getEmail(),
                teacher.getAddress().getId(), String.valueOf(teacher.getAcademicDegree()));
    }

    @Override
    public Optional<Teacher> getById(int id) {
        return Optional.of(
                jdbcTemplate.queryForObject(GET_TEACHER_BY_ID_QUERY, (resultSet, rows) -> mapToTeacher(resultSet), id));
    }

    @Override
    public void update(Teacher teacher) {
        jdbcTemplate.update(UPDATE_TEACHER_QUERY, teacher.getFirstName(), teacher.getLastName(), teacher.getPhone(),
                teacher.getEmail(), String.valueOf(teacher.getAcademicDegree()), teacher.getId());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_TEACHER_BY_ID_QUERY, id);
    }

    public Teacher mapToTeacher(ResultSet resultSet) throws SQLException {
        Teacher teacher = new Teacher();
        teacher.setId(resultSet.getInt("teacher_id"));
        teacher.setFirstName(resultSet.getString("first_name"));
        teacher.setLastName(resultSet.getString("last_name"));
        teacher.setGender(Gender.valueOf(resultSet.getString("gender")));
        teacher.setBirthDate(resultSet.getObject(5, LocalDate.class));
        teacher.setPhone(resultSet.getString("phone"));
        teacher.setEmail(resultSet.getString("email"));
        teacher.setAddress(addressDao.mapToAddress(resultSet));
        teacher.setAcademicDegree(AcademicDegree.valueOf(resultSet.getString("academicDegree")));
        return teacher;
    }
}