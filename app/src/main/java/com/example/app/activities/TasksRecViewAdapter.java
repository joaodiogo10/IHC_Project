package com.example.app.activities;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;

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
        }

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, tasks.get(position).getName(), Toast.LENGTH_LONG).show();
                switch (tasks.get(position).getType()){
                    case "Medication":
                        MedicationTask task = (MedicationTask) tasks.get(position);
                        showDialog(task.getName(), task.getDay(), task.getHour(), task.getPill(), String.valueOf(task.getDose()), task.getImage());
                        break;
                    case "Measurement":
                        tasks.get(position).setImage(R.drawable.ic_heartbeat);
                        holder.image.setImageResource(R.drawable.ic_heartbeat);
                        break;
                    case "Activity":
                        tasks.get(position).setImage(R.drawable.ic_shoes);
                        holder.image.setImageResource(R.drawable.ic_shoes);
                        break;
                }
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

    void showDialog(String name, String day, String hour, String pill, String dose, int image) {

        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);

        ImageView icon = dialog.findViewById(R.id.image);
        icon.setImageResource(image);

        TextView taskName = dialog.findViewById(R.id.txtTaskName);
        taskName.setText(name);

        EditText editTextDay = dialog.findViewById(R.id.editTextDay);
        editTextDay.setText(day);
        editTextDay.setEnabled(false);

        EditText editTextHour = dialog.findViewById(R.id.editTextHour);
        editTextHour.setText(hour);
        editTextHour.setEnabled(false);

        EditText editTextPill = dialog.findViewById(R.id.editTextPill);
        editTextPill.setText(pill);
        editTextPill.setEnabled(false);

        EditText editTextDose = dialog.findViewById(R.id.editTextDose);
        editTextDose.setText(dose);
        editTextDose.setEnabled(false);

        dialog.show();

    }
}
