package ua.com.rtim.academy.spring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ua.com.rtim.academy.domain.Group;

@Component
public class GroupDao implements CrudRepository<Group> {

    public static final String GET_ALL_GROUPS_QUERY = "SELECT * FROM groups";
    public static final String ADD_NEW_GROUP_QUERY = "INSERT INTO groups(name) VALUES (?)";
    public static final String GET_GROUP_BY_ID_QUERY = "SELECT * FROM groups WHERE group_id = ?";
    public static final String UPDATE_GROUP_QUERY = "UPDATE groups SET name = ? WHERE group_id = ?";
    public static final String DELETE_GROUP_BY_ID_QUERY = "DELETE FROM groups WHERE group_id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GroupDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Group> findAll() {
        return jdbcTemplate.query(GET_ALL_GROUPS_QUERY, (resultSet, rows) -> mapToGroup(resultSet));
    }

    @Override
    public void create(Group group) {
        jdbcTemplate.update(ADD_NEW_GROUP_QUERY, group.getName());
    }

    @Override
    public Optional<Group> getById(int id) {
        return Optional
                .of(jdbcTemplate.queryForObject(GET_GROUP_BY_ID_QUERY, (resultSet, rows) -> mapToGroup(resultSet), id));
    }

    @Override
    public void update(Group group) {
        jdbcTemplate.update(UPDATE_GROUP_QUERY, group.getName(), group.getId());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_GROUP_BY_ID_QUERY, id);
    }

    public Group mapToGroup(ResultSet resultSet) throws SQLException {
        Group group = new Group();
        group.setId(resultSet.getInt("group_id"));
        group.setName(resultSet.getString("name"));
        return group;
    }
}