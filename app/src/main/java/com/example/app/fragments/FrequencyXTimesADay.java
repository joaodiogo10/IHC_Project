package com.example.app.fragments;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.App;
import com.example.app.R;
import com.example.app.activities.AddActivityActivity;
import com.example.app.activities.AddMeasurementActivity;
import com.example.app.activities.AddMedicationActivity;
import com.example.app.activities.AddSymptomCheckActivity;
import com.example.app.adapters.AddMedicationRecViewAdapter;
import com.example.app.adapters.TasksRecViewAdapter;
import com.example.app.classesAna.Picker;
import com.example.app.models.DailyEveryXHours;
import com.example.app.models.DailyXTimesADay;
import com.example.app.models.EveryXDays;
import com.example.app.models.Frequency;
import com.example.app.models.TaskActivity;
import com.example.app.models.TaskMeasurement;
import com.example.app.models.TaskMedication;
import com.example.app.models.TaskSymptomCheck;
import com.example.app.models.Treatment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class FrequencyXTimesADay extends Fragment {

    private RecyclerView pickerRecView;
    private AddMedicationRecViewAdapter recAdapter;
    private View view;
    private ArrayList<Picker> picker;
    private Boolean showButton;
    //private Picker defaultPicker = new Picker("8:10", 1);

    public FrequencyXTimesADay(Boolean showButton) {
        this.showButton = showButton;
    }

    @Nullable
    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_frequency_x_times_a_day, container, false);

        picker = new ArrayList<>();

        picker.add(new Picker("8:10", 1));

        pickerRecView = view.findViewById(R.id.addMedicationHourRecyclerView);

        recAdapter = new AddMedicationRecViewAdapter(view.getContext());
        recAdapter.setHoursDose(picker);

        pickerRecView.setAdapter(recAdapter);
        pickerRecView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.add(new Picker("8:10", 1));
                recAdapter.setHoursDose(picker);
            }
        });

        Button btnSave = (Button) view.findViewById(R.id.buttonCheck);

        if (showButton) {
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
                        //duration = Integer.parseInt(activity.getDuration()); //TODO supor que a dura??ao ?? em dias
                        if (name.equals("")) {
                            Toast toast  = Toast.makeText(getContext(),"Missing Name field", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        else if (pill.equals("")) {
                            Toast toast  = Toast.makeText(getContext(),"Missing Pill field", Toast.LENGTH_SHORT);
                            toast.show();
                        } else {
                            createMedicationTreatment(name, pill, notes, 5, recAdapter.getPicker());
                            Context context = getActivity().getApplicationContext();
                            Toast toast = Toast.makeText(context, "Reminder added successfully!", Toast.LENGTH_SHORT);
                            toast.show();
                            getActivity().finish();
                        }
                    } else if (getActivity().getClass().equals(AddMeasurementActivity.class)) {
                        //TODO pq ?? que o form n tem dura??ao !?
                        AddMeasurementActivity activity = (AddMeasurementActivity) getActivity();
                        activity.setValues();
                        name = activity.getSelectedMeasure();
                        notes = activity.getNotes();
                        createMeasurementTreatment(name, notes, 5); //5 dura??ao provisoria
                        Context context = getActivity().getApplicationContext();
                        Toast toast = Toast.makeText(context, "Treatment added successfully!", Toast.LENGTH_SHORT);
                        toast.show();
                        getActivity().finish();
                    } else if (getActivity().getClass().equals(AddSymptomCheckActivity.class)) {
                        //TODO acrescentar duration??
                        AddSymptomCheckActivity activity = (AddSymptomCheckActivity) getActivity();
                        activity.setValues();
                        name = activity.getSymptomName();
                        notes = activity.getNotes();
                        if (name.equals("")) {
                            Toast toast  = Toast.makeText(getContext(),"Missing Name field", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        else {
                            createSymptomCheckTreatment(name, notes, 5); //dura??ao provisoria
                            Context context = getActivity().getApplicationContext();
                            Toast toast = Toast.makeText(context, "Treatment added successfully!", Toast.LENGTH_SHORT);
                            toast.show();
                            getActivity().finish();
                        }
                    } else {
                        AddActivityActivity activity = (AddActivityActivity) getActivity();
                        activity.setValues();
                        name = activity.getActivityName();
                        notes = activity.getNotes();
                        if (name.equals("")) {
                            Toast toast  = Toast.makeText(getContext(),"Missing Name field", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        else {
                            createActivityTreatment(name, notes, 5);
                            Context context = getActivity().getApplicationContext();
                            Toast toast = Toast.makeText(context, "Treatment added successfully!", Toast.LENGTH_SHORT);
                            toast.show();
                            getActivity().finish();
                        }
                    }
                }
            });

        } else {
            btnSave.setVisibility(View.GONE);
        }
        return view;
    }

    public RecyclerView getPickerRecView() {
        return pickerRecView;
    }

    public AddMedicationRecViewAdapter getAddMedicationRecViewAdapter() {
        return recAdapter;
    }

    public ArrayList<Picker> getFinalPicker() {
        return recAdapter.getPicker();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createMedicationTreatment(String name, String pill, String notes, int duration, ArrayList<Picker> picker) {

        LocalDate startDate = LocalDate.now();
        TreeMap<LocalTime, TaskMedication> dailyTasks = new TreeMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        DailyXTimesADay frq = new DailyXTimesADay();

        for (int i = 0; i < picker.size(); i++) {
            LocalTime hour = LocalTime.parse(picker.get(i).getHour(), formatter);
            int dose = picker.get(i).getDose();
            TaskMedication task = new TaskMedication(hour, dose, pill);
            dailyTasks.put(hour, task);
        }

        LocalDate endDate = startDate.plusDays(duration); //TODO usar a dura??ao aqui
        com.example.app.models.Treatment<TaskMedication> treatment = new Treatment<>(name, frq, notes, startDate, endDate, dailyTasks, TaskMedication.class);

        App.listTreatment.add(treatment);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createMeasurementTreatment(String name, String notes, int duration) {
        LocalDate startDate = LocalDate.now();
        TreeMap<LocalTime, TaskMeasurement> dailyTasks = new TreeMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        DailyXTimesADay frq = new DailyXTimesADay();

        for (int i = 0; i < picker.size(); i++) {
            LocalTime hour = LocalTime.parse(picker.get(i).getHour(), formatter);
            TaskMeasurement task = new TaskMeasurement(hour, name);
            dailyTasks.put(hour, task);
        }

        LocalDate endDate = startDate.plusDays(duration); //TODO usar a dura??ao aqui
        com.example.app.models.Treatment<TaskMeasurement> treatment = new Treatment<>(name, frq, notes, startDate, endDate, dailyTasks, TaskMeasurement.class);

        App.listTreatment.add(treatment);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createSymptomCheckTreatment(String name, String notes, int duration) {
        LocalDate startDate = LocalDate.now();
        TreeMap<LocalTime, TaskSymptomCheck> dailyTasks = new TreeMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        DailyXTimesADay frq = new DailyXTimesADay();

        for (int i = 0; i < picker.size(); i++) {
            LocalTime hour = LocalTime.parse(picker.get(i).getHour(), formatter);
            TaskSymptomCheck task = new TaskSymptomCheck(hour);
            dailyTasks.put(hour, task);
        }

        LocalDate endDate = startDate.plusDays(duration); //TODO usar a dura??ao aqui
        com.example.app.models.Treatment<TaskSymptomCheck> treatment = new Treatment<>(name, frq, notes, startDate, endDate, dailyTasks, TaskSymptomCheck.class);

        App.listTreatment.add(treatment);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createActivityTreatment(String name, String notes, int duration) {
        LocalDate startDate = LocalDate.now();
        TreeMap<LocalTime, TaskActivity> dailyTasks = new TreeMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        DailyXTimesADay frq = new DailyXTimesADay();

        for (int i = 0; i < picker.size(); i++) {
            LocalTime hour = LocalTime.parse(picker.get(i).getHour(), formatter);
            TaskActivity task = new TaskActivity(hour);
            dailyTasks.put(hour, task);
        }

        LocalDate endDate = startDate.plusDays(duration); //TODO usar a dura??ao aqui
        com.example.app.models.Treatment<TaskActivity> treatment = new Treatment<>(name, frq, notes, startDate, endDate, dailyTasks, TaskActivity.class);

        App.listTreatment.add(treatment);
    }
}
