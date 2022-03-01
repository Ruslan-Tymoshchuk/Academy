package ua.com.rtim.academy.dao;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import ua.com.rtim.academy.dao.mapper.GroupMapper;
import ua.com.rtim.academy.domain.Group;
import ua.com.rtim.academy.domain.Lesson;

@Component
public class GroupDao implements CrudRepository<Group> {

    public static final String GET_ALL_GROUPS_QUERY = "SELECT * FROM groups";
    public static final String ADD_NEW_GROUP_QUERY = "INSERT INTO groups(name) VALUES (?)";
    public static final String GET_GROUP_BY_ID_QUERY = "SELECT * FROM groups WHERE id = ?";
    public static final String UPDATE_GROUP_QUERY = "UPDATE groups SET name = ? WHERE id = ?";
    public static final String DELETE_GROUP_BY_ID_QUERY = "DELETE FROM groups WHERE id = ?";
    public static final String GET_LESSON_GROUPS_QUERY = "SELECT g.* FROM lessons_groups lg LEFT JOIN groups g ON g.id = lg.group_id WHERE lesson_id = ?";

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
    public Group create(Group group) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_GROUP_QUERY, new String[] { "id" });
            statement.setString(1, group.getName());
            return statement;
        }, keyHolder);
        group.setId(keyHolder.getKeyAs(Integer.class));
        return group;
    }

    @Override
    public Group getById(int id) {
        return jdbcTemplate.queryForObject(GET_GROUP_BY_ID_QUERY, groupMapper, id);
    }

    @Override
    public void update(Group group) {
        jdbcTemplate.update(UPDATE_GROUP_QUERY, group.getName(), group.getId());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_GROUP_BY_ID_QUERY, id);
    }

    public List<Group> findByLesson(Lesson lesson) {
        return jdbcTemplate.query(GET_LESSON_GROUPS_QUERY, groupMapper, lesson.getId());
    }
}