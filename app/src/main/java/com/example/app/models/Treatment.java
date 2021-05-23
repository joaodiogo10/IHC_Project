package com.example.app.models;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.lang.reflect.ParameterizedType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Treatment<T extends Task> {
    private String name;
    private Frequency frequency;
    private String notes;
    private LocalDate startDate;
    private LocalDate endDate;
    private SortedMap<LocalDate, Map<LocalTime, T>> tasks;
    private SortedMap<LocalTime, T> dailyTasks;
    private Class<T> type;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Treatment(String name, Frequency frequency, String notes, LocalDate startDate, LocalDate endData, TreeMap<LocalTime, T> dailyTasks, Class<T> type) {
        assert (LocalDate.now().compareTo(startDate) >= 0);
        this.name = name;
        this.frequency = frequency;
        this.notes = notes;
        this.startDate = startDate;
        this.endDate = endData;
        this.dailyTasks = Collections.synchronizedSortedMap(dailyTasks);
        this.tasks = Collections.synchronizedSortedMap(new TreeMap<LocalDate, Map<LocalTime, T>>());
        this.type = type;
        initTasks();
    }

    /*
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addTask(T task) {
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
    */

    public Class<T> getType() {
        return this.type;
    }

    public Map<LocalDate, Map<LocalTime, T>> getTasks() {
        return tasks;
    }

    public Map<LocalTime, T> getDailyTaskByDate(LocalDate date) {
        return tasks.get(date);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public T getTaskByDateTime(LocalDate date, LocalTime time) {
        return tasks.get(date).get(time);
    }

    public String getName() {
        return name;
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

    public List<LocalTime> getDailyTasksHours() {
        return new ArrayList<LocalTime>(dailyTasks.keySet());
    }

    //verify if at given day there is any task
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean checkByDay(LocalDate date) {
        if (date.compareTo(startDate) < 0 || endDate.compareTo(endDate) > 0) {
            return false;
        }
        return frequency.checkByDay(date);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public String toString() {
        String strDailyTasks = "";
        List<LocalTime> dailyTasksKeys = dailyTasks.keySet().stream().sorted().collect(Collectors.toList());
        for (LocalTime time :
                dailyTasksKeys) {
            strDailyTasks += dailyTasks.get(time).toString() + "\n";
        }

        String strTasks = "";
        List<LocalDate> dates = tasks.keySet().stream().sorted().collect(Collectors.toList());
        for (LocalDate date :
                dates) {
            strTasks += "Date: " + date.toString() + "; " + tasks.get(date).toString() + "\n";
        }
        return "\nTreatment{" +
                "\nfrequency=" + frequency +
                ",\n notes='" + notes + '\'' +
                ",\n startDate=" + startDate +
                ",\n endDate=" + endDate +
                ",\n dailyTasks=\n" + strDailyTasks +
                ",\n tasks=\n" + strTasks+'}';
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initTasks() {
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
}
