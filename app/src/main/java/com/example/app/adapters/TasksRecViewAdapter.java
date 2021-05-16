package com.example.app.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.classesAna.MedicationTask;
import com.example.app.classesAna.Task;

import java.util.ArrayList;

public class TasksRecViewAdapter extends RecyclerView.Adapter<TasksRecViewAdapter.ViewHolder> {

    private ArrayList<Task> tasks = new ArrayList<>();
    private Context context;

    public TasksRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.today_task_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TasksRecViewAdapter.ViewHolder holder, int position) {
        holder.txtTaskName.setText(tasks.get(position).getName());
        holder.txtHour.setText(tasks.get(position).getHour());

        switch (tasks.get(position).getType()){
            case "Medication":
                tasks.get(position).setImage(R.drawable.ic_pill);
                holder.image.setImageResource(R.drawable.ic_pill);
                break;
            case "Measurement":
                tasks.get(position).setImage(R.drawable.ic_heartbeat);
                holder.image.setImageResource(R.drawable.ic_heartbeat);
                break;
            case "Activity":
                tasks.get(position).setImage(R.drawable.ic_shoes);
                holder.image.setImageResource(R.drawable.ic_shoes);
                break;
            case "Symptom Check":
                tasks.get(position).setImage(R.drawable.ic_smile);
                holder.image.setImageResource(R.drawable.ic_smile);
                break;
        }

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(tasks.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtHour, txtTaskName;
        private ImageView image;
        private CardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTaskName = itemView.findViewById(R.id.txtTaskName);
            txtHour = itemView.findViewById(R.id.txtHour);
            parent = itemView.findViewById(R.id.parent);
            image = itemView.findViewById(R.id.image);
        }
    }

    //Notes missing
    void showDialog(Task task) {

        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        switch (task.getType()) {
            case "Medication":
                MedicationTask medicationTask = (MedicationTask) task;
                dialog.setContentView(R.layout.dialog_medication_task);
                TextView editTextPill = dialog.findViewById(R.id.editTextPill);
                TextView editTextDose = dialog.findViewById(R.id.editTextDose);
                editTextPill.setText(medicationTask.getPill());
                editTextDose.setText(String.valueOf(medicationTask.getDose()));
                break;
            case "Symptom Check":
                dialog.setContentView(R.layout.dialog_sympton_check);
                break;
            default:
                dialog.setContentView(R.layout.dialog);
                break;
        }

        ImageView icon = dialog.findViewById(R.id.image);
        TextView taskName = dialog.findViewById(R.id.txtTaskName);
        TextView editTextDay = dialog.findViewById(R.id.editTextDay);
        TextView editTextHour = dialog.findViewById(R.id.editTextHour);

        icon.setImageResource(task.getImage());
        taskName.setText(task.getName());
        editTextDay.setText(task.getDay());
        editTextHour.setText(task.getHour());

        dialog.show();

        Button buttonCancel = dialog.findViewById(R.id.buttonCancel);

        buttonCancel.setOnClickListener( (v) -> {
            dialog.dismiss();
        });
    }
}