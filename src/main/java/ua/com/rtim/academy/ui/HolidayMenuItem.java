package ua.com.rtim.academy.ui;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import ua.com.rtim.academy.domain.Academy;
import ua.com.rtim.academy.domain.Holiday;

public class HolidayMenuItem {

    public HolidayMenuItem(Academy academy, Scanner scanner) {
        System.out.println("Holiday: a: Find All, b: Create, c: Update, d: Delete");
        switch (scanner.next()) {
        case "a":
            findAllHolidays(academy);
            break;
        case "b":
            createHoliday(academy, scanner);
            break;
        case "c":
            updateHoliday(academy, scanner);
            break;
        case "d":
            deleteHoliday(academy, scanner);
            break;
        default:
            break;
        }
    }

    public void findAllHolidays(Academy academy) {
        List<Holiday> holidays = academy.getAllHolidays();
        holidays.forEach(holiday -> System.out.println(holiday.getName()));
    }

    private void createHoliday(Academy academy, Scanner scanner) {
        Holiday holiday = new Holiday();
        System.out.println("Name");
        holiday.setName(scanner.next());
        System.out.println("Date:");
        System.out.println("Year");
        int year = scanner.nextInt();
        System.out.println("Month");
        int month = scanner.nextInt();
        System.out.println("Day");
        int day = scanner.nextInt();
        LocalDate date = LocalDate.of(year, month, day);
        holiday.setDate(date);
        academy.addHoliday(holiday);
    }

    private void updateHoliday(Academy academy, Scanner scanner) {
        Holiday holiday = academy.getHolidayById(scanner.nextInt());
        System.out.println("Name");
        holiday.setName(scanner.next());
        System.out.println("Date");
        int year = scanner.nextInt();
        System.out.println("Month");
        int month = scanner.nextInt();
        System.out.println("Day");
        int day = scanner.nextInt();
        LocalDate date = LocalDate.of(year, month, day);
        holiday.setDate(date);
    }

    public void deleteHoliday(Academy academy, Scanner scanner) {
        System.out.println("Holiday id");
        academy.deleteHolidayById(scanner.nextInt());
    }
}