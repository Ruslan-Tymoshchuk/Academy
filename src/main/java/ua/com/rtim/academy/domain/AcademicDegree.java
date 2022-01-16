package ua.com.rtim.academy.domain;

public enum AcademicDegree {

    UNKNOWN("Unknown"), BACHELOR("Bachelor"), MASTER("Master"), DOCTORAL("Doctoral");

    private final String displayValue;

    private AcademicDegree(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}