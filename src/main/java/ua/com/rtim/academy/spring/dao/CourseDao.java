package ua.com.rtim.academy.spring.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ua.com.rtim.academy.domain.Course;

@Component
public class CourseDao implements CrudRepository<Course> {

    public static final String GET_ALL_COURSES_QUERY = "SELECT * FROM courses";
    public static final String ADD_NEW_COURSE_QUERY = "INSERT INTO courses(name, description) VALUES (?, ?)";
    public static final String GET_COURSE_BY_ID_QUERY = "SELECT * FROM courses WHERE id = ?";
    public static final String UPDATE_COURSE_QUERY = "UPDATE courses SET name = ?, description = ? WHERE id = ?";
    public static final String DELETE_COURSE_BY_ID_QUERY = "DELETE FROM courses WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final CourseMapper courseMapper;

    public CourseDao(JdbcTemplate jdbcTemplate, CourseMapper courseMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.courseMapper = courseMapper;
    }

    @Override
    public List<Course> findAll() {
        return jdbcTemplate.query(GET_ALL_COURSES_QUERY, courseMapper);
    }

    @Override
    public void create(Course course) {
        jdbcTemplate.update(ADD_NEW_COURSE_QUERY, course.getName(), course.getDescription());
    }

    @Override
    public Course getById(int id) {
        return jdbcTemplate.queryForObject(GET_COURSE_BY_ID_QUERY, courseMapper, id);
    }

    @Override
    public void update(Course course) {
        jdbcTemplate.update(UPDATE_COURSE_QUERY, course.getName(), course.getDescription(), course.getId());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_COURSE_BY_ID_QUERY, id);
    }
}