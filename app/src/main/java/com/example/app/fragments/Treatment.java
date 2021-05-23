package com.example.app.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.App;
import com.example.app.R;
import com.example.app.adapters.TasksRecViewAdapter;
import com.example.app.adapters.TreatmentsListRecViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class Treatment extends Fragment {

    private TreatmentsListRecViewAdapter adapter;
    private RecyclerView treatmentsRecView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(R.string.nav_treatment);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_treatment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        treatmentsRecView = view.findViewById(R.id.treatmentsRecyclerView);
        adapter = new TreatmentsListRecViewAdapter();
        adapter.setTreatments(App.listTreatment);
        treatmentsRecView.setAdapter(adapter);
        treatmentsRecView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        view.findViewById(R.id.floatingActionButton).setOnClickListener(
                v -> NavHostFragment.findNavController(this).navigate(R.id.action_treatmentFragment_to_treatmentBottomSheetFragment)
        );
    }
}