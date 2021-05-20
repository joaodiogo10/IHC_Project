package com.example.app.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app.R;
import com.example.app.activities.MainActivity;
import com.example.app.adapters.ScheduleListDaysAdapter;
import com.example.app.classesAna.MedicationTask;
import com.example.app.classesAna.Task;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class ScheduleLists extends Fragment {

    RecyclerView dailyTasksRecycler;
    ArrayList<String> days;
    ArrayList<ArrayList<Task>> dailyTasks;
    ScheduleListDaysAdapter listDaysAdapter;
    LinearLayoutManager dailyTasksLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule_lists, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        dailyTasks = new ArrayList<ArrayList<Task>>();
        days = new ArrayList<>();
        dailyTasksRecycler = view.findViewById(R.id.rvDaysList);

        days = new ArrayList<>();
        for(int i=1; i<= 10; i++) {
            days.add("Day" + i);
            ArrayList<Task> dailyTask = new ArrayList<>();
            dailyTask.add(new MedicationTask("Medication","Take pills", i + "/05/2021","10:30", "Ben-u-ron", 1));
            dailyTask.add(new Task("Measurement","Measure blood pressure",i + "/05/2021", "15:20"));
            dailyTask.add(new Task("Activity","Running",i + "/05/2021", "18:00"));
            dailyTask.add(new Task("Symptom Check","Symptom Check",i + "/05/2021", "20:00"));
            dailyTasks.add(dailyTask);
        }
        listDaysAdapter = new ScheduleListDaysAdapter(getContext(), days, dailyTasks);
        dailyTasksLayoutManager = new LinearLayoutManager(getContext());
        dailyTasksRecycler.setLayoutManager(dailyTasksLayoutManager);
        dailyTasksRecycler.setAdapter(listDaysAdapter);

        super.onViewCreated(view, savedInstanceState);
    }
}