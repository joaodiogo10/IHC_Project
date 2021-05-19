package com.example.app.classesAna;

public class Picker {

    private String hour;
    private int dose;

    public Picker(String hour, int dose) {
        this.hour = hour;
        this.dose = dose;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public int getDose() {
        return dose;
    }

    public void setDose(int dose) {
        this.dose = dose;
    }
}
