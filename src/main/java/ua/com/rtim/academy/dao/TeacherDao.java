package ua.com.rtim.academy.dao;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ua.com.rtim.academy.dao.mapper.TeacherMapper;
import ua.com.rtim.academy.domain.Address;
import ua.com.rtim.academy.domain.Course;
import ua.com.rtim.academy.domain.Teacher;

@Component
public class TeacherDao implements CrudRepository<Teacher> {

    public static final String GET_ALL_TEACHERS_QUERY = "SELECT t.id, first_name, last_name, gender, birth_date, phone, email, "
            + "ad.*, academic_degree FROM teachers t LEFT JOIN addresses ad ON ad.id = t.address_id";
    public static final String ADD_NEW_TEACHER_QUERY = "INSERT INTO teachers"
            + "(first_name, last_name, gender, birth_date, phone, email, address_id, academic_degree) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String ADD_TEACHER_COURSES_QUERY = "INSERT INTO teachers_courses (teacher_id, course_id) VALUES (?, ?)";
    public static final String UPDATE_TEACHER_COURSES_QUERY = "INSERT INTO teachers_courses (teacher_id, course_id) SELECT ?, ? "
            + "WHERE NOT EXISTS (SELECT teacher_id, course_id FROM teachers_courses WHERE teacher_id = ? AND course_id = ?)";
    public static final String GET_TEACHER_BY_ID_QUERY = "SELECT t.id, first_name, last_name, gender, birth_date, phone, email, "
            + "ad.*, academic_degree FROM teachers t LEFT JOIN addresses ad ON ad.id = t.address_id WHERE t.id = ?";
    public static final String UPDATE_TEACHER_QUERY = "UPDATE teachers SET first_name = ?, last_name = ?, phone = ?, email = ?, academic_degree = ? WHERE id = ?";
    public static final String DELETE_TEACHER_BY_ID_QUERY = "DELETE FROM teachers WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final TeacherMapper teacherMapper;
    private final AddressDao addressDao;

    public TeacherDao(JdbcTemplate jdbcTemplate, TeacherMapper teacherMapper, AddressDao addressDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.teacherMapper = teacherMapper;
        this.addressDao = addressDao;
    }

    @Override
    public List<Teacher> findAll() {
        return jdbcTemplate.query(GET_ALL_TEACHERS_QUERY, teacherMapper);
    }

    @Override
    @Transactional
    public Teacher create(Teacher teacher) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_TEACHER_QUERY, new String[] { "id" });
            statement.setString(1, teacher.getFirstName());
            statement.setString(2, teacher.getLastName());
            statement.setString(3, String.valueOf(teacher.getGender()));
            statement.setObject(4, teacher.getBirthDate());
            statement.setString(5, teacher.getPhone());
            statement.setString(6, teacher.getEmail());
            Address address = addressDao.create(teacher.getAddress());
            teacher.setAddress(address);
            statement.setInt(7, address.getId());
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
        return teacher;
    }

    @Override
    public Teacher getById(int id) {
        return jdbcTemplate.queryForObject(GET_TEACHER_BY_ID_QUERY, teacherMapper, id);
    }

    @Override
    @Transactional
    public void update(Teacher teacher) {
        jdbcTemplate.update(UPDATE_TEACHER_QUERY, teacher.getFirstName(), teacher.getLastName(),
                teacher.getPhone(),
                teacher.getEmail(), String.valueOf(teacher.getAcademicDegree()), teacher.getId());
        addressDao.update(teacher.getAddress());
        List<Course> courses = teacher.getCourses();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(UPDATE_TEACHER_COURSES_QUERY);
            for (Course course : courses) {
                statement.setInt(1, teacher.getId());
                statement.setInt(2, course.getId());
                statement.setInt(3, teacher.getId());
                statement.setInt(4, course.getId());
            }
            return statement;
        });
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_TEACHER_BY_ID_QUERY, id);
    }
}