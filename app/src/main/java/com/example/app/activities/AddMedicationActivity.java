package com.example.app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.app.App;
import com.example.app.R;
import com.example.app.adapters.AddMedicationRecViewAdapter;
import com.example.app.classesAna.Picker;
import com.example.app.fragments.FrequencyDailyEveryXHours;
import com.example.app.fragments.FrequencyEveryXDays;
import com.example.app.fragments.FrequencySpecificDaysWeek;
import com.example.app.fragments.FrequencyXTimesADay;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AddMedicationActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private FrequencySpecificDaysWeek frequencySpecificDaysWeek;
    private FrequencyXTimesADay frequencyXTimesADay;
    private FrequencyEveryXDays frequencyEveryXDays;
    private int selectedCardView;
    private String taskName, pill, duration, notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Spinner spinnerFrequency = (Spinner) findViewById(R.id.spinnerFrequency);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.frequency_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerFrequency.setAdapter(adapter);

        spinnerFrequency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectItemText = (String) parent.getItemAtPosition(position);

                switch (selectItemText) {
                    case "Daily X times a day":
                        frequencyXTimesADay = new FrequencyXTimesADay(true);

                        FragmentTransaction transactionXTimesADay = getSupportFragmentManager().beginTransaction();
                        transactionXTimesADay.replace(R.id.frequencyFragmentContainer, frequencyXTimesADay).commit();
                        break;
                    case "Daily every X hours":
                        FrequencyDailyEveryXHours frequencyDailyEveryXHours = new FrequencyDailyEveryXHours();

                        FragmentTransaction transactionDailyEveryXHours = getSupportFragmentManager().beginTransaction();
                        transactionDailyEveryXHours.replace(R.id.frequencyFragmentContainer, frequencyDailyEveryXHours).commit();
                        break;
                    case "Every X days":
                        frequencyEveryXDays = new FrequencyEveryXDays();
                        selectedCardView = 1;

                        FragmentTransaction transactionEveryXDays = getSupportFragmentManager().beginTransaction();
                        transactionEveryXDays.replace(R.id.frequencyFragmentContainer, frequencyEveryXDays).commit();
                        break;
                    case "Specific days of the week":
                        frequencySpecificDaysWeek = new FrequencySpecificDaysWeek();
                        selectedCardView = 2;

                        FragmentTransaction transactionSpecificDaysWeek = getSupportFragmentManager().beginTransaction();
                        transactionSpecificDaysWeek.replace(R.id.frequencyFragmentContainer, frequencySpecificDaysWeek).commit();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        CardView cardView;
        AddMedicationRecViewAdapter recAdapter;
        ArrayList<Picker> picker;
        int position;
        switch (selectedCardView) {
            case 1:
                cardView = (CardView) frequencyEveryXDays.getFrequencyXTimesADay().getPickerRecView().findViewWithTag(frequencyEveryXDays.getFrequencyXTimesADay().getAddMedicationRecViewAdapter().getCardViewSelectedPosition());
                recAdapter = frequencyEveryXDays.getFrequencyXTimesADay().getAddMedicationRecViewAdapter();
                position = recAdapter.currentPosition;
                picker = recAdapter.getPicker();
                break;
            case 2:
                cardView = (CardView) frequencySpecificDaysWeek.getFrequencyXTimesADay().getPickerRecView().findViewWithTag(frequencySpecificDaysWeek.getFrequencyXTimesADay().getAddMedicationRecViewAdapter().getCardViewSelectedPosition());
                recAdapter = frequencySpecificDaysWeek.getFrequencyXTimesADay().getAddMedicationRecViewAdapter();
                position = recAdapter.currentPosition;
                picker = recAdapter.getPicker();
                break;
            default:
                cardView = (CardView) frequencyXTimesADay.getPickerRecView().findViewWithTag(frequencyXTimesADay.getAddMedicationRecViewAdapter().getCardViewSelectedPosition());
                recAdapter = frequencyXTimesADay.getAddMedicationRecViewAdapter();
                position = recAdapter.currentPosition;
                picker = recAdapter.getPicker();
                break;
        }

        EditText editHour = (EditText) cardView.findViewById(R.id.editHour);
        editHour.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));

        picker.get(position).setHour(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));

        recAdapter.setHoursDose(picker);
    }

    public void setValues() {
        taskName = ((EditText) findViewById(R.id.editTextTaskName)).getText().toString();
        pill = ((EditText) findViewById(R.id.editTextPill)).getText().toString();
        duration = ((EditText) findViewById(R.id.editTextDuration)).getText().toString();
        notes = ((EditText) findViewById(R.id.editTextNotes)).getText().toString();
    }

    public String getTaskName() {
        return taskName;
    }

    public String getPill() {
        return pill;
    }

    public String getDuration() {
        return duration;
    }

    public String getNotes() {
        return notes;
    }
}