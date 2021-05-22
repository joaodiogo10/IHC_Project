package com.example.app.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class TaskMeasurement extends Task{
    public enum MeasurementTask{
        BLOOD_PRESSURE
    }
    
    private int measurementValue;
    private MeasurementTask task;

    public TaskMeasurement(LocalTime time, MeasurementTask task, String name) {
        super(time, State.PENDING, name);
        this.measurementValue = -1;
        this.task = task;
    }

    public void setMeasurement(int value) {
        assert (value >= 0);
        this.measurementValue = value;
    }

    public int getMeasurementValue() {
        return measurementValue;
    }

    public String toString() {
        return "MeasurementTask{" +
                "Value= " + measurementValue == null ? "undefined" : measurementValue +
                " " + super.toString() +'}';
    }
}
