package ua.com.rtim.academy.dao;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import ua.com.rtim.academy.domain.Address;
import ua.com.rtim.academy.domain.Student;
import ua.com.rtim.academy.mapper.StudentMapper;

@Component
public class StudentDao implements CrudRepository<Student> {

    public static final String GET_ALL_STUDENTS_QUERY = "SELECT * FROM students st LEFT JOIN addresses ad ON ad.id = st.address_id "
            + "LEFT JOIN groups gr ON gr.id = st.group_id";
    public static final String ADD_NEW_STUDENT_QUERY = "INSERT INTO students(first_name, last_name, gender, birth_date, phone, email, address_id, group_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String ADD_STUDENT_ADDRESS_QUERY = "INSERT INTO addresses(country, region, city, street, house_number, postal_code) "
            + "VALUES (?, ?, ?, ?, ?, ?)";
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
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Address address = student.getAddress();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_STUDENT_ADDRESS_QUERY, new String[] { "id" });
            statement.setString(1, address.getCountry());
            statement.setString(2, address.getRegion());
            statement.setString(3, address.getCity());
            statement.setString(4, address.getStreet());
            statement.setString(5, address.getHouseNumber());
            statement.setString(6, address.postalCode());
            return statement;
        }, keyHolder);
        address.setId(keyHolder.getKeyAs(Integer.class));
        student.setAddress(address);
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_STUDENT_QUERY, new String[] { "id" });
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.setString(3, String.valueOf(student.getGender()));
            statement.setObject(4, student.getBirthDate());
            statement.setString(5, student.getPhone());
            statement.setString(6, student.getEmail());
            statement.setInt(7, student.getAddress().getId());
            statement.setInt(8, student.getGroup().getId());
            return statement;
        }, keyHolder);
        student.setId(keyHolder.getKeyAs(Integer.class));
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