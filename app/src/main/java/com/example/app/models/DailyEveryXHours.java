package com.example.app.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DailyEveryXHours extends Frequency {
    private int hourInterval;

    public DailyEveryXHours(int hourInterval) {
        assert(hourInterval <= 24);
        this.hourInterval = hourInterval;
    }

    public int getHourInterval() {
        return hourInterval;
    }

    @Override
    public boolean checkByDay(LocalDate date) {
        return true;
    }

    @Override
    public String toString() {
        return "Every " + hourInterval + " hours";
    }
}
