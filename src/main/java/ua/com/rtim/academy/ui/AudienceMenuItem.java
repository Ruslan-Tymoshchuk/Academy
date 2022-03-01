package ua.com.rtim.academy.ui;

import java.util.List;
import java.util.Scanner;

import ua.com.rtim.academy.dao.AudienceDao;
import ua.com.rtim.academy.domain.Audience;

public class AudienceMenuItem {

    private final AudienceDao audienceDao;

    public AudienceMenuItem(AudienceDao audienceDao, Scanner scanner) {
        System.out.println("Audience: a: Find All, b: Create c: Update, d: Delete");
        this.audienceDao = audienceDao;
        switch (scanner.next()) {
        case "a":
            findAllAudiences();
            break;
        case "b":
            createAudience(scanner);
            break;
        case "c":
            updateAudience(scanner);
            break;
        case "d":
            deleteAudience(scanner);
            break;
        default:
            break;
        }
    }

    public void findAllAudiences() {
        List<Audience> audiences = audienceDao.findAll();
        audiences.forEach(audience -> System.out.println(audience.getNumber()));
    }

    private void createAudience(Scanner scanner) {
        Audience audience = new Audience();
        System.out.println("Number");
        audience.setNumber(scanner.nextInt());
        System.out.println("Capacity");
        audience.setCapacity(scanner.nextInt());
        audienceDao.create(audience);
    }

    private void updateAudience(Scanner scanner) {
        System.out.println("Aduence id");
        Audience audience = audienceDao.getById(scanner.nextInt());
        System.out.println("Number");
        audience.setNumber(scanner.nextInt());
        System.out.println("Capacity");
        audience.setCapacity(scanner.nextInt());
        audienceDao.update(audience);
    }

    private void deleteAudience(Scanner scanner) {
        System.out.println("Audience id");
        audienceDao.delete(scanner.nextInt());
    }
}