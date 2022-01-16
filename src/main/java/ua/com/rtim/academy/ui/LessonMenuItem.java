package ua.com.rtim.academy.ui;

import static java.lang.Integer.parseInt;
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
                "Lesson: a: Create, b: Create Lesson Time, c: Update, d: Add time to Lesson, e: Add groups, f: Delete");
        switch (scanner.nextLine()) {
        case "a":
            createLesson(academy, scanner);
            break;
        case "b":
            createLessonTime(scanner);
            break;
        case "c":
            updateLesson(academy, scanner);
            break;
        case "d":
            addTimeToLesson(academy, scanner);
            break;
        case "e":
            addGroupsToLesson(academy, scanner);
            break;
        case "f":
            deleteLesson(academy, scanner);
            break;
        default:
            break;
        }
    }

    private void createLesson(Academy academy, Scanner scanner) {
        Lesson lesson = new Lesson();
        System.out.println("Teacher id");
        lesson.setTeacher(academy.getTeacherById(parseInt(scanner.nextLine())));
        System.out.println("Course");
        lesson.setCourse(academy.getCourseById(parseInt(scanner.nextLine())));
        System.out.println("Audience");
        Audience audience = academy.getAudienceById(parseInt(scanner.nextLine()));
        lesson.setAudience(audience);
        academy.addLesson(lesson);
    }

    private void updateLesson(Academy academy, Scanner scanner) {
        System.out.println("Leeson id");
        Lesson lesson = academy.getLessonById(parseInt(scanner.nextLine()));
        System.out.println("Teacher");
        Teacher teacher = academy.getTeacherById(parseInt(scanner.nextLine()));
        lesson.setTeacher(teacher);
        System.out.println("Course");
        Course course = academy.getCourseById(parseInt(scanner.nextLine()));
        lesson.setCourse(course);
        System.out.println("Audience");
        Audience audience = academy.getAudienceById(parseInt(scanner.nextLine()));
        lesson.setAudience(audience);
        System.out.println("Date");
        int year = parseInt(scanner.nextLine());
        System.out.println("Month");
        int month = parseInt(scanner.nextLine());
        System.out.println("Day");
        int day = parseInt(scanner.nextLine());
        LocalDate date = LocalDate.of(year, month, day);
        lesson.setDate(date);
    }

    public void createLessonTime(Scanner scanner) {
        LessonTime lessonTime = new LessonTime();
        System.out.println("Start time");
        lessonTime.setStartTime(of(parseInt(scanner.nextLine()), parseInt(scanner.nextLine())));
        System.out.println("End time");
        lessonTime.setEndTime(of(parseInt(scanner.nextLine()), parseInt(scanner.nextLine())));
        lessonTimes.add(lessonTime);
    }

    private void addTimeToLesson(Academy academy, Scanner scanner) {
        System.out.println("Lesson id");
        Lesson lesson = academy.getLessonById(parseInt(scanner.nextLine()));
        System.out.println("Lesson time id");
        LessonTime lessonTime = lessonTimes.get(parseInt(scanner.nextLine()));
        lesson.setTime(lessonTime);
    }

    private void addGroupsToLesson(Academy academy, Scanner scanner) {
        System.out.println("Leeson id");
        Lesson lesson = academy.getLessonById(parseInt(scanner.nextLine()));
        List<Group> groups = new ArrayList<>();
        System.out.println("Group id");
        Group group = academy.getGroupById(parseInt(scanner.nextLine()));
        groups.add(group);
        lesson.setGroups(groups);
    }

    private void deleteLesson(Academy academy, Scanner scanner) {
        System.out.println("Lesson Id");
        academy.deleteLessonById(parseInt(scanner.nextLine()));
    }
}