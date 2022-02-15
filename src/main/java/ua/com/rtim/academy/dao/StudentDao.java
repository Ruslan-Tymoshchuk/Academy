package ua.com.rtim.academy.dao;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ua.com.rtim.academy.dao.mapper.StudentMapper;
import ua.com.rtim.academy.domain.Address;
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
    private final AddressDao addressDao;

    public StudentDao(JdbcTemplate jdbcTemplate, StudentMapper studentMapper, AddressDao addressDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.studentMapper = studentMapper;
        this.addressDao = addressDao;
    }

    @Override
    public List<Student> findAll() {
        return jdbcTemplate.query(GET_ALL_STUDENTS_QUERY, studentMapper);
    }

    @Override
    @Transactional
    public Student create(Student student) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_STUDENT_QUERY, new String[] { "id" });
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.setString(3, String.valueOf(student.getGender()));
            statement.setObject(4, student.getBirthDate());
            statement.setString(5, student.getPhone());
            statement.setString(6, student.getEmail());
            Address address = addressDao.create(student.getAddress());
            student.setAddress(address);
            statement.setInt(7, address.getId());
            statement.setInt(8, student.getGroup().getId());
            return statement;
        }, keyHolder);
        student.setId(keyHolder.getKeyAs(Integer.class));
        return student;
    }

    @Override
    public Student getById(int id) {
        return jdbcTemplate.queryForObject(GET_STUDENT_BY_ID_QUERY, studentMapper, id);
    }

    @Override
    @Transactional
    public void update(Student student) {
        jdbcTemplate.update(UPDATE_STUDENT_QUERY, student.getFirstName(), student.getLastName(), student.getPhone(),
                student.getEmail(), student.getGroup().getId(), student.getId());
        addressDao.update(student.getAddress());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_STUDENT_BY_ID_QUERY, id);
    }
}