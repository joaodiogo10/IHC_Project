package com.example.app.models;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.app.interfaces.Frequency;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

public class TreatmentMedication extends Treatment{
    private String medicationName;
    private Map<LocalDate,Map<LocalTime,TaskMedication>> tasks;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public TreatmentMedication(Frequency frequency, String medicationName, String notes, LocalDate startDate, LocalDate endDate, Hashtable<LocalTime,TaskMedication> dailyTasks) {
        super(frequency, notes, startDate, endDate);
        this.medicationName = medicationName;
        this.tasks = new Hashtable<>();
        Log.d("Test", dailyTasks.toString());

        fillTasks(dailyTasks);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void fillTasks(Hashtable<LocalTime,TaskMedication> dailyTasks) {
        Frequency frq = super.getFrequency();
        LocalDate tmp = super.getStartDate();
        int i = 0;

        while( tmp.compareTo(super.getEndDate()) <= 0 ) {
            if(frq.checkByDay(tmp)) {
                Iterator<LocalTime> times = dailyTasks.keySet().iterator();
                tasks.put(tmp, new Hashtable<>());
                while(times.hasNext()) {
                    LocalTime time = times.next();
                    tasks.get(tmp).put(time,
                            new TaskMedication(tmp, time, Task.State.PENDING, dailyTasks.get(time).getDose()));
                }
            }
            i++;
            tmp = super.getStartDate().plusDays(i);
        }
    }

    public TaskMedication getTasksByDateTime(LocalDate date, LocalTime time) {
        return tasks.get(date).get(time);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String TaskToString() {
        String res = "";
        for(LocalDate ld : new TreeSet<>(tasks.keySet())) {
            res = res.concat(ld.toString() + ": ");
            res = res.concat(tasks.get(ld).toString() + ": \n");
            res = res.concat("\n");
        }
        return res;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public String toString() {
        return "Medication{" +
                "medicationName='" + medicationName + '\'' +
                super.toString() +  "Taks:" + TaskToString() + '}';
    }
}


