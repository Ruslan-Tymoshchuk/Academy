package ua.com.rtim.academy.ui;

import static java.lang.System.lineSeparator;

import java.util.Scanner;

import ua.com.rtim.academy.domain.Academy;

public class Main {

    public static void main(String[] args) {
        Academy academy = new Academy("NURE", "14 Nauky Ave., Kharkiv, Kharkiv region, 61000", "+380577021013");
        System.out.println("a: Audience" + lineSeparator() + "b: Course" + lineSeparator() + "c: Group"
                + lineSeparator() + "d: Holiday" + lineSeparator() + "e: Lesson" + lineSeparator() + "f: Student"
                + lineSeparator() + "g: Teacher");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            switch (scanner.nextLine()) {
            case "a":
                new AudienceMenuItem(academy, scanner);
                break;
            case "b":
                new CourseMenuItem(academy, scanner);
                break;
            case "c":
                new GroupMenuItem(academy, scanner);
                break;
            case "d":
                new HolidayMenuItem(academy, scanner);
                break;
            case "e":
                new LessonMenuItem(academy, scanner);
                break;
            case "f":
                new StudentMenuItem(academy, scanner);
                break;
            case "g":
                new TeacherMenuItem(academy, scanner);
                break;
            default:
                break;
            }
        }
        scanner.close();
    }
}