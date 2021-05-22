package com.example.app.models;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

public class DailyXTimesADay extends Frequency {

    public DailyXTimesADay() {
    }
    @Override
    public boolean checkByDay(LocalDate date) {
        return true;
    }

    @NotNull
    @Override
    public String toString() {
        return "Daily";
    }
}
