package com.example.app.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class TaskActivity extends Task{

    public TaskActivity(LocalDate date, LocalTime time) {
        super(date, time, State.PENDING);
    }

    public String toString() {
        return "ActivityTask{ " + super.toString() +'}';
    }
}
