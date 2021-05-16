package com.example.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.app.R;
import com.example.app.activities.AddActivityActivity;
import com.example.app.activities.AddMeasurementActivity;
import com.example.app.activities.AddMedicationActivity;
import com.example.app.activities.AddSymptomCheckActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class TreatmentBottomSheetFragment extends BottomSheetDialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.treatment_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.bottomSheetMedication).setOnClickListener(v -> {
            TreatmentBottomSheetFragment.this.dismiss();
            startActivity(new Intent(getActivity(), AddMedicationActivity.class));
        });

        view.findViewById(R.id.bottomSheetMeasurements).setOnClickListener(v -> {
            TreatmentBottomSheetFragment.this.dismiss();
            startActivity(new Intent(getActivity(), AddMeasurementActivity.class));
        });

        view.findViewById(R.id.bottomSheetActivity).setOnClickListener(v -> {
            TreatmentBottomSheetFragment.this.dismiss();
            startActivity(new Intent(getActivity(), AddActivityActivity.class));
        });

        view.findViewById(R.id.bottomSheetSymptomCheck).setOnClickListener(v -> {
            TreatmentBottomSheetFragment.this.dismiss();
            startActivity(new Intent(getActivity(), AddSymptomCheckActivity.class));
        });
    }
}