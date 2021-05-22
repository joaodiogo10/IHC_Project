package com.example.app.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static java.time.format.TextStyle.FULL;

public class SpecificDaysOfWeek extends Frequency{
    private List<DayOfWeek> daysOfWeek;

    public SpecificDaysOfWeek(ArrayList<DayOfWeek> daysOfWeek ) {
        this.daysOfWeek = daysOfWeek;
    }

    public List<DayOfWeek> getDaysOfWeek() {
        return daysOfWeek;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean checkByDay(LocalDate date) {
        boolean ret = false;
        for (DayOfWeek day:
             daysOfWeek) {
            if(date.getDayOfWeek() == day)
                ret = true;
        }
        return ret;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public String toString() {
        String str = "";
        for (DayOfWeek day:
             daysOfWeek) {
                str = str.concat(day.getDisplayName(FULL, Locale.UK));
        }
        return str;
    }
}
