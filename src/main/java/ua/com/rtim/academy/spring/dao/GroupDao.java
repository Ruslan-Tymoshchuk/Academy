package ua.com.rtim.academy.spring.dao;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ua.com.rtim.academy.domain.Group;

@Component
public class GroupDao implements CrudRepository<Group> {

    public static final String GET_ALL_GROUPS_QUERY = "SELECT * FROM groups";
    public static final String ADD_NEW_GROUP_QUERY = "INSERT INTO groups(name) VALUES (?)";
    public static final String GET_GROUP_BY_ID_QUERY = "SELECT * FROM groups WHERE id = ?";
    public static final String UPDATE_GROUP_QUERY = "UPDATE groups SET name = ? WHERE id = ?";
    public static final String DELETE_GROUP_BY_ID_QUERY = "DELETE FROM groups WHERE id = ?";
    public static final String ADD_LESSON_TO_GROUP_QUERY = "INSERT INTO lessons_groups(lesson_id, group_id) VALUES (?, ?)";

    private final JdbcTemplate jdbcTemplate;
    private final GroupMapper groupMapper;

    public GroupDao(JdbcTemplate jdbcTemplate, GroupMapper groupMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.groupMapper = groupMapper;
    }

    @Override
    public List<Group> findAll() {
        return jdbcTemplate.query(GET_ALL_GROUPS_QUERY, groupMapper);
    }

    @Override
    @Transactional
    public void create(Group group) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_GROUP_QUERY, new String[] { "id" });
            statement.setString(1, group.getName());
            return statement;
        }, keyHolder);
        group.setId(keyHolder.getKeyAs(Integer.class));
        group.getLessons()
                .forEach(lesson -> jdbcTemplate.update(ADD_LESSON_TO_GROUP_QUERY, lesson.getId(), group.getId()));
    }

    @Override
    public Group getById(int id) {
        return jdbcTemplate.queryForObject(GET_GROUP_BY_ID_QUERY, groupMapper, id);
    }

    @Override
    @Transactional
    public void update(Group group) {
        jdbcTemplate.update(UPDATE_GROUP_QUERY, group.getName(), group.getId());
        group.getLessons()
                .forEach(lesson -> jdbcTemplate.update(ADD_LESSON_TO_GROUP_QUERY, lesson.getId(), group.getId()));
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_GROUP_BY_ID_QUERY, id);
    }
}