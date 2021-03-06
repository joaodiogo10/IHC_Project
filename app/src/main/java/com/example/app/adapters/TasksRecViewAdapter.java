package com.example.app.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.App;
import com.example.app.R;
import com.example.app.classesAna.MedicationTask;
import com.example.app.classesAna.Task;
import com.example.app.models.TaskMeasurement;
import com.example.app.models.TaskSymptomCheck;

import java.time.LocalDate;
import java.time.LocalTime;
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

    @SuppressLint("ResourceAsColor")
    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onBindViewHolder(@NonNull TasksRecViewAdapter.ViewHolder holder, int position) {
        holder.txtTaskName.setText(tasks.get(position).getName());
        holder.txtHour.setText(tasks.get(position).getHour());
        holder.stateImageGreen.setImageResource(R.drawable.ic_circulo_preto);
        holder.stateImageRed.setImageResource(R.drawable.ic_circulo_preto);
        holder.stateImageYellow.setImageResource(R.drawable.ic_circulo_preto);

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

        int treatmentIdx = tasks.get(position).getTreatmentIdx();
        LocalDate date =  LocalDate.parse( (CharSequence) tasks.get(position).getDay());
        LocalTime time = LocalTime.parse( (CharSequence) tasks.get(position).getHour());

        com.example.app.models.Task stateTask = App.listTreatment.get(treatmentIdx).getTaskByDateTime(date,time);
        if (stateTask.getState() == com.example.app.models.Task.State.DONE) {
            holder.stateImageRed.setVisibility(View.GONE);
            holder.stateImageGreen.setVisibility(View.VISIBLE);
            holder.stateImageYellow.setVisibility(View.GONE);
        }
        else if (date.compareTo(LocalDate.now()) > 0) {
            holder.stateImageRed.setVisibility(View.GONE);
            holder.stateImageGreen.setVisibility(View.GONE);
            holder.stateImageYellow.setVisibility(View.VISIBLE);
        }
        else if (date.compareTo(LocalDate.now()) < 0) {
            holder.stateImageRed.setVisibility(View.VISIBLE);
            holder.stateImageGreen.setVisibility(View.GONE);
            holder.stateImageYellow.setVisibility(View.GONE);
        }
        else if (time.compareTo(LocalTime.now()) < 0) {
            holder.stateImageRed.setVisibility(View.VISIBLE);
            holder.stateImageGreen.setVisibility(View.GONE);
            holder.stateImageYellow.setVisibility(View.GONE);
        }
        else if (stateTask.getState() == com.example.app.models.Task.State.PENDING) {
            holder.stateImageRed.setVisibility(View.GONE);
            holder.stateImageGreen.setVisibility(View.GONE);
            holder.stateImageYellow.setVisibility(View.VISIBLE);
        }

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                showDialog(tasks.get(position), position);
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
        private ImageView image, stateImageGreen, stateImageRed, stateImageYellow;
        private CardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTaskName = itemView.findViewById(R.id.txtTaskName);
            txtHour = itemView.findViewById(R.id.txtHour);
            parent = itemView.findViewById(R.id.parent);
            image = itemView.findViewById(R.id.image);
            stateImageGreen = itemView.findViewById(R.id.imageStateGreen);
            stateImageRed = itemView.findViewById(R.id.imageStateRed);
            stateImageYellow = itemView.findViewById(R.id.imageStateYellow);
        }
    }

    //TODO:Notes missing
    @RequiresApi(api = Build.VERSION_CODES.O)
    void showDialog(Task task, int position) {

        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        Activity act = (Activity) context;
        int treatmentIdx = task.getTreatmentIdx();
        LocalDate date =  LocalDate.parse( (CharSequence) task.getDay());
        LocalTime time = LocalTime.parse( (CharSequence) task.getHour());

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
                dialog.setContentView(R.layout.dialog_symptom_check);
                RadioGroup radioGroup = dialog.findViewById(R.id.radioGroup);
                TaskSymptomCheck sTask = (TaskSymptomCheck) App.listTreatment.get(treatmentIdx).getTaskByDateTime(date,time);

                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = (RadioButton) dialog.findViewById(checkedId);
                        TextView feelingLegend = dialog.findViewById(R.id.txtFeelingLegend);
                        feelingLegend.setVisibility(View.VISIBLE);
                        feelingLegend.setText(radioButton.getText());
                    }
                });

                if (sTask.getFeeling() != null) {
                    if (sTask.getFeeling().getText().equals("very bad")) {
                        RadioButton button = (RadioButton) dialog.findViewById(R.id.cryingButton);
                        button.setChecked(true);
                    }
                    else if (sTask.getFeeling().getText().equals("bad")) {
                        RadioButton button = (RadioButton) dialog.findViewById(R.id.sadButton);
                        button.setChecked(true);
                    }
                    else  if (sTask.getFeeling().getText().equals("moderate")) {
                        RadioButton button = (RadioButton) dialog.findViewById(R.id.neutralButton);
                        button.setChecked(true);
                    }
                    else if (sTask.getFeeling().getText().equals("good")) {
                        RadioButton button = (RadioButton) dialog.findViewById(R.id.happyButton);
                        button.setChecked(true);
                    }
                    else {
                        RadioButton button = (RadioButton) dialog.findViewById(R.id.veryHappyButton);
                        button.setChecked(true);
                    }
                }

                if (act.getTitle().equals("History")) {
                    radioGroup.setFocusable(false);
                }
                else {
                    radioGroup.setFocusable(true);
                }
                break;
            case "Measurement":
                dialog.setContentView(R.layout.dialog_measurement);
                TaskMeasurement mTask = (TaskMeasurement) App.listTreatment.get(treatmentIdx).getTaskByDateTime(date,time);
                EditText measureValue = new EditText(context);
                TextView units = dialog.findViewById(R.id.txtUnits);
                switch (task.getName()) {
                    case "Blood Pressure":
                        units.setText("mmHg");
                        break;
                    case "Blood Glucose":
                        units.setText("mg/dl");
                        break;
                    case "Weight":
                        units.setText("kg");
                        break;
                    default:
                        units.setText("bpm");
                        break;
                }
                if ( mTask.getMeasurementValue() > 0 ) {
                    measureValue = dialog.findViewById(R.id.editTxtValue);
                    measureValue.setText(String.valueOf(mTask.getMeasurementValue()));
                }

                if (act.getTitle().equals("History")) {
                    measureValue.setFocusable(false);
                }
                else {
                    measureValue.setFocusable(true);
                }
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
        Button buttonCheck = dialog.findViewById(R.id.buttonCheck);

        if (act.getTitle().equals("History")) {
            buttonCheck.setVisibility(View.GONE);
        }
        else {
            buttonCheck.setVisibility(View.VISIBLE);
        }

        buttonCancel.setOnClickListener( (v) -> {
            dialog.dismiss();
        });
        
        buttonCheck.setOnClickListener( (v) -> {

            if (task.getType().equals("Measurement")) {
                EditText measureValue = dialog.findViewById(R.id.editTxtValue);
                String value =  measureValue.getText().toString();
                if (value.equals("")) {
                    Toast toast = Toast.makeText(context, "You must define a value", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    App.listTreatment.get(treatmentIdx).getTaskByDateTime(date,time).setState(com.example.app.models.Task.State.DONE);
                    TaskMeasurement mTask = (TaskMeasurement) App.listTreatment.get(treatmentIdx).getTaskByDateTime(date,time);
                    mTask.setMeasurementValue(Integer.parseInt(value));
                    dialog.dismiss();
                    if(act.getTitle().equals("Today")) {
                        tasks.remove(position);
                    }
                    setTasks(tasks);
                    Toast toast = Toast.makeText(context, "Task confirmed with success", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            else if (task.getType().equals("Symptom Check")) {
                RadioGroup radioGroup = dialog.findViewById(R.id.radioGroup);
                TaskSymptomCheck sTask = (TaskSymptomCheck) App.listTreatment.get(treatmentIdx).getTaskByDateTime(date,time);

                if (radioGroup.getCheckedRadioButtonId() == -1) {
                    Toast toast = Toast.makeText(context, "You must set a feeling", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    RadioButton button = (RadioButton) dialog.findViewById(radioGroup.getCheckedRadioButtonId());
                    App.listTreatment.get(treatmentIdx).getTaskByDateTime(date,time).setState(com.example.app.models.Task.State.DONE);
                    sTask.setFeeling(TaskSymptomCheck.getEnumFeeling(button.getText().toString()));
                    dialog.dismiss();
                    if(act.getTitle().equals("Today")) {
                        tasks.remove(position);
                    }
                    setTasks(tasks);
                    Toast toast = Toast.makeText(context, "Task confirmed with success", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            else {
                App.listTreatment.get(treatmentIdx).getTaskByDateTime(date,time).setState(com.example.app.models.Task.State.DONE);
                dialog.dismiss();
                if(act.getTitle().equals("Today")) {
                    tasks.remove(position);
                }
                setTasks(tasks);
                Toast toast = Toast.makeText(context, "Task confirmed with success", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}
