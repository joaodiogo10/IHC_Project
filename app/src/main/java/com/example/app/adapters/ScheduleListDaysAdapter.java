package com.example.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.classesAna.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class ScheduleListDaysAdapter extends RecyclerView.Adapter<ScheduleListDaysAdapter.ViewHolder> {
    ArrayList<LocalDate> listDays;
    Map<LocalDate, ArrayList<Task>>dailyTasks;
    Context context;

    public ScheduleListDaysAdapter(Context context, ArrayList<LocalDate> listDays, Map<LocalDate, ArrayList<Task>> dailyTasks) {
        this.listDays = listDays;
        this.context = context;
        this.dailyTasks = dailyTasks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_schedule_daily_tasks, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ScheduleListDaysAdapter.ViewHolder holder, int position) {
        LocalDate date = listDays.get(position);
        holder.day.setText(capitalize(date.getDayOfWeek().toString())+ ", " + date.toString());

        //Initialize inner recycler
        TasksRecViewAdapter dailyAdapter = new TasksRecViewAdapter(context);
        Collections.sort(dailyTasks.get(listDays.get(position)));
        dailyAdapter.setTasks(dailyTasks.get(listDays.get(position)));
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        holder.rvDailyTasks.setLayoutManager(layoutManager);
        holder.rvDailyTasks.setAdapter(dailyAdapter);
    }

    @Override
    public int getItemCount() {
        return listDays.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView day;
        RecyclerView rvDailyTasks;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.day);
            rvDailyTasks = itemView.findViewById(R.id.rvTasks);
        }
    }

    private String capitalize(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }
}
