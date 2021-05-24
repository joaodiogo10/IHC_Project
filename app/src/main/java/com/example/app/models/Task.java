package com.example.app.models;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;

public abstract class Task implements Cloneable{
    public enum State {
        DONE,
        PENDING
    }

    private LocalTime time;
    private State state;

    public Task(LocalTime time, State state) {
        this.time = time;
        this.state = state;
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

    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        return (Task) super.clone();
    }
}
