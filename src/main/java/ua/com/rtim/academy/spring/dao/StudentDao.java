package ua.com.rtim.academy.spring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ua.com.rtim.academy.domain.Gender;
import ua.com.rtim.academy.domain.Group;
import ua.com.rtim.academy.domain.Student;

@Component
public class StudentDao implements CrudRepository<Student> {

    public static final String GET_ALL_STUDENTS_QUERY = "SELECT * FROM students st LEFT JOIN addresses ad ON ad.address_id = st.address_id "
            + "LEFT JOIN groups gr ON gr.group_id = st.group_id";
    public static final String ADD_NEW_STUDENT_QUERY = "INSERT INTO students(first_name, last_name, gender, birthDate, phone, email, address_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String GET_STUDENT_BY_ID_QUERY = "SELECT * FROM students st LEFT JOIN addresses ad ON ad.address_id = st.address_id "
            + "LEFT JOIN groups gr ON gr.group_id = st.group_id WHERE st.student_id = ?";
    public static final String UPDATE_STUDENT_QUERY = "UPDATE students SET first_name = ?, last_name = ?, phone = ?, email = ? WHERE student_id = ?";
    public static final String DELETE_STUDENT_BY_ID_QUERY = "DELETE FROM students WHERE student_id = ?";
    public static final String ADD_TO_GROUP_QUERY = "UPDATE students SET group_id = ? WHERE student_id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final AddressDao addressDao;
    private final GroupDao groupDao;

    @Autowired
    public StudentDao(JdbcTemplate jdbcTemplate, AddressDao addressDao, GroupDao groupDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.addressDao = addressDao;
        this.groupDao = groupDao;
    }

    @Override
    public List<Student> findAll() {
        return jdbcTemplate.query(GET_ALL_STUDENTS_QUERY, (resultSet, rows) -> mapToStudent(resultSet));
    }

    @Override
    public void create(Student student) {
        jdbcTemplate.update(ADD_NEW_STUDENT_QUERY, student.getFirstName(), student.getLastName(),
                String.valueOf(student.getGender()), student.getBirthDate(), student.getPhone(), student.getEmail(),
                student.getAddress().getId());
    }

    @Override
    public Optional<Student> getById(int id) {
        return Optional.of(
                jdbcTemplate.queryForObject(GET_STUDENT_BY_ID_QUERY, (resultSet, rows) -> mapToStudent(resultSet), id));
    }

    @Override
    public void update(Student student) {
        jdbcTemplate.update(UPDATE_STUDENT_QUERY, student.getFirstName(), student.getLastName(), student.getPhone(),
                student.getEmail(), student.getId());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_STUDENT_BY_ID_QUERY, id);
    }

    public void addToGroup(Group group, Student student) {
        jdbcTemplate.update(ADD_TO_GROUP_QUERY, group.getId(), student.getId());
    }

    private Student mapToStudent(ResultSet resultSet) throws SQLException {
        Student student = new Student();
        student.setId(resultSet.getInt("student_id"));
        student.setFirstName(resultSet.getString("first_name"));
        student.setLastName(resultSet.getString("last_name"));
        student.setGender(Gender.valueOf(resultSet.getString("gender")));
        student.setBirthDate(resultSet.getObject(5, LocalDate.class));
        student.setPhone(resultSet.getString("phone"));
        student.setEmail(resultSet.getString("email"));
        student.setAddress(addressDao.mapToAddress(resultSet));
        student.setGroup(groupDao.mapToGroup(resultSet));
        return student;
    }
}