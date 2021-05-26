package com.example.app.fragments;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class ScheduleLists extends Fragment {

    RecyclerView dailyTasksRecycler;
    ScheduleListDaysAdapter listDaysAdapter;
    LinearLayoutManager dailyTasksLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule_lists, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        dailyTasksRecycler = view.findViewById(R.id.rvDaysList);
        setListDayRecycler();

        super.onViewCreated(view, savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setListDayRecycler() {
        Map<LocalDate, ArrayList<Task>> map = new TreeMap<>();
        Map<LocalDate, ArrayList<Task>> dailyTasks = Collections.synchronizedMap(map);

        List<Treatment> treatments = App.listTreatment;

        for (int i = 0; i < treatments.size(); i++) {
            treatmentHandlerPicker(treatments.get(i), dailyTasks, i);
        }

        ArrayList<LocalDate> dates = new ArrayList(Arrays.asList(dailyTasks.keySet().toArray()));
        listDaysAdapter = new ScheduleListDaysAdapter(getContext(), dates, dailyTasks);
        dailyTasksLayoutManager = new LinearLayoutManager(getContext());
        dailyTasksLayoutManager.setReverseLayout(true);
        dailyTasksLayoutManager.setStackFromEnd(true);
        dailyTasksRecycler.setLayoutManager(dailyTasksLayoutManager);
        dailyTasksRecycler.setAdapter(listDaysAdapter);

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

        //process Today's tasks
        Map<LocalTime, TaskSymptomCheck> todayTasks = tasks.get(today);

        if(todayTasks != null)
        {
            for (LocalTime time :
                    todayTasks.keySet()) {

                //only display today tasks DONE
                if (todayTasks.get(time).getState() == com.example.app.models.Task.State.DONE) {
                    if (!dailyTasks.containsKey(today)) dailyTasks.put(today, new ArrayList<>());
                    TaskSymptomCheck task = todayTasks.get(time);
                    dailyTasks.get(today).add(new Task("Symptom Check", treatment.getName(), today.toString(), time.toString(), treatmentIdx));
                }
            }
        }

        //process remaining tasks until today
        for (LocalDate date :
                tasks.keySet()) {

            if (date.compareTo(today) >= 0) break;

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

        //process Today's tasks
        Map<LocalTime, TaskMeasurement> todayTasks = tasks.get(today);

        if(todayTasks != null) {
            for (LocalTime time :
                    todayTasks.keySet()) {

                //only display today tasks DONE
                if (todayTasks.get(time).getState() == com.example.app.models.Task.State.DONE) {
                    if (!dailyTasks.containsKey(today)) dailyTasks.put(today, new ArrayList<>());
                    TaskMeasurement task = todayTasks.get(time);
                    dailyTasks.get(today).add(new Task("Measurement", treatment.getName(), today.toString(), time.toString(), treatmentIdx));
                }
            }
        }

        for (LocalDate date :
                tasks.keySet()) {

            if (date.compareTo(today) >= 0) break;

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

        //process Today's tasks
        Map<LocalTime, TaskActivity> todayTasks = tasks.get(today);

        if(todayTasks != null) {
            for (LocalTime time :
                    todayTasks.keySet()) {

                //only display today tasks DONE
                if (todayTasks.get(time).getState() == com.example.app.models.Task.State.DONE) {
                    if (!dailyTasks.containsKey(today)) dailyTasks.put(today, new ArrayList<>());
                    TaskActivity task = todayTasks.get(time);
                    dailyTasks.get(today).add(new Task("Activity", treatment.getName(), today.toString(), time.toString(), treatmentIdx));
                }
            }
        }

        for (LocalDate date :
                tasks.keySet()) {

            if (date.compareTo(today) >= 0) break;

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

        //process Today's tasks
        Map<LocalTime, TaskMedication> todayTasks = tasks.get(today);
        if(todayTasks != null) {
            for (LocalTime time :
                    todayTasks.keySet()) {

                //only display today tasks DONE
                if (todayTasks.get(time).getState() == com.example.app.models.Task.State.DONE) {
                    if (!dailyTasks.containsKey(today)) dailyTasks.put(today, new ArrayList<>());
                    TaskMedication task = todayTasks.get(time);
                    dailyTasks.get(today).add(new MedicationTask("Medication", treatment.getName(), today.toString(), time.toString(), task.getPillName(), task.getDose(), treatmentIdx));
                }
            }
        }

        for (LocalDate date :
                tasks.keySet()) {

            if (date.compareTo(today) >= 0) break;

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