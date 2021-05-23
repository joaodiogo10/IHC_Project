package com.example.app.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.app.App;
import com.example.app.R;
import com.example.app.activities.AddMeasurementActivity;
import com.example.app.activities.AddMedicationActivity;
import com.example.app.models.DailyEveryXHours;
import com.example.app.models.Frequency;
import com.example.app.models.TaskMeasurement;
import com.example.app.models.TaskMedication;
import com.example.app.models.Treatment;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TreeMap;

public class FrequencyDailyEveryXHours extends Fragment {

    private View view;

    @Nullable
    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_frequency_daily_every_x_hours, container, false);

        Button btnSave = view.findViewById(R.id.buttonCheck);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, notes;
                int duration;

                if (getActivity().getClass().equals(AddMedicationActivity.class)) {
                    AddMedicationActivity activity = (AddMedicationActivity) getActivity();
                    activity.setValues();
                    String pill = activity.getPill();
                    name = activity.getTaskName();
                    notes = activity.getNotes();
                    duration = Integer.parseInt(activity.getDuration()); //TODO supor que a duraçao é em dias
                    createMedicationTreatment(name, pill, notes, duration);
                } else if (getActivity().getClass().equals(AddMeasurementActivity.class)) {
                    //TODO pq é que o form n tem duraçao !?
                    AddMeasurementActivity activity = (AddMeasurementActivity) getActivity();
                    activity.setValues();
                    name = activity.getSelectedMeasure();
                    notes = activity.getNotes();
                    createMeasurementTreatment(name, notes, 5); //5 duraçao provisoria
                }

                //TODO falta sair daqui
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView first = view.findViewById(R.id.textViewFirstIntake);
        TextView last = view.findViewById(R.id.textViewLastIntake);

        if (!getActivity().getClass().getSimpleName().equals("AddMedicationActivity")) {
            view.findViewById(R.id.textViewDose).setVisibility(View.GONE);
            view.findViewById(R.id.editTextDose).setVisibility(View.GONE);
        }

        if (getActivity().getClass().getSimpleName().equals("AddMeasurementActivity")) {
            first.setText("First Measure");
            last.setText("Last Measure");
        } else if (getActivity().getClass().getSimpleName().equals("AddSymptomCheckActivity")) {
            first.setText("First Check");
            last.setText("Last Check");
        } else if (getActivity().getClass().getSimpleName().equals("AddActivityActivity")) {
            first.setText("First Activity");
            last.setText("Last Activity");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createMedicationTreatment(String name, String pill, String notes, int duration) {

        TreeMap<LocalTime, TaskMedication> dailyTasks = new TreeMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        //TODO estes textos tem de estar 08:00
        LocalTime firstHour = LocalTime.parse(((EditText) view.findViewById(R.id.editTextFirstIntake)).getText().toString(), formatter);
        LocalTime lastHour = LocalTime.parse(((EditText) view.findViewById(R.id.editTextLastIntake)).getText().toString(), formatter);

        int interval = Integer.parseInt(((EditText) view.findViewById(R.id.editTextRemind)).getText().toString());
        int dose = Integer.parseInt(((EditText) view.findViewById(R.id.editTextDose)).getText().toString());

        DailyEveryXHours frq = new DailyEveryXHours(interval);
        List<LocalTime> tasksHours = Frequency.getHoursWithinTime(firstHour, lastHour, interval);

        for (LocalTime hour :
                tasksHours) {
            TaskMedication task = new TaskMedication(hour, dose, name);
            dailyTasks.put(hour, task);
        }

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(duration); //TODO usar a duraçao aqui
        com.example.app.models.Treatment<TaskMedication> treatment = new Treatment<>(frq, notes, startDate, endDate, dailyTasks, TaskMedication.class);

        App.listTreatment.add(treatment);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createMeasurementTreatment(String name, String notes, int duration) {
        TreeMap<LocalTime, TaskMeasurement> dailyTasks = new TreeMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        //TODO estes textos tem de estar 08:00
        LocalTime firstHour = LocalTime.parse(((EditText) view.findViewById(R.id.editTextFirstIntake)).getText().toString(), formatter);
        LocalTime lastHour = LocalTime.parse(((EditText) view.findViewById(R.id.editTextLastIntake)).getText().toString(), formatter);

        int interval = Integer.parseInt(((EditText) view.findViewById(R.id.editTextRemind)).getText().toString());

        DailyEveryXHours frq = new DailyEveryXHours(interval);
        List<LocalTime> tasksHours = Frequency.getHoursWithinTime(firstHour, lastHour, interval);

        for (LocalTime hour :
                tasksHours) {
            TaskMeasurement task = new TaskMeasurement(hour, name);
            dailyTasks.put(hour, task);
        }

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(duration); //TODO usar a duraçao aqui
        com.example.app.models.Treatment<TaskMeasurement> treatment = new Treatment<>(frq, notes, startDate, endDate, dailyTasks, TaskMeasurement.class);

        App.listTreatment.add(treatment);
    }
}
