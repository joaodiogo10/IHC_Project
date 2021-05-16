package com.example.app.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app.R;
import com.example.app.classesAna.MedicationTask;
import com.example.app.classesAna.Task;
import com.example.app.adapters.TasksRecViewAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class Today extends Fragment {

    private RecyclerView tasksRecView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(R.string.nav_today);
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_today, container, false);

        ArrayList<Task> tasks = new ArrayList<>();

        tasks.add(new MedicationTask("Medication","Take pills", "15/05/2021","10:30", "Ben-u-ron", 1));
        tasks.add(new Task("Measurement","Measure blood pressure","15/05/2021", "15:20"));
        tasks.add(new Task("Activity","Running","15/05/2021", "18:00"));
        tasks.add(new Task("Symptom Check","Symptom Check","15/05/2021", "20:00"));

        tasksRecView = view.findViewById(R.id.tasksTodayRecyclerView);

        TasksRecViewAdapter adapter = new TasksRecViewAdapter(view.getContext());
        adapter.setTasks(tasks);

        tasksRecView.setAdapter(adapter);
        tasksRecView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }
}