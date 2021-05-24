package com.example.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.models.TaskActivity;
import com.example.app.models.TaskMeasurement;
import com.example.app.models.TaskMedication;
import com.example.app.models.Treatment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TreatmentsListRecViewAdapter extends RecyclerView.Adapter<TreatmentsListRecViewAdapter.ViewHolder> {

    private List<Treatment> treatments = new ArrayList<Treatment>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_treatments, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TreatmentsListRecViewAdapter.ViewHolder holder, int position) {
        holder.txtTreatmentName.setText(treatments.get(position).getName());
        holder.txtHour.setText(treatments.get(position).getDailyTasksHours().toString());
        holder.txtFrequency.setText(treatments.get(position).getFrequency().toString());

        if (treatments.get(position).getType().equals(TaskMedication.class)) {
            holder.image.setImageResource(R.drawable.ic_pill);
        } else if (treatments.get(position).getType().equals(TaskMeasurement.class)) {
            holder.image.setImageResource(R.drawable.ic_heartbeat);
        } else if (treatments.get(position).getType().equals(TaskActivity.class)) {
            holder.image.setImageResource(R.drawable.ic_shoes);
        } else {
            holder.image.setImageResource(R.drawable.ic_smile);
        }

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return treatments.size();
    }

    public void setTreatments(List<Treatment> treatments) {
        this.treatments = treatments;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtHour, txtTreatmentName, txtFrequency;
        private ImageView image;
        private CardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTreatmentName = itemView.findViewById(R.id.txtTreatmentName);
            txtHour = itemView.findViewById(R.id.txtViewTreatmentFrequencyHours);
            txtFrequency = itemView.findViewById(R.id.txtViewTreatmentFrequency);
            parent = itemView.findViewById(R.id.parent);
            image = itemView.findViewById(R.id.image);
        }
    }
}
