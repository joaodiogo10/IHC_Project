package com.example.app.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.app.interfaces.Frequency;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;


public abstract class Treatment {
    private Frequency frequency;
    private String notes;
    private LocalDate startDate;
    private LocalDate endDate;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Treatment(Frequency frequency, String notes, LocalDate startDate, LocalDate endData) {
        assert(LocalDate.now().compareTo(startDate) >= 0);
        this.frequency = frequency;
        this.notes = notes;
        this.startDate = startDate;
        this.endDate = endData;
    }

    //verify if at given day there is any task
    @RequiresApi(api = Build.VERSION_CODES.O)
    public final boolean checkByDay(LocalDate date) {
        if(date.compareTo(startDate) < 0 || endDate.compareTo(endDate) > 0) {
            return false;
        }
        return frequency.checkByDay(date);
    }

    public final LocalDate getStartDate() {
        return startDate;
    }

    public final LocalDate getEndDate() {
        return endDate;
    }

    public final Frequency getFrequency() {
        return frequency;
    }

    public final String getNotes() {
        return notes;
    }


    @Override
    public String toString() {
        return "Treatment{" +
                "frequency=" + frequency +
                ", notes='" + notes + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }


    public static class DailyXTimesADay implements Frequency{
        public boolean checkByDay(LocalDate date) {
            return true;
        }
        @Override
        public String toString() {
            return "DailyXTimesADay";
        }
    }
}
