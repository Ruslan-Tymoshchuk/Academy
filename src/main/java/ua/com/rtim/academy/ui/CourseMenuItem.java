package ua.com.rtim.academy.ui;

import java.util.List;
import java.util.Scanner;

import ua.com.rtim.academy.domain.Academy;
import ua.com.rtim.academy.domain.Course;

public class CourseMenuItem {

    public CourseMenuItem(Academy academy, Scanner scanner) {
        System.out.println("Course: a: Find All, b: Create, c: Update, d: Delete");
        switch (scanner.next()) {
        case "a":
            findAllCourses(academy);
            break;
        case "b":
            createCourse(academy, scanner);
            break;
        case "c":
            updateCourse(academy, scanner);
            break;
        case "d":
            deleteCourse(academy, scanner);
            break;
        default:
            break;
        }
    }

    public void findAllCourses(Academy academy) {
        List<Course> courses = academy.getAllCourses();
        courses.forEach(course -> System.out.println(course.getName()));
    }

    private void createCourse(Academy academy, Scanner scanner) {
        Course course = new Course();
        System.out.println("Name");
        course.setName(scanner.next());
        System.out.println("Description");
        course.setDescription(scanner.next());
        academy.addCourse(course);
    }

    private void updateCourse(Academy academy, Scanner scanner) {
        System.out.println("Course id");
        Course course = academy.getCourseById(scanner.nextInt());
        System.out.println("Name");
        course.setName(scanner.next());
        System.out.println("Description");
        course.setDescription(scanner.next());
    }

    private void deleteCourse(Academy academy, Scanner scanner) {
        System.out.println("Course id");
        academy.deleteCourseById(scanner.nextInt());
    }
}