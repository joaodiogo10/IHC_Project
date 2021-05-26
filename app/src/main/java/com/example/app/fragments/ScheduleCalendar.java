package com.example.app.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.example.app.R;

import org.jetbrains.annotations.NotNull;
import com.example.app.activities.CalendarDaySelected;
import com.example.app.activities.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */

public class ScheduleCalendar extends Fragment {

    private static final String TAG = "Calendar";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule_calendar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CalendarView mcalendar = view.findViewById(R.id.calendarView);

        mcalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + month + "/" + year;
                Log.d(TAG, "onSelectedDayChange: date: " + date);
                startActivity(new Intent(getActivity(), LoginActivity.class)); // mudar loginActivity por CalendarDaySelected
            }
        });
    }
}