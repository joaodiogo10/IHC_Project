package com.example.app.classesAna;

public class Task {

    //notes missing
    private String type;
    private String name;
    private String day;
    private String hour;
    private int image;      //to save drawable image

    public Task(String type, String name, String day, String hour) {
        this.type = type;
        this.name = name;
        this.hour = hour;
        this.day = day;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDay() { return day; }

    public void setDay(String day) { this.day = day; }

    @Override
    public String toString() {
        return "Task{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", day='" + day + '\'' +
                ", hour='" + hour + '\'' +
                ", image=" + image +
                '}';
    }
}
