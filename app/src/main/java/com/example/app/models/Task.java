package com.example.app.models;

import java.time.LocalTime;

public abstract class Task {
    public enum State {
        DONE,
        PENDING
    }

    private LocalTime time;
    private State state;
    private String name;

    public Task(LocalTime time, State state, String name) {
        this.time = time;
        this.state = state;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public LocalTime getTime() {
        return time;
    }

    public State getState() {
        return state;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Task{" +
                " time=" + time +
                ", state=" + state +
                '}';
    }
}
