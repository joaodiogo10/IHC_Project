package com.example.app.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class EveryXDays extends Frequency{
    private int dayInterval;
    private LocalDate start;


    public EveryXDays(int dayInterval, LocalDate start) {
        this.dayInterval = dayInterval;
        this.start = start;
    }

    public int getDayInterval() {
        return dayInterval;
    }

    public LocalDate getStartDate() {
        return start;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean checkByDay(LocalDate date) {
        assert(date.compareTo(start) >= 0);
        long nDays =  DAYS.between(start, date);
        return nDays % dayInterval == 0;
    }

    @Override
    public String toString() {
        return "Every " + dayInterval + " days";
    }
}
