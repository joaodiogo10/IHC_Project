package com.example.app.fragments;

import android.app.usage.UsageEvents;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.example.app.R;
import com.example.app.activities.CalendarDaySelected;
import com.example.app.activities.LoginActivity;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */

public class Calendar extends Fragment {

    private static final String TAG = "Calendar";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(R.string.nav_calender);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule_calendar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CalendarView calendar = view.findViewById(R.id.calendarView);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                LocalDate date = LocalDate.of(year, month + 1, dayOfMonth);
                Intent intent = new Intent(getActivity(), CalendarDaySelected.class);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });
    }
}