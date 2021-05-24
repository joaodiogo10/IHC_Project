package com.example.app.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app.App;
import com.example.app.R;
import com.example.app.classesAna.MedicationTask;
import com.example.app.models.Task;
import com.example.app.adapters.TasksRecViewAdapter;
import com.example.app.models.TaskActivity;
import com.example.app.models.TaskMeasurement;
import com.example.app.models.TaskMedication;
import com.example.app.models.TaskSymptomCheck;
import com.example.app.models.Treatment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class Today extends Fragment {

    private View view;
    private RecyclerView tasksRecView;
    private TasksRecViewAdapter adapter;
    private ArrayList<com.example.app.classesAna.Task> todayTasksShow = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(R.string.nav_today);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_today, container, false);

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tasksRecView = view.findViewById(R.id.tasksTodayRecyclerView);
        setTodayTasksRecycler();

        super.onViewCreated(view, savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setTodayTasksRecycler() {
        Map<LocalTime, Task> todayTasks;

        List<Treatment> treatments = App.listTreatment;

        for (Treatment treatment :
                treatments) {
            todayTasks = treatment.getDailyTaskByDate(LocalDate.now());
            if (todayTasks != null) {
                treatmentHandlerPicker(treatment, todayTasks);
            }
        }

        adapter = new TasksRecViewAdapter(view.getContext());
        adapter.setTasks(todayTasksShow);
        tasksRecView.setAdapter(adapter);
        tasksRecView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void treatmentHandlerPicker(Treatment treatment, Map<LocalTime, Task> dailyTasks) {
        if (treatment.getType().equals(TaskMedication.class)) {
            handleMedicationTask(dailyTasks, treatment.getName());
        } else if (treatment.getType().equals(TaskActivity.class)) {
            handleActivityTask(dailyTasks, treatment.getName());
        } else if (treatment.getType().equals(TaskMeasurement.class)) {
            handleMeasurementTask(dailyTasks, treatment.getName());
        } else {
            handleSymptomCheckTask(dailyTasks, treatment.getName());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void handleSymptomCheckTask(Map<LocalTime, Task> dailyTasks, String treatmentName) {

        for (LocalTime time :
                dailyTasks.keySet()) {

            TaskSymptomCheck task = (TaskSymptomCheck) dailyTasks.get(time);
            todayTasksShow.add(new com.example.app.classesAna.Task("Symptom Check", treatmentName, LocalDate.now().toString(), time.toString()));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void handleMeasurementTask(Map<LocalTime, Task> dailyTasks, String treatmentName) {

        for (LocalTime time :
                dailyTasks.keySet()) {

            TaskMeasurement task = (TaskMeasurement) dailyTasks.get(time);
            todayTasksShow.add(new com.example.app.classesAna.Task("Measurement", treatmentName, LocalDate.now().toString(), time.toString()));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void handleActivityTask(Map<LocalTime, Task> dailyTasks, String treatmentName) {

        for (LocalTime time :
                dailyTasks.keySet()) {

            TaskActivity task = (TaskActivity) dailyTasks.get(time);
            todayTasksShow.add(new com.example.app.classesAna.Task("Activity", treatmentName, LocalDate.now().toString(), time.toString()));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void handleMedicationTask(Map<LocalTime, Task> dailyTasks, String treatmentName) {

        for (LocalTime time :
                dailyTasks.keySet()) {

            TaskMedication task = (TaskMedication) dailyTasks.get(time);
            todayTasksShow.add(new MedicationTask("Medication",treatmentName, LocalDate.now().toString(), time.toString(), task.getPillName(), task.getDose()));
        }
    }
}