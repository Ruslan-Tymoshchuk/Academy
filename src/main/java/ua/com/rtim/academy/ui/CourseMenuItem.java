package ua.com.rtim.academy.ui;

import static java.lang.Integer.parseInt;

import java.util.Scanner;

import ua.com.rtim.academy.domain.Academy;
import ua.com.rtim.academy.domain.Course;

public class CourseMenuItem {

    public CourseMenuItem(Academy academy, Scanner scanner) {
        System.out.println("Course: a: Create, b: Update, c: Delete");
        switch (scanner.nextLine()) {
        case "a":
            createCourse(academy, scanner);
            break;
        case "b":
            updateCourse(academy, scanner);
            break;
        case "c":
            deleteCourse(academy, scanner);
            break;
        default:
            break;
        }
    }

    private void createCourse(Academy academy, Scanner scanner) {
        Course course = new Course();
        System.out.println("Name");
        course.setName(scanner.nextLine());
        System.out.println("Description");
        course.setDescription(scanner.nextLine());
        academy.addCourse(course);
    }

    private void updateCourse(Academy academy, Scanner scanner) {
        System.out.println("Course id");
        Course course = academy.getCourseById(parseInt(scanner.nextLine()));
        System.out.println("Name");
        course.setName(scanner.nextLine());
        System.out.println("Description");
        course.setDescription(scanner.nextLine());
    }

    private void deleteCourse(Academy academy, Scanner scanner) {
        System.out.println("Course id");
        academy.deleteCourseById(parseInt(scanner.nextLine()));
    }
}