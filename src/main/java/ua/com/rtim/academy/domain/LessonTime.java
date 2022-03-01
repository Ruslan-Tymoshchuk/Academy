package ua.com.rtim.academy.domain;

import java.time.LocalTime;
import java.util.Objects;

public class LessonTime {

    private Integer id;
    private LocalTime startTime;
    private LocalTime endTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(endTime, id, startTime);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LessonTime other = (LessonTime) obj;
        return Objects.equals(endTime, other.endTime) && Objects.equals(id, other.id)
                && Objects.equals(startTime, other.startTime);
    }
}