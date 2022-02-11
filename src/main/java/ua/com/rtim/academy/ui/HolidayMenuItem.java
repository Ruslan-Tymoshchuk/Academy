package ua.com.rtim.academy.ui;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import ua.com.rtim.academy.dao.HolidayDao;
import ua.com.rtim.academy.domain.Holiday;

public class HolidayMenuItem {

    private final HolidayDao holidayDao;

    public HolidayMenuItem(HolidayDao holidayDao, Scanner scanner) {
        System.out.println("Holiday: a: Find All, b: Create, c: Update, d: Delete");
        this.holidayDao = holidayDao;
        switch (scanner.next()) {
        case "a":
            findAllHolidays();
            break;
        case "b":
            createHoliday(scanner);
            break;
        case "c":
            updateHoliday(scanner);
            break;
        case "d":
            deleteHoliday(scanner);
            break;
        default:
            break;
        }
    }

    public void findAllHolidays() {
        List<Holiday> holidays = holidayDao.findAll();
        holidays.forEach(holiday -> System.out.println(holiday.getName()));
    }

    private void createHoliday(Scanner scanner) {
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
        holidayDao.create(holiday);
    }

    private void updateHoliday(Scanner scanner) {
        System.out.println("Holiday id");
        Holiday holiday = holidayDao.getById(scanner.nextInt());
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
        holidayDao.update(holiday);
    }

    public void deleteHoliday(Scanner scanner) {
        System.out.println("Holiday id");
        holidayDao.delete(scanner.nextInt());
    }
}