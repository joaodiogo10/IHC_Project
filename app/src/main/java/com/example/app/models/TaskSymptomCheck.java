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
    public TaskSymptomCheck(LocalDate date, LocalTime time) {
        super(date, time, State.PENDING);
        feeling = null;
    }

    public void setFeeling(Feeling feeling) {
            this.feeling = feeling;
    }

    public String toString() {
        return "SymptomCheckTask{" +
                "Feeling= " + feeling == null ? "undefined" : feeling +
                " " + super.toString() +'}';
    }
}
