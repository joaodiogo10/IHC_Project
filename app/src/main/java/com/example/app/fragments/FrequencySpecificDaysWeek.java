package com.example.app.fragments;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.app.App;
import com.example.app.R;
import com.example.app.activities.AddActivityActivity;
import com.example.app.activities.AddMeasurementActivity;
import com.example.app.activities.AddMedicationActivity;
import com.example.app.activities.AddSymptomCheckActivity;
import com.example.app.activities.MainActivity;
import com.example.app.classesAna.Picker;
import com.example.app.models.EveryXDays;
import com.example.app.models.SpecificDaysOfWeek;
import com.example.app.models.TaskActivity;
import com.example.app.models.TaskMeasurement;
import com.example.app.models.TaskMedication;
import com.example.app.models.TaskSymptomCheck;
import com.example.app.models.Treatment;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

import ca.antonious.materialdaypicker.MaterialDayPicker;

public class FrequencySpecificDaysWeek extends Fragment {

    private FrequencyXTimesADay frequencyXTimesADay;
    private View view;

    @Nullable
    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_frequency_specific_days_week, container, false);

        MaterialDayPicker dayPicker = view.findViewById(R.id.day_picker);
        dayPicker.setLocale(Locale.ENGLISH);

        frequencyXTimesADay = new FrequencyXTimesADay(false);

        FragmentTransaction transactionXTimesADay = getChildFragmentManager().beginTransaction();
        transactionXTimesADay.replace(R.id.frequencyHourFragmentContainer, frequencyXTimesADay).commit();

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
                    //duration = Integer.parseInt(activity.getDuration()); //TODO supor que a dura??ao ?? em dias
                    List<MaterialDayPicker.Weekday> selectedDays = dayPicker.getSelectedDays();
                    ArrayList<DayOfWeek> daysOfWeek = getDaysOfWeek(selectedDays);
                    ArrayList<Picker> picker = frequencyXTimesADay.getFinalPicker();
                    if (name.equals("")) {
                        Toast toast  = Toast.makeText(getContext(),"Missing Name field", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else if (pill.equals("")) {
                        Toast toast  = Toast.makeText(getContext(),"Missing Pill field", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else {
                        createMedicationTreatment(name, pill, notes, 5, picker, daysOfWeek);
                        Context context = getActivity().getApplicationContext();
                        Toast toast = Toast.makeText(context, "Reminder added successfully!", Toast.LENGTH_LONG);
                        toast.show();
                        getActivity().finish();
                    }
                } else if (getActivity().getClass().equals(AddMeasurementActivity.class)) {
                    //TODO pq ?? que o form n tem dura??ao !?
                    AddMeasurementActivity activity = (AddMeasurementActivity) getActivity();
                    activity.setValues();
                    name = activity.getSelectedMeasure();
                    notes = activity.getNotes();
                    List<MaterialDayPicker.Weekday> selectedDays = dayPicker.getSelectedDays();
                    ArrayList<DayOfWeek> daysOfWeek = getDaysOfWeek(selectedDays);
                    ArrayList<Picker> picker = frequencyXTimesADay.getFinalPicker();
                    createMeasurementTreatment(name, notes, 5, picker, daysOfWeek); //5 dura??ao provisoria
                    Context context = getActivity().getApplicationContext();
                    Toast toast = Toast.makeText(context, "Treatment added successfully!", Toast.LENGTH_LONG);
                    toast.show();
                    getActivity().finish();
                } else if (getActivity().getClass().equals(AddSymptomCheckActivity.class)) {
                    //TODO acrescentar duration??
                    AddSymptomCheckActivity activity = (AddSymptomCheckActivity) getActivity();
                    activity.setValues();
                    name = activity.getSymptomName();
                    notes = activity.getNotes();
                    List<MaterialDayPicker.Weekday> selectedDays = dayPicker.getSelectedDays();
                    ArrayList<DayOfWeek> daysOfWeek = getDaysOfWeek(selectedDays);
                    ArrayList<Picker> picker = frequencyXTimesADay.getFinalPicker();
                    if (name.equals("")) {
                        Toast toast  = Toast.makeText(getContext(),"Missing Name field", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else {
                        createSymptomCheckTreatment(name, notes, 5, picker, daysOfWeek); //dura??ao provisoria
                        Context context = getActivity().getApplicationContext();
                        Toast toast = Toast.makeText(context, "Treatment added successfully!", Toast.LENGTH_LONG);
                        toast.show();
                        getActivity().finish();
                    }
                } else {
                    AddActivityActivity activity = (AddActivityActivity) getActivity();
                    activity.setValues();
                    name = activity.getActivityName();
                    notes = activity.getNotes();
                    List<MaterialDayPicker.Weekday> selectedDays = dayPicker.getSelectedDays();
                    ArrayList<DayOfWeek> daysOfWeek = getDaysOfWeek(selectedDays);
                    ArrayList<Picker> picker = frequencyXTimesADay.getFinalPicker();
                    if (name.equals("")) {
                        Toast toast  = Toast.makeText(getContext(),"Missing Name field", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else {
                        createActivityTreatment(name, notes, 5, picker, daysOfWeek);
                        Context context = getActivity().getApplicationContext();
                        Toast toast = Toast.makeText(context, "Treatment added successfully!", Toast.LENGTH_LONG);
                        toast.show();
                        getActivity().finish();
                    }
                }
            }
        });

        return view;
    }

    public FrequencyXTimesADay getFrequencyXTimesADay() {
        return frequencyXTimesADay;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<DayOfWeek> getDaysOfWeek(List<MaterialDayPicker.Weekday> selectedDays) {
        ArrayList<DayOfWeek> daysOfWeek = new ArrayList<>();

        for (MaterialDayPicker.Weekday weekday : selectedDays) {
            if (weekday.equals(MaterialDayPicker.Weekday.MONDAY)) {
                daysOfWeek.add(DayOfWeek.MONDAY);
            } else if (weekday.equals(MaterialDayPicker.Weekday.TUESDAY)) {
                daysOfWeek.add(DayOfWeek.TUESDAY);
            } else if (weekday.equals(MaterialDayPicker.Weekday.WEDNESDAY)) {
                daysOfWeek.add(DayOfWeek.WEDNESDAY);
            } else if (weekday.equals(MaterialDayPicker.Weekday.THURSDAY)) {
                daysOfWeek.add(DayOfWeek.THURSDAY);
            } else if (weekday.equals(MaterialDayPicker.Weekday.FRIDAY)) {
                daysOfWeek.add(DayOfWeek.FRIDAY);
            } else if (weekday.equals(MaterialDayPicker.Weekday.SATURDAY)) {
                daysOfWeek.add(DayOfWeek.SATURDAY);
            } else if (weekday.equals(MaterialDayPicker.Weekday.SUNDAY)) {
                daysOfWeek.add(DayOfWeek.SUNDAY);
            }
        }

        return daysOfWeek;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createMedicationTreatment(String name, String pill, String notes, int duration, ArrayList<Picker> picker, ArrayList<DayOfWeek> daysOfWeek) {

        LocalDate startDate = LocalDate.now();
        TreeMap<LocalTime, TaskMedication> dailyTasks = new TreeMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        //int interval = Integer.parseInt(((EditText) view.findViewById(R.id.editTextRemindDays)).getText().toString());
        //TODO falta aqui implementar o intervalo ??
        SpecificDaysOfWeek frq = new SpecificDaysOfWeek(daysOfWeek);

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
    public void createMeasurementTreatment(String name, String notes, int duration, ArrayList<Picker> picker, ArrayList<DayOfWeek> daysOfWeek) {

        LocalDate startDate = LocalDate.now();
        TreeMap<LocalTime, TaskMeasurement> dailyTasks = new TreeMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        //int interval = Integer.parseInt(((EditText) view.findViewById(R.id.editTextRemindDays)).getText().toString());
        //TODO falta aqui implementar o intervalo ??
        SpecificDaysOfWeek frq = new SpecificDaysOfWeek(daysOfWeek);

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
    public void createSymptomCheckTreatment(String name, String notes, int duration, ArrayList<Picker> picker, ArrayList<DayOfWeek> daysOfWeek) {

        LocalDate startDate = LocalDate.now();
        TreeMap<LocalTime, TaskSymptomCheck> dailyTasks = new TreeMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        //int interval = Integer.parseInt(((EditText) view.findViewById(R.id.editTextRemindDays)).getText().toString());
        //TODO falta aqui implementar o intervalo ??
        SpecificDaysOfWeek frq = new SpecificDaysOfWeek(daysOfWeek);

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
    public void createActivityTreatment(String name, String notes, int duration, ArrayList<Picker> picker, ArrayList<DayOfWeek> daysOfWeek) {

        LocalDate startDate = LocalDate.now();
        TreeMap<LocalTime, TaskActivity> dailyTasks = new TreeMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        //int interval = Integer.parseInt(((EditText) view.findViewById(R.id.editTextRemindDays)).getText().toString());
        //TODO falta aqui implementar o intervalo ??
        SpecificDaysOfWeek frq = new SpecificDaysOfWeek(daysOfWeek);

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
