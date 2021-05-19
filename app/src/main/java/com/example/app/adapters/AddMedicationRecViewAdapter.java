package com.example.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.classesAna.Picker;
import com.example.app.fragments.TimePickerFragment;

import java.util.ArrayList;

public class AddMedicationRecViewAdapter extends RecyclerView.Adapter<AddMedicationRecViewAdapter.ViewHolder> {

    private ArrayList<Picker> picker = new ArrayList<>();
    private Context context;
    private View view;
    private ViewHolder holder;
    private int cardViewSelectedPosition;

    public AddMedicationRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_medication_hour_dose, parent, false);
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
                setCardViewSelectedPosition(position);
                pickerFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), "Time Picker");
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private EditText hours;
        private EditText dose;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            hours = itemView.findViewById(R.id.editHour);
            dose = itemView.findViewById(R.id.editDose);
        }
    }
}
