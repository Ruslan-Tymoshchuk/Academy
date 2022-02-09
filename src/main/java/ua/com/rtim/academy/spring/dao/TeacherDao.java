package ua.com.rtim.academy.spring.dao;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ua.com.rtim.academy.domain.Course;
import ua.com.rtim.academy.domain.Teacher;
import ua.com.rtim.academy.domain.Vacation;

@Component
@Transactional
public class TeacherDao implements CrudRepository<Teacher> {

    public static final String GET_ALL_TEACHERS_QUERY = "SELECT t.id, first_name, last_name, gender, birth_date, phone, email, "
            + "ad.*, academic_degree FROM teachers t LEFT JOIN addresses ad ON ad.id = t.address_id";
    public static final String ADD_NEW_TEACHER_QUERY = "INSERT INTO teachers"
            + "(first_name, last_name, gender, birth_date, phone, email, address_id, academic_degree) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String ADD_TEACHER_COURSES_QUERY = "INSERT INTO teachers_courses (teacher_id, course_id) VALUES (?, ?)";
    public static final String GET_TEACHER_BY_ID_QUERY = "SELECT t.id, first_name, last_name, gender, birth_date, phone, email, "
            + "ad.*, academic_degree FROM teachers t LEFT JOIN addresses ad ON ad.id = t.address_id WHERE t.id = ?";
    public static final String GET_TEACHER_COURSES_BY_ID_QUERY = "SELECT c.* FROM teachers_courses tc LEFT JOIN courses c ON c.id = tc.course_id WHERE teacher_id = ?";
    public static final String GET_TEACHER_VACATIONS_BY_ID_QUERY = "SELECT id, start_date, end_date FROM vacations WHERE teacher_id = ?";
    public static final String UPDATE_TEACHER_QUERY = "UPDATE teachers SET first_name = ?, last_name = ?, phone = ?, email = ?, academic_degree = ? WHERE id = ?";
    public static final String UPDATE_TEACHER_COURSES_QUERY = "UPDATE teachers_courses SET course_id = ? WHERE teacher_id = ?";
    public static final String DELETE_TEACHER_BY_ID_QUERY = "DELETE FROM teachers WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final TeacherMapper teacherMapper;
    private final CourseMapper courseMapper;
    private final VacationMapper vacationMapper;

    public TeacherDao(JdbcTemplate jdbcTemplate, TeacherMapper teacherMapper, VacationMapper vacationMapper,
            CourseMapper courseMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.teacherMapper = teacherMapper;
        this.courseMapper = courseMapper;
        this.vacationMapper = vacationMapper;
    }

    @Override
    public List<Teacher> findAll() {
        return jdbcTemplate.query(GET_ALL_TEACHERS_QUERY, teacherMapper);
    }

    @Override
    public void create(Teacher teacher) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
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
        teacher.getCourses()
                .forEach(course -> jdbcTemplate.update(ADD_TEACHER_COURSES_QUERY, teacher.getId(), course.getId()));
    }

    @Override
    public Teacher getById(int id) {
        Teacher teacher = jdbcTemplate.queryForObject(GET_TEACHER_BY_ID_QUERY, teacherMapper, id);
        List<Course> courses = jdbcTemplate.query(GET_TEACHER_COURSES_BY_ID_QUERY, courseMapper, id);
        List<Vacation> vacations = jdbcTemplate.query(GET_TEACHER_VACATIONS_BY_ID_QUERY, vacationMapper, id);
        teacher.setCourses(courses);
        vacations.forEach(vacation -> vacation.setTeacher(teacher));
        teacher.setVacations(vacations);
        return teacher;
    }

    @Override
    public void update(Teacher teacher) {
        jdbcTemplate.update(UPDATE_TEACHER_QUERY, teacher.getFirstName(), teacher.getLastName(), teacher.getPhone(),
                teacher.getEmail(), String.valueOf(teacher.getAcademicDegree()), teacher.getId());
        teacher.getCourses()
                .forEach(course -> jdbcTemplate.update(UPDATE_TEACHER_COURSES_QUERY, course.getId(), teacher.getId()));
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_TEACHER_BY_ID_QUERY, id);
    }
}