package com.example.app.activities;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.app.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        Hashtable<LocalTime, TaskMedication> dailyTasks= new Hashtable<>();
        dailyTasks.put(LocalTime.of(12,50),new TaskMedication(2));
        dailyTasks.put(LocalTime.of(15,50),new TaskMedication(1));
        dailyTasks.put(LocalTime.of(18,50),new TaskMedication(3));
        TreatmentMedication medication= new TreatmentMedication(new Treatment.DailyXTimesADay(), "ben u ron", "ola", LocalDate.now(), LocalDate.of(2021,5,20), dailyTasks);
        Log.d("Test", medication.toString());
     */
        //Bottom menu navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.bottomNavHostFragment);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

    }

}