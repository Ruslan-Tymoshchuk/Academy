package ua.com.rtim.academy.ui;

import static java.time.LocalDate.of;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ua.com.rtim.academy.dao.AddressDao;
import ua.com.rtim.academy.dao.CourseDao;
import ua.com.rtim.academy.dao.TeacherDao;
import ua.com.rtim.academy.domain.AcademicDegree;
import ua.com.rtim.academy.domain.Address;
import ua.com.rtim.academy.domain.Course;
import ua.com.rtim.academy.domain.Gender;
import ua.com.rtim.academy.domain.Teacher;

public class TeacherMenuItem {

    private final TeacherDao teacherDao;
    private final AddressDao addressDao;
    private final CourseDao courseDao;

    public TeacherMenuItem(TeacherDao teacherDao, AddressDao addressDao, CourseDao courseDao,
            Scanner scanner) {
        System.out.println("Teacher: a: Find All, b: Create, c: Update, d: Update Address, e: Delete");
        this.teacherDao = teacherDao;
        this.addressDao = addressDao;
        this.courseDao = courseDao;
        switch (scanner.next()) {
        case "a":
            findAllTeachers();
            break;
        case "b":
            createTeacher(scanner);
            break;
        case "c":
            updateTeacher(scanner);
            break;
        case "d":
            updateTeacherAddress(scanner);
            break;
        case "e":
            deleteTeacher(scanner);
            break;
        default:
            break;
        }
    }

    public void findAllTeachers() {
        List<Teacher> teachers = teacherDao.findAll();
        teachers.forEach(
                teacher -> System.out.println(String.format("%s %s", teacher.getFirstName(), teacher.getLastName())));
    }

    public void createTeacher(Scanner scanner) {
        Teacher teacher = new Teacher();
        System.out.println("First name");
        teacher.setFirstName(scanner.next());
        System.out.println("Last name");
        teacher.setLastName(scanner.next());
        System.out.println("Gender: Male, Female");
        Gender gender = Gender.valueOf(scanner.next().toUpperCase());
        teacher.setGender(gender);
        System.out.println("Birth:");
        teacher.setBirthDate(addDate(scanner));
        System.out.println("Phone number");
        teacher.setPhone(scanner.next());
        System.out.println("Mail");
        teacher.setEmail(scanner.next());
        AddressMenuItem addressMenuItem = new AddressMenuItem();
        System.out.println("Address:");
        teacher.setAddress(addressMenuItem.addAddress(addressDao, scanner));
        System.out.println("Academic degree: Bachelor, Master, Doctoral");
        AcademicDegree academicDegree = AcademicDegree.valueOf(scanner.next().toUpperCase());
        teacher.setAcademicDegree(academicDegree);
        System.out.println("Select courses id's");
        List<Course> courses = new ArrayList<>();
        while (scanner.hasNextInt()) {
            Course course = courseDao.getById(scanner.nextInt());
            courses.add(course);
        }
        teacher.setCourses(courses);
        teacherDao.create(teacher);
    }

    public void updateTeacher(Scanner scanner) {
        System.out.println("Teacher id");
        Teacher teacher = teacherDao.getById(scanner.nextInt());
        System.out.println("First name");
        teacher.setFirstName(scanner.next());
        System.out.println("Last name");
        teacher.setLastName(scanner.next());
        System.out.println("Phone number");
        teacher.setPhone(scanner.next());
        System.out.println("Mail");
        teacher.setEmail(scanner.next());
        System.out.println("Academic degree: Bachelor, Master, Doctoral");
        AcademicDegree academicDegree = AcademicDegree.valueOf(scanner.next().toUpperCase());
        teacher.setAcademicDegree(academicDegree);
        System.out.println("Select courses id's");
        List<Course> courses = new ArrayList<>();
        while (scanner.hasNextInt()) {
            Course course = courseDao.getById(scanner.nextInt());
            courses.add(course);
        }
        teacher.setCourses(courses);
        teacherDao.update(teacher);
    }

    public void updateTeacherAddress(Scanner scanner) {
        System.out.println("Teacher id");
        Teacher teacher = teacherDao.getById(scanner.nextInt());
        System.out.println("Address");
        Address address = teacher.getAddress();
        AddressMenuItem addressMenuItem = new AddressMenuItem();
        addressMenuItem.updateAddress(addressDao, address, scanner);
        addressDao.update(address);
    }

    public void deleteTeacher(Scanner scanner) {
        System.out.println("Teacher id");
        teacherDao.delete(scanner.nextInt());
    }

    private LocalDate addDate(Scanner scanner) {
        System.out.println("Year");
        int year = scanner.nextInt();
        System.out.println("Month");
        int month = scanner.nextInt();
        System.out.println("Day");
        int day = scanner.nextInt();
        return of(year, month, day);
    }
}