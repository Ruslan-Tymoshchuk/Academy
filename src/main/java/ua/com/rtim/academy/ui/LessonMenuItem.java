package ua.com.rtim.academy.ui;

import static java.time.LocalTime.of;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ua.com.rtim.academy.domain.Academy;
import ua.com.rtim.academy.domain.Audience;
import ua.com.rtim.academy.domain.Course;
import ua.com.rtim.academy.domain.Group;
import ua.com.rtim.academy.domain.Lesson;
import ua.com.rtim.academy.domain.LessonTime;
import ua.com.rtim.academy.domain.Teacher;

public class LessonMenuItem {

    private List<LessonTime> lessonTimes = new ArrayList<>();

    public LessonMenuItem(Academy academy, Scanner scanner) {
        System.out.println(
                "Lesson: a: Find All, b: Create, c: Create Lesson Time, d: Update, e: Add time to Lesson, f: Add groups, g: Delete");
        switch (scanner.next()) {
        case "a":
            findAllLessons(academy);
            break;
        case "b":
            createLesson(academy, scanner);
            break;
        case "c":
            createLessonTime(scanner);
            break;
        case "d":
            updateLesson(academy, scanner);
            break;
        case "e":
            addTimeToLesson(academy, scanner);
            break;
        case "f":
            addGroupsToLesson(academy, scanner);
            break;
        case "g":
            deleteLesson(academy, scanner);
            break;
        default:
            break;
        }
    }

    public void findAllLessons(Academy academy) {
        List<Lesson> lessons = academy.getAllLessons();
        lessons.forEach(lesson -> System.out.println(lesson.getTime()));
    }

    private void createLesson(Academy academy, Scanner scanner) {
        Lesson lesson = new Lesson();
        System.out.println("Teacher id");
        lesson.setTeacher(academy.getTeacherById(scanner.nextInt()));
        System.out.println("Course");
        lesson.setCourse(academy.getCourseById(scanner.nextInt()));
        System.out.println("Audience");
        Audience audience = academy.getAudienceById(scanner.nextInt());
        lesson.setAudience(audience);
        academy.addLesson(lesson);
    }

    private void updateLesson(Academy academy, Scanner scanner) {
        System.out.println("Leeson id");
        Lesson lesson = academy.getLessonById(scanner.nextInt());
        System.out.println("Teacher");
        Teacher teacher = academy.getTeacherById(scanner.nextInt());
        lesson.setTeacher(teacher);
        System.out.println("Course");
        Course course = academy.getCourseById(scanner.nextInt());
        lesson.setCourse(course);
        System.out.println("Audience");
        Audience audience = academy.getAudienceById(scanner.nextInt());
        lesson.setAudience(audience);
        System.out.println("Date");
        int year = scanner.nextInt();
        System.out.println("Month");
        int month = scanner.nextInt();
        System.out.println("Day");
        int day = scanner.nextInt();
        LocalDate date = LocalDate.of(year, month, day);
        lesson.setDate(date);
    }

    public void createLessonTime(Scanner scanner) {
        LessonTime lessonTime = new LessonTime();
        System.out.println("Start time");
        lessonTime.setStartTime(of(scanner.nextInt(), scanner.nextInt()));
        System.out.println("End time");
        lessonTime.setEndTime(of(scanner.nextInt(), scanner.nextInt()));
        lessonTimes.add(lessonTime);
    }

    private void addTimeToLesson(Academy academy, Scanner scanner) {
        System.out.println("Lesson id");
        Lesson lesson = academy.getLessonById(scanner.nextInt());
        System.out.println("Lesson time id");
        LessonTime lessonTime = lessonTimes.get(scanner.nextInt());
        lesson.setTime(lessonTime);
    }

    private void addGroupsToLesson(Academy academy, Scanner scanner) {
        System.out.println("Leeson id");
        Lesson lesson = academy.getLessonById(scanner.nextInt());
        List<Group> groups = new ArrayList<>();
        System.out.println("Group id");
        Group group = academy.getGroupById(scanner.nextInt());
        groups.add(group);
        lesson.setGroups(groups);
    }

    private void deleteLesson(Academy academy, Scanner scanner) {
        System.out.println("Lesson Id");
        academy.deleteLessonById(scanner.nextInt());
    }
}