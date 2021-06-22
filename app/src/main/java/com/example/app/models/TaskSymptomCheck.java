package com.example.app.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class TaskSymptomCheck extends Task{
    public enum Feeling {
        VERY_BAD("very bad"),
        BAD("bad"),
        MODERATE("moderate"),
        GOOD("good"),
        VERY_GOOD("very good");

        private final String text;

        Feeling(final String text) {
            this.text = text;
        }

        public String getText() {
            return this.text;
        }
    }

    Feeling feeling;
    public TaskSymptomCheck(LocalTime time) {
        super(time, State.PENDING);
        feeling = null;
    }

    public void setFeeling(Feeling feeling) {
            this.feeling = feeling;
    }

    public Feeling getFeeling() {
        return feeling;
    }

    static public Feeling getEnumFeeling(String text) {

        if (text.equals("very bad")) {
            return Feeling.VERY_BAD;
        }
        else if (text.equals("bad")) {
            return Feeling.BAD;
        }
        else  if (text.equals("moderate")) {
            return Feeling.MODERATE;
        }
        else if (text.equals("good")) {
            return Feeling.GOOD;
        }

        return Feeling.VERY_GOOD;
    }

    public String toString() {
        return "SymptomCheckTask{" +
                "Feeling= " + feeling == null ? "undefined" : feeling +
                " " + super.toString() +'}';
    }
}
