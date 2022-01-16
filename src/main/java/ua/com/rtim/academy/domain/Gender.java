package ua.com.rtim.academy.domain;

public enum Gender {

    UNKNOWN("Unknown"), MALE("Male"), FEMALE("Female");

    private final String displayValue;

    private Gender(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}