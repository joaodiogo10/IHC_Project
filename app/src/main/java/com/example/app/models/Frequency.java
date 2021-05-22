package com.example.app.models;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Frequency {
    public abstract boolean checkByDay(LocalDate date);

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static List<LocalTime> getHoursWithinTime(LocalTime startTime, LocalTime endTime, int interval) {
        List<LocalTime> hours = new ArrayList<>();
        hours.add(startTime);

        int i = 1;
        while(startTime.plusHours(interval*i).compareTo(endTime) < 0)
        {
            hours.add(startTime.plusHours(interval*i));
            i++;
        }
        hours.add(endTime);
        return hours;
    }

}
