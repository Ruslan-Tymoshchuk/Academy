package ua.com.rtim.academy.ui;

import static java.time.LocalDate.of;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import ua.com.rtim.academy.dao.TeacherDao;
import ua.com.rtim.academy.dao.VacationDao;
import ua.com.rtim.academy.domain.Teacher;
import ua.com.rtim.academy.domain.Vacation;

public class VacationMenuItem {

    private final VacationDao vacationDao;
    private final TeacherDao teacherDao;

    public VacationMenuItem(VacationDao vacationDao, TeacherDao teacherDao, Scanner scanner) {
        System.out.println("Vacation: a: Find All, b: Create c: Update, d: Get by Date, e: Delete");
        this.vacationDao = vacationDao;
        this.teacherDao = teacherDao;
        switch (scanner.next()) {
        case "a":
            findAllVacations();
            break;
        case "b":
            createVacation(scanner);
            break;
        case "c":
            updateVacation(scanner);
            break;
        case "d":
            getVacationsByDateInterval(scanner);
            break;
        case "e":
            deleteVacation(scanner);
            break;
        default:
            break;
        }
    }

    public void findAllVacations() {
        List<Vacation> vacations = vacationDao.findAll();
        vacations.forEach(
                vacation -> System.out.println(String.format("%s %s", vacation.getStartDate(), vacation.getEndDate())));
    }

    private void createVacation(Scanner scanner) {
        System.out.println("Teacher id");
        Vacation vacation = new Vacation();
        Teacher teacher = teacherDao.getById(scanner.nextInt());
        vacation.setTeacher(teacher);
        System.out.println("Start date");
        vacation.setStartDate(addDate(scanner));
        System.out.println("End date");
        vacation.setEndDate(addDate(scanner));
        vacationDao.create(vacation);
    }

    private void updateVacation(Scanner scanner) {
        System.out.println("Vacation id");
        Vacation vacation = vacationDao.getById(scanner.nextInt());
        System.out.println("Teacher id");
        Teacher teacher = teacherDao.getById(scanner.nextInt());
        vacation.setTeacher(teacher);
        System.out.println("Start date");
        vacation.setStartDate(addDate(scanner));
        System.out.println("End date");
        vacation.setEndDate(addDate(scanner));
        vacationDao.update(vacation);
    }

    private void getVacationsByDateInterval(Scanner scanner) {
        System.out.println("Start date");
        LocalDate startDate = addDate(scanner);
        System.out.println("End date");
        LocalDate endDate = addDate(scanner);
        List<Vacation> vacations = vacationDao.getVacationsByDateInterval(startDate, endDate);
        vacations.forEach(
                vacation -> System.out.println(String.format("%s %s", vacation.getStartDate(), vacation.getEndDate())));
    }

    private void deleteVacation(Scanner scanner) {
        System.out.println("Vacation id");
        vacationDao.delete(scanner.nextInt());
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