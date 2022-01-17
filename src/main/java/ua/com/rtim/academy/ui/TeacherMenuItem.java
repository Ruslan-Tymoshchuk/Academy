package ua.com.rtim.academy.ui;

import static java.time.LocalDate.of;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ua.com.rtim.academy.domain.AcademicDegree;
import ua.com.rtim.academy.domain.Academy;
import ua.com.rtim.academy.domain.Address;
import ua.com.rtim.academy.domain.Gender;
import ua.com.rtim.academy.domain.Teacher;
import ua.com.rtim.academy.domain.Vacation;

public class TeacherMenuItem {

    public TeacherMenuItem(Academy academy, Scanner scanner) {
        System.out.println("Teacher: a: Find All, b: Create, c: Add Vacation, d: Update, e: Update Address, f: Delete");
        switch (scanner.next()) {
        case "a":
            findAllStudents(academy);
            break;
        case "b":
            createTeacher(academy, scanner);
            break;
        case "c":
            addTeacherVacation(academy, scanner);
            break;
        case "d":
            updateTeacher(academy, scanner);
            break;
        case "e":
            updateTeacherAddress(academy, scanner);
            break;
        case "f":
            deleteTeacher(academy, scanner);
            break;
        default:
            break;
        }
    }

    public void findAllStudents(Academy academy) {
        List<Teacher> teachers = academy.getAllTeachers();
        teachers.forEach(
                teacher -> System.out.println(String.format("%s %s", teacher.getFirstName(), teacher.getLastName())));
    }

    public void createTeacher(Academy academy, Scanner scanner) {
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
        teacher.setAddress(addressMenuItem.addAddress(scanner));
        System.out.println("Academic degree: Bachelor, Master, Doctoral");
        AcademicDegree academicDegree = AcademicDegree.valueOf(scanner.next().toUpperCase());
        teacher.setAcademicDegree(academicDegree);
        academy.addTeacher(teacher);
    }

    public void addTeacherVacation(Academy academy, Scanner scanner) {
        System.out.println("Teacher id");
        Teacher teacher = academy.getTeacherById(scanner.nextInt());
        Vacation vacation = new Vacation();
        System.out.println("Start date");
        vacation.setStartDate(addDate(scanner));
        System.out.println("End date");
        vacation.setEndDate(addDate(scanner));
        List<Vacation> vacations = new ArrayList<>();
        vacations.add(vacation);
        teacher.setVacations(vacations);
    }

    public void updateTeacher(Academy academy, Scanner scanner) {
        System.out.println("Teacher id");
        Teacher teacher = academy.getTeacherById(scanner.nextInt());
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
    }

    public void updateTeacherAddress(Academy academy, Scanner scanner) {
        System.out.println("Teacher id");
        Teacher teacher = academy.getTeacherById(scanner.nextInt());
        System.out.println("Address");
        Address address = teacher.getAddress();
        AddressMenuItem addressMenuItem = new AddressMenuItem();
        addressMenuItem.updateAddress(address, scanner);
        teacher.setAddress(address);
    }

    public void deleteTeacher(Academy academy, Scanner scanner) {
        System.out.println("Teacher id");
        academy.deleteTeacherById(scanner.nextInt());
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