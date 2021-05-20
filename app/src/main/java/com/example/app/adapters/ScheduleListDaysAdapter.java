package com.example.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.classesAna.Task;

import java.util.ArrayList;


public class ScheduleListDaysAdapter extends RecyclerView.Adapter<ScheduleListDaysAdapter.ViewHolder> {
    ArrayList<String> listDays;
    ArrayList<ArrayList<Task>> dailyTasks;
    Context context;

    public ScheduleListDaysAdapter(Context context, ArrayList<String> listDays, ArrayList<ArrayList<Task>> dailyTasks) {
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

    @Override
    public void onBindViewHolder(@NonNull ScheduleListDaysAdapter.ViewHolder holder, int position) {
        holder.day.setText(listDays.get(position));

        //Initialize inner recycler
        TasksRecViewAdapter dailyAdapter = new TasksRecViewAdapter(context);
        dailyAdapter.setTasks(dailyTasks.get(position));
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
}
