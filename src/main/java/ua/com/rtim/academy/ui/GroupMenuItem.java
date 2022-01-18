package ua.com.rtim.academy.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ua.com.rtim.academy.domain.Academy;
import ua.com.rtim.academy.domain.Group;
import ua.com.rtim.academy.domain.Student;

public class GroupMenuItem {

    public GroupMenuItem(Academy academy, Scanner scanner) {
        System.out.println("Group: a: Find All, b: Create, c: Update, d: Add students to group, e: Delete");
        switch (scanner.next()) {
        case "a":
            findAllGroups(academy);
            break;
        case "b":
            createGroup(academy, scanner);
            break;
        case "c":
            updateGroup(academy, scanner);
            break;
        case "d":
            addStudentsToGroup(academy, scanner);
            break;
        case "e":
            deleteGroup(academy, scanner);
            break;
        default:
            break;
        }
    }

    public void findAllGroups(Academy academy) {
        List<Group> groups = academy.getAllGroups();
        groups.forEach(group -> System.out.println(group.getName()));
    }

    private void createGroup(Academy academy, Scanner scanner) {
        Group group = new Group();
        System.out.println("Name");
        group.setName(scanner.next());
        academy.addGroup(group);
    }

    private void updateGroup(Academy academy, Scanner scanner) {
        System.out.println("Group id");
        Group group = academy.getGroupById(scanner.nextInt());
        System.out.println("Name");
        group.setName(scanner.next());
    }

    private void addStudentsToGroup(Academy academy, Scanner scanner) {
        List<Student> students = new ArrayList<>();
        System.out.println("How many students?");
        int maxStudents = scanner.nextInt();
        while (students.size() < maxStudents) {
            System.out.println("Student id");
            Student student = academy.getStudentById(scanner.nextInt());
            students.add(student);
        }
        System.out.println("Group id");
        Group group = academy.getGroupById(scanner.nextInt());
        group.setStudents(students);
    }

    private void deleteGroup(Academy academy, Scanner scanner) {
        System.out.println("Group id");
        academy.deleteGroupById(scanner.nextInt());
    }
}