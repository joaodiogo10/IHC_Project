package com.example.app.models;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class TaskActivity extends Task  implements Cloneable{

    public TaskActivity(LocalTime time) {
        super(time, State.PENDING);
    }

    public String toString() {
        return "ActivityTask{ " + super.toString() +'}';
    }
}
