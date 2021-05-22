package com.example.app.models;

import java.time.LocalDate;

public class DailyEveryXHours extends Frequency {
    private int hours;

    public DailyEveryXHours(int hours) {
        assert(hours <= 24);
        this.hours = hours;
    }

    public int getHourInterval() {
        return hours;
    }

    @Override
    public boolean checkByDay(LocalDate date) {
        return true;
    }

    @Override
    public String toString() {
        return "Every " + hours + "hours";
    }
}
