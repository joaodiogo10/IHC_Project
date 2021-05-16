package com.example.app.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TaskMedication extends Task{
    private int dose;

    public TaskMedication(LocalDate date, LocalTime time, State state, int dose) {
        super(date, time,state);
        this.dose = dose;
    }

    public TaskMedication(int dose) {
        super(null, null, State.PENDING);
        this.dose = dose;
    }



    public int getDose() {
        return dose;
    }

    @Override
    public String toString() {
        return "MedicationTask{" +
                "dose=" + dose +
                " " + super.toString() +'}';
    }
}
