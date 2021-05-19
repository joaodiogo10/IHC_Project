package com.example.app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.app.R;
import com.example.app.adapters.AddMedicationRecViewAdapter;
import com.example.app.classesAna.Picker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AddMedicationActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private RecyclerView pickerRecView;
    private AddMedicationRecViewAdapter recAdapter;
    //Default
    //private Picker defaultPicker = new Picker("8:10", 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.frequency_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        ArrayList<Picker> picker = new ArrayList<>();

        picker.add(new Picker("8:10", 1));

        pickerRecView = findViewById(R.id.addMedicationHourRecyclerView);

        recAdapter = new AddMedicationRecViewAdapter(this);
        recAdapter.setHoursDose(picker);

        pickerRecView.setAdapter(recAdapter);
        pickerRecView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.add(new Picker("8:10", 1));
                recAdapter.setHoursDose(picker);
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        CardView cardView = (CardView) pickerRecView.findViewWithTag(recAdapter.getCardViewSelectedPosition());
        EditText editHour = (EditText) cardView.findViewById(R.id.editHour);
        editHour.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
    }
}