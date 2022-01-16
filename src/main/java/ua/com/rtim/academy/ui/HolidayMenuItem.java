package ua.com.rtim.academy.ui;

import static java.lang.Integer.parseInt;

import java.time.LocalDate;
import java.util.Scanner;

import ua.com.rtim.academy.domain.Academy;
import ua.com.rtim.academy.domain.Holiday;

public class HolidayMenuItem {

    public HolidayMenuItem(Academy academy, Scanner scanner) {
        System.out.println("Holiday: a: Create, b: Update, c: Delete");
        switch (scanner.nextLine()) {
        case "a":
            createHoliday(academy, scanner);
            break;
        case "b":
            updateHoliday(academy, scanner);
            break;
        case "c":
            deleteHoliday(academy, scanner);
            break;
        default:
            break;
        }
    }

    private void createHoliday(Academy academy, Scanner scanner) {
        Holiday holiday = new Holiday();
        System.out.println("Name");
        holiday.setName(scanner.nextLine());
        System.out.println("Date:");
        System.out.println("Year");
        int year = parseInt(scanner.nextLine());
        System.out.println("Month");
        int month = parseInt(scanner.nextLine());
        System.out.println("Day");
        int day = parseInt(scanner.nextLine());
        LocalDate date = LocalDate.of(year, month, day);
        holiday.setDate(date);
        academy.addHoliday(holiday);
    }

    private void updateHoliday(Academy academy, Scanner scanner) {
        Holiday holiday = academy.getHolidayById(parseInt(scanner.nextLine()));
        System.out.println("Name");
        holiday.setName(scanner.nextLine());
        System.out.println("Date");
        int year = parseInt(scanner.nextLine());
        System.out.println("Month");
        int month = parseInt(scanner.nextLine());
        System.out.println("Day");
        int day = parseInt(scanner.nextLine());
        LocalDate date = LocalDate.of(year, month, day);
        holiday.setDate(date);
    }

    public void deleteHoliday(Academy academy, Scanner scanner) {
        System.out.println("Holiday id");
        academy.deleteHolidayById(parseInt(scanner.nextLine()));
    }
}