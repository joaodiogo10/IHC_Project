package com.example.app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.app.R;

public class FrequencyEveryXDays extends Fragment {

    private FrequencyXTimesADay frequencyXTimesADay;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_frequency_every_x_days, container, false);

        frequencyXTimesADay = new FrequencyXTimesADay();

        FragmentTransaction transactionXTimesADay = getChildFragmentManager().beginTransaction();
        transactionXTimesADay.replace(R.id.frequencyHourFragmentContainer, frequencyXTimesADay).commit();

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
        }
        else if (getActivity().getClass().getSimpleName().equals("AddSymptomCheckActivity")) {
            first.setText("First Check");
            last.setText("Last Check");
        }
        else if (getActivity().getClass().getSimpleName().equals("AddActivityActivity")) {
            first.setText("First Activity");
            last.setText("Last Activity");
        }
    }

    public FrequencyXTimesADay getFrequencyXTimesADay() {
        return frequencyXTimesADay;
    }
}
