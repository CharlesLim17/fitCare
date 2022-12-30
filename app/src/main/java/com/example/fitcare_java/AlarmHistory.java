package com.example.fitcare_java;

public class AlarmHistory {
    String taskName;
    String time;
    int id;

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getAm_pm() {
        return am_pm;
    }

    public void setAm_pm(int am_pm) {
        this.am_pm = am_pm;
    }

    int hour;
    int minute;
    int am_pm;

    public AlarmHistory(){

    }

    public AlarmHistory(String taskName, String time, int id) {
        this.taskName = taskName;
        this.time = time;
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
