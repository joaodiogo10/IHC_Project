package com.example.app.models;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class Task {


    public enum State {
        DONE,
        SKIPPED,
        PENDING,
        EXPIRED
    }

    private LocalDate date;
    private LocalTime time;
    private State state;


    public Task(LocalDate date, LocalTime time, State state) {
        this.date = date;
        this.time = time;
        this.state = state;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Task{" +
                "date=" + date +
                ", time=" + time +
                ", state=" + state +
                '}';
    }
}
