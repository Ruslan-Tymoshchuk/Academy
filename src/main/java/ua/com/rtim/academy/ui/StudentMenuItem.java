package ua.com.rtim.academy.ui;

import static java.lang.Integer.parseInt;

import java.time.LocalDate;
import java.util.Scanner;

import ua.com.rtim.academy.domain.Academy;
import ua.com.rtim.academy.domain.Address;
import ua.com.rtim.academy.domain.Group;
import ua.com.rtim.academy.domain.Student;

public class StudentMenuItem {

	public StudentMenuItem(Academy academy, Scanner scanner) {
		System.out.println("Student: a: Create, b: Update, c: Update Address, d: Set Group For the student, e: Delete");
		switch (scanner.nextLine()) {
		case "a":
			createStudent(academy, scanner);
			break;
		case "b":
			updateStudent(academy, scanner);
			break;
		case "c":
			updateStudentAddress(academy, scanner);
			break;
		case "d":
			addStudentToGroup(academy, scanner);
			break;
		case "e":
			deleteStudent(academy, scanner);
			break;
		default:
			break;
		}
	}

	public void createStudent(Academy academy, Scanner scanner) {
		Student student = new Student();
		System.out.println("First name");
		student.setFirstName(scanner.nextLine());
		System.out.println("Last name");
		student.setLastName(scanner.nextLine());
		System.out.println("Gender");
		student.setGender(scanner.nextLine());
		System.out.println("Birth:");
		System.out.println("Year");
		int year = parseInt(scanner.nextLine());
		System.out.println("Month");
		int month = parseInt(scanner.nextLine());
		System.out.println("Day");
		int day = parseInt(scanner.nextLine());
		LocalDate date = LocalDate.of(year, month, day);
		student.setBirthDate(date);
		System.out.println("Phone number");
		student.setPhone(parseInt(scanner.nextLine()));
		System.out.println("Mail");
		student.setEmail(scanner.nextLine());
		System.out.println("Address:");
		AddressMenuItem addressMenuItem = new AddressMenuItem();
		Address address = addressMenuItem.addAddress(scanner);
		student.setAddress(address);
		academy.addStudent(student);
	}

	public void updateStudent(Academy academy, Scanner scanner) {
		System.out.println("Student id");
		Student student = academy.getStudentById(parseInt(scanner.nextLine()));
		System.out.println("First name");
		student.setFirstName(scanner.nextLine());
		System.out.println("Last name");
		student.setLastName(scanner.nextLine());
		System.out.println("Phone number");
		student.setPhone(parseInt(scanner.nextLine()));
		System.out.println("Mail");
		student.setEmail(scanner.nextLine());
	}

	public void updateStudentAddress(Academy academy, Scanner scanner) {
		System.out.println("Student id");
		Student student = academy.getStudentById(parseInt(scanner.nextLine()));
		System.out.println("Address");
		Address address = student.getAddress();
		AddressMenuItem addressMenuItem = new AddressMenuItem();
		addressMenuItem.updateAddress(address, scanner);
		student.setAddress(address);
	}

	public void addStudentToGroup(Academy academy, Scanner scanner) {
		System.out.println("Student id");
		Student student = academy.getStudentById(parseInt(scanner.nextLine()));
		System.out.println("Group id");
		Group group = academy.getGroupById(parseInt(scanner.nextLine()));
		student.setGroup(group);
	}

	public void deleteStudent(Academy academy, Scanner scanner) {
		System.out.println("Student id");
		academy.deleteStudentById(parseInt(scanner.nextLine()));
	}
}