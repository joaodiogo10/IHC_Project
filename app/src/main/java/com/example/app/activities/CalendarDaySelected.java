package com.example.app.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import com.example.app.App;
import com.example.app.R;
import com.example.app.adapters.ScheduleListDaysAdapter;
import com.example.app.classesAna.MedicationTask;
import com.example.app.classesAna.Task;
import com.example.app.models.TaskActivity;
import com.example.app.models.TaskMeasurement;
import com.example.app.models.TaskMedication;
import com.example.app.models.TaskSymptomCheck;
import com.example.app.models.Treatment;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CalendarDaySelected extends AppCompatActivity {

    RecyclerView tasksRecycler;
    ScheduleListDaysAdapter listDaysAdapter;
    LinearLayoutManager dailyTasksLayoutManager;
    LocalDate date;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_day_selected);
        date = (LocalDate) this.getIntent().getSerializableExtra("date");
        String title = date.getDayOfMonth() + " " + date.getMonth().toString().substring(0, 1).toUpperCase() + date.getMonth().toString().substring(1).toLowerCase();
        setTitle(title);
        tasksRecycler = findViewById(R.id.tasks);
        setListDayRecycler();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setListDayRecycler() {
        Map<LocalDate, ArrayList<Task>> map = new TreeMap<>();
        Map<LocalDate, ArrayList<Task>> dailyTasks = Collections.synchronizedMap(map);

        List<Treatment> treatments = App.listTreatment;

        for (int i = 0; i < treatments.size(); i++) {
            treatmentHandlerPicker(treatments.get(i), dailyTasks, i);
        }
        ArrayList<LocalDate> tasks = new ArrayList<>();
        tasks.add(date);
        listDaysAdapter = new ScheduleListDaysAdapter(this, tasks, dailyTasks);
        dailyTasksLayoutManager = new LinearLayoutManager(this);
        dailyTasksLayoutManager.setReverseLayout(true);
        dailyTasksLayoutManager.setStackFromEnd(true);
        tasksRecycler.setLayoutManager(dailyTasksLayoutManager);
        tasksRecycler.setAdapter(listDaysAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void treatmentHandlerPicker(Treatment treatment, Map<LocalDate, ArrayList<Task>> dailyTasks, int treatmentIdx) {
        if (treatment.getType().equals(TaskMedication.class)) {
            handleMedicationTask(treatment, dailyTasks, treatmentIdx);
        } else if (treatment.getType().equals(TaskActivity.class)) {
            handleActivityTask(treatment, dailyTasks, treatmentIdx);
        } else if (treatment.getType().equals(TaskMeasurement.class)) {
            handleMeasurementTask(treatment, dailyTasks, treatmentIdx);
        } else {
            handleSymptomCheckTask(treatment, dailyTasks, treatmentIdx);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void handleSymptomCheckTask(Treatment treatment, Map<LocalDate, ArrayList<Task>> dailyTasks, int treatmentIdx) {
        LocalDate today = LocalDate.now();

        Map<LocalDate, Map<LocalTime, TaskSymptomCheck>> tasks = treatment.getTasks();

        for (LocalDate date :
                tasks.keySet()) {

            if (!dailyTasks.containsKey(date))
                dailyTasks.put(date, new ArrayList<>());

            for (LocalTime time :
                    tasks.get(date).keySet()) {

                TaskSymptomCheck task = tasks.get(date).get(time);
                dailyTasks.get(date).add(new Task("Symptom Check", treatment.getName(), date.toString(), time.toString(), treatmentIdx));
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void handleMeasurementTask(Treatment treatment, Map<LocalDate, ArrayList<Task>> dailyTasks, int treatmentIdx) {
        LocalDate today = LocalDate.now();

        Map<LocalDate, Map<LocalTime, TaskMeasurement>> tasks = treatment.getTasks();

        for (LocalDate date :
                tasks.keySet()) {

            if (!dailyTasks.containsKey(date))
                dailyTasks.put(date, new ArrayList<>());

            for (LocalTime time :
                    tasks.get(date).keySet()) {
                TaskMeasurement task = tasks.get(date).get(time);
                dailyTasks.get(date).add(new Task("Measurement", treatment.getName(), date.toString(), time.toString(), treatmentIdx));

            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void handleActivityTask(Treatment treatment, Map<LocalDate, ArrayList<Task>> dailyTasks, int treatmentIdx) {
        LocalDate today = LocalDate.now();

        Map<LocalDate, Map<LocalTime, TaskActivity>> tasks = treatment.getTasks();

        for (LocalDate date :
                tasks.keySet()) {

            if (!dailyTasks.containsKey(date))
                dailyTasks.put(date, new ArrayList<>());

            for (LocalTime time :
                    tasks.get(date).keySet()) {
                TaskActivity task = tasks.get(date).get(time);
                dailyTasks.get(date).add(new Task("Activity", treatment.getName(), date.toString(), time.toString(), treatmentIdx));
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void handleMedicationTask(Treatment treatment, Map<LocalDate, ArrayList<Task>> dailyTasks, int treatmentIdx) {
        LocalDate today = LocalDate.now();

        Map<LocalDate, Map<LocalTime, TaskMedication>> tasks = treatment.getTasks();

        for (LocalDate date :
                tasks.keySet()) {

            if (!dailyTasks.containsKey(date))
                dailyTasks.put(date, new ArrayList<>());

            for (LocalTime time :
                    tasks.get(date).keySet()) {
                TaskMedication task = tasks.get(date).get(time);
                dailyTasks.get(date).add(new MedicationTask("Medication", treatment.getName(), date.toString(), time.toString(), task.getPillName(), task.getDose(), treatmentIdx));
            }
        }
    }

}