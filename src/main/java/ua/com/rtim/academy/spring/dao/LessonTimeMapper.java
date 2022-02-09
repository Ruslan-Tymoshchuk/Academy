package ua.com.rtim.academy.spring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.com.rtim.academy.domain.LessonTime;

@Component
public class LessonTimeMapper implements RowMapper<LessonTime> {

    @Override
    public LessonTime mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        LessonTime lessonTime = new LessonTime();
        lessonTime.setId(resultSet.getInt("id"));
        lessonTime.setStartTime(resultSet.getObject(2, LocalTime.class));
        lessonTime.setEndTime(resultSet.getObject(3, LocalTime.class));
        return lessonTime;
    }
}