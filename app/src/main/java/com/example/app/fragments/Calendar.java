package com.example.app.fragments;

import android.app.usage.UsageEvents;
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
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + month + "/" + year;
                Date date1 = new Date(year-1900,month,dayOfMonth);
                Log.d(TAG, "onSelectedDayChange: date: " + date);
                System.out.println("onSelectedDayChange: date2: " + date1);
                //startActivity(new Intent(getActivity(), LoginActivity.class)); // mudar loginActivity por CalendarDaySelected
            }
        });
    }
}