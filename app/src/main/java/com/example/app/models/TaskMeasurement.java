package com.example.app.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class TaskMeasurement extends Task{
    enum MeasurementTask{
        BLOODPRESSURE
    }
    
    private int measurementValue;
    private MeasurementTask task;
    public TaskMeasurement(LocalDate date, LocalTime time, MeasurementTask task) {
        super(date, time, State.PENDING);
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
