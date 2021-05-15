package com.example.app.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app.R;
import com.example.app.activities.MedicationTask;
import com.example.app.activities.Task;
import com.example.app.activities.TasksRecViewAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Today#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Today extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView tasksRecView;

    public Today() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Today.
     */
    // TODO: Rename and change types and number of parameters
    public static Today newInstance(String param1, String param2) {
        Today fragment = new Today();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

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

        tasksRecView = view.findViewById(R.id.tasksTodayRecyclerView);
       /* recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));*/

        TasksRecViewAdapter adapter = new TasksRecViewAdapter(view.getContext());
        adapter.setTasks(tasks);

        tasksRecView.setAdapter(adapter);
        tasksRecView.setLayoutManager(new LinearLayoutManager(view.getContext()));


        return view;
    }
}