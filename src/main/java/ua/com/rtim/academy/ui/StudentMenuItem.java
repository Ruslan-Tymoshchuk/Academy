package ua.com.rtim.academy.ui;

import static java.time.LocalDate.of;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import ua.com.rtim.academy.domain.Address;
import ua.com.rtim.academy.domain.Gender;
import ua.com.rtim.academy.domain.Group;
import ua.com.rtim.academy.domain.Student;
import ua.com.rtim.academy.spring.dao.AddressDao;
import ua.com.rtim.academy.spring.dao.GroupDao;
import ua.com.rtim.academy.spring.dao.StudentDao;

public class StudentMenuItem {

    private final StudentDao studentDao;
    private final AddressDao addressDao;
    private final GroupDao groupDao;

    public StudentMenuItem(StudentDao studentDao, AddressDao addressDao, GroupDao groupDao, Scanner scanner) {
        System.out.println(
                "Student: a: Find All, b: Create, c: Update, d: Update Address, e: Set Group For the student, f: Delete");
        this.studentDao = studentDao;
        this.addressDao = addressDao;
        this.groupDao = groupDao;
        switch (scanner.next()) {
        case "a":
            findAllStudents();
            break;
        case "b":
            createStudent(scanner);
            break;
        case "c":
            updateStudent(scanner);
            break;
        case "d":
            updateStudentAddress(scanner);
            break;
        case "e":
            addStudentToGroup(scanner);
            break;
        case "f":
            deleteStudent(scanner);
            break;
        default:
            break;
        }
    }

    public void findAllStudents() {
        List<Student> students = studentDao.findAll();
        students.forEach(
                student -> System.out.println(String.format("%s %s", student.getFirstName(), student.getLastName())));
    }

    public void createStudent(Scanner scanner) {
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
        Address address = addressMenuItem.addAddress(addressDao, scanner);
        student.setAddress(address);
        studentDao.create(student);
    }

    public void updateStudent(Scanner scanner) {
        System.out.println("Student id");
        Student student = studentDao.getById(scanner.nextInt()).get();
        System.out.println("First name");
        student.setFirstName(scanner.next());
        System.out.println("Last name");
        student.setLastName(scanner.next());
        System.out.println("Phone number");
        student.setPhone(scanner.next());
        System.out.println("Mail");
        student.setEmail(scanner.next());
        studentDao.update(student);
    }

    public void updateStudentAddress(Scanner scanner) {
        System.out.println("Student id");
        Student student = studentDao.getById(scanner.nextInt()).get();
        System.out.println("Address");
        Address address = student.getAddress();
        AddressMenuItem addressMenuItem = new AddressMenuItem();
        addressMenuItem.updateAddress(addressDao, address, scanner);
        student.setAddress(address);
    }

    public void addStudentToGroup(Scanner scanner) {
        System.out.println("Student id");
        Student student = studentDao.getById(scanner.nextInt()).get();
        System.out.println("Group id");
        Group group = groupDao.getById(scanner.nextInt()).get();
        student.setGroup(group);
        studentDao.addToGroup(group, student);
    }

    public void deleteStudent(Scanner scanner) {
        System.out.println("Student id");
        studentDao.delete(scanner.nextInt());
    }
}