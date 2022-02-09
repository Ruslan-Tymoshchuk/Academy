package ua.com.rtim.academy.spring.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ua.com.rtim.academy.domain.Student;

@Component
public class StudentDao implements CrudRepository<Student> {

    public static final String GET_ALL_STUDENTS_QUERY = "SELECT * FROM students st LEFT JOIN addresses ad ON ad.id = st.address_id "
            + "LEFT JOIN groups gr ON gr.id = st.group_id";
    public static final String ADD_NEW_STUDENT_QUERY = "INSERT INTO students(first_name, last_name, gender, birth_date, phone, email, address_id, group_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String GET_STUDENT_BY_ID_QUERY = "SELECT * FROM students st LEFT JOIN addresses ad ON ad.id = st.address_id "
            + "LEFT JOIN groups gr ON gr.id = st.group_id WHERE st.id = ?";
    public static final String UPDATE_STUDENT_QUERY = "UPDATE students SET first_name = ?, last_name = ?, phone = ?, email = ?, group_id = ? WHERE id = ?";
    public static final String DELETE_STUDENT_BY_ID_QUERY = "DELETE FROM students WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final StudentMapper studentMapper;

    public StudentDao(JdbcTemplate jdbcTemplate, StudentMapper studentMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.studentMapper = studentMapper;
    }

    @Override
    public List<Student> findAll() {
        return jdbcTemplate.query(GET_ALL_STUDENTS_QUERY, studentMapper);
    }

    @Override
    public void create(Student student) {
        jdbcTemplate.update(ADD_NEW_STUDENT_QUERY, student.getFirstName(), student.getLastName(),
                String.valueOf(student.getGender()), student.getBirthDate(), student.getPhone(), student.getEmail(),
                student.getAddress().getId(), student.getGroup().getId());
    }

    @Override
    public Student getById(int id) {
        return jdbcTemplate.queryForObject(GET_STUDENT_BY_ID_QUERY, studentMapper, id);
    }

    @Override
    public void update(Student student) {
        jdbcTemplate.update(UPDATE_STUDENT_QUERY, student.getFirstName(), student.getLastName(), student.getPhone(),
                student.getEmail(), student.getGroup().getId(), student.getId());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_STUDENT_BY_ID_QUERY, id);
    }
}