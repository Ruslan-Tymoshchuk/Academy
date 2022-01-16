package ua.com.rtim.academy.domain;

import java.time.LocalDate;
import java.util.List;

public class Lesson {

    private Integer id;
    private Teacher teacher;
    private Course course;
    private Audience audience;
    private LocalDate date;
    private LessonTime time;
    private List<Group> groups;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Audience getAudience() {
        return audience;
    }

    public void setAudience(Audience audience) {
        this.audience = audience;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LessonTime getTime() {
        return time;
    }

    public void setTime(LessonTime time) {
        this.time = time;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}