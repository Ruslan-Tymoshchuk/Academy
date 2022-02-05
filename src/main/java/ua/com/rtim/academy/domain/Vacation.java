package ua.com.rtim.academy.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Vacation {

    private Integer id;
    private LocalDate startDate;
    private LocalDate endDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(endDate, id, startDate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vacation other = (Vacation) obj;
        return Objects.equals(endDate, other.endDate) && Objects.equals(id, other.id)
                && Objects.equals(startDate, other.startDate);
    }
}