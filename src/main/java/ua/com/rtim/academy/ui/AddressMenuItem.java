package ua.com.rtim.academy.ui;

import java.util.Scanner;

import ua.com.rtim.academy.dao.AddressDao;
import ua.com.rtim.academy.domain.Address;

public class AddressMenuItem {

    public Address addAddress(AddressDao addressDao, Scanner scanner) {
        Address address = new Address();
        System.out.println("Country");
        address.setCountry(scanner.next());
        System.out.println("Region");
        address.setRegion(scanner.next());
        System.out.println("City");
        address.setCity(scanner.next());
        System.out.println("Street");
        address.setStreet(scanner.next());
        System.out.println("House number");
        address.setHouseNumber(scanner.next());
        System.out.println("Index");
        address.setPostalCode(scanner.next());
        return address;
    }

    public void updateAddress(AddressDao addressDao, Address address, Scanner scanner) {
        System.out.println("Address: a: Country, b: Region, c: City, d: Street, e: House number, f: Index");
        switch (scanner.next()) {
        case "a":
            address.setCountry(scanner.next());
            break;
        case "b":
            address.setRegion(scanner.next());
            break;
        case "c":
            address.setCity(scanner.next());
            break;
        case "d":
            address.setStreet(scanner.next());
            break;
        case "e":
            address.setHouseNumber(scanner.next());
            break;
        case "f":
            address.setPostalCode(scanner.next());
            break;
        default:
            break;
        }
        addressDao.update(address);
    }
}