package com.example.app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.app.R;
import com.example.app.adapters.AddMedicationRecViewAdapter;
import com.example.app.classesAna.Picker;
import com.example.app.fragments.FrequencyDailyEveryXHours;
import com.example.app.fragments.FrequencyEveryXDays;
import com.example.app.fragments.FrequencySpecificDaysWeek;
import com.example.app.fragments.FrequencyXTimesADay;

import java.util.ArrayList;

public class AddSymptomCheckActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private FrequencySpecificDaysWeek frequencySpecificDaysWeek;
    private FrequencyXTimesADay frequencyXTimesADay;
    private FrequencyEveryXDays frequencyEveryXDays;
    private FrequencyDailyEveryXHours frequencyDailyEveryXHours;
    private int selectedCardView;
    private String symptomName;
    private String notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_symptom_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Spinner spinnerFrequency = (Spinner) findViewById(R.id.spinnerFrequency);
        ArrayAdapter<CharSequence> adapterFrequency = ArrayAdapter.createFromResource(this, R.array.frequency_array, android.R.layout.simple_spinner_item);
        adapterFrequency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrequency.setAdapter(adapterFrequency);

        spinnerFrequency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectItemText = (String) parent.getItemAtPosition(position);

                switch (selectItemText) {
                    case "Daily X times a day":
                        frequencyXTimesADay = new FrequencyXTimesADay(true);
                        selectedCardView = 0;
                        FragmentTransaction transactionXTimesADay = getSupportFragmentManager().beginTransaction();
                        transactionXTimesADay.replace(R.id.frequencyFragmentContainer, frequencyXTimesADay).commit();
                        break;
                    case "Daily every X hours":
                        frequencyDailyEveryXHours = new FrequencyDailyEveryXHours();
                        selectedCardView = 3;
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
        //Values just for initialization
        CardView cardView = new CardView(getBaseContext());
        AddMedicationRecViewAdapter recAdapter = new AddMedicationRecViewAdapter(getBaseContext());
        ArrayList<Picker> picker = new ArrayList<>();
        int position = 0;
        int clickedHour = 0;
        String hour;

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
            case 0:
                cardView = (CardView) frequencyXTimesADay.getPickerRecView().findViewWithTag(frequencyXTimesADay.getAddMedicationRecViewAdapter().getCardViewSelectedPosition());
                recAdapter = frequencyXTimesADay.getAddMedicationRecViewAdapter();
                position = recAdapter.currentPosition;
                picker = recAdapter.getPicker();
                break;
            case 3:
                clickedHour = frequencyDailyEveryXHours.getClickedHour();
                break;
            default:
                break;
        }

        hour = checkDigit(hourOfDay) + ":" + checkDigit(minute);

        if (clickedHour == 1) {
            frequencyDailyEveryXHours.getFirst().setText(hour);
        }
        else if (clickedHour == 2 ){
            frequencyDailyEveryXHours.getLast().setText(hour);
        }
        else {
            EditText editHour = (EditText) cardView.findViewById(R.id.editHour);
            editHour.setText(hour);

            picker.get(position).setHour(hour);

            recAdapter.setHoursDose(picker);
        }
    }

    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    public void setValues() {
        symptomName = ((EditText) findViewById(R.id.editTextTaskName)).getText().toString();
        notes = ((EditText) findViewById(R.id.editTextNotes)).getText().toString();
    }

    public String getSymptomName() {
        return symptomName;
    }

    public String getNotes() {
        return notes;
    }
}