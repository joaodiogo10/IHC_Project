package com.example.app.models;
import java.time.LocalDate;

public abstract class Frequency {
    public abstract boolean checkByDay(LocalDate date);
}
