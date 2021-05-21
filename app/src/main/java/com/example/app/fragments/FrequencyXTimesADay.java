package com.example.app.fragments;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.adapters.AddMedicationRecViewAdapter;
import com.example.app.classesAna.Picker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FrequencyXTimesADay extends Fragment {

    private RecyclerView pickerRecView;
    private AddMedicationRecViewAdapter recAdapter;
    private View view;
    //private Picker defaultPicker = new Picker("8:10", 1);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_frequency_x_times_a_day, container, false);

        ArrayList<Picker> picker = new ArrayList<>();

        picker.add(new Picker("8:10", 1));

        pickerRecView = view.findViewById(R.id.addMedicationHourRecyclerView);

        recAdapter = new AddMedicationRecViewAdapter(view.getContext());
        recAdapter.setHoursDose(picker);

        pickerRecView.setAdapter(recAdapter);
        pickerRecView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.add(new Picker("8:10", 1));
                recAdapter.setHoursDose(picker);
            }
        });
        return view;
    }

    public RecyclerView getPickerRecView() {
        return pickerRecView;
    }

    public AddMedicationRecViewAdapter getAddMedicationRecViewAdapter() {
        return recAdapter;
    }
}
