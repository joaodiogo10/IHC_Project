package com.example.app.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Treatment<T extends Task> {
    private Frequency frequency;
    private String notes;
    private LocalDate startDate;
    private LocalDate endDate;
    private Map<LocalDate, Map<LocalTime, T>> tasks;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Treatment(Frequency frequency, String notes, LocalDate startDate, LocalDate endData) {
        assert (LocalDate.now().compareTo(startDate) >= 0);
        this.frequency = frequency;
        this.notes = notes;
        this.startDate = startDate;
        this.endDate = endData;
    }

    //verify if at given day there is any task
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean checkByDay(LocalDate date) {
        if (date.compareTo(startDate) < 0 || endDate.compareTo(endDate) > 0) {
            return false;
        }
        return frequency.checkByDay(date);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addTask(T task) {
        assert (checkByDay(task.getDate()));
        LocalDate date = task.getDate();
        LocalTime time = task.getTime();
        Map<LocalTime, T> dailyTasks = tasks.get(date);

        if (dailyTasks == null) {
            dailyTasks = new HashMap<>();
        }
        dailyTasks.put(time, task);
        tasks.put(date, dailyTasks);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public T getTasksByDateTime(LocalDate date, LocalTime time) {
        return tasks.get(date).get(time);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public String getNotes() {
        return notes;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void fillTasks(HashMap<LocalTime, T> dailyTasks) {
        Frequency frq = frequency;
        LocalDate tmp = startDate;
        int i = 0;

        while (tmp.compareTo(endDate) <= 0) {
            if (frq.checkByDay(tmp)) {
                tasks.put(tmp, dailyTasks);
            }
            i++;
            tmp = startDate.plusDays(i);
        }
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
}
