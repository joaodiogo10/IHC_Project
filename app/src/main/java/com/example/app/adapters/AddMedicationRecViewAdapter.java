package com.example.app.adapters;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.activities.AddMedicationActivity;
import com.example.app.classesAna.Picker;
import com.example.app.fragments.TimePickerFragment;

import java.util.ArrayList;

public class AddMedicationRecViewAdapter extends RecyclerView.Adapter<AddMedicationRecViewAdapter.ViewHolder> {

    private ArrayList<Picker> picker = new ArrayList<>();
    private Context context;
    private View view;
    private ViewHolder holder;
    private int cardViewSelectedPosition;
    public int currentPosition;

    public AddMedicationRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_medication_hour_dose, parent, false);
        if (!context.getClass().getSimpleName().equals("AddMedicationActivity")) {
            view.findViewById(R.id.txtDose).setVisibility(View.GONE);
            view.findViewById(R.id.editDose).setVisibility(View.GONE);
        }
        holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddMedicationRecViewAdapter.ViewHolder holder, int position) {
        holder.hours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment pickerFragment = new TimePickerFragment();
                holder.cardView.setTag(position);
                currentPosition = position;
                setCardViewSelectedPosition(position);
                pickerFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), "Time Picker");
            }
        });

        holder.dose.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                picker.get(position).setDose(Integer.parseInt(s.toString()));
            }
        });
    }

    public int getCardViewSelectedPosition() {
        return cardViewSelectedPosition;
    }

    public void setCardViewSelectedPosition(int cardViewSelectedPosition) {
        this.cardViewSelectedPosition = cardViewSelectedPosition;
    }

    @Override
    public int getItemCount() {
        return picker.size();
    }

    public void setHoursDose(ArrayList<Picker> hoursDose) {
        this.picker = hoursDose;
        notifyDataSetChanged();
    }

    public ArrayList<Picker> getPicker() {
        return picker;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView textDose;
        private EditText hours;
        private EditText dose;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            hours = itemView.findViewById(R.id.editHour);
            dose = itemView.findViewById(R.id.editDose);
            textDose = itemView.findViewById(R.id.txtDose);
        }
    }
}
