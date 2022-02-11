package ua.com.rtim.academy.dao;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import ua.com.rtim.academy.domain.Address;
import ua.com.rtim.academy.domain.Course;
import ua.com.rtim.academy.domain.Teacher;
import ua.com.rtim.academy.mapper.TeacherMapper;

@Component
public class TeacherDao implements CrudRepository<Teacher> {

    public static final String GET_ALL_TEACHERS_QUERY = "SELECT t.id, first_name, last_name, gender, birth_date, phone, email, "
            + "ad.*, academic_degree FROM teachers t LEFT JOIN addresses ad ON ad.id = t.address_id";
    public static final String ADD_NEW_TEACHER_QUERY = "INSERT INTO teachers"
            + "(first_name, last_name, gender, birth_date, phone, email, address_id, academic_degree) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String ADD_TEACHER_ADDRESS_QUERY = "INSERT INTO addresses(country, region, city, street, house_number, postal_code) "
            + "VALUES (?, ?, ?, ?, ?, ?)";
    public static final String ADD_TEACHER_COURSES_QUERY = "INSERT INTO teachers_courses (teacher_id, course_id) VALUES (?, ?)";
    public static final String GET_TEACHER_BY_ID_QUERY = "SELECT t.id, first_name, last_name, gender, birth_date, phone, email, "
            + "ad.*, academic_degree FROM teachers t LEFT JOIN addresses ad ON ad.id = t.address_id WHERE t.id = ?";
    public static final String UPDATE_TEACHER_QUERY = "UPDATE teachers SET first_name = ?, last_name = ?, phone = ?, email = ?, academic_degree = ? WHERE id = ?";
    public static final String UPDATE_TEACHER_COURSES_QUERY = "UPDATE teachers_courses SET course_id = ? WHERE teacher_id = ?";
    public static final String DELETE_TEACHER_BY_ID_QUERY = "DELETE FROM teachers WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final TeacherMapper teacherMapper;

    public TeacherDao(JdbcTemplate jdbcTemplate, TeacherMapper teacherMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.teacherMapper = teacherMapper;
    }

    @Override
    public List<Teacher> findAll() {
        return jdbcTemplate.query(GET_ALL_TEACHERS_QUERY, teacherMapper);
    }

    @Override
    public void create(Teacher teacher) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Address address = teacher.getAddress();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_TEACHER_ADDRESS_QUERY, new String[] { "id" });
            statement.setString(1, address.getCountry());
            statement.setString(2, address.getRegion());
            statement.setString(3, address.getCity());
            statement.setString(4, address.getStreet());
            statement.setString(5, address.getHouseNumber());
            statement.setString(6, address.postalCode());
            return statement;
        }, keyHolder);
        address.setId(keyHolder.getKeyAs(Integer.class));
        teacher.setAddress(address);
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_TEACHER_QUERY, new String[] { "id" });
            statement.setString(1, teacher.getFirstName());
            statement.setString(2, teacher.getLastName());
            statement.setString(3, String.valueOf(teacher.getGender()));
            statement.setObject(4, teacher.getBirthDate());
            statement.setString(5, teacher.getPhone());
            statement.setString(6, teacher.getEmail());
            statement.setInt(7, teacher.getAddress().getId());
            statement.setString(8, String.valueOf(teacher.getAcademicDegree()));
            return statement;
        }, keyHolder);
        teacher.setId(keyHolder.getKeyAs(Integer.class));
        List<Course> courses = teacher.getCourses();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_TEACHER_COURSES_QUERY);
            for (Course course : courses) {
                statement.setInt(1, teacher.getId());
                statement.setInt(2, course.getId());
            }
            return statement;
        });
    }

    @Override
    public Teacher getById(int id) {
        return jdbcTemplate.queryForObject(GET_TEACHER_BY_ID_QUERY, teacherMapper, id);
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
}