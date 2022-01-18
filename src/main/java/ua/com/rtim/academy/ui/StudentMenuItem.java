package ua.com.rtim.academy.ui;

import static java.time.LocalDate.of;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import ua.com.rtim.academy.domain.Academy;
import ua.com.rtim.academy.domain.Address;
import ua.com.rtim.academy.domain.Gender;
import ua.com.rtim.academy.domain.Group;
import ua.com.rtim.academy.domain.Student;

public class StudentMenuItem {

    public StudentMenuItem(Academy academy, Scanner scanner) {
        System.out.println(
                "Student: a: Find All, b: Create, c: Update, d: Update Address, e: Set Group For the student, f: Delete");
        switch (scanner.next()) {
        case "a":
            findAllStudents(academy);
            break;
        case "b":
            createStudent(academy, scanner);
            break;
        case "c":
            updateStudent(academy, scanner);
            break;
        case "d":
            updateStudentAddress(academy, scanner);
            break;
        case "e":
            addStudentToGroup(academy, scanner);
            break;
        case "f":
            deleteStudent(academy, scanner);
            break;
        default:
            break;
        }
    }

    public void findAllStudents(Academy academy) {
        List<Student> students = academy.getAllStudents();
        students.forEach(
                student -> System.out.println(String.format("%s %s", student.getFirstName(), student.getLastName())));
    }

    public void createStudent(Academy academy, Scanner scanner) {
        Student student = new Student();
        System.out.println("First name");
        student.setFirstName(scanner.next());
        System.out.println("Last name");
        student.setLastName(scanner.next());
        System.out.println("Gender: Male, Female");
        Gender gender = Gender.valueOf(scanner.next().toUpperCase());
        student.setGender(gender);
        System.out.println("Birth:");
        System.out.println("Year");
        int year = scanner.nextInt();
        System.out.println("Month");
        int month = scanner.nextInt();
        System.out.println("Day");
        int day = scanner.nextInt();
        LocalDate date = of(year, month, day);
        student.setBirthDate(date);
        System.out.println("Phone number");
        student.setPhone(scanner.next());
        System.out.println("Mail");
        student.setEmail(scanner.next());
        System.out.println("Address:");
        AddressMenuItem addressMenuItem = new AddressMenuItem();
        Address address = addressMenuItem.addAddress(scanner);
        student.setAddress(address);
        academy.addStudent(student);
    }

    public void updateStudent(Academy academy, Scanner scanner) {
        System.out.println("Student id");
        Student student = academy.getStudentById(scanner.nextInt());
        System.out.println("First name");
        student.setFirstName(scanner.next());
        System.out.println("Last name");
        student.setLastName(scanner.next());
        System.out.println("Phone number");
        student.setPhone(scanner.next());
        System.out.println("Mail");
        student.setEmail(scanner.next());
    }

    public void updateStudentAddress(Academy academy, Scanner scanner) {
        System.out.println("Student id");
        Student student = academy.getStudentById(scanner.nextInt());
        System.out.println("Address");
        Address address = student.getAddress();
        AddressMenuItem addressMenuItem = new AddressMenuItem();
        addressMenuItem.updateAddress(address, scanner);
        student.setAddress(address);
    }

    public void addStudentToGroup(Academy academy, Scanner scanner) {
        System.out.println("Student id");
        Student student = academy.getStudentById(scanner.nextInt());
        System.out.println("Group id");
        Group group = academy.getGroupById(scanner.nextInt());
        student.setGroup(group);
    }

    public void deleteStudent(Academy academy, Scanner scanner) {
        System.out.println("Student id");
        academy.deleteStudentById(scanner.nextInt());
    }
}