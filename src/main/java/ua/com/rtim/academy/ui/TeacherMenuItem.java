package ua.com.rtim.academy.ui;

import static java.lang.Integer.parseInt;
import static java.time.LocalDate.of;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ua.com.rtim.academy.domain.Academy;
import ua.com.rtim.academy.domain.Address;
import ua.com.rtim.academy.domain.Teacher;
import ua.com.rtim.academy.domain.Vacation;

public class TeacherMenuItem {

	public TeacherMenuItem(Academy academy, Scanner scanner) {
		System.out.println("Teacher: a: Create, b: Add Vacation, c: Update, d: Update Address, e: Delete");
		switch (scanner.nextLine()) {
		case "a":
			createTeacher(academy, scanner);
			break;
		case "b":
			addTeacherVacation(academy, scanner);
			break;
		case "c":
			updateTeacher(academy, scanner);
			break;
		case "d":
			updateTeacherAddress(academy, scanner);
			break;
		case "e":
			deleteTeacher(academy, scanner);
			break;
		default:
			break;
		}
	}

	public void createTeacher(Academy academy, Scanner scanner) {
		Teacher teacher = new Teacher();
		System.out.println("First name");
		teacher.setFirstName(scanner.nextLine());
		System.out.println("Last name");
		teacher.setLastName(scanner.nextLine());
		System.out.println("Gender");
		teacher.setGender(scanner.nextLine());
		System.out.println("Birth:");
		teacher.setBirthDate(addDate(scanner));
		System.out.println("Phone number");
		teacher.setPhone(parseInt(scanner.nextLine()));
		System.out.println("Mail");
		teacher.setEmail(scanner.nextLine());
		AddressMenuItem addressMenuItem = new AddressMenuItem();
		System.out.println("Address");
		teacher.setAddress(addressMenuItem.addAddress(scanner));
		System.out.println("Academic degree");
		teacher.setAcademicDegree(scanner.nextLine());
		academy.addTeacher(teacher);
	}

	public void addTeacherVacation(Academy academy, Scanner scanner) {
		System.out.println("Teacher id");
		Teacher teacher = academy.getTeacherById(parseInt(scanner.nextLine()));
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
		Teacher teacher = academy.getTeacherById(parseInt(scanner.nextLine()));
		System.out.println("First name");
		teacher.setFirstName(scanner.nextLine());
		System.out.println("Last name");
		teacher.setLastName(scanner.nextLine());
		System.out.println("Phone number");
		teacher.setPhone(parseInt(scanner.nextLine()));
		System.out.println("Mail");
		teacher.setEmail(scanner.nextLine());
		System.out.println("Academic degree");
		teacher.setAcademicDegree(scanner.nextLine());
	}

	public void updateTeacherAddress(Academy academy, Scanner scanner) {
		System.out.println("Teacher id");
		Teacher teacher = academy.getTeacherById(parseInt(scanner.nextLine()));
		System.out.println("Address");
		Address address = teacher.getAddress();
		AddressMenuItem addressMenuItem = new AddressMenuItem();
		addressMenuItem.updateAddress(address, scanner);
		teacher.setAddress(address);
	}

	public void deleteTeacher(Academy academy, Scanner scanner) {
		System.out.println("Teacher id");
		academy.deleteTeacherById(parseInt(scanner.nextLine()));
	}

	private LocalDate addDate(Scanner scanner) {
		System.out.println("Year");
		int year = parseInt(scanner.nextLine());
		System.out.println("Month");
		int month = parseInt(scanner.nextLine());
		System.out.println("Day");
		int day = parseInt(scanner.nextLine());
		return of(year, month, day);
	}
}