package ua.com.rtim.academy.ui;

import java.util.List;
import java.util.Scanner;

import ua.com.rtim.academy.domain.Course;
import ua.com.rtim.academy.spring.dao.CourseDao;

public class CourseMenuItem {

    private final CourseDao courseDao;

    public CourseMenuItem(CourseDao courseDao, Scanner scanner) {
        System.out.println("Course: a: Find All, b: Create, c: Update, d: Delete");
        this.courseDao = courseDao;
        switch (scanner.next()) {
        case "a":
            findAllCourses();
            break;
        case "b":
            createCourse(scanner);
            break;
        case "c":
            updateCourse(scanner);
            break;
        case "d":
            deleteCourse(scanner);
            break;
        default:
            break;
        }
    }

    public void findAllCourses() {
        List<Course> courses = courseDao.findAll();
        courses.forEach(course -> System.out.println(course.getName()));
    }

    private void createCourse(Scanner scanner) {
        Course course = new Course();
        System.out.println("Name");
        course.setName(scanner.next());
        System.out.println("Description");
        course.setDescription(scanner.next());
        courseDao.create(course);
    }

    private void updateCourse(Scanner scanner) {
        System.out.println("Course id");
        Course course = courseDao.getById(scanner.nextInt());
        System.out.println("Name");
        course.setName(scanner.next());
        System.out.println("Description");
        course.setDescription(scanner.next());
        courseDao.update(course);
    }

    private void deleteCourse(Scanner scanner) {
        System.out.println("Course id");
        courseDao.delete(scanner.nextInt());
    }
}