package ua.com.rtim.academy.ui;

import static java.lang.Integer.parseInt;

import java.util.Scanner;

import ua.com.rtim.academy.domain.Academy;
import ua.com.rtim.academy.domain.Audience;

public class AudienceMenuItem {

    public AudienceMenuItem(Academy academy, Scanner scanner) {
        System.out.println("Audience: a: Create b: Update, c: Delete");
        switch (scanner.nextLine()) {
        case "a":
            createAudience(academy, scanner);
            break;
        case "b":
            updateAudience(academy, scanner);
            break;
        case "c":
            deleteAudience(academy, scanner);
            break;
        default:
            break;
        }
    }

    private void createAudience(Academy academy, Scanner scanner) {
        Audience audience = new Audience();
        System.out.println("Number");
        audience.setNumber(parseInt(scanner.nextLine()));
        System.out.println("Capacity");
        audience.setCapacity(parseInt(scanner.nextLine()));
        academy.addAudience(audience);
    }

    private void updateAudience(Academy academy, Scanner scanner) {
        System.out.println("Aduence id");
        Audience audience = academy.getAudienceById(parseInt(scanner.nextLine()));
        System.out.println("Number");
        audience.setNumber(parseInt(scanner.nextLine()));
        System.out.println("Capacity");
        audience.setCapacity(parseInt(scanner.nextLine()));
    }

    private void deleteAudience(Academy academy, Scanner scanner) {
        System.out.println("Aduence id");
        academy.deleteAudienceById(parseInt(scanner.nextLine()));
    }
}