package ua.com.rtim.academy.ui;

import ua.com.rtim.academy.domain.Gender;

public class GenderMenuItem {

    public Gender chooseGender(String text) {
        switch (text) {
        case "MALE":
            return Gender.valueOf(text);
        case "FEMALE":
            return Gender.valueOf(text);
        default:
            return Gender.UNKNOWN;
        }
    }
}