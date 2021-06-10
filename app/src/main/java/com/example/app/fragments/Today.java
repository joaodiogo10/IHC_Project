package com.example.app.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
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
import java.util.Collections;
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
    private ConstraintLayout emptyTasks;
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
        emptyTasks = view.findViewById(R.id.todayEmptyTasks);
        view.findViewById(R.id.floatingActionButton).setOnClickListener(
                v -> NavHostFragment.findNavController(this).navigate(R.id.action_todayFragment_to_treatmentBottomSheetFragment)
        );

        super.onViewCreated(view, savedInstanceState);
    }
    
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResume() {
        super.onResume();
        boolean empty = setTodayTasksRecycler();
        if(empty)
            emptyTasks.setVisibility(View.VISIBLE);
        else
            emptyTasks.setVisibility(View.GONE);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean setTodayTasksRecycler() {
        Map<LocalTime, Task> todayTasks;
        boolean empty = true;

        List<Treatment> treatments = App.listTreatment;

        for(int i = 0; i < treatments.size(); i++) {
            todayTasks = treatments.get(i).getDailyTaskByDate(LocalDate.now());
            if (todayTasks != null) {
                empty = false;
                treatmentHandlerPicker(treatments.get(i), todayTasks, i);
            }
        }

        adapter = new TasksRecViewAdapter(view.getContext());
        Collections.sort(todayTasksShow);
        adapter.setTasks(todayTasksShow);
        tasksRecView.setAdapter(adapter);
        tasksRecView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return empty;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void treatmentHandlerPicker(Treatment treatment, Map<LocalTime, Task> dailyTasks, int treatmentIdx) {
        if (treatment.getType().equals(TaskMedication.class)) {
            handleMedicationTask(dailyTasks, treatment.getName(), treatmentIdx);
        } else if (treatment.getType().equals(TaskActivity.class)) {
            handleActivityTask(dailyTasks, treatment.getName(), treatmentIdx);
        } else if (treatment.getType().equals(TaskMeasurement.class)) {
            handleMeasurementTask(dailyTasks, treatment.getName(), treatmentIdx);
        } else {
            handleSymptomCheckTask(dailyTasks, treatment.getName(), treatmentIdx);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void handleSymptomCheckTask(Map<LocalTime, Task> dailyTasks, String treatmentName, int treatmentIdx) {

        for (LocalTime time :
                dailyTasks.keySet()) {

            if(dailyTasks.get(time).getState() == Task.State.PENDING) {
                TaskSymptomCheck task = (TaskSymptomCheck) dailyTasks.get(time);
                todayTasksShow.add(new com.example.app.classesAna.Task("Symptom Check", treatmentName, LocalDate.now().toString(), time.toString(), treatmentIdx));
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void handleMeasurementTask(Map<LocalTime, Task> dailyTasks, String treatmentName, int treatmentIdx) {

        for (LocalTime time :
                dailyTasks.keySet()) {

            if(dailyTasks.get(time).getState() == Task.State.PENDING) {
                TaskMeasurement task = (TaskMeasurement) dailyTasks.get(time);
                todayTasksShow.add(new com.example.app.classesAna.Task("Measurement", treatmentName, LocalDate.now().toString(), time.toString(), treatmentIdx));
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void handleActivityTask(Map<LocalTime, Task> dailyTasks, String treatmentName, int treatmentIdx) {

        for (LocalTime time :
                dailyTasks.keySet()) {
            if(dailyTasks.get(time).getState() == Task.State.PENDING) {
                TaskActivity task = (TaskActivity) dailyTasks.get(time);
                todayTasksShow.add(new com.example.app.classesAna.Task("Activity", treatmentName, LocalDate.now().toString(), time.toString(), treatmentIdx));
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void handleMedicationTask(Map<LocalTime, Task> dailyTasks, String treatmentName, int treatmentIdx) {

        for (LocalTime time :
                dailyTasks.keySet()) {

            if (dailyTasks.get(time).getState() == Task.State.PENDING) {
                TaskMedication task = (TaskMedication) dailyTasks.get(time);
                todayTasksShow.add(new MedicationTask("Medication", treatmentName, LocalDate.now().toString(), time.toString(), task.getPillName(), task.getDose(), treatmentIdx));
            }
        }
    }
}