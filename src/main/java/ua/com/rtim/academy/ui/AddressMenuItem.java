package ua.com.rtim.academy.ui;

import static java.lang.Integer.parseInt;

import java.util.Scanner;

import ua.com.rtim.academy.domain.Address;

public class AddressMenuItem {

	public Address addAddress(Scanner scanner) {
		Address address = new Address();
		System.out.println("Country");
		address.setCountry(scanner.nextLine());
		System.out.println("Region");
		address.setRegion(scanner.nextLine());
		System.out.println("City");
		address.setCity(scanner.nextLine());
		System.out.println("Street");
		address.setStreet(scanner.nextLine());
		System.out.println("House number");
		address.setHouseNumber(parseInt(scanner.nextLine()));
		System.out.println("Index");
		address.setIndex(parseInt(scanner.nextLine()));
		return address;
	}

	public void updateAddress(Address address, Scanner scanner) {
		System.out.println("Address: a: Country, b: Region, c: City, d: Street, e: House number, f: Index");
		switch (scanner.nextLine()) {
		case "a":
			address.setCountry(scanner.nextLine());
			break;
		case "b":
			address.setRegion(scanner.nextLine());
			break;
		case "c":
			address.setCity(scanner.nextLine());
			break;
		case "d":
			address.setStreet(scanner.nextLine());
			break;
		case "e":
			address.setHouseNumber(parseInt(scanner.nextLine()));
			break;
		case "f":
			address.setIndex(parseInt(scanner.nextLine()));
			break;
		default:
			break;
		}
	}
}