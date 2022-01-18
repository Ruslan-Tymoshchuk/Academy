package ua.com.rtim.academy.ui;

import java.util.List;
import java.util.Scanner;

import ua.com.rtim.academy.domain.Academy;
import ua.com.rtim.academy.domain.Audience;

public class AudienceMenuItem {

    public AudienceMenuItem(Academy academy, Scanner scanner) {
        System.out.println("Audience: a: Find All, b: Create c: Update, d: Delete");
        switch (scanner.next()) {
        case "a":
            findAllAudiences(academy);
            break;
        case "b":
            createAudience(academy, scanner);
            break;
        case "c":
            updateAudience(academy, scanner);
            break;
        case "d":
            deleteAudience(academy, scanner);
            break;
        default:
            break;
        }
    }

    public void findAllAudiences(Academy academy) {
        List<Audience> audiences = academy.getAllAudiences();
        audiences.forEach(audience -> System.out.println(audience.getNumber()));
    }

    private void createAudience(Academy academy, Scanner scanner) {
        Audience audience = new Audience();
        System.out.println("Number");
        audience.setNumber(scanner.nextInt());
        System.out.println("Capacity");
        audience.setCapacity(scanner.nextInt());
        academy.addAudience(audience);
    }

    private void updateAudience(Academy academy, Scanner scanner) {
        System.out.println("Aduence id");
        Audience audience = academy.getAudienceById(scanner.nextInt());
        System.out.println("Number");
        audience.setNumber(scanner.nextInt());
        System.out.println("Capacity");
        audience.setCapacity(scanner.nextInt());
    }

    private void deleteAudience(Academy academy, Scanner scanner) {
        System.out.println("Aduence id");
        academy.deleteAudienceById(scanner.nextInt());
    }
}