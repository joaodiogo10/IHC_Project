package com.example.app;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.app.models.DailyEveryXHours;
import com.example.app.models.Frequency;
import com.example.app.models.Task;
import com.example.app.models.TaskMedication;
import com.example.app.models.Treatment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.TreeMap;

public class TreatmentsTest {

    public void test() {
        TreeMap<LocalTime, TaskMedication> dailyTasks = new TreeMap<>();
        int interval = 2;
        DailyEveryXHours frq = new DailyEveryXHours(interval);

        LocalTime startTime = LocalTime.of(12, 30);
        LocalTime endTime = LocalTime.of(18, 30);

        List<LocalTime> tasksHours = Frequency.getHoursWithinTime(startTime, endTime, interval);
        for (LocalTime hour :
                tasksHours) {
            TaskMedication task = new TaskMedication(hour, 2, "Xarope");
            dailyTasks.put(hour, task);
        }

        LocalDate startDate = LocalDate.of(2021, 5, 1);
        LocalDate endDate = LocalDate.of(2021, 5, 24);
        Treatment<TaskMedication> treatment = new Treatment<TaskMedication>(frq, "notes", startDate, endDate, dailyTasks, TaskMedication.class);
        Log.d("Test", treatment.toString());

        TaskMedication medTask = treatment.getDailyTaskByDate(LocalDate.of(2021, 5, 2)).get(LocalTime.of(12, 30));
        medTask.setState(Task.State.DONE);
        Log.d("Test", treatment.getDailyTaskByDate(LocalDate.of(2021, 5, 2)).toString());
        App.listTreatment.add(treatment);
        boolean instance = treatment.getType().equals(TaskMedication.class);
        Log.d("Test", instance ? "true" : "false");
    }

}
