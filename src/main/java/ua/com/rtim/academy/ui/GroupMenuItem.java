package ua.com.rtim.academy.ui;

import static java.lang.Integer.parseInt;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ua.com.rtim.academy.domain.Academy;
import ua.com.rtim.academy.domain.Group;
import ua.com.rtim.academy.domain.Student;

public class GroupMenuItem {

    public GroupMenuItem(Academy academy, Scanner scanner) {
        System.out.println("Group: a: Create, b: Update, c: Add students to group, d: Delete");
        switch (scanner.nextLine()) {
        case "a":
            createGroup(academy, scanner);
            break;
        case "b":
            updateGroup(academy, scanner);
            break;
        case "c":
            addStudentsToGroup(academy, scanner);
            break;
        case "d":
            deleteGroup(academy, scanner);
            break;
        default:
            break;
        }
    }

    private void createGroup(Academy academy, Scanner scanner) {
        Group group = new Group();
        System.out.println("Name");
        group.setName(scanner.nextLine());
        academy.addGroup(group);
    }

    private void updateGroup(Academy academy, Scanner scanner) {
        System.out.println("Group id");
        Group group = academy.getGroupById(parseInt(scanner.nextLine()));
        System.out.println("Name");
        group.setName(scanner.nextLine());
    }

    private void addStudentsToGroup(Academy academy, Scanner scanner) {
        List<Student> students = new ArrayList<>();
        System.out.println("How many students?");
        int maxStudents = parseInt(scanner.nextLine());
        while (students.size() < maxStudents) {
            System.out.println("Student id");
            Student student = academy.getStudentById(parseInt(scanner.nextLine()));
            students.add(student);
        }
        System.out.println("Group id");
        Group group = academy.getGroupById(parseInt(scanner.nextLine()));
        group.setStudents(students);
    }

    private void deleteGroup(Academy academy, Scanner scanner) {
        System.out.println("Group id");
        academy.deleteGroupById(parseInt(scanner.nextLine()));
    }
}