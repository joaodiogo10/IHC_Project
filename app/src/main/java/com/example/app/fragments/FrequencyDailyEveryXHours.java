package com.example.app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.app.R;

import org.w3c.dom.Text;

public class FrequencyDailyEveryXHours extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_frequency_daily_every_x_hours, container, false);
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
}
