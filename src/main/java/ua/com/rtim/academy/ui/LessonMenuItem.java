package ua.com.rtim.academy.ui;

import static java.time.LocalTime.of;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ua.com.rtim.academy.dao.AudienceDao;
import ua.com.rtim.academy.dao.CourseDao;
import ua.com.rtim.academy.dao.GroupDao;
import ua.com.rtim.academy.dao.LessonDao;
import ua.com.rtim.academy.dao.LessonTimeDao;
import ua.com.rtim.academy.dao.TeacherDao;
import ua.com.rtim.academy.domain.Audience;
import ua.com.rtim.academy.domain.Course;
import ua.com.rtim.academy.domain.Group;
import ua.com.rtim.academy.domain.Lesson;
import ua.com.rtim.academy.domain.LessonTime;
import ua.com.rtim.academy.domain.Teacher;

public class LessonMenuItem {

    private final LessonDao lessonDao;
    private final TeacherDao teacherDao;
    private final CourseDao courseDao;
    private final AudienceDao audienceDao;
    private final LessonTimeDao lessonTimeDao;
    private final GroupDao groupDao;

    public LessonMenuItem(LessonDao lessonDao, TeacherDao teacherDao, CourseDao courseDao, AudienceDao audienceDao,
            LessonTimeDao lessonTimeDao, GroupDao groupDao, Scanner scanner) {
        System.out.println(
                "Lesson: a: Find All, b: Create, c: Create Lesson Time, d: Update, e: Add time to Lesson, f: Group lessons, g: Delete");
        this.lessonDao = lessonDao;
        this.teacherDao = teacherDao;
        this.courseDao = courseDao;
        this.audienceDao = audienceDao;
        this.lessonTimeDao = lessonTimeDao;
        this.groupDao = groupDao;
        switch (scanner.next()) {
        case "a":
            findAllLessons();
            break;
        case "b":
            createLesson(scanner);
            break;
        case "c":
            createLessonTime(scanner);
            break;
        case "d":
            updateLesson(scanner);
            break;
        case "e":
            addTimeToLesson(scanner);
            break;
        case "f":
            getGroupLessons(scanner);
            break;
        case "g":
            deleteLesson(scanner);
            break;
        default:
            break;
        }
    }

    public void findAllLessons() {
        List<Lesson> lessons = lessonDao.findAll();
        lessons.forEach(lesson -> System.out.println(lesson.getDate()));
    }

    private void createLesson(Scanner scanner) {
        Lesson lesson = new Lesson();
        System.out.println("Teacher id");
        lesson.setTeacher(teacherDao.getById(scanner.nextInt()));
        System.out.println("Course id");
        lesson.setCourse(courseDao.getById(scanner.nextInt()));
        System.out.println("Audience id");
        Audience audience = audienceDao.getById(scanner.nextInt());
        lesson.setAudience(audience);
        System.out.println("Date");
        int year = scanner.nextInt();
        System.out.println("Month");
        int month = scanner.nextInt();
        System.out.println("Day");
        int day = scanner.nextInt();
        LocalDate date = LocalDate.of(year, month, day);
        lesson.setDate(date);
        System.out.println("Lesson time id");
        LessonTime lessonTime = lessonTimeDao.getById(scanner.nextInt());
        lesson.setTime(lessonTime);
        System.out.println("Select groups id's");
        List<Group> groups = new ArrayList<>();
        while (scanner.hasNextInt()) {
            Group group = groupDao.getById(scanner.nextInt());
            groups.add(group);
        }
        lesson.setGroups(groups);
        lessonDao.create(lesson);
    }

    private void updateLesson(Scanner scanner) {
        System.out.println("Leeson id");
        Lesson lesson = lessonDao.getById(scanner.nextInt());
        System.out.println("Teacher id");
        Teacher teacher = teacherDao.getById(scanner.nextInt());
        lesson.setTeacher(teacher);
        System.out.println("Course id");
        Course course = courseDao.getById(scanner.nextInt());
        lesson.setCourse(course);
        System.out.println("Audience id");
        Audience audience = audienceDao.getById(scanner.nextInt());
        lesson.setAudience(audience);
        System.out.println("Date");
        int year = scanner.nextInt();
        System.out.println("Month");
        int month = scanner.nextInt();
        System.out.println("Day");
        int day = scanner.nextInt();
        LocalDate date = LocalDate.of(year, month, day);
        lesson.setDate(date);
        System.out.println("Lesson time id");
        LessonTime lessonTime = lessonTimeDao.getById(scanner.nextInt());
        lesson.setTime(lessonTime);
        System.out.println("Select groups id's");
        List<Group> groups = new ArrayList<>();
        while (scanner.hasNextInt()) {
            Group group = groupDao.getById(scanner.nextInt());
            groups.add(group);
        }
        lesson.setGroups(groups);
        lessonDao.update(lesson);
    }

    public void createLessonTime(Scanner scanner) {
        LessonTime lessonTime = new LessonTime();
        System.out.println("Start time");
        lessonTime.setStartTime(of(scanner.nextInt(), scanner.nextInt()));
        System.out.println("End time");
        lessonTime.setEndTime(of(scanner.nextInt(), scanner.nextInt()));
        lessonTimeDao.create(lessonTime);
    }

    private void addTimeToLesson(Scanner scanner) {
        System.out.println("Lesson id");
        Lesson lesson = lessonDao.getById(scanner.nextInt());
        System.out.println("Lesson time id");
        LessonTime lessonTime = lessonTimeDao.getById(scanner.nextInt());
        lesson.setTime(lessonTime);
        lessonDao.update(lesson);
    }

    public void getGroupLessons(Scanner scanner) {
        System.out.println("Group id");
        int groupId = scanner.nextInt();
        System.out.println("Start time");
        LocalDate startDate = addDate(scanner);
        System.out.println("End time");
        LocalDate endDate = addDate(scanner);
        List<Lesson> lessons = lessonDao.findByGroupIdAndDateInterval(groupId, startDate, endDate);
        lessons.forEach(lesson -> System.out.println(lesson.getDate()));
    }

    private void deleteLesson(Scanner scanner) {
        System.out.println("Lesson Id");
        lessonDao.delete(scanner.nextInt());
    }

    private LocalDate addDate(Scanner scanner) {
        System.out.println("Year");
        int year = scanner.nextInt();
        System.out.println("Month");
        int month = scanner.nextInt();
        System.out.println("Day");
        int day = scanner.nextInt();
        return LocalDate.of(year, month, day);
    }
}