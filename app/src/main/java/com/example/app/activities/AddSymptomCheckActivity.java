package com.example.app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.app.R;
import com.example.app.fragments.FrequencyDailyEveryXHours;
import com.example.app.fragments.FrequencyXTimesADay;
import com.example.app.fragments.ScheduleCalendar;
import com.example.app.fragments.ScheduleLists;
import com.google.android.material.tabs.TabLayout;

public class AddSymptomCheckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_symptom_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Spinner spinnerFrequency = (Spinner) findViewById(R.id.spinnerFrequency);
        ArrayAdapter<CharSequence> adapterFrequency = ArrayAdapter.createFromResource(this, R.array.frequency_array, android.R.layout.simple_spinner_item);
        adapterFrequency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrequency.setAdapter(adapterFrequency);


        /*FrequencyXTimesADay frequencyXTimesADay = new FrequencyXTimesADay();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frequencyFragmentContainer, frequencyXTimesADay).commit();*/

        spinnerFrequency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectItemText = (String) parent.getItemAtPosition(position);

                switch (selectItemText) {
                    case "Daily X times a day":
                        FrequencyXTimesADay frequencyXTimesADay = new FrequencyXTimesADay();

                        FragmentTransaction transactionXTimesADay = getSupportFragmentManager().beginTransaction();
                        transactionXTimesADay.replace(R.id.frequencyFragmentContainer, frequencyXTimesADay).commit();
                        break;
                    case "Daily every X hours":
                        FrequencyDailyEveryXHours frequencyDailyEveryXHours = new FrequencyDailyEveryXHours();

                        FragmentTransaction transactionDailyEveryXHours = getSupportFragmentManager().beginTransaction();
                        transactionDailyEveryXHours.replace(R.id.frequencyFragmentContainer, frequencyDailyEveryXHours).commit();
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


}