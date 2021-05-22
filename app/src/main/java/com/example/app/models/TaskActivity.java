package com.example.app.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class TaskActivity extends Task{

    public TaskActivity(LocalTime time, String name) {
        super(time, State.PENDING, name);
    }

    public String toString() {
        return "ActivityTask{ " + super.toString() +'}';
    }
}
