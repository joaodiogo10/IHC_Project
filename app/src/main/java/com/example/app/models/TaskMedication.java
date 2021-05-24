package com.example.app.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class TaskMedication extends Task{
    private int dose;
    private String pillName;

    public TaskMedication(LocalTime time,int dose, String pillName) {
        super(time, State.PENDING);
        this.dose = dose;
        this.pillName = pillName;
    }
    public int getDose() {
        return dose;
    }

    public String getPillName() { return pillName; }
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
