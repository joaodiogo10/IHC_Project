package com.example.app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.app.R;

public class AddMedicationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}