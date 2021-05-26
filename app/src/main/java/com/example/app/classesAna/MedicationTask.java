package com.example.app.classesAna;

public class MedicationTask extends Task {

    private String pill;
    private int dose;

    public MedicationTask(String type, String name, String day, String hour, String pill, int dose, int treatmentIdx) {
        super(type, name, day, hour, treatmentIdx);
        this.pill = pill;
        this.dose = dose;
    }

    public String getPill() {
        return pill;
    }

    public void setPill(String pill) {
        this.pill = pill;
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
                "pill='" + pill + '\'' +
                ", dose=" + dose +
                '}';
    }
}