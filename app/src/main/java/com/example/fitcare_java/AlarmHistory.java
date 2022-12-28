package com.example.fitcare_java;

public class AlarmHistory {
    String taskName;
    String time;
    int id;

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
