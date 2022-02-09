package ua.com.rtim.academy.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ua.com.rtim.academy.domain.Group;
import ua.com.rtim.academy.domain.Lesson;
import ua.com.rtim.academy.spring.dao.GroupDao;
import ua.com.rtim.academy.spring.dao.LessonDao;

public class GroupMenuItem {

    private final GroupDao groupDao;
    private final LessonDao lessonDao;

    public GroupMenuItem(GroupDao groupDao, LessonDao lessonDao, Scanner scanner) {
        System.out.println("Group: a: Find All, b: Create, c: Update, d: Delete");
        this.groupDao = groupDao;
        this.lessonDao = lessonDao;
        switch (scanner.next()) {
        case "a":
            findAllGroups();
            break;
        case "b":
            createGroup(scanner);
            break;
        case "c":
            updateGroup(scanner);
            break;
        case "d":
            deleteGroup(scanner);
            break;
        default:
            break;
        }
    }

    public void findAllGroups() {
        List<Group> groups = groupDao.findAll();
        groups.forEach(group -> System.out.println(group.getName()));
    }

    private void createGroup(Scanner scanner) {
        Group group = new Group();
        System.out.println("Name");
        group.setName(scanner.next());
        System.out.println("Select lessons id's");
        List<Lesson> lessons = new ArrayList<>();
        while (scanner.hasNextInt()) {
            Lesson lesson = lessonDao.getById(scanner.nextInt());
        lessons.add(lesson);
        }
        group.setLessons(lessons);
        groupDao.create(group);
    }

    private void updateGroup(Scanner scanner) {
        System.out.println("Group id");
        Group group = groupDao.getById(scanner.nextInt());
        System.out.println("Name");
        group.setName(scanner.next());
        System.out.println("Select lessons id's");
        List<Lesson> lessons = new ArrayList<>();
        while (scanner.hasNextInt()) {
            Lesson lesson = lessonDao.getById(scanner.nextInt());
            lessons.add(lesson);
        }
        group.setLessons(lessons);
        groupDao.update(group);
    }

    private void deleteGroup(Scanner scanner) {
        System.out.println("Group id");
        groupDao.delete(scanner.nextInt());
    }
}