package com.example.app.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class TaskMedication extends Task{
    private int dose;

    public TaskMedication(LocalTime time,int dose, String name) {
        super(time, State.PENDING, name);
        this.dose = dose;
    }

    public int getDose() {
        return dose;
    }

    public void setDose(int dose) {
        this.dose = dose;
    }

    @Override
    public String toString() {
        return "MedicationTask{" +
                "dose=" + dose +
                " " + super.toString() +'}';
    }
}
