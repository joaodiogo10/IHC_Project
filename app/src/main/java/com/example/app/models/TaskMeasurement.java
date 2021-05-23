package com.example.app.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class TaskMeasurement extends Task {
    //TODO definir unidades baseado na mediçao
    public enum MeasurementTask {
        BLOOD_PRESSURE,
        WEIGHT,
        BLOOD_GLUCOSE,
        HEART_RATE
    }
    
    private int measurementValue;
    private MeasurementTask task;

    public TaskMeasurement(LocalTime time, String name) {
        super(time, State.PENDING, name);
        this.measurementValue = -1;
        setMeasurementTask(name);
    }

    public void setMeasurement(int value) {
        assert (value >= 0);
        this.measurementValue = value;
    }

    //TODO melhorar o else para n aceitar tudo
    public void setMeasurementTask(String measurementTask) {

        if (measurementTask.equals("Blood Pressure")) {
            task = MeasurementTask.BLOOD_PRESSURE;
        }
        else if (measurementTask.equals("Weight")) {
            task = MeasurementTask.WEIGHT;
        }
        else if (measurementTask.equals("Blood Glucose")) {
            task = MeasurementTask.BLOOD_GLUCOSE;
        }
        else
            task = MeasurementTask.HEART_RATE;
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
