package ua.com.rtim.academy.spring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ua.com.rtim.academy.domain.Course;

@Component
public class CourseDao implements CrudRepository<Course> {

    public static final String GET_ALL_COURSES_QUERY = "SELECT * FROM courses";
    public static final String ADD_NEW_COURSE_QUERY = "INSERT INTO courses(name, description) VALUES (?, ?)";
    public static final String GET_COURSE_BY_ID_QUERY = "SELECT * FROM courses WHERE course_id = ?";
    public static final String UPDATE_COURSE_QUERY = "UPDATE courses SET name = ?, description = ? WHERE course_id = ?";
    public static final String DELETE_COURSE_BY_ID_QUERY = "DELETE FROM courses WHERE course_id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Course> findAll() {
        return jdbcTemplate.query(GET_ALL_COURSES_QUERY, (resultSet, rows) -> mapToCourse(resultSet));
    }

    @Override
    public void create(Course course) {
        jdbcTemplate.update(ADD_NEW_COURSE_QUERY, course.getName(), course.getDescription());
    }

    @Override
    public Optional<Course> getById(int id) {
        return Optional.of(
                jdbcTemplate.queryForObject(GET_COURSE_BY_ID_QUERY, (resultSet, rows) -> mapToCourse(resultSet), id));
    }

    @Override
    public void update(Course course) {
        jdbcTemplate.update(UPDATE_COURSE_QUERY, course.getName(), course.getDescription(), course.getId());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_COURSE_BY_ID_QUERY, id);
    }

    public Course mapToCourse(ResultSet resultSet) throws SQLException {
        Course course = new Course();
        course.setId(resultSet.getInt("course_id"));
        course.setName(resultSet.getString("name"));
        course.setDescription(resultSet.getString("description"));
        return course;
    }
}