package com.example.app;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.app.models.DailyEveryXHours;
import com.example.app.models.DailyXTimesADay;
import com.example.app.models.EveryXDays;
import com.example.app.models.Frequency;
import com.example.app.models.SpecificDaysOfWeek;
import com.example.app.models.Task;
import com.example.app.models.TaskActivity;
import com.example.app.models.TaskMeasurement;
import com.example.app.models.TaskMedication;
import com.example.app.models.TaskSymptomCheck;
import com.example.app.models.Treatment;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class App extends Application {

    public static List<Treatment> listTreatment;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
        listTreatment = new ArrayList<Treatment>();
        testCreateMedicationTreatment();
        testCreateActivityTreatment();
        testCreateActivityMeasurement();
        testCreateActivitySymptomCheck();

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void testCreateActivitySymptomCheck() {
        TreeMap<LocalTime, TaskSymptomCheck> dailyTasks = new TreeMap<>();

        ArrayList<DayOfWeek> daysOfWeek = new ArrayList<>();
        daysOfWeek.add(DayOfWeek.MONDAY);
        daysOfWeek.add(DayOfWeek.SATURDAY);
        daysOfWeek.add(DayOfWeek.THURSDAY);

        SpecificDaysOfWeek frq = new SpecificDaysOfWeek(daysOfWeek);

        LocalTime fistTime = LocalTime.of(9, 15);
        LocalTime secondTime = LocalTime.of(22, 15);


        dailyTasks.put(fistTime, new TaskSymptomCheck(fistTime, "SymptomCheck"));
        dailyTasks.put(secondTime, new TaskSymptomCheck(secondTime, "SymptomCheck"));

        LocalDate startDate = LocalDate.of(2021, 5, 1);
        LocalDate endDate = LocalDate.of(2021, 5, 24);
        Treatment<TaskSymptomCheck> treatment = new Treatment<TaskSymptomCheck>(frq, "notes", startDate, endDate, dailyTasks, TaskSymptomCheck.class);

        App.listTreatment.add(treatment);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void testCreateActivityMeasurement() {
        TreeMap<LocalTime, TaskMeasurement> dailyTasks = new TreeMap<>();
        int interval = 5;
        LocalDate startDate = LocalDate.of(2021, 5, 1);

        EveryXDays frq = new EveryXDays(5, startDate);

        LocalTime fistTime = LocalTime.of(9, 15);
        LocalTime secondTime = LocalTime.of(22, 15);


        dailyTasks.put(fistTime, new TaskMeasurement(fistTime,"Heart Rate"));
        dailyTasks.put(secondTime, new TaskMeasurement(secondTime,"Heart Rate"));


        LocalDate endDate = LocalDate.of(2021, 5, 24);
        Treatment<TaskMeasurement> treatment = new Treatment<TaskMeasurement>(frq, "notes", startDate, endDate, dailyTasks, TaskMeasurement.class);

        App.listTreatment.add(treatment);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void testCreateActivityTreatment() {
        TreeMap<LocalTime, TaskActivity> dailyTasks = new TreeMap<>();
        DailyXTimesADay frq = new DailyXTimesADay();

        LocalTime fistTime = LocalTime.of(7, 30);
        LocalTime secondTime = LocalTime.of(20, 30);


        dailyTasks.put(fistTime, new TaskActivity(fistTime, ">,<"));
        dailyTasks.put(secondTime, new TaskActivity(secondTime, ">,<"));


        LocalDate startDate = LocalDate.of(2021, 5, 1);
        LocalDate endDate = LocalDate.of(2021, 5, 24);
        Treatment<TaskActivity> treatment = new Treatment<TaskActivity>(frq, "notes", startDate, endDate, dailyTasks, TaskActivity.class);

        App.listTreatment.add(treatment);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void testCreateMedicationTreatment() {
        TreeMap<LocalTime, TaskMedication> dailyTasks = new TreeMap<>();
        int interval = 10;
        DailyEveryXHours frq = new DailyEveryXHours(interval);
        LocalTime startTime = LocalTime.of(8, 30);
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
        //Log.d("Test", treatment.toString());

        //TaskMedication medTask = treatment.getDailyTaskByDate(LocalDate.of(2021, 5, 2)).get(LocalTime.of(12, 30));
        //medTask.setState(Task.State.DONE);
        //Log.d("Test", treatment.getDailyTaskByDate(LocalDate.of(2021, 5, 2)).toString());
        App.listTreatment.add(treatment);
    }
}